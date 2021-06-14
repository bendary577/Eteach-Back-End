package com.eteach.eteach.utils;

import com.eteach.eteach.model.file.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class FileStorageUtil {

    @Value("${server.compression.mime-types}")
    private List<String> contentVideos;

    @Value("${image.available.mime-types}")
    private List<String> contentImages;

    @Value("${server.compression.mime-types}")
    private List<String> contentMaterials;

    private final long TXT_SIZE_LIMIT = 200 * 1024 * 1024;


    @Autowired
    public FileStorageUtil(){}


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
        long VIDEO_SIZE_LIMIT = 200 * 1024 * 1024;
        return contentVideos.contains(contentType) && size <= VIDEO_SIZE_LIMIT;
    }

    /*----------------------------- VALIDATE IMAGE FILE -----------------------------------------------------*/
    public boolean validateImageFile(String contentType, Long size) {
        long IMAGE_SIZE_LIMIT = 200 * 1024 * 1024;
        return contentImages.contains(contentType) && size <= IMAGE_SIZE_LIMIT;
    }
    /*----------------------------- VALIDATE IMAGE FILE -----------------------------------------------------*/
    public boolean validateMaterialFile(String contentType, Long size) {
        long PDF_SIZE_LIMIT = 200 * 1024 * 1024;
        return contentMaterials.contains(contentType) && size <= PDF_SIZE_LIMIT;
    }

    public byte[] readImageFromPath(Image image) throws IOException {
         System.out.println("path is " + image.getPath()+File.separator+image.getName());
         BufferedImage imageOnDisk = ImageIO.read(new File(File.separator+image.getPath()+File.separator+image.getName()));
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         ImageIO.write(imageOnDisk, image.getExtension(), byteArrayOutputStream);
         return byteArrayOutputStream.toByteArray();
    }

    // compress the image bytes before storing it in the database
    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    // uncompress the file bytes before returning it to the angular application
    public byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
        }
        return outputStream.toByteArray();
    }

}
