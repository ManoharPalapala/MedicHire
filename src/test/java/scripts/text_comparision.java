package scripts;

import org.apache.commons.text.similarity.JaroWinklerDistance;

public class text_comparision {

    public static void main(String[] args) {
        JaroWinklerDistance jaroWinkler = new JaroWinklerDistance();
        String str1 = "Partizan Belgrade";
        String str2 = "Partizan Belgrade";

        double similarity = jaroWinkler.apply(str1, str2);
        double percentage = 100-(similarity * 100); // Convert to percentage
        System.out.println("Matching Percentage: " + percentage + "%");
    }

}
