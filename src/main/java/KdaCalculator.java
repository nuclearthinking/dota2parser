import json.Match;
import json.MatchDetails;
import json.MatchHistory;
import json.Player;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onifent
 */

public class KdaCalculator {
    Dota2Parser dota2Parser = new Dota2Parser();

    private ApiController api = new ApiController();

    public List<Long> matchIdList(MatchHistory history) {
        List<Long> matchIdList = new ArrayList<Long>();

        ArrayList<Match> matches = history.getResult().getMatches();
        for (Match match : matches) {
            matchIdList.add(match.getMatch_id());
        }
        return matchIdList;
    }

    public ArrayList<MatchDetails> mathDetailsArray(List<Long> matchList) throws IOException {
        ArrayList<MatchDetails> matchDetailses = new ArrayList<MatchDetails>();
        for (Long matchId : matchList) {
            try {
                matchDetailses.add(api.getMatchDetails(matchId));
            } catch (RuntimeException e) {
                System.out.println(e);
                break;
            }
        }
        return matchDetailses;
    }

    public List<int[]> playerScore(ArrayList<MatchDetails> matchDetailses, long accountId) {
        List<int[]> rowKda = new ArrayList<int[]>();
        for (MatchDetails matchDetails : matchDetailses) {
            ArrayList<Player> players = matchDetails.getResult().getPlayers();
            for (Player player : players) {
                if (player.getAccount_id() == accountId) {
                    rowKda.add(new int[]{player.getKills(), player.getDeaths(), player.getAssists()});
                    break;
                }
            }
        }
        return rowKda;
    }

    public double averageKda(List<int[]> score) {
        List<Double> matchKda = new ArrayList<Double>();
        for (int[] match : score) {
            if (match[1] == 0) {
                double matchKdaResult = new BigDecimal((double) match[0] + (double) match[2]).setScale(3, RoundingMode.UP).doubleValue();
                matchKda.add(matchKdaResult);
            } else {
                double matchKdaResult = new BigDecimal(((double) match[0] + (double) match[2]) / (double) match[1]).setScale(3, RoundingMode.UP).doubleValue();
                matchKda.add(matchKdaResult);
            }
        }
        double kdaSumm = 0.0;
        for (double match : matchKda) {
            kdaSumm += match;
        }
        return new BigDecimal(kdaSumm / score.size()).setScale(3, RoundingMode.UP).doubleValue();
    }
}
