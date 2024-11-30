package com.summarizer.review;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
}