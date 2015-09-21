import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.GetMatchHistory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Dota2Api {
    private final String apiKey = "&key=D4165C76B487633AFAD2D89A87CCA831";
    private final String accountId = "83319528";
    private final String accountIdExpression = "&account_id=";
    private final String historyBase = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?matches_requested=";
    private final String lobbyType = "&lobby_type=7";
    private final int amountOfMathces = 10;


    public URL apiRequestBuilder(int mathces, String accountId) {
        String request = historyBase + mathces + accountIdExpression + accountId + lobbyType + apiKey;
        URL requestUrl = null;
        try {
            requestUrl = new URL(request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }

    public static void main(String[] args) throws IOException {
        Dota2Api d2a = new Dota2Api();
        d2a.dota2ApiParser();
    }

    public void dota2ApiParser() throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jp = jsonFactory.createParser(apiRequestBuilder(20, accountId));
        ObjectMapper mapper = new ObjectMapper();
        MappingIterator<GetMatchHistory> last20matchHistory = mapper.readValues(jp, GetMatchHistory.class);
        GetMatchHistory last20matchHistory2 = last20matchHistory.next();
        System.out.println(last20matchHistory2.getResult().getStatus());


    }

}
