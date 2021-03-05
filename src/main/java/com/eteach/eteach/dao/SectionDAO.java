package com.eteach.eteach.dao;

import com.eteach.eteach.model.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionDAO extends JpaRepository<Section, Long> {
    List<Section> findSectionsByCourseId(Long courseId);
}
