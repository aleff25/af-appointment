package pt.solutions.af.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.infra.security.TokenJWTForm;
import pt.solutions.af.infra.security.TokenService;
import pt.solutions.af.user.model.auth.AuthUserView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@RestController
@RequestMapping(path = "/login")
@AllArgsConstructor
public class AuthenticationController {


    private AuthenticationManager manager;
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<TokenJWTForm> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        var base64Credentials = authorizationHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        var credentials = new String(decodedBytes, StandardCharsets.UTF_8);

        var parts = credentials.split(":", 2);
        var login = parts[0].substring(1);
        var password = parts[1];
        password = password.substring(0, password.length() - 1);

        var authToken = new UsernamePasswordAuthenticationToken(login, password);
        var authentication = manager.authenticate(authToken);

        var principal = (AuthUserView) authentication.getPrincipal();
        var token = tokenService.generateToken(principal);

        return ResponseEntity.ok(new TokenJWTForm(token));
    }
}
