package com.eteach.eteach.model.course;


import com.eteach.eteach.enums.Grade;
import com.eteach.eteach.enums.LevelOfDifficulty;
import com.eteach.eteach.enums.Rating;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.file.Video;
import com.eteach.eteach.model.manyToManyRelations.CourseRating;
import com.eteach.eteach.model.manyToManyRelations.CourseRequest;
import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import com.eteach.eteach.model.quiz.Quiz;
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
import java.util.*;


@Entity
@Table(name="courses")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "tags", "quizzes", "students", "sections", "ratings", "whatWillStudentLearn", "course_request"},
                      allowGetters = true)
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

    @Column(nullable = false, length = 50)
    private Grade grade;

    @Column
    private int students_number;

    @Column
    private int lessons_number;

    @Column(nullable = false, length = 100)
    private LevelOfDifficulty difficulty_level;

    @Column
    private int ratings_number;

    @Column
    private Rating rating;

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
    @JsonProperty("whatWillStudentLearn")
    private List<WhatWillStudentLearn> what_you_will_learn;

    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("sections")
    private List<Section> sections;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trailer_video_id", referencedColumnName = "id")
    private Video trailer_video;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "id")
    private Image thumbnail;

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("course_request")
    private List<CourseRequest> courseRequest;

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("ratings")
    private List<CourseRating> ratings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private TeacherAccount teacher_account;

    @OneToMany(mappedBy = "course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("students")
    private Set<StudentCourse> students = new HashSet<>();

    @OneToMany(mappedBy="course",cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonProperty("quizzes")
    private List<Quiz> quizzes;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "course_tag",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    @JsonProperty("tags")
    Set<Tag> tags = new HashSet<>();

   /*
    private transient UserDataConfig userDataConfig = null;

    @Autowired
    public Course(UserDataConfig userDataConfig) {
        this.userDataConfig = userDataConfig;
    }
    */

    public Course() {
        this.what_you_will_learn = new ArrayList<>();
    }

    public Course(@JsonProperty("name") String name,
                  @JsonProperty("description") String description,
                  @JsonProperty("price") float price,
                  @JsonProperty("grade") Grade grade,
                  @JsonProperty("difficulty_level") LevelOfDifficulty difficulty_level){
        this.name = name;
        this.description = description;
        this.price = price;
        this.grade = grade;
        this.difficulty_level = difficulty_level;
    }

    /*------------------------------------ GETTERS AND SETTERS ---------------------------------------*/
    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public TeacherAccount getTeacherAccount() {
        return teacher_account;
    }

    public void setTeacherAccount(TeacherAccount teacherAccount) {
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

    public List<CourseRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<CourseRating> ratings) {
        this.ratings = ratings;
    }

    public List<WhatWillStudentLearn> getWhat_you_will_learn() {
        return what_you_will_learn;
    }

    public void setWhat_you_will_learn(List<WhatWillStudentLearn> what_yow_will_learn) {
        this.what_you_will_learn = what_yow_will_learn;
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

    public int getRatings_number() {
        return ratings_number;
    }

    public void setRatings_number(int ratings_number) {
        this.ratings_number = ratings_number;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public Set<StudentCourse> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentCourse> students) {
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

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<CourseRequest> getCourseRequest() {
        return courseRequest;
    }

    public void setCourseRequest(List<CourseRequest> courseRequest) {
        this.courseRequest = courseRequest;
    }

    /* ---------------------------- PATHS TO PERSIST DATA IN FILESYSTEM ----------------------------------------*/
    /*
    @Transient
    @JsonIgnore
    public String getTrailerVideoDirPath() {
        String coursePath = prepareCoursePaths();
        String trailerVideoPath = new StringBuilder(coursePath)
                .append("trailer")
                .append(java.io.File.separator).toString();
        return trailerVideoPath;
    }

    @Transient
    @JsonIgnore
    public String getThumbnailDirPath() {
        String coursePath = prepareCoursePaths();
        String imagepath = new StringBuilder(coursePath)
                .append("images")
                .append(java.io.File.separator).toString();
        return imagepath;
    }

    @Transient
    @JsonIgnore
    private String prepareCoursePaths(){
        String path = new StringBuilder(userDataConfig.getCoursesDirectory())
                .append(java.io.File.separator)
                .append(id)
                .append(java.io.File.separator).toString();
        return path;
    }
    */


}
