package com.eteach.eteach.service;

import com.eteach.eteach.dao.FileDAO;
import com.eteach.eteach.dao.ImageDAO;
import com.eteach.eteach.dao.MaterialDAO;
import com.eteach.eteach.dao.VideoDAO;
import com.eteach.eteach.exception.FileStorageException;
import com.eteach.eteach.model.file.File;
import com.eteach.eteach.model.file.Image;
import com.eteach.eteach.model.file.Material;
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

    private final FileDAO fileDAO;
    private final VideoDAO videoDAO;
    private final ImageDAO imageDAO;
    private final MaterialDAO materialDAO;
    private FileStorageUtil fileStorageUtil;

    @Autowired
    public FileService(FileDAO fileDAO,
                       VideoDAO videoDAO,
                       ImageDAO imageDAO,
                       MaterialDAO materialDAO,
                       FileStorageUtil fileStorageUtil){
        this.fileDAO = fileDAO;
        this.videoDAO = videoDAO;
        this.imageDAO = imageDAO;
        this.materialDAO = materialDAO;
        this.fileStorageUtil = fileStorageUtil;
    }

    /*--------------------------- STORE FILE IN FILESYSTEM ------------------------------------------*/
    public void saveFileInFileSystem(String fileName,
                                     MultipartFile multipartFile,
                                     Path uploadPathDir) throws IOException {

        if (!Files.exists(uploadPathDir)) {
            System.out.println("path doesn't exist");
            Files.createDirectories(uploadPathDir);
        }
        System.out.println("path exists");
        try{
            byte[] bytes = multipartFile.getBytes();
            Path filePath = uploadPathDir.resolve(fileName);
            System.out.println("path is :" + filePath);
            Files.write(filePath, bytes);
        } catch (IOException ioe) {
            System.out.println("couldn't save file in file system");
            throw new FileStorageException("Could not save file: " + fileName);
        }
    }

    /*--------------------------- GET FILE FROM FILESYSTEM ------------------------------------------*/
    public byte[] getFileFromFileSystem(String path) throws IOException {
        return fileStorageUtil.getFile(path);
    }

    /*--------------------------- CREATE NEW Video OBJECT IN DATABASE ----------------------------------*/
    @Transactional
    public Video createVideoFile(MultipartFile multipartFile, Path path) throws IOException {
        Video video = new Video();
        String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
        video.setName(fileName);
        //file.setExtension(FilenameUtils.getExtension(file.getName()));
        //SAVE FILE IN FILE SYSTEM
        saveFileInFileSystem(fileName, multipartFile, path);
        video.setPath(path.toString());
        return videoDAO.save(video);
    }
    /*--------------------------- CREATE NEW Image OBJECT IN DATABASE ----------------------------------*/
    @Transactional
    public Image createImageFile(MultipartFile multipartFile, Path path) throws IOException {
        System.out.println("in create image file");
        Image image = new Image();
        String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
        System.out.println("filename is :" + fileName);
        image.setName(fileName);
        image.setPath(path.toString());
        //file.setExtension(FilenameUtils.getExtension(file.getName()));
        //SAVE FILE IN FILE SYSTEM
        saveFileInFileSystem(fileName, multipartFile, path);
        System.out.println("image saved in file system");
        return imageDAO.save(image);
    }

    /*--------------------------- CREATE NEW Image OBJECT IN DATABASE ----------------------------------*/
    @Transactional
    public Material createMaterialFile(MultipartFile multipartFile, Path path) throws IOException {
        Material material = new Material();
        String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
        material.setName(fileName);
        //file.setExtension(FilenameUtils.getExtension(file.getName()));
        //SAVE FILE IN FILE SYSTEM
        saveFileInFileSystem(fileName, multipartFile, path);
        material.setPath(path.toString());
        return materialDAO.save(material);
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

    /*----------------------------- VALIDATE MATERIAL FILE -----------------------------------------------------*/
    public boolean validateMaterialFile(String contentType, Long size) {
        return fileStorageUtil.validateMaterialFile(contentType, size);
    }

    /*------------------------------- SAVE IMAGE ----------------------------------------------*/
    public void saveImage(Image image){
        this.imageDAO.save(image);
    }

    /*------------------------------- SAVE VIDEO ----------------------------------------------*/
    public void saveVideo(Video video){
        this.videoDAO.save(video);
    }

}
