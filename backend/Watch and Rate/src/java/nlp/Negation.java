package nlp;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import java.io.IOException;

public class Negation {

    private int numOfPositive;
    private int numOfNegative;
    private static StanfordCoreNLP pipeline;

    Negation() {
        numOfNegative = 0;
        numOfPositive = 0;

    }

    public StanfordCoreNLP getPipeline() {
        return pipeline;
    }

    public static void setPipeline(StanfordCoreNLP pipeline) {
        Negation.pipeline = pipeline;
    }

    public int getNumOfPositive() {
        return numOfPositive;
    }

    public void setNumOfPositive(int numOfPositive) {
        this.numOfPositive = numOfPositive;
    }

    public int getNumOfNegative() {
        return numOfNegative;
    }

    public void setNumOfNegative(int numOfNegative) {
        this.numOfNegative = numOfNegative;
    }

    public void isNegation(String text) throws IOException {
      /*  Sentiword sw = new Sentiword();
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

            for (SemanticGraphEdge edge : dependencies.edgeIterable()) {
                String headIndex = edge.getGovernor().word();
                String depIndex = edge.getDependent().word();
                String relation = edge.getRelation().toString();
                if (relation.equals("neg")) {
                    int res = sw.isPositive(headIndex);
                    if (res == 4 || res == 2) {
                        numOfPositive++;
                    } else if (res == 3 || res == 1) {
                        numOfNegative++;
                    }
                }

            }*/

        }

    }


