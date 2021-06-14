package com.eteach.eteach.http.request.dataRequest.quiz;

public class AddQuizRequest {

    private String title;
    private String difficulty_level;
    private int final_grade;
    private int questions_number;
    private Long course_id;

    public AddQuizRequest(){}

    public AddQuizRequest(String title, String difficulty_level, int final_grade, int questions_number, Long course_id) {
        this.title = title;
        this.difficulty_level = difficulty_level;
        this.final_grade = final_grade;
        this.questions_number = questions_number;
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(String difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public int getFinal_grade() {
        return final_grade;
    }

    public void setFinal_grade(int final_grade) {
        this.final_grade = final_grade;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public int getQuestions_number() {
        return questions_number;
    }

    public void setQuestions_number(int questions_number) {
        this.questions_number = questions_number;
    }
}
