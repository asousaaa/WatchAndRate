package nlp;

public class Sentence {

    private String className;
    private String sentence;
    private Integer positiveWords;
    private Integer negativeWords;
    private Integer strongPositive;
    private Integer strongNegative;
    private Integer subjectiveWords;
    private Integer neutralWords;
    private Integer numOfAdjective;
    private Integer numOfAdverb;
    private double sumOfNegativeScore;
    private double sumOfPositiveScore;

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

    
    
    public Sentence(String sent) {
        sentence = sent;
        className= "";
        positiveWords = 0;
        negativeWords = 0;
        strongPositive = 0;
        strongNegative = 0;
        subjectiveWords = 0;
        neutralWords = 0;
        numOfAdjective = 0;
        numOfAdverb = 0;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Integer getNumOfAdjective() {
        return numOfAdjective;
    }

    public void setNumOfAdjective(Integer numOfAdjective) {
        this.numOfAdjective = numOfAdjective;
    }

    public Integer getNumOfAdverb() {
        return numOfAdverb;
    }

    public void setNumOfAdverb(Integer numOfAdverb) {
        this.numOfAdverb = numOfAdverb;
    }

    
    
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getPositiveWords() {
        return positiveWords;
    }

    public void setPositiveWords(Integer positiveWords) {
        this.positiveWords = positiveWords;
    }

    public Integer getNegativeWords() {
        return negativeWords;
    }

    public void setNegativeWords(Integer negativeWords) {
        this.negativeWords = negativeWords;
    }

    public Integer getSubjectiveWords() {
        return subjectiveWords;
    }

    public void setSubjectiveWords(Integer subjectiveWords) {
        this.subjectiveWords = subjectiveWords;
    }

    public Integer getStrongPositive() {
        return strongPositive;
    }

    public void setStrongPositive(Integer strongPositive) {
        this.strongPositive = strongPositive;
    }

    public Integer getStrongNegative() {
        return strongNegative;
    }

    public void setStrongNegative(Integer strongNegative) {
        this.strongNegative = strongNegative;
    }

    public Integer getNeutralWords() {
        return neutralWords;
    }

    public void setNeutralWords(Integer neutralWords) {
        this.neutralWords = neutralWords;
    }

}
