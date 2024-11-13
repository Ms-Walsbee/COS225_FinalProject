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

    // Constructors/initialization for file details and checking formats
    public DocumentUploader(String filePath) {
        this.filePath = filePath;
        this.fileName = new File(filePath).getName();
        this.fileSize = new File(filePath).length();
        this.fileFormat = getFileExtension(fileName);
        this.uploadTime = new Date();
    }

    // method to get the file content base on the format
    public void extractFileContent() throws IOException {
        if (!supportedFormats.contains(fileFormat.toLowerCase())) {
            errorMessage = "Unsupported Format: " + fileFormat;
            return;
        }
        if ("pdf".equals(fileFormat.toLowerCase())) {
            fileContent = extractPDFContent();
        } else if ("docx".equals(fileFormat.toLowerCase())) {
            fileContent = extractDocxContent();
        }
    }

    // placeholder for pdf content extraction
    private String extractPDFContent() {
        System.out.println("Reading PDF from..." + filePath);
        // Code to extract PDF content goes here
        return "PDF Content";
    }

    // placeholder for docx content extraction
    private String extractDocxContent() {
        System.out.println("Reading DOCX from..." + filePath);
        // Code to extract DOCX content goes here
        return "DOCX Content";
    }

    // helper method to get the file extension to check or handle files based on the
    // type.
    // to extract the file extension (the part after the last dot in the filename),
    // finds the position of the last dot.example: report.docx dot positon is at
    // lastIndexOf=4 so fileName.substring(4 + 1)starts at index 5 returning docx
    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        return lastIndexOf == -1 ? "" : fileName.substring(lastIndexOf + 1);
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
