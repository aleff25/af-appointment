package pt.solutions.af.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pt.solutions.af.user.model.auth.AuthUserView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(AuthUserView user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API AF Solutions")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .withClaim("id", user.getId())
                    .withClaim("firstName", user.getFirstName())
                    .withClaim("lastName", user.getLastName())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error to generate token jwt", ex);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API AF Solutions")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new RuntimeException("Token JWT invalid or expired", ex);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.from(ZonedDateTime.now(ZoneId.of("UTC"))));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims.SUBJECT).asString();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims.EXPIRATION).asDate();
    }

    public Claim extractClaim(String token, String claimKey) {
        final var claims = extractAllClaims(token);
        return claims.get(claimKey);
    }

    private Map<String, Claim> extractAllClaims(String token) {
        return JWT.decode(token)
                .getClaims();
    }
}
