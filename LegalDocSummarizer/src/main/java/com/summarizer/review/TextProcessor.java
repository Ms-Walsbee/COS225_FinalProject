package com.summarizer.review;

import java.util.HashSet;
import java.util.Set;

public class TextProcessor {
    private Set<String> stopWords; // Set of stop words

    // Constructor
    public TextProcessor() {
        this.stopWords = new HashSet<>();
    }

    // Load stop words from a file
    private void loadStopWords(String stopWordsFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] removeStopWords(String[] arrayOfText) {
        return Arrays.stream(arrayOfText)
                     .filter(word -> !stopWords.contains(word))
                     .toArray(String[]::new);
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
}