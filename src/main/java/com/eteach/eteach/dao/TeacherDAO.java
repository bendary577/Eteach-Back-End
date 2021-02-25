package com.eteach.eteach.dao;


import com.eteach.eteach.model.account.TeacherAccount;
import org.springframework.stereotype.Repository;

@Repository("teacherdaoimp")
public interface TeacherDAO extends AccountDAO<TeacherAccount> {

}