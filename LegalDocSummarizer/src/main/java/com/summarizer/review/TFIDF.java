package com.summarizer.review;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/** 
 * The TFIDF class implements the Term Frequency-Inverse Document Frequency (TFIDF) algorithm, 
 * a statistical method used to evaluate the importance of a word within a document collection. 
 * It stores the term frequencies (TF) for documents and calculates the inverse document frequencies (IDF) 
 * to compute the TFIDF scores for individual words or groups of words (sentences). 
 * Our implementation of TFIDF allows us to summarize the legal documents by calculating the scores 
 * for each word and ultimately, each sentence. This class includes 1 constructor and 5 methods 
 * that are used to efficiently run the TFIDF algorithm. 
 */
public class TFIDF {

    private HashSet<String> vocabulary = new HashSet<>();
    private HashMap<String, Float> idf = new HashMap<>();
    private HashMap<ObjectId, HashMap<String, Integer>> tf = new HashMap<>();
    private TextProcessor processor;

    /**
     * Constructor for the TFIDF class. It initializes the class TextProcessor to clean and preprocess text. 
     * This allows us to efficiently sift through the documents, analyze the words, and score the frequencies.
     * 
     * @param processor an instance of {@link TextProcessor} used to clean and preprocess the text.
     */
    public TFIDF(TextProcessor processor) {
        this.processor = processor;
    }

    /**
     * Method to add a new document to the TFIDF algorithm. It takes the document ID and the cleaned text, 
     * which is then processed to calculate the word frequencies. 
     * 
     * @param id the unique identifier of the document (of type {@link ObjectId}).
     * @param text the raw text of the document to be processed.
     */
    public void addSample(ObjectId id, String text) {
        String cleanedText = processor.cleanText(text);
        String[] words = cleanedText.split("\\s+");

        HashMap<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            vocabulary.add(word);
        }
        tf.put(id, wordCount);
    }

    /**
     * Method to calculate the inverse document frequencies (IDF) for each word in the vocabulary. 
     * The IDF values are stored in the {@link #idf} map.
     */
    public void calculateIDF() {
        int totalDocs = tf.size();

        for (String word : vocabulary) {
            int docCount = 0;

            for (HashMap<String, Integer> wordCount : tf.values()) {
                if (wordCount.containsKey(word)) {
                    docCount++;
                }
            }

            if (totalDocs > 1) {
                idf.put(word, (float) Math.log((double) (totalDocs + 1) / (docCount + 1)));
            } else {
                idf.put(word, 0.1f);
            }
        }
    }

    /**
     * Method to calculate the TF-IDF score for a given word in a specific document. 
     * 
     * @param id the unique identifier of the document (of type {@link ObjectId}).
     * @param word the word for which the TF-IDF score is to be calculated.
     * @return the TF-IDF score for the given word in the specified document.
     */
    public float calculateTFIDF(ObjectId id, String word) {
        if (!tf.containsKey(id) || !idf.containsKey(word)) {
            return 0;
        }

        HashMap<String, Integer> wordCount = tf.get(id);
        if (!wordCount.containsKey(word)) {
            return 0;
        }

        int termFrequency = wordCount.get(word);
        float idfValue = idf.get(word);

        return termFrequency * idfValue;
    }

    /**
     * Method to calculate the TF-IDF score for a given sentence in a specific document. 
     * 
     * @param id the unique identifier of the document (of type {@link ObjectId}).
     * @param words an array of words representing the sentence for which the TF-IDF score is to be calculated.
     * @return the TF-IDF score for the given sentence in the specified document.
     */
    public float calculateTFIDF(ObjectId id, String[] words) {
        float score = 0;
        for (String word : words) {
            score += calculateTFIDF(id, word);
        }
        return score;
    }

    /**
     * Method to retrieve all the document IDs for which we have TF-IDF scores. 
     * 
     * @return a list of {@link ObjectId} representing all the document IDs.
     */
    public ArrayList<ObjectId> getIds() {
        return new ArrayList<>(tf.keySet());
    }
}