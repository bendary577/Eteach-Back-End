package com.eteach.eteach.security.userdetails;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("realuserdaoimp")
public abstract class RealApplicationUserDao implements ApplicationUserDao {
    /*
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username){

    }
    */
}
