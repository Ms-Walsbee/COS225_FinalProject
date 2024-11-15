package com.summarizer.review;

import org.bson.Document;

public class DocumentUploader {
    // Attributes
    private String title;
    private String authors;
    // add more attributes here

    // Constructors/initialization for file details and checking formats
    public DocumentUploader(String title, String authors) {
        this.title = title;
        this.authors = authors;
    }

    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("authors", authors);
        // add document appends for abstract, categories.
        return document;
    }
}
