package com.eteach.eteach.security.utils;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentAuthenticatedUser {

}


/*
* access the currently authenticated user in the controllers.
* */