package com.eteach.eteach.model;


import com.eteach.eteach.enums.Rating;
import com.eteach.eteach.enums.Subjects;
import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="course")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String description;

    @NotNull(message = "Please enter price")
    @Column(nullable = false)
    private float price;

    @Column(nullable = false, length = 11)
    private String trailer_video;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String duration;

    @Column(nullable = false, length = 11)
    private String intro;

    @Column(nullable = false, length = 100)
    private String image;

    @Column(nullable = false, length = 50)
    private Grade grade;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String what_yow_will_learn;

    @Column(nullable = false, length = 100)
    private int students_number;

    @Column(nullable = false, length = 100)
    private LevelOfDifficulty difficulty_level;

    @Column(nullable = false, length = 100)
    private Rating rating;

    @Column(nullable = false, length = 100)
    private int ratings_number;

    @Column(nullable = false, length = 100)
    private Subjects category;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated_at;

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Section> sections;

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<RatingInstance> ratings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private TeacherAccount teacherAccount;

    public Course() { }

    public Course(@JsonProperty("id")Long id, @JsonProperty("name") String name,
                  @JsonProperty("description") String description, @JsonProperty("price") float price,
                  @JsonProperty("trailer_video") String trailer_video, @JsonProperty("duration") String duration,
                  @JsonProperty("intro") String intro, @JsonProperty("image")String image,
                  @JsonProperty("grade") Grade grade, @JsonProperty("what_yow_will_learn") String what_yow_will_learn,
                  @JsonProperty("students_number") int students_number, @JsonProperty("difficulty_level") LevelOfDifficulty difficulty_level,
                  @JsonProperty("rating") Rating rating, @JsonProperty("rating_number") int ratings_number,
                  @JsonProperty("category") Subjects category){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.trailer_video = trailer_video;
        this.duration = duration;
        this.intro = intro;
        this.image = image;
        this.grade = grade;
        this.what_yow_will_learn = what_yow_will_learn;
        this.students_number = students_number;
        this.difficulty_level = difficulty_level;
        this.rating = rating;
        this.ratings_number = ratings_number;
        this.difficulty_level = difficulty_level;
        this.category = category;

    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public TeacherAccount getTeacher() {
        return teacherAccount;
    }

    public void setTeacher(TeacherAccount teacherAccount) {
        this.teacherAccount = teacherAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTrailer_video() {
        return trailer_video;
    }

    public void setTrailer_video(String trailer_video) {
        this.trailer_video = trailer_video;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<RatingInstance> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingInstance> ratings) {
        this.ratings = ratings;
    }

    public String getWhat_yow_will_learn() {
        return what_yow_will_learn;
    }

    public void setWhat_yow_will_learn(String what_yow_will_learn) {
        this.what_yow_will_learn = what_yow_will_learn;
    }

    public int getStudents_number() {
        return students_number;
    }

    public void setStudents_number(int students_number) {
        this.students_number = students_number;
    }

    public LevelOfDifficulty getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(LevelOfDifficulty difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getRatings_number() {
        return ratings_number;
    }

    public void setRatings_number(int ratings_number) {
        this.ratings_number = ratings_number;
    }

    public Subjects getCategory() {
        return category;
    }

    public void setCategory(Subjects category) {
        this.category = category;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Transient
    public String getTrailerVideoPath() {
        if (trailer_video == null || id == null) return null;
        return "/videos/" + id + "/" + trailer_video;
    }


}
