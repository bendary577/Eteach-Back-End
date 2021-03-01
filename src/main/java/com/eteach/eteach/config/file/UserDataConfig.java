package com.eteach.eteach.config.file;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user.data")
public class UserDataConfig {

    private String directoryPath;
    private String coursesDirectory;
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
