package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import static com.eteach.eteach.security.rolesandpermessions.Role.*;

@Repository("fakeuserdaoimp")
public class FakeApplicationUserDao implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;
    private List<ApplicationUser> applicationUsers;

    @Autowired
    public FakeApplicationUserDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        initializeApplicationUserList();
    }

    /*------------------------------- GET APPLICATION USER BY USERNAME ----------------------------------*/
    @Override
    public Optional<ApplicationUser> findApplicationUserByUsername(String username) {
        return getFakeApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    /*------------------------------------ GET USER BY USERNAME -----------------------------------------*/
    @Override
    public Optional<User> findUserByUsername(String username) {
        return getFakeUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    /*------------------------------------ CREATE NEW APPLICATION USER -----------------------------------*/
    @Override
    public UserDetails save(User user) {
        initializeApplicationUserList();
        ApplicationUser appUser = new ApplicationUser(
                                    user,
                                    user.getRole().getGrantedAuthorities(),
                                    true,
                                    true,
                                    true,
                                    true
                            );
        this.applicationUsers.add(appUser);
        loopOnUsersList();
        return appUser;
    }

    /*----------------------------------------- INITIALIZE APPLICATION USERS LIST ----------------------------------------------------------------*/

    private void initializeApplicationUserList(){
        User user1 = new User("mohamed", passwordEncoder.encode("password"));
        System.out.println(user1.getPassword());
        User user2 = new User("ahmed", passwordEncoder.encode("password"));
        User user3 = new User("mistafa", passwordEncoder.encode("password"));
         this.applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        user1,
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        user2,
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        user3,
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
    }

    /*-------------------------------------- LOOP ON APPLICATION USERS -----------------------------------------*/
    public void loopOnUsersList(){
        for(int i=0; i<getFakeApplicationUsers().size();i++){
            System.out.println("user = " + getFakeApplicationUsers().get(i).getUsername());
        }
    }

    /*-------------------------------------- GET SOME FAKE APPLICATION USERS -----------------------------------------*/
    private List<ApplicationUser> getFakeApplicationUsers() {
        return applicationUsers;
    }

    /*-------------------------------------- GET SOME FAKE USERS -----------------------------------------*/
    private List<User> getFakeUsers() {
        List<User> users = Lists.newArrayList(
                new User("mohamed",
                        "bendary",
                        "bendary577",
                        "bendary@gmail.com",
                        "password"
                ),
                new User(
                        "mohamed",
                        "bendary",
                        "bendary577",
                        "bendary@gmail.com",
                        "password"
                ),
                new User(
                        "mohamed",
                        "bendary",
                        "bendary577",
                        "bendary@gmail.com",
                        "password"
                )
        );

        return users;
    }
}
