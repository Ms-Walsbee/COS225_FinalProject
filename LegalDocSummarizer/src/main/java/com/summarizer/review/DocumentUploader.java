//Document uploader
package com.summarizer.review;

import org.bson.Document;

public class DocumentUploader {
    // Attributes
    private String title;
    private String authors;
    private String overview;
    private String categories;
    private String content; // New attribute for document content

    // Constructors/initialization for file details and checking formats
    public DocumentUploader(String title, String authors, String overview, String categories, String content) {
        this.title = title;
        this.authors = authors;
        this.overview = overview;
        this.categories = categories;
        this.content = content; // Initialize content
    }

    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("authors", authors);
        document.append("overview", overview);
        document.append("categories", categories);
        document.append("content", content); // Add content to the document

        return document;
    }
}
