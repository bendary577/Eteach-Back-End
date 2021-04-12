package com.eteach.eteach.enums;

public enum LevelOfDifficulty {
    EASY("Basic"),
    MEDIUM("Intermediate"),
    ADVANCED("Advanced");

    private final String name;

     private LevelOfDifficulty(String name){
        this.name = name;
    }

    public String toString(){return this.name;}
}
