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
    this.tdfifScores = tdfifScores;
    this.sentenceRankings = sentenceRankings;
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
}