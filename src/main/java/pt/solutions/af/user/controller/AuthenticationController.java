package pt.solutions.af.user.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.solutions.af.infra.security.TokenJWTForm;
import pt.solutions.af.infra.security.TokenService;
import pt.solutions.af.user.application.dto.AuthenticationForm;
import pt.solutions.af.user.model.AuthUserView;


@RestController
@RequestMapping(path = "/login")
@AllArgsConstructor
public class AuthenticationController {


    private AuthenticationManager manager;
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<TokenJWTForm> login(@Valid @RequestBody AuthenticationForm form) {
        var authToken = new UsernamePasswordAuthenticationToken(form.login(), form.password());
        var authentication = manager.authenticate(authToken);

        var principal = (AuthUserView) authentication.getPrincipal();
        var token = tokenService.generateToken(principal);

        return ResponseEntity.ok(new TokenJWTForm(token));
    }
}
