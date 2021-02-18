package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
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
    @Column(nullable = false, length = 80)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 11)
    private String phone_number;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public User() { }

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


}
