package com.eteach.eteach.service;
import com.eteach.eteach.dao.QuizDAO;
import com.eteach.eteach.event.QuizAddedEvent;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class QuizService implements ApplicationEventPublisherAware {

    private QuizDAO quizDAO;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    public QuizService(QuizDAO quizDAO){

        this.quizDAO = quizDAO;
    }

    public Quiz createQuiz(Quiz quiz){
        Course course = quiz.getCourse();
        TeacherAccount teacher = course.getTeacher_account();
        String teacherName = teacher.getUser().getUsername();
        String courseName = course.getName();
        //PUBLISH EVENT THAT A NEW QUIZ IS ADDED
        publisher.publishEvent(new QuizAddedEvent(this, teacherName, courseName, quiz));
        return this.quizDAO.save(quiz);
    }

    public Quiz getQuiz(Long id){
        Quiz lesson =  this.quizDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", id));
        return lesson;
    }

    public List<Quiz> getAllQuizzes(){
        return this.quizDAO.findAll();
    }

    public void deleteQuiz(Quiz lesson){
        this.quizDAO.delete(lesson);
    }

    public void assignQuizToStudents(){

    }

}
