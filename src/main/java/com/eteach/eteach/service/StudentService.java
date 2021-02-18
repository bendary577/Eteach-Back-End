package com.eteach.eteach.service;

import com.eteach.eteach.dao.StudentDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.StudentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentDAO studentDAO;

    @Autowired
    public StudentService(@Qualifier("studentdaoimp") StudentDAO studentDAO){
        this.studentDAO = studentDAO;
    }

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
}
