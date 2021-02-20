package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    @Autowired
    public ApplicationUserService(@Qualifier("fakeuserdaoimp") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserDao
                .findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username))
                );
    }

    public User createUser(User user){
        applicationUserDao.save(user);
        return user;
    }

    public boolean existsByUsername(String username){
        return true;
    }

    public boolean existsByEmail(String email){
        return true;
    }


    public UserDetails loadUserById(Long id ) throws UsernameNotFoundException {
        return applicationUserDao
                .findUserById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %d not found", id))
                );
    }

}
