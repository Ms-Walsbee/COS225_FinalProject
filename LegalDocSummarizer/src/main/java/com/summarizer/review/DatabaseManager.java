package com.summarizer.review;

import java.util.Date;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
    //Attributes
    private String summaries;
    private String userSummaries;
    private Date lastUpdated;
    private int documentID; 

    //Constructors
    public DatabaseManager(String summaries, String userSummaries, Date lastUpdated, int documentID){
        this.summaries = summaries;
        this.userSummaries = userSummaries;
        this.lastUpdated = lastUpdated;
        this.documentID = documentID;
    }

    //Getters
    public String getSummaries(){
        return summaries;
    }

    public String getUserSummaries(){
        return userSummaries;
    }

    public Date getLastUpdated(){
        return lastUpdated;
    }

    public int getDocumentID(){
        return documentID;
    }

    //Setters
    public void setSummaries(String summaries){

    }

    public void setUserSummaries(String usersummaries){

    }

    public void setLastUpdated(Date lastUpdated){

    }

    public void setDocumentID(int documentID){

    }
    
    //Methods


    //Connection String
}
