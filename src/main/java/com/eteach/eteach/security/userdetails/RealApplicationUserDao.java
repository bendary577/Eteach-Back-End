package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RealApplicationUserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
