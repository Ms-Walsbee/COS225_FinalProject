package com.summarizer.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.bson.BsonValue;

public class TFIDF {

    private HashSet<String> vocabulary = new HashSet<>();
    private HashMap<String, Float> idf = new HashMap<>();
    private HashMap<BsonValue, HashMap<String, Integer>> tf = new HashMap<>();
    private TextProcessor processor;

    public TFIDF(TextProcessor processor) {
        this.processor = processor;
    }

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

    public float calculateTFIDF(BsonValue id, String[] words) {
        float score = 0;
        for (String word : words) {
            score += calculateTFIDF(id, word);
        }
        return score;
    }

    public ArrayList<BsonValue> getIds() {
        return new ArrayList<>(tf.keySet());
    }

}