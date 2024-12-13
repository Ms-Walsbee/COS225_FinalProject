package com.summarizer.review;

import java.util.Date;

/**
 * This class contains information such as the summaries, user summaries,
 * last update time, and document ID. The {@code Document} class is designed to store both 
 * system-generated summaries, typically representing the main content or analysis of the 
 * content, and user-generated summaries, which might reflect user input or annotations. 
 * It also tracks when the document was last updated, providing crucial information about 
 * the document's recency. The document ID serves as a unique identifier for the document, 
 * ensuring that each document can be reliably referenced within a system.
 */
public class Document {
      // Attributes
    private String summaries;
    private String userSummaries;
    private Date lastUpdated;
    private int documentID;

    // Getters

    /**
     * Gets the summaries generated by the machine learning algorithm.
     * 
     * @return a {@code String} containing the machine learning summaries for the document.
     */
    public String getSummaries() {
        return summaries;
    }

    /**
     * Gets the user-generated summaries of the document.
     * 
     * @return a {@code String} containing the user summaries for the document.
     */
    public String getUserSummaries() {
        return userSummaries;
    }

    /**
     * Gets the last update time of the document.
     * 
     * @return a {@code Date} object representing when the document was last updated.
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Gets the unique document ID.
     * 
     * @return an {@code int} representing the document's ID.
     */
    public int getDocumentID() {
        return documentID;
    }

    // Setters

    /**
     * Sets the summaries for the document.
     * 
     * @param summaries a {@code String} containing the new summaries for the document.
     */
    public void setSummaries(String summaries) {
    }

    /**
     * Sets the user-generated summaries for the document.
     * 
     * @param userSummaries a {@code String} containing the new user summaries for the document.
     */
    public void setUserSummaries(String usersummaries) {
    }

    /**
     * Sets the last update time for the document.
     * 
     * @param lastUpdated a {@code Date} representing the new last update time for the document.
     */
    public void setLastUpdated(Date lastUpdated) {
    }

    /**
     * Sets the unique document ID.
     * 
     * @param documentID an {@code int} representing the new document ID.
     */
    public void setDocumentID(int documentID) {
    }
}
