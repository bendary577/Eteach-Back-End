package com.eteach.eteach.enums;

public enum Grade {
    FIRST_PRIMARY("first primary"),
    SECOND_PRIMARY("second primary"),
    THIRD_PRIMARY("third primary"),
    FOURTH_PRIMARY("fourth primary"),
    FIFTH_PRIMARY("fifth primary"),
    SIXTH_PRIMARY("sixth primary"),
    FIRST_PREPARATORY("first preparatory"),
    SECOND_PREPARATORY("second preparatory"),
    THIRD_PREPARATORY("third preparatory"),
    FIRST_SECONDARY("first secondary"),
    SECOND_SECONDARY("second secondary"),
    THIRD_SECONDARY("third secondary");

    private final String name;

    private Grade(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }

}
