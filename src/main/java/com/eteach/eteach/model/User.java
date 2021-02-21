package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import com.eteach.eteach.security.rolesandpermessions.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Entity
@Table(name="user",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String first_name;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String second_name;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String username;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 11)
    private String phone_number;

    private boolean enabled;

    private boolean tokenExpired;

    private Role role;

    public User(){ }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String first_name, String second_name,String username, String email, String password) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(@JsonProperty("id")Long id, @JsonProperty("first_name") String first_name,
                @JsonProperty("second_name") String second_name, @JsonProperty("email") String email,
                @JsonProperty("password") String password, @JsonProperty("phone_number") String phone_number){
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled(){
        return this.enabled;
    }

    public void setEnabled(boolean value){
        this.enabled=value;
    }

    public Set<SimpleGrantedAuthority> getPermissions(){ return this.role.getGrantedAuthorities();}
}
