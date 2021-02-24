package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ApplicationUserService implements UserDetailsService {

    private final RealApplicationUserDao applicationUserDao;

    @Autowired
    public ApplicationUserService(RealApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    /*-------------------------------------- GET USER BY USERNAME -----------------------------------------*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = applicationUserDao
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username)));
        return ApplicationUser.create(user);

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


}
