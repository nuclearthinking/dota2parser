package dota2kda.controller;

import dota2kda.Dota2Kda;
import dota2kda.KdaCalculator;
import dota2kda.json.MatchDetails;
import dota2kda.json.MatchHistory;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private Dota2Kda d2k = new Dota2Kda();

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
        button.setDisable(true);
        final String[] s = new String[1];
        new Thread(() -> {
            s[0] = calculationStart();
        }).start();
        button.setDisable(false);
    }

    protected String calculationStart() {
        double progress = 0.0;
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
        ArrayList<MatchDetails> matchDetailses = new ArrayList<>();
        for (Long matchId : matchesList) {
            try {
                progress += 0.05;
                matchDetailses.add(api.getMatchDetails(matchId));
                final double finalProgress = progress;
                Platform.runLater(() -> progressBar.setProgress(finalProgress));
            } catch (IOException e) {
                progress += 0.05;
                final double finalProgress = progress;
                resultText.setText(e.getMessage());
                Platform.runLater(() -> progressBar.setProgress(finalProgress));
            }
        }
        List<int[]> scoreList = calc.playerScore(matchDetailses, accountId);
        double averageKDA = calc.averageKda(scoreList);
        resultText.setText("Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA);
        return "Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA;
    }

    public Task createWorker() {

        return new Task() {
            @Override
            protected Object call() throws Exception {
                double progress = 0.0;
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
                ArrayList<MatchDetails> matchDetailses = new ArrayList<>();
                for (Long matchId : matchesList) {
                    try {
                        progress += 0.05;
                        matchDetailses.add(api.getMatchDetails(matchId));
                        final double finalProgress = progress;
                        Platform.runLater(() -> progressBar.setProgress(finalProgress));
                    } catch (IOException e) {
                        progress += 0.05;
                        final double finalProgress = progress;
                        resultText.setText(e.getMessage());
                        Platform.runLater(() -> progressBar.setProgress(finalProgress));
                    }
                }
                List<int[]> scoreList = calc.playerScore(matchDetailses, accountId);
                double averageKDA = calc.averageKda(scoreList);
                String s = "Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA;
                resultText.setText(s);
                return true;
            }
        };
    }

}
