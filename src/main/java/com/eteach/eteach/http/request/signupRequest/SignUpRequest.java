package com.eteach.eteach.http.request.signupRequest;

import com.eteach.eteach.enums.AccountType;

import javax.validation.constraints.*;
import java.io.Serializable;

public class SignUpRequest implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 6, max = 20)
    private String phone_number;

    @NotNull
    private int accountType;

    public SignUpRequest(){}

    public SignUpRequest(@NotBlank @Size(min = 3, max = 15) String username, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(min = 6, max = 20) String password, @NotBlank @Size(min = 6, max = 20) String phone_number, @NotNull int accountType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.accountType = accountType;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}