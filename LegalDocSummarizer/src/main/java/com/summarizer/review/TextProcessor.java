package com.summarizer.review;

import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextProcessor {
    private Set<String> stopWords; // Set of stop words

    // Constructor
    public TextProcessor() {
        this.stopWords = new HashSet<>();
        loadStopWords("src/main/resources/listOfStopWords.txt"); // Default stopwords file path
    }

    // Load stop words from a file
    private void loadStopWords(String stopWordsFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim());
            }
            System.out.println("Stop words loaded: " + stopWords.size());
        } catch (IOException e) {
            System.err.println("Failed to load stop words from file. Using default stop words.");

        }
    }

    private String[] removeStopWords(String[] arrayOfText) {
        Set<String> words = new HashSet<>();
        for (String word : arrayOfText) {
            if (!stopWords.contains(word)) {
                words.add(word);
            }
        }
        return words.toArray(new String[0]);
    }

    private String lowerAllCases(String text) {
        return text.toLowerCase();
    }

    private String removePunctuation(String text) {
        return text.replaceAll("[^a-zA-Z0-9]", " ");
    }

    private String[] tokenizeWords(String text) {
        return text.split("\\s+");
    }

    // Clean text by lowercasing, removing punctuation, and stop words
    public String cleanText(String text) {
        text = lowerAllCases(text);
        text = removePunctuation(text);
        String[] words = tokenizeWords(text);
        words = removeStopWords(words);
        return String.join(" ", words);
    }
}