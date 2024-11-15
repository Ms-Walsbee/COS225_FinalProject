package com.summarizer.review;

import java.util.Date;
import org.bson.Document;

public class DocumentUploader {
    // Attributes
    private String title;
    private String authors;
    private Date publicationDate;

    // add more attributes here

    // Constructors/initialization for file details and checking formats
    public DocumentUploader(String title, String authors, Date publicationDate) {
        this.title = title;
        this.authors = authors;
        this.publicationDate = publicationDate;
    }

    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("authors", authors);
        document.append("publicationDate", publicationDate);
        return document;
    }
}
