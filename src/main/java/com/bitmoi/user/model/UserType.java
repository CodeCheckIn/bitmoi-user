package com.bitmoi.user.model;

public enum UserType {
    STUDENT(1), TEACHER(2), ADVISOR(9);

    UserType(int typeValue) {
        this.value = typeValue;
    }

    private final int value;

    public int getValue() {
        return this.value;
    }
}
