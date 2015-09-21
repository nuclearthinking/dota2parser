package json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMatchDetails {
    private DetailsResult result;

    public DetailsResult getResult() {
        return result;
    }
}
