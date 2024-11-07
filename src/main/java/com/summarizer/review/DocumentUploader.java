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

    // Getters
    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public double getFileSize() {
        return fileSize;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    // Setters
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    // TODO: Add methods
}
