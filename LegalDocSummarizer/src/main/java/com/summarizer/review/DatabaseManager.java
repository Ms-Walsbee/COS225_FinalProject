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

/**
 * This DatabaseManager class the MongoDB database connection and provides
 * methods for adding documents,
 * deleting documents, and deleting the entire collection.
 * 
 */

public class DatabaseManager {
    private String connectionString, databaseName, collectionName;

    /**
     * This constructs a new DatabaseManager with the provided database and
     * collection names.
     * 
     * @param dbName         Name of the database to connect to.
     * @param collectionName Name of the collection to with the database.
     */
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
    /**
     * Adds a single Document to the database "doc_data" collection.
     * 
     * @param document The document to be added to the database.
     * @return Returns result of adding the document, null if error.
     */
    public InsertOneResult addToDatabase(Document document) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);

            MongoCollection<Document> collection = database.getCollection(collectionName);

            return collection.insertOne(document);

        } catch (Exception e) {

            System.out.println("An error occurred while adding the document to the database: " + e.getMessage());

            return null;
        }
    }

    /**
     * Create a collection in the database with the collection name.
     */
    public void createCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);

            database.createCollection(collectionName);

            System.out.print("\033[0;36mCollection created successfully!\033[0m");
        } catch (Exception e) {

            System.out.println("An error occurred while creating the collection: " + e.getMessage());
        }
    }

    /**
     * Deletes the collection in the database.
     */
    public void deleteCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);

            database.getCollection(collectionName).drop();

        }
    }

    /**
     * Deletes all documents in the collections.
     */
    public void deleteAllDocuments() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);

            MongoCollection<Document> collection = database.getCollection(collectionName);

            collection.deleteMany(new Document());

            System.out.println("All documents deleted successfully!");
        }
    }

    /**
     * This retrieves documents from the database where the "authors" field matches
     * the the inputed author string.
     * 
     * @param author The name or part of the authors name to search for.
     * @return The list of documents matching the "authors" query.
     */
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
            System.out.println("An error occurred while retrieving the document: " + e.getMessage());
        }
        return documents;

    }

    /**
     * This retrieves documents from the database where the "title" field matches
     * the the inputed title string.
     * 
     * @param title The title or part of the title search for.
     * @return A list of documents matching the title query.
     */
    public List<Document> getDocumentsByTitle(String title) {

        List<Document> documents = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);

            MongoCollection<Document> collection = database.getCollection(collectionName);

            Document query = new Document("title", new Document("$regex", title).append("$options", "i"));

            for (Document document : collection.find(query)) {
                documents.add(document);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving the document: " + e.getMessage());
            return null;
        }
        return documents;
    }

    /**
     * This method retrieves all documents in the "doc_data" collection to display
     * summaries.
     * 
     * @return documents
     */
    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase(databaseName);

            MongoCollection<Document> collection = database.getCollection(collectionName);

            for (Document doc : collection.find()) {
                documents.add(doc);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving documents: " + e.getMessage());
        }
        return documents;
    }

}
