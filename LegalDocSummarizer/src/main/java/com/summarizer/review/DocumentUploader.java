package com.summarizer.review;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

public class DocumentUploader {
    // Attributes
    private String filePath;
    private String fileFormat;
    private String fileContent;
    private String fileName;
    private double fileSize;
    private Date uploadTime;
    private String errorMessage;

    // Supported formats
    private static final List<String> supportedFormats = Arrays.asList("pdf", "docx");

    // Constructors
    public DocumentUploader(String filePath) {
        this.filePath = filePath;
        this.fileName = new File(filePath).getName();
        this.fileSize = new File(filePath).length();
        this.fileFormat = getFileExtension(fileName);
        this.uploadTime = new Date();

        if (!supportedFormats.contains(fileFormat.toLowerCase())) {
            errorMessage = "Unsupported Format: " + fileFormat;
            System.out.println("Error: " + errorMessage);
        }
    }

    // ToDo add Getters/Setters
    public String getFileContent() {
        return fileContent;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public double getFileSize() {
        return fileSize;
    }

    public Date getUploadTime() {
        return uploadTime;
    }
}
