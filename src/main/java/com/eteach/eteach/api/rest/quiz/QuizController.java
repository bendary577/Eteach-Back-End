package com.eteach.eteach.api.rest.quiz;

import com.eteach.eteach.enums.LevelOfDifficulty;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.request.dataRequest.quiz.AddQuizQuestionsRequest;
import com.eteach.eteach.http.request.dataRequest.quiz.AddQuizRequest;
import com.eteach.eteach.http.request.dataRequest.quiz.StudentQuizAnswersRequest;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.http.response.dataResponse.quiz.QuizAddedResponse;
import com.eteach.eteach.http.response.dataResponse.quiz.QuizzesResponse;
import com.eteach.eteach.http.response.dataResponse.quiz.StudentQuizScoreResponse;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
import com.eteach.eteach.model.quiz.Choice;
import com.eteach.eteach.model.quiz.Question;
import com.eteach.eteach.model.quiz.Quiz;
import com.eteach.eteach.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/quizzes", produces = "application/json;charset=UTF-8")
public class QuizController {

    private final QuizService quizService;
    private final CourseService courseService;
    private final AccountService accountService;
    private final QuestionService questionService;
    private final StudentQuizService studentQuizService;

    @Autowired
    public QuizController(QuizService quizService,
                          CourseService courseService,
                          AccountService accountService,
                          QuestionService questionService,
                          StudentQuizService studentQuizService){
        this.quizService = quizService;
        this.courseService =courseService;
        this.accountService = accountService;
        this.questionService = questionService;
        this.studentQuizService = studentQuizService;
    }

