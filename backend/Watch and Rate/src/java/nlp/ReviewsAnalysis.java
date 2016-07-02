package nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ReviewsAnalysis {

    private int adjNumber;
    private int advNumber;
    private String posModelsPath;
    private MaxentTagger tagger;

    public ReviewsAnalysis() {
        posModelsPath = "";
        tagger = new MaxentTagger(posModelsPath + "english-left3words-distsim.tagger");

    }

    public int getAdjNumber() {
        return adjNumber;
    }

    public void setAdjNumber(int adjNumber) {
        this.adjNumber = adjNumber;
    }

    public int getAdvNumber() {
        return advNumber;
    }

    public void setAdvNumber(int advNumber) {
        this.advNumber = advNumber;
    }

    public Sentence getPos(Sentence sentence) throws IOException {
        adjNumber = 0;
        advNumber = 0;
        String tagged = tagger.tagString(sentence.getSentence());
        Pattern pattern = Pattern.compile("(JJ)");
        Matcher matcher = pattern.matcher(tagged);
        while (matcher.find()) {
            adjNumber++;
        }
        pattern = Pattern.compile("(RB)");
        matcher = pattern.matcher(tagged);
        while (matcher.find()) {
            advNumber++;
        }
        sentence.setNumOfAdjective(adjNumber);
        sentence.setNumOfAdverb(advNumber);
        return sentence;

    }
}
