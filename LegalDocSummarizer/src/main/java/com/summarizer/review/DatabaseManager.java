package com.summarizer.review;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
    private String connectionString, databaseName, collectionName;

    // Constructor with default connection string
    public DatabaseManager(String dbName, String collectionName) {
        this.connectionString = "mongodb+srv://garrettrumery:Ju7GunR2FJrZUu6K@cluster0.mu6h5.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    // Constructor with custom connection string
    public DatabaseManager(String connectionString, String dbName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    // Add a Document to the database collection
    public void addToDatabase(Document document) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            collection.insertOne(document);

            System.out.println("Document added to the database successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the document to the database: " + e.getMessage());
        }
    }

    // Create a collection in the database
    public void createCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.createCollection(collectionName);
            System.out.println("Collection created successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while creating the collection: " + e.getMessage());
        }
    }

    // Delete the collection from the database
    public void deleteCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.getCollection(collectionName).drop();
            System.out.println("Collection deleted successfully!");
        }
    }

    // Delete all documents in the collection
    public void deleteAllDocuments() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.deleteMany(new Document());
            System.out.println("All documents deleted successfully!");
        }
    }
}
