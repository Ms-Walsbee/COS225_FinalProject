//Document uploader
package com.summarizer.review;

import org.bson.Document;

public class DocumentUploader {
    // Attributes
    private String title;
    private String authors;
    private String overview;
    private String categories;

    // Constructors/initialization for file details and checking formats
    public DocumentUploader(String title, String authors, String overview, String categories) {
        this.title = title;
        this.authors = authors;
        this.overview = overview;
        this.categories = categories;
    }

    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("authors", authors);
        document.append("overview", overview);
        document.append("categories", categories);

        return document;
    }
}
