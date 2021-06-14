package com.eteach.eteach.dao;

import com.eteach.eteach.model.course.WhatWillStudentLearn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatWillStudentLearnDao extends JpaRepository<WhatWillStudentLearn, Long> {

}
