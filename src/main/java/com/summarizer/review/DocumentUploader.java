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
    private String fileType;
    private double fileSize;
    private Date uploadTime;

    // Constructors
    public DocumentUploader(String fileName, String fileType, double fileSize, Date uploadTime) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
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
