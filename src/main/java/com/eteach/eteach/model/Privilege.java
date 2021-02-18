package com.eteach.eteach.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "privilege")
@EntityListeners(AuditingEntityListener.class)
public class Privilege implements Serializable {

    @Id
    @Column(name = "privilege_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


}