package com.eteach.eteach.enums;

public enum AccountType {
    STUDENT(0),
    TEACHER(1);

    private final int accountCode;

    AccountType(int accountCode){
        this.accountCode = accountCode;
    }

    public int getAccountCode() {
        return this.accountCode;
    }

}

    //AccountType accountType = AccountType.TEACHER;

    //System.out.println(accountType.getAccountCode());