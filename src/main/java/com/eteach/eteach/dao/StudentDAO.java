package com.eteach.eteach.dao;

import com.eteach.eteach.model.StudentAccount;
import org.springframework.stereotype.Repository;

@Repository("studentdaoimp")
public interface StudentDAO extends AccountDAO<StudentAccount> {

}