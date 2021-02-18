package com.eteach.eteach.dao;


import com.eteach.eteach.model.TeacherAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("teacherdaoimp")
public interface TeacherDAO extends JpaRepository<TeacherAccount, Long> {

}