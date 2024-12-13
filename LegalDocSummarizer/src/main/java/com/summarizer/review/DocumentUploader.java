package com.summarizer.review;

import org.bson.Document;

/**
 * DocumentUploader is responsible for creating and managing document objects
 * for upload to MongoDB. Takes metadata for title, authors, overview, and
 * categories.
 */
public class DocumentUploader {
    private String title;
    private String authors;
    private String overview;
    private String categories;

    /**
     * Constructors/initialization for the documents metadata which will be used to
     * create the MongoDB document.
     * 
     * @param title      The title of the document.
     * @param authors    The authors of the document.
     * @param overview   The overview of the document.
     * @param categories The categories of the document.
     */

    public DocumentUploader(String title, String authors, String overview, String categories) {
        this.title = title;
        this.authors = authors;
        this.overview = overview;
        this.categories = categories;
    }

    /**
     * This creates and returns a MongoDB document object from the current database.
     * 
     * @return
     */
    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("authors", authors);
        document.append("overview", overview);
        document.append("categories", categories);

        return document;
    }
}
