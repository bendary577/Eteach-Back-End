package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.exception.UpdatePasswordException;
import com.eteach.eteach.http.request.authRequest.UpdatePasswordRequest;
import com.eteach.eteach.model.account.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ApplicationUserService implements UserDetailsService {

    private final RealApplicationUserDao applicationUserDao;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserService(RealApplicationUserDao applicationUserDao, PasswordEncoder passwordEncoder) {
        this.applicationUserDao = applicationUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    /*-------------------------------------- LOAD USER BY USERNAME -----------------------------------------*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = applicationUserDao
                .findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
        return ApplicationUser.create(user);

    }

    /*-------------------------------------- GET USER BY USERNAME -----------------------------------------*/
    @Transactional
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = applicationUserDao.findUserByUsername(username);
        return user.orElse(null);
    }

    /*-------------------------------------- GET USER BY EMAIL -----------------------------------------*/
    @Transactional
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = applicationUserDao.findUserByEmail(email);
        return user.orElse(null);
    }

    /*-------------------------------------- CREATE NEW USER -----------------------------------------*/
    public User createUser(User user) {
        return applicationUserDao.save(user);

    }

    /*-------------------------------------- GET APPLICATION USER BY USERNAME -----------------------------------------*/
   /*
    @Transactional
    public Optional<ApplicationUser> findApplicationUserByUsername(String username){
        return applicationUserDao.findApplicationUserByUsername(username);
    }
    */
    /*-------------------------------------- IS USER EXISTS BY USERNAME -----------------------------------------*/
    public boolean existsByUsername(String username){
        return false;
    }

    public boolean existsByEmail(String email){
        return false;
    }

    /*-------------------------------------- GET USER BY ID -----------------------------------------*/
    /*
    public UserDetails loadUserById(Long id ) throws UsernameNotFoundException {
        return applicationUserDao
                .findUserById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %d not found", id))
                );
    }
    */
    /*--------------------------- UPDATE USER PASSWORD ---------------------------------*/
    public User updateUserPassword(User user, UpdatePasswordRequest updatePasswordRequest){
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = updatePasswordRequest.getNewPassword();
        if (!currentPasswordMatches(oldPassword, newPassword)) {
            throw new UpdatePasswordException(user.getEmail(), "Invalid current password");
        }
         newPassword = passwordEncoder.encode(newPassword);
         user.setPassword(newPassword);
         applicationUserDao.save(user);
         return user;
    }

    /*------------------------ CHECK IF OLD & NEW PASSWORDS MATCH -----------------------*/
    private Boolean currentPasswordMatches(String oldPassword, String newPassword) {
        return passwordEncoder.matches(oldPassword, newPassword);
    }

}
