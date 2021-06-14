package com.eteach.eteach.enums;

public enum Rating {
    BAD(1),
    ACCEPTABLE(2),
    GOOD(3),
    VERY_GOOD(4),
    EXCELLENT(5);

    private final int ratingCode;

    Rating(int ratingCode){
        this.ratingCode = ratingCode;
    }

    public int getRatingCode() {
        return this.ratingCode;
    }
}
