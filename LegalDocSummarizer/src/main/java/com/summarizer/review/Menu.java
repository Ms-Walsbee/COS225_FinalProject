package com.summarizer.review;

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
    }
}
