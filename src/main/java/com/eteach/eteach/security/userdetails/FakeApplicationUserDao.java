package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.eteach.eteach.security.rolesandpermessions.Role.*;

@Repository("fakeuserdaoimp")
public abstract class FakeApplicationUserDao implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*-------------------------------------- get users by their user name -----------------------------------------*/
    @Override
    public Optional<ApplicationUser> findUserByUsername(String username) {
        return getFakeApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    /*-------------------------------------- get users by their user name -----------------------------------------*/
    /*
    @Override
    public Optional<ApplicationUser> findUserById(Long id) {
        return new ApplicationUser();

    }
    */

    /*-------------------------------------- get some fake users -----------------------------------------*/
    private List<ApplicationUser> getFakeApplicationUsers() {
        User user = new User("annasmith", passwordEncoder.encode("password"));
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        user,
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        user,
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        user,
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return applicationUsers;
    }

}
