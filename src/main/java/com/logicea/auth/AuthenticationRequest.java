package com.logicea.auth;

public record AuthenticationRequest(
        String username,
        String password
) {

}
