package dota2kda.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsResult {

    private ArrayList<Player> players;
    private boolean radiant_win;
    private int duration;
    private long start_time;
    private long match_id;
    private long match_seq_num;
    private int tower_status_radiant;
    private int tower_status_dire;
    private int barracks_status_radiant;
    private int barracks_status_dire;
    private int cluster;
    private int first_blood_time;
    private int lobby_type;
    private int human_players;
    private int leagueid;
    private int positive_votes;
    private int negative_votes;
    private int game_mode;
    private int engine;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isRadiant_win() {
        return radiant_win;
    }

    public int getDuration() {
        return duration;
    }

    public long getStart_time() {
        return start_time;
    }

    public long getMatch_id() {
        return match_id;
    }

    public long getMatch_seq_num() {
        return match_seq_num;
    }

    public int getTower_status_radiant() {
        return tower_status_radiant;
    }

    public int getTower_status_dire() {
        return tower_status_dire;
    }

    public int getBarracks_status_radiant() {
        return barracks_status_radiant;
    }

    public int getBarracks_status_dire() {
        return barracks_status_dire;
    }

    public int getCluster() {
        return cluster;
    }

    public int getFirst_blood_time() {
        return first_blood_time;
    }

    public int getLobby_type() {
        return lobby_type;
    }

    public int getHuman_players() {
        return human_players;
    }

    public int getLeagueid() {
        return leagueid;
    }

    public int getPositive_votes() {
        return positive_votes;
    }

    public int getNegative_votes() {
        return negative_votes;
    }

    public int getGame_mode() {
        return game_mode;
    }

    public int getEngine() {
        return engine;
    }
}
