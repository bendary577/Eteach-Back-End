package com.eteach.eteach.utils;

import com.eteach.eteach.config.UserDataConfig;
import com.eteach.eteach.enums.FileTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileStorageUtil {

    @Value("${server.compression.mime-types}")
    private List<String> contentVideos;

    private final long VIDEO_SIZE_LIMIT = 200 * 1024 * 1024;
    private final long IMAGE_SIZE_LIMIT = 200 * 1024 * 1024;
    private final long PDF_SIZE_LIMIT = 200 * 1024 * 1024;
    private final long TXT_SIZE_LIMIT = 200 * 1024 * 1024;

    private final UserDataConfig userDataConfig;

    @Autowired
    public FileStorageUtil(UserDataConfig userDataConfig){
        this.userDataConfig = userDataConfig;
    }


    /*----------------------------- RETURN FILE FROM FILE SYSTEM ---------------------------------------*/
    public byte[] getFile(String path) throws IOException {
        Path filepath = Paths.get(path);
        File file = new File(path);
        byte[] fileByteArray = null;
        if (file.exists()) {
            fileByteArray = Files.readAllBytes(filepath);
        }
        return fileByteArray;
    }

    /*----------------------------- VALIDATE VIDEO FILE -----------------------------------------------------*/
    public boolean validateVideoFile(String contentType, Long size) {
        if (!contentVideos.contains(contentType) || size > VIDEO_SIZE_LIMIT) {
            return false;
        }
            return true;
    }

    /*----------------------------- VALIDATE IMAGE FILE -----------------------------------------------------*/
    public boolean validateImageFile(String contentType, Long size) {
        if (!contentVideos.contains(contentType) || size > IMAGE_SIZE_LIMIT) {
            return false;
        }
        return true;
    }

}
