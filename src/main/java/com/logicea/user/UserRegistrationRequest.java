package com.logicea.user;

public record UserRegistrationRequest(
        String email,
        String password,
        String role
) {

}
