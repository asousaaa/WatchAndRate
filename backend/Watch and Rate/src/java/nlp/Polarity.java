package nlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Polarity {

    private String polarity;
    private static String intensifires[];

    Polarity() {
        polarity = "";
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public static void readIntensifier() {
        String file = "";
        try (BufferedReader br = new BufferedReader(new FileReader("intensifires.txt"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                file += sCurrentLine + "\n";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        intensifires = file.split("\\R+");
    }

    public void searchForIntesfire(String sentence) throws IOException {

        String[] splited = sentence.split("\\s+");

        polarity = "";
        Sentiword word = new Sentiword();
        int isPositive = 0;
        for (int i = 0; i < splited.length; i++) {
            for (int j = 0; j < intensifires.length; j++) {
                if (splited[i].equals(intensifires[j]) && i!=splited.length-1) {
                    isPositive = word.isPositive(splited[i + 1]);
                    if (isPositive == 3 || isPositive == 1) {
                        polarity = "positive intensifire";
                    } else if (isPositive == 4 || isPositive == 2) {
                        polarity = "negative intensifire";
                    }
                    System.out.println(splited[i] + "->" + polarity);
                }
            }
        }
    }

    public static String[] getIntensifires() {
        return intensifires;
    }

}
