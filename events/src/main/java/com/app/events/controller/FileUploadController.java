package com.app.events.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class FileUploadController {

    @Autowired
    private ServletContext servletContext;

	public static final String uploadingDir = System.getProperty("user.dir") + "/uploads/";

	@PostMapping("/api/files")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        try {
            String path = uploadingDir + file.getOriginalFilename();
            File newFile = new File(path);
            file.transferTo(newFile);

            return ResponseEntity.status(HttpStatus.OK).body(path);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        

	}
}