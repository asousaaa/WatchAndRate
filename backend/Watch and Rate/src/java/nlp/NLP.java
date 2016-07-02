/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Double.NaN;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;

/**
 *
 * @author HEBA-PC
 */
public class NLP {

    private double rate;
    private int story;
    private int direction;
    private static StanfordCoreNLP pipeline;
    private static ReviewsAnalysis ra;

    public NLP() {
        rate = 0.0;
        story = 0;
        direction = 0;
    }

    public int getStory() {
        return story;
    }

    public void setStory(int story) {
        this.story = story;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    
    

    public static  void loadNLP() throws IOException {
       /* Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        pipeline = new StanfordCoreNLP(props);*/
        Sentiword sw = new Sentiword();
        sw.readSentiwordLexicon();
      //  Negation.setPipeline(pipeline);
        Polarity.readIntensifier();
        ra = new ReviewsAnalysis();
    }

    public void calculateRate(String review) throws IOException, Exception {
        double positiveSentences = 0, allSentences = 0;
        String predictedClass = "";
        File writeFile = new File("test.arff");
        PrintWriter pw = new PrintWriter(writeFile);
        pw.println("@relation movie_review");
        pw.println("@attribute 'positive_words' numeric");
        pw.println("@attribute 'negative_words' numeric");
        pw.println("@attribute 'positive_score' numeric");
        pw.println("@attribute 'negative_score' numeric");
        pw.println("@attribute 'strongPositive' numeric");
        pw.println("@attribute 'strongNegative' numeric");
        pw.println("@attribute 'subjective_words' numeric");
        pw.println("@attribute 'neutral_words' numeric");
        pw.println("@attribute 'adj_words' numeric");
        pw.println("@attribute 'adv_words' numeric");
        pw.println("@attribute 'class' {negative, positive}");
        pw.println("@data");


        String[] splitByPoint = review.split("\\.");
        for (int j = 0; j < splitByPoint.length; j++) {
               // String normalized = normalization(splitByPoint[j]);

            if (splitByPoint[j] == null || splitByPoint[j].isEmpty()) {
                continue;
            }
            System.out.println("your review : " + splitByPoint[j]);
            WekaFileGenerator wk = new WekaFileGenerator(splitByPoint[j], pipeline, ra);
            pw.print(wk.getSentence().getPositiveWords() + "," + wk.getSentence().getNegativeWords() + ","
                    + +wk.getSentence().getSumOfPositiveScore() + "," + wk.getSentence().getSumOfNegativeScore() + ","
                    + wk.getSentence().getStrongPositive() + "," + wk.getSentence().getStrongNegative()
                    + "," + wk.getSentence().getSubjectiveWords() + "," + wk.getSentence().getNeutralWords() + "," + wk.getSentence().getNumOfAdjective() + "," + wk.getSentence().getNumOfAdverb() + ", ? \n");
             //   System.out.println("here");
            //    }

        }

        pw.close();
        DataSource test = new DataSource("test.arff");
        Instances testData = test.getDataSet();
        testData.setClassIndex(testData.numAttributes() - 1);

        Classifier j = (Classifier) weka.core.SerializationHelper.read("movieReview.model");

        for (int i = 0; i < testData.numInstances(); i++) {
            Instance inst = testData.instance(i);
            double predictNum = j.classifyInstance(inst);
            predictedClass = testData.classAttribute().value((int) predictNum);
            System.out.println("Class Predicted: " + predictedClass);
            if (predictedClass.equals("positive")) {
                positiveSentences++;
                System.out.println("positiveSentences = " + positiveSentences);
                if(splitByPoint[i].contains("story")){
                    story = 1;
                }
                if(splitByPoint[i].contains("direction")){
                    direction = 1;
                }
            }
            else
            {
             //   positiveSentences--;
                if(splitByPoint[i].contains("story")){
                    story = 0;
                }
                if(splitByPoint[i].contains("direction")){
                    direction = 0;
                }
            }
            allSentences++;
            System.out.println("allSentences = " + allSentences);
        }
        DecimalFormat format = new DecimalFormat("#0.000");
        rate = (positiveSentences / allSentences) * 100;
        if (rate != NaN) {
            rate = Double.parseDouble(format.format(rate));
            if (rate > 0 && rate <= 10) {
                rate = 0.5;
            } else if (rate > 10 && rate <= 20) {
                rate = 1.0;
            } else if (rate > 20 && rate <= 30) {
                rate = 1.5;
            } else if (rate > 30 && rate <= 40) {
                rate = 2;
            } else if (rate > 40 && rate <= 50) {
                rate = 2.5;
            } else if (rate > 50 && rate <= 60) {
                rate = 3;
            } else if (rate > 60 && rate <= 70) {
                rate = 3.5;
            } else if (rate > 70 && rate <= 80) {
                rate = 4;
            } else if (rate > 80 && rate <= 90) {
                rate = 4.5;
            } else if (rate > 90 && rate <= 100) {
                rate = 5;
            }
        }
        System.out.println("rate: " + rate);

    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
