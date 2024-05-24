package com.jp.issues_management.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jp.issues_management.entity.Image;
import com.jp.issues_management.repository.ImageRepository;

@Service
public class ImageService {
	private static final String IMAGE_DIRECTORY = "uploads/";
	
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;
    
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png");

    public Image saveImage(MultipartFile file) throws IOException {
    	String fileType = file.getContentType();
        if (!ALLOWED_CONTENT_TYPES.contains(fileType)) {
        	logger.error("Invalid file type: " + fileType);
            throw new IllegalArgumentException("Invalid file type. Only JPEG, JPG, and PNG are allowed.");
        }
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(IMAGE_DIRECTORY + fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Image image = new Image();
        image.setFileName(fileName);
        image.setFileType(file.getContentType());
        image.setFilePath(filePath.toString());

        logger.info("Image saved successfully: " + fileName);
        return imageRepository.save(image);
    }
}
