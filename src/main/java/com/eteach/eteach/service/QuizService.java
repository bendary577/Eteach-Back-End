package com.eteach.eteach.service;
import com.eteach.eteach.dao.QuizDAO;
import com.eteach.eteach.event.newQuiz.QuizAddedEvent;
import com.eteach.eteach.event.newQuiz.QuizAddedEventPublisher;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.quiz.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class QuizService {

    private final QuizDAO quizDAO;
    private final QuizAddedEventPublisher quizAddedEventPublisher;

    @Autowired
    public QuizService(QuizDAO quizDAO,
                       QuizAddedEventPublisher quizAddedEventPublisher){

        this.quizDAO = quizDAO;
        this.quizAddedEventPublisher = quizAddedEventPublisher;
    }

    //-------------------------- CREATE NEW QUIZ -----------------------------------------
    public Quiz createQuiz(Quiz quiz){
        assignQuizToStudents(quiz);
        publishQuizAddedEvent(quiz);
        return this.quizDAO.save(quiz);
    }

    //-------------------------- GET A SINGLE QUIZ -----------------------------------------
    public Quiz getQuiz(Long id){
        Quiz lesson =  this.quizDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", id));
        return lesson;
    }

    //-------------------------- GET ALL TEACHER QUIZZES -----------------------------------------
    public List<Quiz> getAllCourseQuizzes(Long courseId){
        return this.quizDAO.findQuizzesByCourseId(courseId);
    }

    //-------------------------- DELETE A SINGLE QUIZ -----------------------------------------
    public void deleteQuiz(Quiz lesson){
        this.quizDAO.delete(lesson);
    }

    //-------------------------- PUBLISH QUIZ ADDED EVENT ------------------------------------
    public void publishQuizAddedEvent(Quiz quiz){
        Course course = quiz.getCourse();
        TeacherAccount teacher = course.getTeacher_account();
        String teacherName = teacher.getUser().getUsername();
        String courseName = course.getName();
        //PUBLISH EVENT THAT A NEW QUIZ IS ADDED
        quizAddedEventPublisher.publishQuizAddedEvent(new QuizAddedEvent(this, teacherName, courseName, quiz));
    }

    //------------------------ ASSIGN NEW QUIZ TO STUDENTS -----------------------------------
    public void assignQuizToStudents(Quiz quiz){
        List<StudentAccount> students = (List<StudentAccount>) quiz.getCourse().getStudents();
        if(students != null){
            students.forEach(student -> student.getQuizzes().add(quiz));
        }else{
            System.out.println("students are null");
        }
    }

}
