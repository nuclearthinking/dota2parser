import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.Match;
import json.MatchDetails;
import json.MatchHistory;
import json.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dota2Api {
    final String apiKey = "&key=D4165C76B487633AFAD2D89A87CCA831";
    long accountId = 0;
    final String accountIdVar = "&account_id=";
    final String history = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?matches_requested=";
    final String lobbyType = "&lobby_type=7";
    final String details = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?";
    final String matchIdVar = "match_id=";

    public URL apiGetMatchHistory(int mathces, long accountId) {
        String request = history + mathces + accountIdVar + accountId + lobbyType + apiKey;
        URL requestUrl = null;
        try {
            requestUrl = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }

    public URL apiGetMatchDetails(long matchId) throws MalformedURLException {
        String request = details + matchIdVar + matchId + apiKey;
        return new URL(request);
    }

    public static void main(String[] args) throws IOException {
        Dota2Api d2a = new Dota2Api();
        d2a.accountId = d2a.getAccountId();
        long startTime = System.currentTimeMillis();
        MatchHistory history = d2a.getMatchHistory();
        List<Long> matchList = d2a.matchIdList(history);
        ArrayList<MatchDetails> detailses = d2a.mathDetailsArray(matchList);
        List<int[]> scoreList = d2a.playerScore(detailses, d2a.accountId);
        double averageKda = d2a.averageKda(scoreList);
        System.out.println("Average KDA for last 20 rating games = " + averageKda);
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public MatchHistory getMatchHistory() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jp = jsonFactory.createParser(apiGetMatchHistory(20, accountId));
        ObjectMapper mapper = new ObjectMapper();
        MappingIterator<MatchHistory> last20matchHistory = mapper.readValues(jp, MatchHistory.class);
        return last20matchHistory.next();
    }

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
            matchDetailses.add(getMatchDetails(matchId));
        }
        return matchDetailses;
    }

    public MatchDetails getMatchDetails(long matchId) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jp = jsonFactory.createParser(apiGetMatchDetails(matchId));
        ObjectMapper mapper = new ObjectMapper();
        MappingIterator<MatchDetails> matchDetails = mapper.readValues(jp, MatchDetails.class);
        MatchDetails match = matchDetails.next();
        return match;
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

    protected String inputString() throws IOException {
        String inputLine = null;
        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
        inputLine = is.readLine();

        return inputLine.toLowerCase();
    }

    protected long getAccountId() throws IOException {
        int ticket = 0;
        String input;
        boolean isCorrectString = false;

        while (!isCorrectString) {
            System.out.println("Enter your Steam ID ");
            input = inputString();
            isCorrectString = checkUserInput(input);
            if (isCorrectString) {
                ticket = Integer.parseInt(input);
                break;
            }
            System.out.println("Icorrect input:(");
            System.out.println("");
        }
        System.out.println("Taking data from VALVE ...");
        return ticket;
    }

    protected boolean checkUserInput(String input) {
        if (input == null) {
            return false;
        }
        try {
            long tryLong = Long.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;

    }

}
