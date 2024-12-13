package com.summarizer.review;

import com.mongodb.client.result.InsertOneResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;

/**
 * This Menu class is used as the main interface for managing the Legal Document
 * Summaries in MongoDB.
 * It includes methods for document interaction like uploading, summarizing and
 * querying documents.
 *
 */

public class Menu {
    private DatabaseManager databaseManager;
    private DatabaseManager docDatabaseManager;

    /**
     * This constructs a new Menu instance with DatabaseManager, it initializes
     * database managers for two collections.
     * -"documents" for storing document metadata from the group.
     * -"doc_data" for storing detailed information about a document from the user
     * input.
     */
    public Menu() {
        this.databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        this.docDatabaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");

    }

    /**
     * This method is called before showing menu options.
     * It creates a new collection in MongoDB called documents and parses
     * information from the .csv file.
     */

    public void startUp() {
        // Create a collection in the database to store objects
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        databaseManager.createCollection();

        // Parse documentsMetaData.csv
        String csvFile = "src/main/resources/documentsMetaData1.csv"; // use metadata1.csv for quicker testing purposes.
        String line;
        String delimiter = "#";
        // Parse the documents
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                String title = values[1];
                String authors = values[2];
                String overview = values[3];
                String categories = values[4];

                // upload document to database
                DocumentUploader documentUploader = new DocumentUploader(title, authors, overview, categories);
                databaseManager.addToDatabase(documentUploader.getDocument());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when the user wants to exit the application.
     * It will delete both collections and entire database.
     */
    public void shutDown() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "documents");
        databaseManager.deleteCollection();
        DatabaseManager databaseManager2 = new DatabaseManager("LegalDocSummarizer", "doc_data");
        databaseManager2.deleteCollection();
    }

    /**
     * When the user inputs 1, it adds douments to the MongoDB database under
     * "doc_data" collection.
     * It collects:
     * -Document title
     * -Author Names
     * -Overview (summary)
     * -Category
     * If failure, prints the error message.
     * 
     * @param Scanner scanner object for reading user input
     */

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
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding the document to the database: " + e.getMessage());
        }

    }

    /**
     * When the user inputs 2, it retrieves documents by title from the MongoDB
     * "documents" and "doc_data" collections.
     * It processes the documents "overview" (summary) using TF-IDF for sentence
     * scoring.
     * Then selects top scoring sentences to construct a summary on a 2 maxSentence
     * basis.
     * 
     * @param Scanner scanner object for reading user input
     */

    public void generateSentenceSummary(Scanner scanner) {
        System.out.print("Please enter the title of the document to generate a summary: ");
        String title = scanner.nextLine();

        // Retrieve the document by title from doc_data and documents
        List<Document> docs = databaseManager.getDocumentsByTitle(title);
        List<Document> docFromDocData = docDatabaseManager.getDocumentsByTitle(title);
        List<Document> allDocs = new ArrayList<>();
        allDocs.addAll(docs);
        allDocs.addAll(docFromDocData);

        if (allDocs.isEmpty()) {
            System.out.println("\nNo document found with the title: " + title);
        } else {
            Document document = allDocs.get(0);

            String documentTitle = document.getString("title");
            String authors = document.getString("authors");
            String overview = document.getString("overview");
            String categories = document.getString("categories");

            // Initialize TextProcessor and TFIDF
            TextProcessor textProcessor = new TextProcessor();
            TFIDF tfidf = new TFIDF(textProcessor);

            String[] sentences = overview.split("(?<=[.!?])\\s+"); // Split by sentence boundaries
            String cleanedOverview = textProcessor.cleanText(overview);
            tfidf.addSample(document.getObjectId("_id"), cleanedOverview);
            tfidf.calculateIDF();

            // Score sentences
            Map<String, Float> sentenceScores = new HashMap<>();
            for (String sentence : sentences) {
                String cleanedSentence = textProcessor.cleanText(sentence);
                String[] words = cleanedSentence.split("\\s+");
                float score = tfidf.calculateTFIDF(document.getObjectId("_id"), words);
                sentenceScores.put(sentence, score);
            }
            // Sort sentences by score
            List<Map.Entry<String, Float>> sortedSentences = sentenceScores.entrySet().stream()
                    .sorted((a, b) -> Float.compare(b.getValue(), a.getValue()))
                    .collect(Collectors.toList());

            // Select top sentences for the summary
            int maxSentences = 2;
            StringBuilder summary = new StringBuilder();
            for (int i = 0; i < Math.min(maxSentences, sortedSentences.size()); i++) {
                summary.append(sortedSentences.get(i).getKey()).append(" ");
            }

            System.out.println("\n\u001B[33mGenerating Summary for Title:\033[0m '" + documentTitle
                    + "'\u001B[33m.\033[0m");
            System.out.println("\n\u001B[33mBy author(s):\033[0m '" + authors + "'\u001B[33m.\033[0m");
            System.out.println("\n\u001B[33mAnd by category(s):\033[0m '" + categories + "'\u001B[33m.\033[0m\n");
            System.out
                    .println("\u001B[33mGenerated Sentence-Based Summary:\033[0m " + summary.toString().trim() + "\n");
        }
    }

    /**
     * Retrieves and displays summaries of documents based on title from both
     * "doc_data" and "documents" collection.
     * 
     * @param Scanner scanner object for reading user input
     */

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

                System.out.println("\n\u001B[33mFull summary for title:\033[0m '" + titleFromDoc + "'.\033[0m");
                System.out.println("\n\u001B[33mBy the author(s):\033[0m '" + authors + "'.\033[0m");
                System.out.println("\n\u001B[33mFull summary:\033[0m " + document.getString("overview"));
                System.out.println();
            }
        }
    }

    /**
     * Retrieves and displays summaries of documents based on author from both
     * collections "doc_data" and "documents" collection.
     * 
     * @param Scanner scanner object for reading user input
     */

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

                System.out.println("\n\u001B[33mFull summary for title:\033[0m '" + title + "'.\033[0m");
                System.out.println("\n\u001B[33mBy the author(s):\033[0m '" + authorFromDoc + "'.\033[0m");
                System.out.println("\n\u001B[33mFull summary:\033[0m " + doc.getString("overview"));
                System.out.println();
            }

        }
    }

    /**
     * This method displays the summaries of all the documents the user submits
     * into "doc_data" collection does not generate.
     * This shows both the title and the summary.
     */
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

    /**
     * This method displays the authors of all the documents the user submits into
     * "doc_data" collection.
     * This shows both the title and the authors of all documents submitted by the
     * user.
     */
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

    /**
     * This method displays the categories of all the documents the user submits
     * into "doc_data" collection. (only displays, does not generates summary)
     * This shows just the categories of all the documents.
     * To do: Implement search by categories.
     */
    private static void displayCategories() {
        DatabaseManager databaseManager = new DatabaseManager("LegalDocSummarizer", "doc_data");

        // Retrieve all documents from the database
        List<Document> documents = databaseManager.getAllDocuments();

        if (documents.isEmpty()) {
            System.out.println("\nNo categories available in the database.");
        } else {
            System.out.println("\nCategories of all documents in the database:");
            for (Document doc : documents) {
                String categories = doc.getString("categories");
                System.out.println("Categories: " + categories);
            }
        }
    }

    /**
     * The main method to run the Legal Doc Summarizer application with color to
     * display whats user input and what information is code input.
     * It presents a menu to the user and allows interaction with the application
     * with options like:
     * -Uploading Documents
     * -Generating Documents
     * -Displaying Document Information
     * -Exiting the application, uses a shutdown method to delete the data
     * collections in MongoDB
     * 
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\033[0;36mStarting the Legal Doc Summarizer.\033[0m");
        Menu menu = new Menu();
        menu.startUp();

        System.out.println("\n");
        System.out.println("\u001B[32mHello! Welcome to the Summarizer!\033[0m");

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 8) {
            System.out.println("\u001B[33mPlease select one of the following options: \033[0m");

            System.out.println("\033[0;36m1. Upload legal document.");
            System.out.println("2. Generate summary of a document.");
            System.out.println("3. Search database by title.");
            System.out.println("4. Search database by author.");
            System.out.println("5. Display title and summary of user submitted documents.");
            System.out.println("6. Display title and authors of user submitted documents.");
            System.out.println("7. Display categories of user submitted documents.");
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
                    System.out.println("\u001B[32mAdding document....\33[0m");
                    menu.addDocumentToDatabase(scanner);
                    break;
                case 2:
                    System.out.println("\u001B[32mGenerating sentence-based summary of the document...\033[0m");
                    menu.generateSentenceSummary(scanner);
                    break;
                case 3:
                    System.out.println("\u001B[32mRetrieving documents from the database by title....\033[0m");
                    menu.retrieveSummaryByTitle(scanner);
                    break;
                case 4:
                    System.out.println("\u001B[32mRetrieving past summary from the database by author....\033[0m");
                    menu.retrieveSummaryByAuthor(scanner);
                    break;
                case 5:
                    System.out.println("\u001B[32mDisplaying summarization....\033[0m");
                    displaySummary();
                    break;
                case 6:
                    System.out.println("\u001B[32mDisplaying authors....\033[0m");
                    displayAuthors();
                    break;
                case 7:
                    System.out.println("\u001B[32mDisplaying categories....\033[0m");
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
