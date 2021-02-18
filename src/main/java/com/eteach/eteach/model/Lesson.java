package com.eteach.eteach.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="lesson")
@EntityListeners(AuditingEntityListener.class)
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String description;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String video;

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @NotBlank
    @Column(nullable = false, length = 100)
    private String video_duration;

    @OneToMany(mappedBy="lesson",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Material> materials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    public Lesson() { }

    public Lesson(@JsonProperty("id")Long id, @JsonProperty("title") String title,
                  @JsonProperty("description")String description, @JsonProperty("video") String video,
                  @JsonProperty("video_duration")String video_duration){
        this.id = id;
        this.title = title;
        this.description = description;
        this.video = video;
        this.video_duration = video_duration;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
