package com.eteach.eteach.api.rest.quiz;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import com.eteach.eteach.model.quiz.Quiz;
import com.eteach.eteach.service.AccountService;
import com.eteach.eteach.service.CourseService;
import com.eteach.eteach.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/api/v1/quizzes", produces = "application/json;charset=UTF-8")
public class QuizController {

    private final QuizService quizService;
    private final CourseService courseService;
    private final AccountService accountService;

    @Autowired
    public QuizController(QuizService quizService,
                          CourseService courseService,
                          AccountService accountService) {
        this.quizService = quizService;
        this.courseService =courseService;
        this.accountService = accountService;
    }

    //---------------------------- CREATE NEW QUIZ ------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/{id}")
    public ResponseEntity<?> postQuiz(@Valid @RequestBody Quiz quiz, @PathVariable Long courseId) {
        //GET THE QUIZ COURSE
        Course course = courseService.getCourse(courseId);
        Quiz savedQuiz = this.quizService.createQuiz(quiz);
        course.getQuizzes().add(savedQuiz);
        savedQuiz.setCourse(course);
        //--- can be changed so that student assign quiz for himself not automatically --------------
        List<StudentAccount> students = course.getStudents().stream()
                .map( student -> {
                    StudentQuiz studentQuiz = new StudentQuiz();
                    StudentQuizKey studentQuizKey = new StudentQuizKey();
                    studentQuizKey.setStudentId(student.getId());
                    studentQuizKey.setQuizId(savedQuiz.getId());
                    studentQuiz.setId(studentQuizKey);
                    quizService.assignQuizToStudent(savedQuiz.getId(), studentQuiz);
                    student.getQuizzes().add(studentQuiz);
                    accountService.saveStudent(student);
                    return student;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "quiz" + savedQuiz.getTitle() + "added successfully"));
    }

    //---------------------------- RETURN ALL COURSE QUIZZES WITH PAGINATION -----------------------
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/{courseId}")
    public List<Quiz> getAllCourseQuizzes(@PathVariable Long courseId) {
        return quizService.getAllCourseQuizzes(courseId);
    }

    //-------------------------- GET SINGLE QUIZ ------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable(value = "id") Long id) {
        return quizService.getQuiz(id);
    }

    //----------------------- DELETE SINGLE QUIZ ------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable(value = "id") Long id) {
        Quiz quiz = quizService.getQuiz(id);
        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz", "id", id);
        }
        quizService.deleteQuiz(quiz);
        return ResponseEntity.ok().build();
    }

    //------------------------ AUTOMATIC MARKING QUIZ ----------------------------------

}