package com.eteach.eteach.security.userdetails;

import com.eteach.eteach.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;

    public ApplicationUserService(@Autowired @Qualifier("fakeuserdaoimp") ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    @Transactional
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
