package nlp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.text.DecimalFormat;

public class Sentiword {

    private static HashMap<Integer, Record> lexicon = new HashMap<>();
    private HashMap<Integer, String> negationWords;
    //InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("../resources/lexicon");
    private int positiveWords;
    private int negativeWords;
    private int strongPositive;
    private int strongNegative;
    private int subjectiveWords;
    private int neutralWords;
    private double sumOfNegativeScore;
    private double sumOfPositiveScore;

    public Sentiword() throws IOException {
        negationWords = new HashMap<>();
        positiveWords = 0;
        negativeWords = 0;
        subjectiveWords = 0;
        strongNegative = 0;
        strongPositive = 0;
        neutralWords = 0;
        sumOfNegativeScore = 0.0;
        sumOfPositiveScore = 0.0;

    }

    public double getSumOfNegativeScore() {
        return sumOfNegativeScore;
    }

    public void setSumOfNegativeScore(double sumOfNegativeScore) {
        this.sumOfNegativeScore = sumOfNegativeScore;
    }

    public double getSumOfPositiveScore() {
        return sumOfPositiveScore;
    }

    public void setSumOfPositiveScore(double sumOfPositiveScore) {
        this.sumOfPositiveScore = sumOfPositiveScore;
    }

    public int getNeutralWords() {
        return neutralWords;
    }

    public void setNeutralWords(int neutralWords) {
        this.neutralWords = neutralWords;
    }

    public int getStrongPositive() {
        return strongPositive;
    }

    public void setStrongPositive(int strongPositive) {
        this.strongPositive = strongPositive;
    }

    public int getStrongNegative() {
        return strongNegative;
    }

    public void setStrongNegative(int strongNegative) {
        this.strongNegative = strongNegative;
    }

    public int getPositiveWords() {
        return positiveWords;
    }

    public int getNegativeWords() {
        return negativeWords;
    }

    public int getSubjectiveWords() {
        return subjectiveWords;
    }

    public void setSubjectiveWords(int subjectiveWords) {
        this.subjectiveWords = subjectiveWords;
    }

    public void readSentiwordLexicon() throws IOException {
        String line;
        //    FileInputStream fstream = new FileInputStream(filename);
       /* File currentDirectory = new File(new File("lexicon").getAbsolutePath());
        System.out.println(currentDirectory.getCanonicalPath());
        System.out.println(currentDirectory.getAbsolutePath());*/
        BufferedReader br = new BufferedReader(new FileReader("lexicon"));
     //  BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            while ((line = br.readLine()) != null) {
         if (line.startsWith("#")) {
         continue;
         }
         String[] parts = line.split("\\s+");
         Record r;
         try {

         String[] synTermsSplit = parts[4].split(" ");
         for (String syn : synTermsSplit) {
         String[] synTermAndRank = syn.split("#");
         String synTerm = synTermAndRank[0];

         r = new Record(synTerm, Double.valueOf(parts[2]) + 0.0001, Double.valueOf(parts[3]) + 0.0001);
         int index = Math.abs(synTerm.hashCode());
         lexicon.put(index, r);

         }
         } catch (Exception ex) {
         continue;
         }

         }
        //   br.close();
    }

    public Record findWord(String word) {
        Record r = new Record();
        int hash = word.hashCode();
        if (lexicon.containsKey(hash)) {
            r = lexicon.get(hash);
        }
        return r;

    }

    /* public Boolean isNegation(String word) {

     Boolean negate = false;
     int hash = word.hashCode();

     if (negationWords.containsKey(hash)) {
     negate = true;
     }
     return negate;
     }*/
    public int isPositive(String word) {

        Record r = findWord(word);
        DecimalFormat format = new DecimalFormat("#0.000");

        sumOfPositiveScore += r.getPositiveWeight();
        sumOfNegativeScore += r.getNegativeWeight();
        sumOfPositiveScore = Double.parseDouble(format.format(sumOfPositiveScore));
        sumOfNegativeScore = Double.parseDouble(format.format(sumOfNegativeScore));
        if (r.getPositiveWeight() > r.getNegativeWeight() && r.getPositiveWeight() > 0.5) {
            return 1;
        }
        if (r.getPositiveWeight() < r.getNegativeWeight() && r.getNegativeWeight() > 0.5) {
            return 2;
        }

        if (r.getPositiveWeight() > r.getNegativeWeight()) {
            return 3;
        }
        if (r.getPositiveWeight() < r.getNegativeWeight()) {
            return 4;
        }
        if (r.getPositiveWeight() == r.getNegativeWeight() && r.getNegativeWeight() != 0) {
            return 5;
        }
        return 0;
    }

    public void count(String sentence, StanfordCoreNLP pipeline) throws IOException {
        String[] splited = sentence.split(" ");
        int positiveWord = 0;
        int equality = 0;
        //  Negation ng = new Negation();
        Polarity pol = new Polarity();
        //ng.setPipeline(pipeline);
        if (sentence.length() < 40) {
            // ng.isNegation(sentence);
            pol.searchForIntesfire(sentence);
            String polar = pol.getPolarity();
            if (polar.equals("positive intensifire")) {
                strongPositive++;
                positiveWords++;
            } else if (polar.equals("negative intensifire")) {
                strongNegative++;
                negativeWords++;

            }
        }
        for (int i = 0; i < splited.length; i++) {

            // strongPositive=1 , strongNegative=2 , positive=3 , negative=4, class= 5, Null=0
            positiveWord = isPositive(splited[i]);
            if (positiveWord == 1) {
                strongPositive++;
                subjectiveWords++;

            } else if (positiveWord == 2) {
                strongNegative++;
                subjectiveWords++;
            } else if (positiveWord == 3) {
                positiveWords++;
                subjectiveWords++;

            } else if (positiveWord == 4) {
                negativeWords++;
                subjectiveWords++;
            } else if (positiveWord == 5) {
                equality++;
                /* if(className.equals("negative") || sumOfNegativeScore > sumOfPositiveScore){
                 negativeWords++;
                 subjectiveWords++;
                 }
                 else if(className.equals("positive") || sumOfNegativeScore < sumOfPositiveScore){
                 positiveWords++;
                 subjectiveWords++;
                 }*/

                if (i != splited.length - 1) {
                    if (isPositive(splited[i + 1]) == 1 || isPositive(splited[i + 1]) == 3) {
                        positiveWords++;
                        subjectiveWords++;
                    } else if (isPositive(splited[i + 1]) == 2 || isPositive(splited[i + 1]) == 4) {
                        negativeWords++;
                        subjectiveWords++;
                    }
                }
            } else if (positiveWord == 0) {
                neutralWords++;
            }

            //}
        }
        int negative, positive;
      //  negative = ng.getNumOfNegative();
        //  positive = ng.getNumOfPositive();
        // negativeWords += negative;
        //   positiveWords += positive;
        if (sumOfNegativeScore > sumOfPositiveScore) {
            negativeWords += equality;
            subjectiveWords += equality;
        } else if (sumOfNegativeScore < sumOfPositiveScore) {
            positiveWords += equality;
            subjectiveWords += equality;
        }

    }

    public void testFunction() {
        System.out.println("positive= " + positiveWords + "  negative= " + negativeWords);
    }

}
