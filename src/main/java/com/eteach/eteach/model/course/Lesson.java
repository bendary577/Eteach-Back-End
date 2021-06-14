package com.eteach.eteach.model.course;

import com.eteach.eteach.model.file.Material;
import com.eteach.eteach.model.file.Video;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="lessons")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"materials", "section"})
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
    private String video_duration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @OneToMany(mappedBy="lesson",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("materials")
    private List<Material> materials;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @JsonProperty("section")
    private Section section;

    public Lesson() { }

    public Lesson(@JsonProperty("id")Long id,
                  @JsonProperty("title") String title,
                  @JsonProperty("description")String description,
                  @JsonProperty("video_duration")String video_duration){
        this.id = id;
        this.title = title;
        this.description = description;
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

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
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

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @Transient
    public String getVideoDirPath() {
        if (video == null || this.id == null) return null;
        return "/lesson-videos/" + this.id + "/" + video;
    }

    @Transient
    public String getMaterialDirPath() {
        if (video == null || this.id == null) return null;
        return "/lesson-materials/" + this.id + "/" + materials;
    }

}
