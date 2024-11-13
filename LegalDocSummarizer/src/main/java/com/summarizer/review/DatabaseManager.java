package com.summarizer.review;

import java.util.Date;

//add databasemanager attributes getters and setters to a document java class.
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
    // Attributes
    private String summaries;
    private String userSummaries;
    private Date lastUpdated;
    private int documentID;

    // Getters
    public String getSummaries() {
        return summaries;
    }

    public String getUserSummaries() {
        return userSummaries;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public int getDocumentID() {
        return documentID;
    }

    // Setters
    public void setSummaries(String summaries) {

    }

    public void setUserSummaries(String usersummaries) {

    }

    public void setLastUpdated(Date lastUpdated) {

    }

    public void setDocumentID(int documentID) {

    }

    // Methods

    // Connection String
}
