package com.eteach.eteach.dao;

import com.eteach.eteach.model.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDAO<T extends File> extends JpaRepository<T, Long> {

}
