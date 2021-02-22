package com.eteach.eteach.service;

import com.eteach.eteach.dao.FileDAO;
import com.eteach.eteach.exception.FileStorageException;
import com.eteach.eteach.model.File;
import com.eteach.eteach.utils.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FileService {


    private FileDAO fileDAO;
    private FileStorageUtil fileStorageUtil;
    private String path;

    @Autowired
    public FileService(FileDAO fileDAO, FileStorageUtil fileStorageUtil){
        this.fileDAO = fileDAO;
        this.fileStorageUtil = fileStorageUtil;
    }

    /*--------------------------- STORE FILE IN FILESYSTEM ------------------------------------------*/
    public void saveFileInFileSystem(String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(getPath());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new FileStorageException("Could not save image file: " + fileName);
        }
    }

    /*--------------------------- GET FILE FROM FILESYSTEM ------------------------------------------*/
    public byte[] getFileFromFileSystem(String path) throws IOException {
        return fileStorageUtil.getFile(path);
    }

    /*--------------------------- CREATE NEW FILE OBJECT IN DATABASE ----------------------------------*/
    @Transactional
    public File createFile(MultipartFile multipartFile) throws IOException {
        File file = new File();
        String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
        file.setName(fileName);
        //file.setExtension(FilenameUtils.getExtension(file.getName()));
        //SAVE FILE IN FILE SYSTEM
        saveFileInFileSystem(fileName, multipartFile);
        file.setPath(path);
        return fileDAO.save(file);
    }

    /*----------------------------- GET FILE OBJECT FROM DATABASE -------------------------------------*/
    public Optional<File> findById(Long id) {
        return fileDAO.findById(id);
    }

    /*----------------------------- VALIDATE VIDEO FILE -----------------------------------------------------*/
    public boolean validateVideoFile(String contentType, Long size) {
        return fileStorageUtil.validateVideoFile(contentType, size);
    }
    /*----------------------------- VALIDATE IMAGE FILE -----------------------------------------------------*/
    public boolean validateImageFile(String contentType, Long size) {
        return fileStorageUtil.validateImageFile(contentType, size);
    }
    /*------------------------------- GETTERS AND SETTERS ----------------------------------------------*/
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
