package json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    private long account_id;
    private int player_slot;
    private int hero_id;
    private int kills;
    private int deaths;
    private int assists;

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public long getAccount_id() {
        return account_id;
    }

    public int getPlayer_slot() {
        return player_slot;
    }

    public int getHero_id() {
        return hero_id;
    }
}
