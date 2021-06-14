package com.eteach.eteach.service;

import com.eteach.eteach.dao.StudentCourseDao;
import com.eteach.eteach.model.compositeKeys.StudentCourseKey;
import com.eteach.eteach.model.manyToManyRelations.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    private final StudentCourseDao studentCourseDao;

    @Autowired
    public StudentCourseService(StudentCourseDao studentCourseDao) {
        this.studentCourseDao = studentCourseDao;
    }

    public StudentCourse saveStudentCourse(StudentCourse studentCourse) {
        return this.studentCourseDao.save(studentCourse);
    }

    public StudentCourse updateStudentCourse(StudentCourse oldStudentCourse, StudentCourse newStudentCourse) {
        return this.studentCourseDao.save(newStudentCourse);
    }

    public StudentCourse getStudentCourse(Long studentId, Long courseId) {
        return this.studentCourseDao.findStudentCourseById(studentId, courseId);
    }

    public List<StudentCourse> getAllStudentCourses() {
        return this.studentCourseDao.findAll();
    }

    public void deleteStudentCourse(StudentCourse studentCourse) {
        this.studentCourseDao.delete(studentCourse);
    }
}
