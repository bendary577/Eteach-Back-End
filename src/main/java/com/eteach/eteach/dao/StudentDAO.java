package com.eteach.eteach.dao;

import com.eteach.eteach.model.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("studentdaoimp")
public interface StudentDAO extends JpaRepository<StudentAccount, Long> {

}