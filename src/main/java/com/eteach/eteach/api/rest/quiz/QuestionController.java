package com.eteach.eteach.api.rest.quiz;

import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.quiz.Question;
import com.eteach.eteach.model.quiz.Quiz;
import com.eteach.eteach.service.FileService;
import com.eteach.eteach.service.QuestionService;
import com.eteach.eteach.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/questions", produces = "application/json;charset=UTF-8")
public class QuestionController {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final FileService fileService;

    @Autowired
    public QuestionController(QuestionService questionService,
                              QuizService quizService,
                              FileService fileService){
        this.questionService = questionService;
        this.quizService = quizService;
        this.fileService = fileService;
    }

    //----------------------------- CREATE A NEW QUESTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/{quizId}")
    public ResponseEntity<?> postQuestion(@PathVariable Long quizId,
                                          @Valid @RequestBody Question question,
                                          @RequestPart("image") @Valid @NotNull @NotEmpty MultipartFile image) throws IOException {
        Quiz quiz = quizService.getQuiz(quizId);
        quiz.getQuestions().add(question);
        question.setQuiz(quiz);
        //check if image is provided or not
        if(image != null){
            String contentType = image.getContentType();
            Long size = image.getSize();
            if (!fileService.validateVideoFile(contentType, size)) {
                return ResponseEntity.ok(new ApiResponse(HttpStatus.BAD_REQUEST, "thumbnail is not valid"));
            }
            String path = question.getImageDirPath();
            fileService.setPath(path);
            //create image object
            Image questionImage = fileService.createImageFile(image);
            //assign question to image and vice versa
            questionImage.setQuestion(question);
            question.setImage(questionImage);
            //save changes in database
            fileService.saveImage(questionImage);
        }
        questionService.saveQuestion(question);
        quizService.saveQuiz(quiz);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "question saved successfully"));
    }

    //----------------------------- GET ALL QUESTIONS ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{quizId}")
    public List<Question> getAllQuestions(@PathVariable Long quizId) {
        return questionService.getQuestionsByQuizId(quizId);
    }

    //----------------------------- GET A SINGLE QUESTION ---------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE','ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }

    //----------------------------- UPDATE A SINGLE QUESTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable(value = "id") Long id, @Valid @RequestBody Question newQuestion) {
        Question oldQuestion = questionService.getQuestion(id);
        if(oldQuestion == null){
            throw new ResourceNotFoundException("Question", "id", id);
        }
        return questionService.updateQuestion(oldQuestion, newQuestion);
    }

    //----------------------------- DELETE A SINGLE QUESTION ---------------------------------------------------
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        Question question = questionService.getQuestion(questionId);
        if(question == null){
            throw new ResourceNotFoundException("Question", "id", questionId);
        }
        questionService.deleteQuestion(question);
        return ResponseEntity.ok().build();
    }
}
