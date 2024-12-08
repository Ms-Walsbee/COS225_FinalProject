package com.summarizer.review;

import com.mongodb.client.result.InsertOneResult;
// import com.summarizer.review.DatabaseManager;
// import com.summarizer.review.DocumentUploader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;

public class Menu {
    private DatabaseManager databaseManager;
    private DatabaseManager docDatabaseManager;

    public Menu() {
        this.databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        this.docDatabaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");

    }

    public void startUp() {
        // Create a collection in the database to store objects
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        databaseManager.createCollection();

        // Parse documentsMetaData.csv
        String csvFile = "src/main/resources/documentsMetaData1.csv"; // this is a smaller document, we need to use te
                                                                      // larger file after we figure out how to stop
                                                                      // adding all the documents to the database first
        String line;
        String delimiter = "#";
        // Parse the documents
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                // Float id = Float.parseFloat(values[values.length - 5]);
                String title = values[1];
                String authors = values[2];
                String overview = values[3];
                String categories = values[4];

                // Add more values to the reader.

                // upload document to database
                DocumentUploader documentUploader = new DocumentUploader(title, authors, overview, categories);
                databaseManager.addToDatabase(documentUploader.getDocument());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        databaseManager.deleteCollection();
        DatabaseManager databaseManager2 = new DatabaseManager("LegalDocSummarizer", "doc_data");
        databaseManager2.deleteCollection();
    }

    public void addDocumentToDatabase(Scanner scanner) {

        System.out.print("Please enter the name of the document: ");
        String docTitle = scanner.nextLine();

        System.out.print("Please enter the authors of the document: ");
        String docAuthors = scanner.nextLine();

        System.out.print("Please enter the overview of the document: ");
        String docOverview = scanner.nextLine();

        System.out.print("Please enter the category of the document: ");
        String docCategories = scanner.nextLine();

        DocumentUploader documentUploader = new DocumentUploader(docTitle, docAuthors, docOverview, docCategories);

        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");

        try {
            // Now addToDatabase returns InsertOneResult
            InsertOneResult result = databaseManager.addToDatabase(documentUploader.getDocument());

            if (result != null) {
                System.out.println("\u001B[32mDocument successfully added to the database.\033[0m\n");
            } else {
                System.out.println("Failed to add the document to the database.");
                //
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding the document to the database: " + e.getMessage());
        }

    }
public void generateSummary(Scanner scanner) {
    System.out.print("Please enter the title of the document to generate a summary: ");
    String title = scanner.nextLine();
    
    // Retrieve the document by title
    List<Document> docs = databaseManager.getDocumentsByTitle(title);
    
    if (docs.isEmpty()) {
        System.out.println("\nNo document found with the title: " + title);
    } else {
        // Assuming there's only one document with the given title
        Document document = docs.get(0);
        String overview = document.getString("overview");
        
        // Here you can implement your summarization logic
        // For demonstration, let's assume the overview itself is the summary
        String summary = overview; // Replace this with actual summarization logic
        
        System.out.println("\nGenerated Summary for '" + title + "':");
        System.out.println(summary);
    }
}

    public void retrieveSummaryByTitle(Scanner scanner) {
        System.out.print("Please enter the title of the document: ");
        String title = scanner.nextLine();
        List<Document> docs = databaseManager.getDocumentsByTitle(title);
        List<Document> docFromDocData = docDatabaseManager.getDocumentsByTitle(title);

        List<Document> allDocs = new ArrayList<>();
        allDocs.addAll(docs);
        allDocs.addAll(docFromDocData);
        if (allDocs.isEmpty()) {
            System.out.println("\nNo document found with the title: " + title);
            System.out.println();
        } else {
            for (Document document : allDocs) {
                String authors = document.getString("authors");
                String titleFromDoc = document.getString("title");

                System.out.println(
                        "\nSummary for title '" + titleFromDoc + "' by the author(s) '" + authors + "':"
                                + document.getString("overview"));
                System.out.println();
            }
        }
    }

    public void retrieveSummaryByAuthor(Scanner scanner) {
        System.out.print("Please enter the author of the document: ");
        String authors = scanner.nextLine();
        List<Document> docs = databaseManager.getDocumentsByAuthor(authors);
        List<Document> docFromDocData = docDatabaseManager.getDocumentsByAuthor(authors);

        List<Document> allDocs = new ArrayList<>();
        allDocs.addAll(docs);
        allDocs.addAll(docFromDocData);
        if (allDocs.isEmpty()) {
            System.out.println("\nNo document found by the author: " + authors);
            System.out.println();
        } else {
            for (Document doc : allDocs) {
                String title = doc.getString("title");
                String authorFromDoc = doc.getString("authors");

                System.out.println(
                        "\nSummary for author '" + authorFromDoc + "' from the document titled '" + title + "':"
                                + doc.getString("overview"));
                System.out.println();
            }

        }
    }

    // Displays the summary of a document from mongoDB
    private static void displaySummary() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");
    
        // Retrieve all documents from the database
        List<Document> documents = databaseManager.getAllDocuments();
    
        if (documents.isEmpty()) {
            System.out.println("\nNo summaries available in the database.");
        } else {
            System.out.println("\nSummaries of all documents in the database:");
            for (Document doc : documents) {
                String title = doc.getString("title");
                String overview = doc.getString("overview");
                System.out.println("\nTitle: " + title);
                System.out.println("Summary: " + overview);
            }
        }
    }

