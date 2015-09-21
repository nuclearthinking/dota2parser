package json;

import java.util.ArrayList;

public class Result {

    private int status;
    private int num_results;
    private int total_results;
    private int results_remaining;
    private ArrayList<Match> matches;

    public int getStatus() {
        return status;
    }

    public int getNum_results() {
        return num_results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getResults_remaining() {
        return results_remaining;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }
}
