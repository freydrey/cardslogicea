package com.logicea.card;

public enum CardStatusEnum {
    TODO("To Do"),
    INPROGRESS("In Progress"),
    DONE("Done");

    private String name;

    CardStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}