package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userdaoimp")
public interface ApplicationUserDao extends JpaRepository<User, Long> {
    Optional<ApplicationUser> findUserByUsername(String username);

    Optional<ApplicationUser> findUserById(Long id);


    @Query("SELECT u FROM User u WHERE u.email = :username")
    Optional<User> getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email")String email);
}
