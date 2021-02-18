package com.eteach.eteach.service;

import com.eteach.eteach.dao.TeacherDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.TeacherAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherService {

    private TeacherDAO teacherDAO;

    @Autowired
    public TeacherService(@Qualifier("teacherdaoimp") TeacherDAO teacherDAO){
        this.teacherDAO = teacherDAO;
    }

    public TeacherAccount createTeacher(TeacherAccount teacherAccount){
        return this.teacherDAO.save(teacherAccount);
    }

    public TeacherAccount updateTeacher(TeacherAccount oldTeacherAccount, TeacherAccount newTeacherAccount){
        return this.teacherDAO.save(newTeacherAccount);
    }

    public TeacherAccount getTeacher(Long id){
        TeacherAccount teacherAccount =  this.teacherDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));
        return teacherAccount;
    }

    public List<TeacherAccount> getAllTeachers(){
        return this.teacherDAO.findAll();
    }

    public void deleteTeacher(TeacherAccount teacherAccount){
        this.teacherDAO.delete(teacherAccount);
    }
}
