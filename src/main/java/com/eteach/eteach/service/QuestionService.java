package com.eteach.eteach.service;

import com.eteach.eteach.dao.QuestionDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.http.response.ApiResponse;
import com.eteach.eteach.model.file.File;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.file.Video;
import com.eteach.eteach.model.quiz.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class QuestionService {

    private QuestionDAO questionDAO;
    private FileService fileService;

    @Autowired
    public QuestionService(QuestionDAO questionDAO, FileService fileService){
        this.questionDAO = questionDAO;
        this.fileService = fileService;
    }

    public Question saveQuestion(Question question){
        return this.questionDAO.save(question);
    }

    public Question updateQuestion(Question oldQuestion, Question newQuestion){
        return this.questionDAO.save(newQuestion);
    }

    public Question getQuestion(Long id){
        Question question =  this.questionDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        return question;
    }

    public List<Question> getQuestionsByQuizId(Long quizId){
        return this.questionDAO.findQuestionsByQuizId(quizId);
    }

   /*
   public List<Question> getAllQuestions(){
        return this.questionDAO.findAll();
    }
    */

    public void deleteQuestion(Question question){
        this.questionDAO.delete(question);
    }


    //--------------------- VALIDATE QUESTION IMAGE -------------------------------

    public int validateQuestionImage(MultipartFile image){
        String contentType = image.getContentType();
        Long size = image.getSize();
        if (!fileService.validateVideoFile(contentType, size)) {
            return 1;
        }
        return 0;
    }

    //------------------------- CREATE QUESTION IMAGE OBJECT ------------------------------

    public Image createQuestionImageIbject(Long quizId, Long questionId, MultipartFile image) throws IOException {
        Path path = Paths.get("D:", "projects","E-Teach - Front End","dist","assets", "images", "quizzes", quizId.toString(), "questions", questionId.toString());
        return fileService.createImageFile(image, path);
    }
}
