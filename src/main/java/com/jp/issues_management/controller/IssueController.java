package com.jp.issues_management.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jp.issues_management.entity.Image;
import com.jp.issues_management.entity.Issue;
import com.jp.issues_management.service.ImageService;
import com.jp.issues_management.service.IssueService;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

	@Autowired
    private IssueService issueService;
	
	@Autowired
    private ImageService imageService;
	
	private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @PostMapping
    public ResponseEntity<?> reportIssue(@RequestParam("email") String email,
                                             @RequestParam("category") String category,
                                             @RequestParam("description") String description,
                                             @RequestParam("image") MultipartFile imageFile) {
    	
    	logger.info("Email: " + email);
    	logger.info("Category: " + category);
    	logger.info("Description: " + description);
    	logger.info("Image File: " + imageFile.getOriginalFilename());
        
        Issue issue = new Issue();
        issue.setEmail(email);
        issue.setCategory(category);
        issue.setDescription(description);
        
        if (!imageFile.isEmpty()) {
        	try {
                Image savedImage = imageService.saveImage(imageFile);
                issue.setImage(savedImage);
                logger.info("Image uploaded for issue by: " + email);
            } catch (IOException e) {
            	logger.error("Error saving image for issue by: " + email, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } catch (IllegalArgumentException e) {
            	logger.warn("Invalid file type uploaded by: " + email);
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        
        Issue savedIssue = issueService.saveIssue(issue);
        logger.info("Issue reported successfully by: " + email);
        return ResponseEntity.ok(savedIssue);
    }

    @GetMapping
    public List<Issue> getIssuesByEmail(@RequestParam("email") String email) {
        return issueService.getIssuesByEmail(email);
    }
    
}
