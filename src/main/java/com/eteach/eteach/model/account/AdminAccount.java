package com.eteach.eteach.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="admin_accounts")
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("admin_account")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class AdminAccount extends Account implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    public AdminAccount() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
