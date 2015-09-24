package dota2kda.controller;

import dota2kda.KdaCalculator;
import dota2kda.json.MatchDetails;
import dota2kda.json.MatchHistory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private static final int MATCHES_AMOUNT = 20;
    private ApiController api = new ApiController();
    private KdaCalculator calc = new KdaCalculator();

    @FXML
    private TextField steamIdTextArea;

    @FXML
    private Label steamIdLabel;

    @FXML
    private Button button;

    @FXML
    private Label resultText;

    @FXML
    private ProgressBar progressBar;


    public MainController() {

    }

    @FXML
    private void initialize() {

    }

    public void calculate() {
        String s1 = steamIdTextArea.getText();
        int accountId = Integer.parseInt(s1);
        MatchHistory history = null;
        try {
            try {
                history = api.getMatchHistory(MATCHES_AMOUNT, accountId);
            } catch (IOException e) {
                resultText.setText(e.getMessage());
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Task can't be completed");
            throw new RuntimeException("Closing ...");
        }
        List<Long> matchesList = calc.matchIdList(history);
        ArrayList<MatchDetails> matchDetailses = null;
        try {
            matchDetailses = calc.mathDetailsArray(matchesList);
        } catch (IOException e) {
            resultText.setText(e.getMessage());
        }
        List<int[]> scoreList = calc.playerScore(matchDetailses, accountId);
        double averageKDA = calc.averageKda(scoreList);
        String s = "Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA;
        resultText.setText(s);
    }
}
