package com.logicea.auth;

import com.logicea.user.User;
import com.logicea.jwt.JWTUtil;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager
            , JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        System.out.println(request.username() + " " + request.password());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

            User principal = (User) authentication.getPrincipal();
            System.out.println(principal);
            String token = jwtUtil.issueToken(principal.getUsername(), principal.getRole());
            return new AuthenticationResponse(token);


    }

}
