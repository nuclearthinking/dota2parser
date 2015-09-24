package dota2kda.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchHistory {

    private MatchesResult result;

    public MatchesResult getResult() {
        return result;
    }
}
