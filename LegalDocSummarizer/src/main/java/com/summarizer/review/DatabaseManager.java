package com.summarizer.review;

//add databasemanager attributes getters and setters to a document java class.
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
    private String connectionString, databaseName, collectionName;

    // Connection String
    public DatabaseManager(String dbName, String collectionName) {
        this.connectionString = "mongodb+srv://garrettrumery:Cos225group7@cos225.mu6h5.mongodb.net/?retryWrites=true&w=majority&appName=COS225";
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    public DatabaseManager(String connectionString, String dbName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    // add a method to addToDatabase
    public void addToDatabase(Document document) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

        }
    }
}
