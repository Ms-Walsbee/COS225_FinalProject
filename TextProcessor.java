public class TextProcessor {
    // Attributes
    private String status;            // Current status of the document processing
    private String preprocessText;    // Stores cleaned text
    private String summary;           // Stores generated summary
    private int documentID;           // Unique identifier for the document

    // Constructor
    public TextProcessor(int documentID) {
        this.documentID = documentID;
        this.status = "Uploaded"; // Initial status
        this.preprocessText = "";
        this.summary = "";
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreprocessText() {
        return preprocessText;
    }

    public void setPreprocessText(String preprocessText) {
        this.preprocessText = preprocessText;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getDocumentID() {
        return documentID;
    }

 
}
