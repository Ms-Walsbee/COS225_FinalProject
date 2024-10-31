import java.util.Date;

public class Document {
    // Attributes
    private String filename;
    private String filetype;
    private double fileSize;
    private Date uploadTime;

    // Constructors
    public Document(String filename, String filetype, double fileSize, Date uploadTime) {
        this.filename = filename;
        this.filetype = filetype;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
    }

    //TODO: Implement Getters and Setters
}
