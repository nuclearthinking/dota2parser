import java.util.ArrayList;
import java.util.List;

public class CdaCalculate {

    public final String apiKey = "D4165C76B487633AFAD2D89A87CCA831";

    public static void main(String[] args) {

        List<Double> matchKda = new ArrayList<Double>();

        List<int[]> rowKda = new ArrayList<int[]>();

        rowKda.add(new int[]{9, 9, 16});
        rowKda.add(new int[]{5, 8, 10});
        rowKda.add(new int[]{8, 13, 24});
        rowKda.add(new int[]{6, 7, 26});
        rowKda.add(new int[]{7, 12, 7});
        rowKda.add(new int[]{6, 10, 10});
        rowKda.add(new int[]{5, 11, 7});
        rowKda.add(new int[]{5, 9, 7});

        for (int[] match : rowKda) {
            double matchKdaResult = ((double) match[0] + (double) match[2]) / (double) match[1];
            matchKda.add(matchKdaResult);
        }

        double kdaSumm = 0.0;
        for (Double match : matchKda) {
            System.out.println(match);
            kdaSumm += match;
        }

        double averageKda = kdaSumm / rowKda.size();

        System.out.println("Средний кда = " + averageKda);
    }



}
