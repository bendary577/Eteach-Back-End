package com.eteach.eteach.dao;

import com.eteach.eteach.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO<T extends Account> extends JpaRepository<T, Long> {
}