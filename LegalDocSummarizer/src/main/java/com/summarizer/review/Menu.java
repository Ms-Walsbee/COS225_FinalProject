package com.summarizer.review;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {

    public void startUp() {
        // Create a collection in the database to store objects
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        databaseManager.createCollection();

        // Parse documentsMetaData.csv
        String csvFile = "src/main/resources/documentsMetaData.csv";
        String line;
        String delimiter = "#";
        // Parse the documents
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                // String id = values[0];
                String title = values[1];
                String authors = values[2]; // switch from string to a list of authors
                // String abstract = values[3];
                // String categories = values[4];
                // Add more values to the reader.

                // upload document to database
                DocumentUploader documentUploader = new DocumentUploader(title, authors);
                databaseManager.addToDatabase(documentUploader.getDocument());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        // databaseManager.deleteCollection("LegalDocSummarizer");
    }

    public static void main(String[] args) {
        System.out.println("Starting the Legal Doc Summarizer..");

        Menu menu = new Menu();
        menu.startUp();

        System.out.println("Hello! Welcome to the Summarizer!");

        System.out.println("Please select one of the following options: ");

        System.out.println("1. Upload legal document.");
        System.out.println("2. Generate summary of a document.");
        System.out.println("3. Retrieve past summary from the database.");
        System.out.println("4. Display abstract of a document.");
        System.out.println("5. Exit.");

        System.out.print("Enter you choice: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // upload document
                    // uploadDocument();
                    break;
                case 2:
                    // generate summary
                    // generateSummary();
                    break;
                case 3:
                    // retrieve summary from database
                    // retrieveSummary();
                    break;
                case 4:
                    // display abstract of a document
                    // displayAbstract();
                    break;

                case 5:
                    // exit
                    System.out.println("Exiting the summarizer...");
                    // shutdown();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
