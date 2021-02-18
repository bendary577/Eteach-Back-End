package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="material")
@EntityListeners(AuditingEntityListener.class)
public class Material implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String file_name;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String file_path;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String file_url;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String file_size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    public Material() { }

    public Material(@JsonProperty("id")Long id, @JsonProperty("file_name") String file_name,
                  @JsonProperty("file_path")String file_path, @JsonProperty("file_url") String file_url,
                  @JsonProperty("file_size")String file_size){
        this.id = id;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_size = file_size;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }
}
