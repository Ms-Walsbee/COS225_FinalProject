package com.summarizer.review;

import java.util.HashSet;
import java.util.Set;

public class TextProcessor {
    private Set<String> stopWords; // Set of stop words

    // Constructor
    public TextProcessor() {
        this.stopWords = new HashSet<>();
    }
}