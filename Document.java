import java.util.Date;

//TODO
//Things to do for the doc summarizer.
//use a word count for the document reader?
//these words will be used in the summarization process
//use lowering text/tolowercase
//remove stop words
//use lemmatization or stemming to reduce words to their root form
//use tf-idf to calculate word importance
//use a summary length for the document reader
//use a summarization algorithm to generate a summary of the document from the abstract or introduction

public class Document {
    // Attributes
    private String fileName;
    private String fileType;
    private double fileSize;
    private Date uploadTime;
    // name
    // authors
    // tags
    // type
    // keywords

    // Constructors
    public Document(String fileName, String fileType, double fileSize, Date uploadTime) {
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