    //---------------------------- CREATE NEW QUIZ ------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/")
    public ResponseEntity<?> postQuiz(@Valid @RequestBody AddQuizRequest addQuizRequest) {
        System.out.println("################### in quiz 1");
        //get the quiz course and create quiz
        Course course = courseService.getCourse(addQuizRequest.getCourse_id());

        System.out.println("################### in quiz 2");
        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "we have an error find requested course"));
        }

        System.out.println("################### in quiz 3");
        //create quiz object
        Quiz quiz = new Quiz();
        quiz.setTitle(addQuizRequest.getTitle());
        quiz.setFinal_grade(addQuizRequest.getFinal_grade());
        quiz.setQuestions_number(addQuizRequest.getQuestions_number());
        for(LevelOfDifficulty levelOfDifficulty : LevelOfDifficulty.values()){
            if(addQuizRequest.getDifficulty_level().equals(levelOfDifficulty.toString())){
                quiz.setDifficulty_level(levelOfDifficulty);
            }
        }

        System.out.println("################### in quiz 4");
        //assign courses to quizzes
        quiz.setCourse(course);
        course.getQuizzes().add(quiz);

        System.out.println("################### in quiz 5");
        //update info in database
        quizService.createQuiz(quiz);
        courseService.saveCourse(course);

        System.out.println("################### in quiz 6");
        return ResponseEntity.ok(new QuizAddedResponse(HttpStatus.OK, "quiz " + quiz.getTitle() + " added successfully", quiz.getTitle(), quiz.getId(), quiz.getQuestions_number()));
    }

    //---------------------------- ADD QUIZ QUESTIONS ------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(value = "/{id}/", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<?> addQuizQuestion(@PathVariable Long id,
                                             @Valid @RequestPart("question") AddQuizQuestionsRequest addQuizQuestionsRequest,
                                             @RequestPart(name="img", required = false) MultipartFile questionImage) throws IOException {
        System.out.println("question text : " + addQuizQuestionsRequest.getText());
        System.out.println("question first choice : " + addQuizQuestionsRequest.getChoices().get(0));
        System.out.println("question answer number : " + addQuizQuestionsRequest.getAnswer());
        System.out.println("question has image : " + addQuizQuestionsRequest.isHasImage());
        System.out.println("image name " + questionImage.getOriginalFilename());
        Quiz quiz = this.quizService.getQuiz(id);
        if(quiz == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "quiz not found"));
        }

        Question question = new Question();
        //set question text
        question.setText(addQuizQuestionsRequest.getText());
        question.setRightAnswerNumber(addQuizQuestionsRequest.getAnswer());

        System.out.println("does request has image " + addQuizQuestionsRequest.isHasImage());

        //add image to question
        if(addQuizQuestionsRequest.isHasImage() && questionImage != null){
            int imageValidation = this.questionService.validateQuestionImage(questionImage);
            if(imageValidation == 1 ){
                return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "question image is not valid"));
            }
            Image image = this.questionService.createQuestionImageIbject(quiz.getId(), question.getId(), questionImage);
            question.setImage(image);
        }

        //add choices to question
        int choicesCounter = 0;
        addQuizQuestionsRequest.getChoices().forEach(choice -> {
            Choice questionChoice = new Choice();
            questionChoice.setText(choice);
            questionChoice.setNumber(choicesCounter);

            questionChoice.setQuestion(question);
            question.getChoices().add(questionChoice);
        });

        quiz.getQuestions().add(question);
        question.setQuiz(quiz);
        this.questionService.saveQuestion(question);
        this.quizService.saveQuiz(quiz);

        if(quiz.getQuestions_number() == addQuizQuestionsRequest.getQuestionNumber()){
            //launch notification event that quiz is added
        }

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "quiz " + quiz.getTitle() + "questions added successfully"));
    }



    //---------------------------- RETURN ALL COURSE QUIZZES WITH PAGINATION -----------------------
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/course/{courseId}/")
    public ResponseEntity<?> getAllCourseQuizzes(@PathVariable Long courseId) {
        Course course = this.courseService.getCourse(courseId);
        if(course == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "error in finding course"));
        }
        List<Quiz> quizzes = course.getQuizzes();
        if(quizzes == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "there is an error returning course quizzes"));
        }
        return ResponseEntity.ok((new QuizzesResponse(HttpStatus.OK, "course quizzes returned successfully", quizzes)));
    }

    //-------------------------- GET SINGLE QUIZ ------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_STUDENT')")
    @GetMapping("/{id}/")
    public Quiz getQuiz(@PathVariable(value = "id") Long id) {
        return quizService.getQuiz(id);
    }

    //----------------------- DELETE SINGLE QUIZ ------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteQuiz(@PathVariable(value = "id") Long id) {
        Quiz quiz = quizService.getQuiz(id);
        if (quiz == null) {
            throw new ResourceNotFoundException("Quiz", "id", id);
        }
        quizService.deleteQuiz(quiz);
        return ResponseEntity.ok().build();
    }


    //------------------------ AUTOMATIC MARKING QUIZ ----------------------------------
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/{id}/results/")
    public ResponseEntity<?> submitQuizQuestions(@PathVariable Long id, @RequestBody StudentQuizAnswersRequest studentQuizAnswersRequest){

        Quiz quiz = this.quizService.getQuiz(id);
        if(quiz == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we can't find requested quiz"));
        }

        StudentAccount studentAccount = this.accountService.getStudent(studentQuizAnswersRequest.getStudentId());
        if(studentAccount == null){
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND, "we can't find requested student"));
        }

        List<Integer> answers = studentQuizAnswersRequest.getStudentAnswers();
        int studentScore = this.quizService.markQuizAnswers(quiz, answers);

        StudentQuizKey studentQuizKey = new StudentQuizKey();
        studentQuizKey.setStudentId(studentAccount.getId());
        studentQuizKey.setQuizId(quiz.getId());

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(studentQuizKey);
        studentQuiz.setQuiz(quiz);
        studentQuiz.setStudent(studentAccount);
        studentQuiz.setSubmittedOn(new Date());
        studentQuiz.setScore(studentScore);

        quiz.setApplied_students_number(quiz.getApplied_students_number()+1);

        this.studentQuizService.saveStudentQuiz(studentQuiz);
        this.quizService.saveQuiz(quiz);

        return ResponseEntity.ok(new StudentQuizScoreResponse(HttpStatus.OK, "student score returned successfully",studentScore, quiz));
    }

}