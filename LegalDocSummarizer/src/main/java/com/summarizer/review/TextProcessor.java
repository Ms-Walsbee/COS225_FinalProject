package com.summarizer.review;

public class TextProcessor {
    // Attributes
    private String status; // Current status of the document processing
    private String preprocessText; // Stores cleaned text
    private String summary; // Stores generated summary
    private int documentID; // Unique identifier for the document

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

    // Methods for document processing
    public void processDocument(String documentContent) {
        setStatus("Processing");
        try {
            String cleanedText = cleanText(documentContent);
            setPreprocessText(cleanedText);
            String summary = generateSummary(cleanedText);
            setSummary(summary);
            setStatus("Completed");
        } catch (Exception e) {
            setStatus("Error");
            System.out.println("Error processing document: " + e.getMessage());
        }
    }

    private String cleanText(String text) {
        return text.replaceAll("\\W", " ").toLowerCase();
    }

    private String generateSummary(String text) {
        return text.length() > 100 ? text.substring(0, 100) + "..." : text;
    }
}
