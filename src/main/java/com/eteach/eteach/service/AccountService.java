package com.eteach.eteach.service;

import com.eteach.eteach.dao.AccountDAO;
import com.eteach.eteach.dao.StudentDAO;
import com.eteach.eteach.dao.TeacherDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.account.StudentAccount;
import com.eteach.eteach.model.account.TeacherAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountDAO accountDAO;
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO,
                          @Qualifier("studentdaoimp")StudentDAO studentDAO,
                          @Qualifier("teacherdaoimp")TeacherDAO teacherDAO){
        this.accountDAO = accountDAO;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
    }

    /*-------------------------------------- STUDENT ACCOUNT ----------------------------------------------*/
    public StudentAccount createStudent(StudentAccount studentAccount){
        return this.studentDAO.save(studentAccount);
    }

    public StudentAccount updateStudent(StudentAccount oldStudentAccount, StudentAccount newStudentAccount){
        return this.studentDAO.save(newStudentAccount);
    }

    public StudentAccount getStudent(Long id){
        StudentAccount studentAccount =  this.studentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return studentAccount;
    }

    public List<StudentAccount> getAllStudents(){
        return this.studentDAO.findAll();
    }

    public void deleteStudent(StudentAccount studentAccount){
        this.studentDAO.delete(studentAccount);
    }

    /*-------------------------------------- TEACHER ACCOUNT ----------------------------------------------*/
    public TeacherAccount createTeacher(TeacherAccount teacherAccount){return this.teacherDAO.save(teacherAccount);}

    public TeacherAccount updateTeacher(TeacherAccount oldTeacherAccount, TeacherAccount newTeacherAccount){
        return this.teacherDAO.save(newTeacherAccount);
    }

    public TeacherAccount getTeacher(Long id){
        TeacherAccount teacherAccount =  this.teacherDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return teacherAccount;
    }

    public List<TeacherAccount> getAllTeachers(){
        return this.teacherDAO.findAll();
    }

    public void deleteTeacher(TeacherAccount teacherAccount){
        this.teacherDAO.delete(teacherAccount);
    }

}
