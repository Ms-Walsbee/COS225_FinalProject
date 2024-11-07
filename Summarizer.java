import java.util.HashMap;
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

    public String getsummary(){
        return summary;
    }
}