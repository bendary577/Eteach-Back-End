package com.eteach.eteach.dao;

import com.eteach.eteach.model.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionDAO extends JpaRepository<Section, Long> {
}
