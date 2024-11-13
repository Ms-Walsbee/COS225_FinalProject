import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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