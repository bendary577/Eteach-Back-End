package com.eteach.eteach.model;;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="section")
@EntityListeners(AuditingEntityListener.class)
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;

    @OneToMany(mappedBy="section",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Lesson> lessons;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    public Section() { }

    public Section(@JsonProperty("id")Long id, @JsonProperty("title") String title){
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
