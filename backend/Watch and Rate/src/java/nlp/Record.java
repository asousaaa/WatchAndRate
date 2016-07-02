package nlp;
public class Record {

	private String word;
	private double positiveWeight;
	private double negativeWeight;
	
	public Record() {
		super();
		this.word = "";
		this.positiveWeight = 0;
		this.negativeWeight = 0;
	}
	
	
	
	public Record(String word, double positiveWeight, double negativeWeight) {
		super();
		this.word = word;
		this.positiveWeight = positiveWeight;
		this.negativeWeight = negativeWeight;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getPositiveWeight() {
		return positiveWeight;
	}
	public void setPositiveWeight(double positiveWeight) {
		this.positiveWeight = positiveWeight;
	}
	public double getNegativeWeight() {
		return negativeWeight;
	}
	public void setNegativeWeight(double negativeWeight) {
		this.negativeWeight = negativeWeight;
	}
	
	
}
