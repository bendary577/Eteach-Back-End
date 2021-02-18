package com.eteach.eteach.service;

import com.eteach.eteach.dao.TeacherDAO;
import com.eteach.eteach.exception.ResourceNotFoundException;
import com.eteach.eteach.model.Teacher;
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

    public Teacher createTeacher(Teacher teacher){
        return this.teacherDAO.save(teacher);
    }

    public Teacher updateTeacher(Teacher oldTeacher, Teacher newTeacher){
        return this.teacherDAO.save(newTeacher);
    }

    public Teacher getTeacher(Long id){
        Teacher teacher =  this.teacherDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));
        return teacher;
    }

    public List<Teacher> getAllTeachers(){
        return this.teacherDAO.findAll();
    }

    public void deleteTeacher(Teacher teacher){
        this.teacherDAO.delete(teacher);
    }
}
