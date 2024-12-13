package com.summarizer.review;

import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class handles cleaning up and processing text. 
 * It can load a list of stop words, clean text by removing stop words, 
 * punctuation, and converting everything to lowercase. 
 * The goal is to prepare text for further analysis, like summarization.
 */
public class TextProcessor {
    private Set<String> stopWords; // A collection of words to ignore during text processing

    /**
     * Creates a new TextProcessor and loads a set of stop words from a file.
     * This ensures that the system knows which words to ignore when cleaning text.
     */
    public TextProcessor() {
        this.stopWords = new HashSet<>();
        loadStopWords("src/main/resources/listOfStopWords.txt"); // Path to the stopwords file
    }

    /**
     * Loads stop words from the specified file into a set.
     * Stop words are common words like "the," "is," or "and" that don't add much meaning.
     *
     * @param stopWordsFilePath The location of the file containing stop words.
     */
    private void loadStopWords(String stopWordsFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Couldn't load stop words from the file. Using default stop words instead.");
        }
    }

    /**
     * Filters out any stop words from the provided list of words.
     * 
     * @param arrayOfText An array of words to filter.
     * @return A new array that doesn't include stop words.
     */
    private String[] removeStopWords(String[] arrayOfText) {
        Set<String> words = new HashSet<>();
        for (String word : arrayOfText) {
            if (!stopWords.contains(word)) {
                words.add(word);
            }
        }
        return words.toArray(new String[0]);
    }

    /**
     * Converts all the characters in the text to lowercase.
     * 
     * @param text The text you want to make lowercase.
     * @return The lowercase version of the text.
     */
    private String lowerAllCases(String text) {
        return text.toLowerCase();
    }

    /**
     * Strips out punctuation from the text and replaces it with spaces.
     * For example, "hello, world!" becomes "hello world".
     * 
     * @param text The input text.
     * @return The text without any punctuation.
     */
    private String removePunctuation(String text) {
        return text.replaceAll("[^a-zA-Z0-9]", " ");
    }

    /**
     * Breaks a piece of text into individual words using spaces as the separator.
     * 
     * @param text The text to split into words.
     * @return An array of words from the text.
     */
    private String[] tokenizeWords(String text) {
        return text.split("\\s+");
    }

    /**
     * Cleans up the input text by applying the following steps:
     * - Converts all text to lowercase.
     * - Removes punctuation (replacing it with spaces).
     * - Splits the text into individual words.
     * - Removes any stop words from the list of words.
     * 
     * @param text The input text that needs cleaning.
     * @return A cleaned version of the text, ready for analysis.
     */
    public String cleanText(String text) {
        text = lowerAllCases(text); // Step 1: Make everything lowercase
        text = removePunctuation(text); // Step 2: Remove punctuation
        String[] words = tokenizeWords(text); // Step 3: Break into words
        words = removeStopWords(words); // Step 4: Remove stop words
        return String.join(" ", words); // Combine the cleaned words back into a string
    }
}
