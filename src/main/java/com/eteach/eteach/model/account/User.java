package com.eteach.eteach.model.account;

import com.eteach.eteach.model.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import com.eteach.eteach.security.rolesandpermessions.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Entity
@Table(name="users",
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

    @NotBlank(message = "username cannot be empty")
    @Column(length = 50)
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Column(length = 80)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "password cannot be empty")
    @Column(length = 80)
    private String password;

    @NotBlank(message = "phone number cannot be empty")
    @Column(length = 11)
    private String phone_number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnoreProperties("user")
    private Account account;

    private boolean enabled;

    private Role role;

    public User(){ }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(@JsonProperty("username") String username,
                @JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("phone_number") String phone_number){
        this.username = username;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
