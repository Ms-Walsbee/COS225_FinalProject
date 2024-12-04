package com.summarizer.review;

import com.mongodb.client.result.InsertOneResult;
import com.summarizer.review.DatabaseManager;
import com.summarizer.review.DocumentUploader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

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
                System.out.println("Document successfully added to the database.\n");
            } else {
                System.out.println("Failed to add the document to the database.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding the document to the database: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        System.out.println("Starting the Legal Doc Summarizer..");
        Menu menu = new Menu();
        menu.startUp();

        System.out.println("\n");
        System.out.println("Hello! Welcome to the Summarizer!");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 7) {
            System.out.println("Please select one of the following options: ");

            System.out.println("1. Upload legal document.");
            System.out.println("2. Generate summary of a document.");
            System.out.println("3. Retrieve past summary from the database by title.");
            System.out.println("4. Retrieve past summary from the database by author.");
            System.out.println("5. Display summarization of a document.");
            System.out.println("6. Display authors of a document.");
            System.out.println("7. Display categories of a document.");
            System.out.println("8. Exit.");

            System.out.print("Enter your choice: ");

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
                    System.out.println("Adding document....");
                    menu.addDocumentToDatabase(scanner);
                    break;
                case 2:
                    System.out.println("Generating summary of the document....");
                    // generateSummary();
                    break;
                case 3:
                    System.out.println("Retrieving past summary from the database by title....");
                    // retrieveSummaryTitle();
                    break;
                case 4:
                    System.out.println("Retrieving past summary from the database by author....");
                    // retrieveSummaryAuthors();
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
                case 8:
                    // exit
                    System.out.println("Exiting the summarizer...");
                    menu.shutDown();
                    scanner.close();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    
     //Displays the summary of a document from mongoDB
     private static void displaySummary() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");
        // TODO: Implement this method
    }

    //Gets the authors from mongoDG and displays them
    private static void displayAuthors() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");
        // TODO: Implement this method
    }

    //Gets the categories from mongoDB and displays them
    private static void displayCategories() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");
        // TODO: Implement this method
    }

   
}
