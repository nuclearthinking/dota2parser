import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.MatchDetails;
import json.MatchHistory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by onifent
 */

public class ApiController {
    final String apiKey = "&key=D4165C76B487633AFAD2D89A87CCA831";
    final String accountIdVar = "&account_id=";
    final String historyBaseUrl = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?matches_requested=";
    final String lobbyType = "&lobby_type=7";
    final String detailsBaseUrl = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?";
    final String matchIdVar = "match_id=";

    public MatchHistory getMatchHistory(int matchesAmount, long accountId) throws IOException {
        URL request = new URL(historyBaseUrl + matchesAmount + accountIdVar + accountId + lobbyType + apiKey);
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jp;
        try {
            jp = jsonFactory.createParser(request);
        } catch (IOException e) {
            throw new RuntimeException("VALVE Api currently available, try later");
        }
        ObjectMapper mapper = new ObjectMapper();
        MappingIterator<MatchHistory> last20matchHistory = mapper.readValues(jp, MatchHistory.class);
        return last20matchHistory.next();
    }

    public MatchDetails getMatchDetails(long matchId) throws IOException {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        URL request = new URL(detailsBaseUrl + matchIdVar + matchId + apiKey);
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jp;
        try {
            jp = jsonFactory.createParser(request);
        } catch (IOException e) {
            throw new RuntimeException("VALVE Api currently available, try later");
        }
        ObjectMapper mapper = new ObjectMapper();
        MappingIterator<MatchDetails> matchDetails = mapper.readValues(jp, MatchDetails.class);
        return matchDetails.next();
    }
}
