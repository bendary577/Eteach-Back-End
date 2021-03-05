package com.eteach.eteach.service;
import com.eteach.eteach.dao.QuizDAO;
import com.eteach.eteach.event.newQuiz.QuizAddedEvent;
import com.eteach.eteach.event.newQuiz.QuizAddedEventPublisher;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
import com.eteach.eteach.model.compositeKeys.StudentQuizKey;
import com.eteach.eteach.model.course.Course;
import com.eteach.eteach.model.manyToManyRelations.StudentQuiz;
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
        publishQuizAddedEvent(quiz);
        return this.quizDAO.save(quiz);
    }

    //-------------------------- assign quiz to students ------------------------------------

    public void assignQuizToStudents(){
        //assignQuizToStudents(quiz);
    }

    //-------------------------- GET A SINGLE QUIZ -----------------------------------------
    public Quiz getQuiz(Long id){
        Quiz quiz =  this.quizDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", id));
        return quiz;
    }

    //---------------------------- UPDATE A SINGLE QUIZ -----------------------------------------
    public Quiz assignQuizToStudent(Long id, StudentQuiz studentQuiz){
        Quiz quiz = getQuiz(id);
        quiz.getStudents().add(studentQuiz);
        return quiz;
    }
    //-------------------------- GET ALL TEACHER QUIZZES -----------------------------------------
    public List<Quiz> getAllCourseQuizzes(Long courseId){
        return this.quizDAO.findQuizzesByCourseId(courseId);
    }

    //-------------------------- DELETE A SINGLE QUIZ -----------------------------------------
    public void deleteQuiz(Quiz quiz){
        this.quizDAO.delete(quiz);
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
    /*
    public void assignQuizToStudents(Quiz quiz){
        List<StudentAccount> students = (List<StudentAccount>) quiz.getCourse().getStudents();
        if(students != null){
            students.forEach(student -> StudentQuiz studentQuiz = new StudentQuiz();
                                        StudentQuizKey studentQuizKey = new StudentQuizKey();
                                        studentQuizKey.setStudentId(student.getId());
                                        studentQuizKey.setQuizId(quiz.getId());
                                        studentQuiz.setId(studentQuizKey);
                                        student.getQuizzes().add(studentQuiz));
        }else{
            System.out.println("students are null");
        }
    }
    */
}
