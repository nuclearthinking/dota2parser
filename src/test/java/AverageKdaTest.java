import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AverageKdaTest {

    KdaCalculator calc = new KdaCalculator();


    @Test
    public void averageKdaNormal() {
        List<int[]> score = new ArrayList<int[]>();
        score.add(new int[]{9, 6, 12});
        score.add(new int[]{11, 9, 23});
        score.add(new int[]{4, 6, 29});
        score.add(new int[]{1, 3, 6});
        score.add(new int[]{13, 3, 21});
        Assert.assertEquals(calc.averageKda(score), 5.29);
    }


    @Test
    public void averageKdaNullScore() {
        List<int[]> score = new ArrayList<int[]>();
        score.add(new int[]{0, 0, 0});
        score.add(new int[]{0, 0, 0});
        score.add(new int[]{0, 0, 0});
        score.add(new int[]{0, 0, 0});
        score.add(new int[]{0, 0, 0});
        score.add(new int[]{0, 0, 0});
        Assert.assertEquals(calc.averageKda(score), 0.0);
    }

    @Test
    public void zeroDeaths(){
        List<int[]> score = new ArrayList<int[]>();
        score.add(new int[]{10, 0, 15});
        score.add(new int[]{10, 0, 15});
        score.add(new int[]{10, 0, 15});
        score.add(new int[]{10, 0, 15});
        Assert.assertEquals(calc.averageKda(score), 25.0);
    }
}
