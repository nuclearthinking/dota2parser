package dota2kda.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    private long match_id;
    private long match_seq_num;
    private long start_time;
    private int lobby_type;
    private int radiant_team_id;
    private int dire_team_id;
    private ArrayList<Player> players;

    public long getMatch_id() {
        return match_id;
    }

    public long getMatch_seq_num() {
        return match_seq_num;
    }

    public long getStart_time() {
        return start_time;
    }

    public int getLobby_type() {
        return lobby_type;
    }

    public int getRadiant_team_id() {
        return radiant_team_id;
    }

    public int getDire_team_id() {
        return dire_team_id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
