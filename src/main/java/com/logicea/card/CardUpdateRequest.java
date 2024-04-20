package com.logicea.card;

public record CardUpdateRequest(
        String name,
        String description,
        String color,
        String status
) {

}
