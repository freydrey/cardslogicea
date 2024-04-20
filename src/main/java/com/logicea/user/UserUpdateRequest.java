package com.logicea.user;

public record UserUpdateRequest(
        String email,
        String password,
        String role
) {

}
