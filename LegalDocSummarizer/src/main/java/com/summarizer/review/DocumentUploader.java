package com.summarizer.review;

import org.bson.Document;

public class DocumentUploader {
    // Attributes
    private Float id;
    private String title;
    private String authors;
    private String overview;
    private String categories;

    // add more attributes here

    // Constructors/initialization for file details and checking formats
    public DocumentUploader(Float id, String title, String authors, String overview, String categories) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.overview = overview;
        this.categories = categories;
    }

    public Document getDocument() {
        Document document = new Document();
        document.append("id", id);
        document.append("title", title);
        document.append("authors", authors);
        document.append("overview", overview);
        document.append("categories", categories);
        return document;
    }
}
