package com.eteach.eteach.service;

import com.eteach.eteach.dao.FileDAO;
import com.eteach.eteach.dao.ImageDAO;
import com.eteach.eteach.dao.VideoDAO;
import com.eteach.eteach.exception.FileStorageException;
import com.eteach.eteach.model.file.File;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.file.Video;
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
    private VideoDAO videoDAO;
    private ImageDAO imageDAO;
    private FileStorageUtil fileStorageUtil;
    private String path;

    @Autowired
    public FileService(FileDAO fileDAO,
                       VideoDAO videoDAO,
                       ImageDAO imageDAO,
                       FileStorageUtil fileStorageUtil){
        this.fileDAO = fileDAO;
        this.videoDAO = videoDAO;
        this.imageDAO = imageDAO;
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
            throw new FileStorageException("Could not save file: " + fileName);
        }
    }

    /*--------------------------- GET FILE FROM FILESYSTEM ------------------------------------------*/
    public byte[] getFileFromFileSystem(String path) throws IOException {
        return fileStorageUtil.getFile(path);
    }

    /*--------------------------- CREATE NEW Video OBJECT IN DATABASE ----------------------------------*/
    @Transactional
    public Video createVideoFile(MultipartFile multipartFile) throws IOException {
        Video video = new Video();
        String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
        video.setName(fileName);
        //file.setExtension(FilenameUtils.getExtension(file.getName()));
        //SAVE FILE IN FILE SYSTEM
        saveFileInFileSystem(fileName, multipartFile);
        video.setPath(path);
        return videoDAO.save(video);
    }
    /*--------------------------- CREATE NEW Image OBJECT IN DATABASE ----------------------------------*/
    @Transactional
    public Image createImageFile(MultipartFile multipartFile) throws IOException {
        Image image = new Image();
        String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
        image.setName(fileName);
        //file.setExtension(FilenameUtils.getExtension(file.getName()));
        //SAVE FILE IN FILE SYSTEM
        saveFileInFileSystem(fileName, multipartFile);
        image.setPath(path);
        return imageDAO.save(image);
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
