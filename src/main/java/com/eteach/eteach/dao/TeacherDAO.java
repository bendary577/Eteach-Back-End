package com.eteach.eteach.dao;


import com.eteach.eteach.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("teacherdaoimp")
public interface TeacherDAO extends JpaRepository<Teacher, Long> {

}