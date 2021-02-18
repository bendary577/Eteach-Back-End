package com.eteach.eteach.service;

import com.eteach.eteach.dao.StudentDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Student;
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

    public Student createStudent(Student student){
        return this.studentDAO.save(student);
    }

    public Student updateStudent(Student oldStudent, Student newStudent){
        return this.studentDAO.save(newStudent);
    }

    public Student getStudent(Long id){
        Student student =  this.studentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return student;
    }

    public List<Student> getAllStudents(){
        return this.studentDAO.findAll();
    }

    public void deleteStudent(Student student){
        this.studentDAO.delete(student);
    }
}
