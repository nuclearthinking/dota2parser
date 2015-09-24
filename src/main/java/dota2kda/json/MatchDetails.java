package dota2kda.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDetails {
    private DetailsResult result;

    public DetailsResult getResult() {
        return result;
    }
}
