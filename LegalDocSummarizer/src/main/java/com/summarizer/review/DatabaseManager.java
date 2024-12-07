package com.summarizer.review;

import com.mongodb.client.result.InsertOneResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
    private String connectionString, databaseName, collectionName;

    // Constructor with default connection string
    public DatabaseManager(String dbName, String collectionName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(
                "src/main/resources/connection.txt"))) {
            connectionString = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading connection string from 'connection.txt'");
            e.printStackTrace();
        }
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    // Add a Document to the database collection
    public InsertOneResult addToDatabase(Document document) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            return collection.insertOne(document);

            // System.out.println("Document added to the database successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the document to the database: " + e.getMessage());
            return null;
        }
    }

    // Create a collection in the database
    public void createCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.createCollection(collectionName);
            System.out.print("Collection created successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while creating the collection: " + e.getMessage());
        }
    }

    // Delete the collection from the database
    public void deleteCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            database.getCollection(collectionName).drop();
            // System.out.println("Collection deleted successfully!");
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

    public List<Document> getDocumentsByAuthor(String author) {
        List<Document> documents = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document("authors", new Document("$regex", author).append("$options", "i"));
            for (Document doc : collection.find(query)) {
                documents.add(doc);
            }

        } catch (Exception e) {
            // Print an error message if an exception occurs
            System.out.println("An error occurred while retrieving the document: " + e.getMessage());
        }
        return documents;

    }

    public List<Document> getDocumentsByTitle(String title) {

        List<Document> documents = new ArrayList<>();

        // Use a try-with-resources statement to ensure the MongoClient is closed
        // automatically
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            // Connect to the specified database
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            // Access the specified collection within the database
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Create a query to find a document
            Document query = new Document("title", new Document("$regex", title).append("$options", "i"));
            // Document doc = collection.find(query).first();
            for (Document document : collection.find(query)) {
                documents.add(document);
            }
        } catch (Exception e) {
            // Print an error message if an exception occurs
            System.out.println("An error occurred while retrieving the document: " + e.getMessage());

            // Return null if the document could not be retrieved
            // return null;
        }
        return documents;
    }

}
