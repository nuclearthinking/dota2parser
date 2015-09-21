import json.MatchDetails;
import json.MatchHistory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dota2Parser {
    private static final int MATCHES_AMOUNT = 20;


    public static void main(String[] args) throws IOException {
        UserInputHelper helper = new UserInputHelper();
        ApiController api = new ApiController();
        KdaCalculator calc = new KdaCalculator();
        long accountId = helper.getAccountId();
        long startTime = System.currentTimeMillis();
        MatchHistory history = null;
        try {
            history = api.getMatchHistory(MATCHES_AMOUNT, accountId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        List<Long> matchesList = calc.matchIdList(history);
        ArrayList<MatchDetails> matchDetailses = calc.mathDetailsArray(matchesList);
        List<int[]> scoreList = calc.playerScore(matchDetailses, accountId);
        double averageKDA = calc.averageKda(scoreList);
        System.out.println("Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA);
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

}
