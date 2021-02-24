package com.eteach.eteach.model;


import com.eteach.eteach.config.UserDataConfig;
import com.eteach.eteach.enums.Rating;
import com.eteach.eteach.enums.Subjects;
import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="courses")
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

    @NotBlank
    @Column(nullable = false, length = 80)
    private String duration;

    @Column(nullable = false, length = 11)
    private String intro;

    @Column(nullable = false, length = 50)
    private Grade grade;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String what_yow_will_learn;

    @Column(nullable = false)
    private int students_number;

    @Column(nullable = false)
    private int lessons_number;

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

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Section> sections;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trailer_video_id", referencedColumnName = "id")
    private Video trailer_video;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "id")
    private Image thumbnail;

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<RatingInstance> ratings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private TeacherAccount teacher_account;

    @ManyToMany(mappedBy = "courses")
    private Set<StudentAccount> students = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "course_tag",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    Set<Tag> tags = new HashSet<>();

    private transient UserDataConfig userDataConfig = null;

    public Course() { }

    @Autowired
    public Course(UserDataConfig userDataConfig) {
        this.userDataConfig = userDataConfig;
    }

    public Course(@JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("price") float price,
                  @JsonProperty("duration") String duration,
                  @JsonProperty("intro") String intro,
                  @JsonProperty("grade") Grade grade,
                  @JsonProperty("what_yow_will_learn") String what_yow_will_learn,
                  @JsonProperty("students_number") int students_number,
                  @JsonProperty("difficulty_level") LevelOfDifficulty difficulty_level,
                  @JsonProperty("rating") Rating rating,
                  @JsonProperty("rating_number") int ratings_number,
                  @JsonProperty("category") Subjects category){
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.intro = intro;
        this.grade = grade;
        this.what_yow_will_learn = what_yow_will_learn;
        this.students_number = students_number;
        this.difficulty_level = difficulty_level;
        this.rating = rating;
        this.ratings_number = ratings_number;
        this.difficulty_level = difficulty_level;
        this.category = category;

    }

    /*------------------------------------ GETTERS AND SETTERS ---------------------------------------*/
    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public TeacherAccount getTeacher() {
        return teacher_account;
    }

    public void setTeacher(TeacherAccount teacherAccount) {
        this.teacher_account = teacherAccount;
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

    public Video getTrailer_video() {
        return trailer_video;
    }

    public void setTrailer_video(Video trailer_video) {
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

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
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

    public TeacherAccount getTeacher_account() {
        return teacher_account;
    }

    public void setTeacher_account(TeacherAccount teacher_account) {
        this.teacher_account = teacher_account;
    }

    public Set<StudentAccount> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentAccount> students) {
        this.students = students;
    }

    public int getLessons_number() {
        return lessons_number;
    }

    public void setLessons_number(int lessons_number) {
        this.lessons_number = lessons_number;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        tags = tags;
    }

    /* ---------------------------- PATHS TO PERSIST DATA IN FILESYSTEM ----------------------------------------*/
    @Transient
    public String getTrailerVideoDirPath() {
        String coursePath = prepareCoursePathes();
        String trailerVideoPath = new StringBuilder(coursePath)
                .append("trailer")
                .append(java.io.File.separator).toString();
        return trailerVideoPath;
    }

    @Transient
    public String getThumbnailDirPath() {
        String coursePath = prepareCoursePathes();
        String imagepath = new StringBuilder(coursePath)
                .append("images")
                .append(java.io.File.separator).toString();
        return imagepath;
    }

    private String prepareCoursePathes(){
        String path = new StringBuilder(userDataConfig.getCoursesDirectory())
                .append(java.io.File.separator)
                .append(id)
                .append(java.io.File.separator).toString();
        return path;
    }



}
