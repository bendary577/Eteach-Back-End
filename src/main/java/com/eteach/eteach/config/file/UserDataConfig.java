package com.eteach.eteach.config.file;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDataConfig {

    @Value("${user.data.directoryPath}")
    private String directoryPath;

    @Value("${user.data.coursesDirectory}")
    private String coursesDirectory;

    @Value("${user.data.accountsDirectory}")
    private String accountsDirectory;

    public UserDataConfig(){}

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getCoursesDirectory() {
        return coursesDirectory;
    }

    public void setCoursesDirectory(String coursesDirectory) {
        this.coursesDirectory = coursesDirectory;
    }

    public String getAccountsDirectory() {
        return accountsDirectory;
    }

    public void setAccountsDirectory(String accountsDirectory) {
        this.accountsDirectory = accountsDirectory;
    }
}
