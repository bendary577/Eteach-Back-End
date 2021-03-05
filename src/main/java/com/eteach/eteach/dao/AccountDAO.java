package com.eteach.eteach.dao;

import com.eteach.eteach.model.account.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO<T extends Account> extends PagingAndSortingRepository<T, Long> {
}