package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="rating_instance")
@EntityListeners(AuditingEntityListener.class)
public class RatingInstance implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private float value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    public RatingInstance() { }

    public RatingInstance(@JsonProperty("id")Long id, @JsonProperty("value") float value){

    }

}
