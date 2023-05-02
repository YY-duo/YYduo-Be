package com.YYduo.KkuldongVarietyStore.security;

import com.YYduo.KkuldongVarietyStore.global.dto.LoginDto;
import com.YYduo.KkuldongVarietyStore.global.dto.RefreshTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginRequest) {
        Map<String, String> tokens = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String username = jwtUtil.getUsernameFromToken(refreshTokenRequest.getRefreshToken());

        if (!tokenStore.validateRefreshToken(username, refreshTokenRequest.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = jwtUtil.generateAccessToken(username);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);

        return ResponseEntity.ok(tokens);
    }
}
