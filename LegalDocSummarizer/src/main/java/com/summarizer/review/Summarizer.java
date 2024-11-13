package com.summarizer.review;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Summarizer{
    //Attributes
    private String summary;
    private int summaryLength;
    private Map<String,Double> tdfifScores;
    private Map<String,Double> sentenceRankings;

    //Constructors 
    public Summarizer(String summary, int summaryLength, Map<String,Double> tdfifScores, Map<String,Double> sentenceRankings){
    this.summary = summary;
    this.summaryLength = summaryLength;
    this.tdfifScores = new HashMap<>();
    this.sentenceRankings = new HashMap<>();
    }

    //Getters
    public String getSummary(){
        return summary;
    }

    public int getSummaryLength(){
        return summaryLength;
    }

    public Map<String,Double> gettdfifScores(){
        return tdfifScores;
    }

    public Map<String,Double> getSentenceRankings(){
        return sentenceRankings;
    }

    //Setters
    public void setSummary(String summary){

    }

    public void setSummaryLength(int summaryLength){

    }

    public void settdfifScores(Map<String,Double> tdfifScores){

    }

    public void setGetSentenceRankings(Map<String,Double> sentenceRankings){

    }

    //Methods 
    public void calculateTFIDF(List<String> document) {
        Map<String, Integer> termFrequencey = new HashMap<>();
        int totalWords = document.size();

        //Calculate the Term Frequency (TF)
        for(String word : document) {
            word = word.toLowerCase();
            termFrequencey.put(word, termFrequencey.getOrDefault(word, 0) + 1);
        }

        //Calculate TFIDF for each word
        for(Map.Entry<String, Integer> entry : termFrequencey.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();

            double tf = (double) count / totalWords;

            double idf = Math.log(1.0 +1.0);

            double tfidf = tf * idf;

            tdfifScores.put(word, tfidf);
        }

        //Ranks the sentences based on the highest scoring words
        rankSentencesByTFIDF(document);
    }

    //Rank sentences based on the sum total of the TFIDF scores of the words in the sentence
    private void rankSentencesByTFIDF(List<String> document) {
        List<String> sentences = splitDocumentIntoSentences(document);
        for(String sentence : sentences) {
            double senteceScore = 0.0;
            String[] words = sentence.split("\\s+");

            for(String word : words) {
                word = word.toLowerCase();
                senteceScore += tdfifScores.getOrDefault(word, 0.0);
            }

            sentenceRankings.put(sentence, senteceScore);
        }
    }

    //Helps the method split the document into sentences
    private List<String> splitDocumentIntoSentences(List<String> document){
        String documentText = String.join(" ", document);

        String sentenceDelimiterRegex = "(?<!\\b(?:Mr|Ms|Dr|Jr|Sr|Inc|e\\.g|i\\.e|U\\.S|etc))([.!?])\\s+";
        
        Pattern pattern = Pattern.compile(sentenceDelimiterRegex);
        String[] sentences = pattern.split(documentText);

        List<String> sentenceList = new ArrayList<>();
        for(String sentence : sentences) {
            if(!sentence.trim().isEmpty()) {
                sentenceList.add(sentence.trim());
            }
        }

        return sentenceList;
    }


    public String generateSummary(List<String> sentences) {

        //Sorts sentences based on their rankings of how important the words are in that sentence
        List<Map.Entry<String, Double>> sortSentences = new ArrayList<>(sentenceRankings.entrySet());
        sortSentences.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
        
        StringBuilder summaryBuilder = new StringBuilder();
        int sentenceCount = 0;

        //Adds the top ranked sentences to the summary
        for(Map.Entry<String, Double> entry : sortSentences) {
            if(sentenceCount >= summaryLength) {
                break;
            }

            summaryBuilder.append(entry.getKey()).append(" ");
            sentenceCount++;
        }

        //Confirms and sets the final summary with the top ranked sentences
        summary = summaryBuilder.toString().trim();
        return summary;
    }
}