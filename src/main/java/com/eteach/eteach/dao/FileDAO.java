package com.eteach.eteach.dao;

import com.eteach.eteach.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("filedaoimp")
public interface FileDAO extends JpaRepository<File, Long> {
}
