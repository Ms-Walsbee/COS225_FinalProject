package com.summarizer.review;

import java.util.Scanner;

public class Menu {

    public void startUp() {

    }

    public void shutDown() {

    }

    public static void main(String[] args) {
        System.out.println("Starting the Legal Doc Summarizer..");

        System.out.println("Hello! Welcome to the Summarizer!");

        System.out.println("Please select one of the following options: ");

        System.out.println("1. Upload legal document.");
        System.out.println("2. Generate summary of a document.");
        System.out.println("3. Retrieve past summary from the database.");
        System.out.println("4. Exit.");

        System.out.print("Enter you choice: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    // upload document
                    break;
                case 2:
                    // generate summary
                    break;
                case 3:
                    // retrieve summary from database
                    break;
                case 4:
                    // exit
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
