package nlp;

import java.io.IOException;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class WekaFileGenerator {


    private Sentence sentence;

    WekaFileGenerator(String review, StanfordCoreNLP pipeline, ReviewsAnalysis ra) throws IOException {
        Sentiword sw = new Sentiword();
        sentence = new Sentence(review);
        sw.count(review, pipeline);
        sentence.setNegativeWords(sw.getNegativeWords());
        sentence.setPositiveWords(sw.getPositiveWords());
        sentence.setNeutralWords(sw.getNeutralWords());
        sentence.setSubjectiveWords(sw.getSubjectiveWords());
        sentence.setStrongNegative(sw.getStrongNegative());
        sentence.setStrongPositive(sw.getStrongPositive());
        sentence.setSumOfNegativeScore(sw.getSumOfNegativeScore());
        sentence.setSumOfPositiveScore(sw.getSumOfPositiveScore());
        
        sentence = ra.getPos(sentence);

    }


    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

}
