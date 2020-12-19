package com.simplilearn.ecom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simplilearn.ecom.controller.FileHandlingController;
import com.simplilearn.ecom.exception.StorageException;

@Service
public class StorageService {

	@Value("${upload.path}")
	private String path;

	public void uploadFile(MultipartFile file) {

		// verify file is not empty
		if (file.isEmpty()) {
			throw new StorageException("Failed to Store a Empty File");
		}
		try {
			String fileName = file.getOriginalFilename();
			InputStream ins = file.getInputStream();
			Files.copy(ins, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			String message = "Failed to upload File :" + file.getName();
			throw new StorageException(message);
		}

	}

	public InputStreamResource convertFiletoStreamResponse(File file) {		
		try {
			return new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {			
			String message = "Failed to download file:" + file.getName() +" file does not exist";
			throw new StorageException(message);
		}
	}
}
