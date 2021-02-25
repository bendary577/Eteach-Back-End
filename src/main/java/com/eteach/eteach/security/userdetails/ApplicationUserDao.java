package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.account.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> findApplicationUserByUsername(String username);
    Optional<User> findUserByUsername(String username);
    UserDetails save(User user);
 /*
    Optional<ApplicationUser> findUserById(Long id);

    @Query("SELECT u FROM User u WHERE u.email = :username")
    Optional<User> getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email")String email);
 */
}

