package com.summarizer.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.bson.BsonValue;

//The TFIDF class, implements the Term Frequency-Inverse Document Frequency (TFIDF) algorithm, which is a 
//statistical method used to evaluate the importance of a word within a document collection. 
//It stores the term frequencies (TF) for documents and calculates the inverse document frequencies (IDF) 
//to compute the TF-IDF scores for individual words or groups of words (sentences). Our implementation 
//of TFIDF allows us to summarize the legal documents by calculating the scores for each word and 
//ultimatly, each sentence. This class includes 1 constructor and 5 methods that are used to efficiently 
//run the TFIDF algorithm.

public class TFIDF {

    private HashSet<String> vocabulary = new HashSet<>();
    private HashMap<String, Float> idf = new HashMap<>();
    private HashMap<BsonValue, HashMap<String, Integer>> tf = new HashMap<>();
    private TextProcessor processor;

    //Constructor for the TFIDF class. It initializes the class TextProcessor to clean and preprocess text.
    //This allows us to efficiently sift though the documents, analyize the words and score the frequencies.
    public TFIDF(TextProcessor processor) {
        this.processor = processor;
    }

    //Method to add a new document to the TFIDF algorithm. It takes the document ID and the cleaned text, 
    //which is then processed to calculate the word frequencies.
    public void addSample(BsonValue id, String text) {
        String cleanedText = processor.cleanText(text);
        String[] words = cleanedText.split("\\s+");
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            vocabulary.add(word);
        }
        tf.put(id, wordCount);
    }

    //Method to calculate the inverse document frequencies (IDF) for each word in the vocabulary.
    public void calculateIDF() {
        for (String word : vocabulary) {
            int count = 0;
            for (HashMap<String, Integer> wordCount : tf.values()) {
                if (wordCount.containsKey(word)) {
                    count++;
                }
            }
            idf.put(word, (float) Math.log((float) tf.size() + 1 / count + 1));
        }
    }

    //Method to calculate the TF-IDF score for a given word in a specific document.
    public float calculateTFIDF(BsonValue id, String word) {
        if (!tf.containsKey(id)) {
            return 0;
        }
        HashMap<String, Integer> wordCount = tf.get(id);
        if (!wordCount.containsKey(word)) {
            return 0;
        }
        return wordCount.get(word) * idf.get(word);
    }

    //Method to calculate the TF-IDF score for a given sentence in a specific document.
    public float calculateTFIDF(BsonValue id, String[] words) {
        float score = 0;
        for (String word : words) {
            score += calculateTFIDF(id, word);
        }
        return score;
    }

    //Method to retrieve all the document IDs for which we have TF-IDF scores.
    public ArrayList<BsonValue> getIds() {
        return new ArrayList<>(tf.keySet());
    }
}