    // Gets the authors from mongoDG and displays them
    private static void displayAuthors() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");
    
        // Retrieve all documents from the database
        List<Document> documents = databaseManager.getAllDocuments();
    
        if (documents.isEmpty()) {
            System.out.println("\nNo authors available in the database.");
        } else {
            System.out.println("\nAuthors of all documents in the database:");
            for (Document doc : documents) {
                String title = doc.getString("title");
                String authors = doc.getString("authors");
                System.out.println("\nTitle: " + title);
                System.out.println("Authors: " + authors);
            }
        }
    }

    // Gets the categories from mongoDB and displays them
    private static void displayCategories() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");
    
        // Retrieve all documents from the database
        List<Document> documents = databaseManager.getAllDocuments();
    
        if (documents.isEmpty()) {
            System.out.println("\nNo categories available in the database.");
        } else {
            System.out.println("\nCategories of all documents in the database:");
            for (Document doc : documents) {
                String title = doc.getString("title");
                String categories = doc.getString("categories");
                System.out.println("\nTitle: " + title);
                System.out.println("Categories: " + categories);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("\033[0;36mStarting the Legal Doc Summarizer.\033[0m");
        Menu menu = new Menu();
        menu.startUp();

        System.out.println("\n");
        System.out.println("\u001B[32mHello! Welcome to the Summarizer!");

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 7) {
            System.out.println("\u001B[33mPlease select one of the following options: \033[0m");

            System.out.println("\033[0;36m1. Upload legal document.");
            System.out.println("2. Generate summary of a document.");
            System.out.println("3. Retrieve past summary from the database by title.");
            System.out.println("4. Retrieve past summary from the database by author.");
            System.out.println("5. Display title and summary of sumbitted documents.");
            System.out.println("6. Display title and authors of submitted documents.");
            System.out.println("7. Display title and categories of submitted documents.");
            System.out.println("8. Exit.");

            System.out.print("Enter your choice: \033[0m");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                System.out.print("\n");
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.println("\u001B[33mAdding document....\33[0m");
                    menu.addDocumentToDatabase(scanner);
                    break;
                case 2:
                    System.out.println("Generating summary of the document....");
                    menu.generateSummary(scanner);
                    break;
                case 3:
                    System.out.println("Retrieving past summary from the database by title....");
                    menu.retrieveSummaryByTitle(scanner);
                    break;
                case 4:
                    System.out.println("Retrieving past summary from the database by author....");
                    menu.retrieveSummaryByAuthor(scanner);
                    break;
                case 5:
                    System.out.println("Displaying summarization....");
                    displaySummary();
                    break;
                case 6:
                    System.out.println("Displaying authors....");
                    displayAuthors();
                    break;
                case 7:
                    System.out.println("Displaying categories....");
                    displayCategories();
                    break;
                case 8:
                    // exit
                    System.out.println("\u001B[31mExiting the summarizer...\033[0m");
                    menu.shutDown();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
