package dota2kda.controller;

import dota2kda.KdaCalculator;
import dota2kda.json.MatchDetails;
import dota2kda.json.MatchHistory;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        steamIdTextArea.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    calculate();
                }
            }
        });
    }

    public void calculate() {
        button.setDisable(true);
        new Thread(this::calculationStart).start();
    }

    protected void calculationStart() {
        double progress = 0.0;
        String s1 = steamIdTextArea.getText();
        int accountId = Integer.parseInt(s1);
        MatchHistory history = null;
        try {
            history = api.getMatchHistory(MATCHES_AMOUNT, accountId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Task can't be completed");
            throw new RuntimeException("Closing ...");
        } catch (IOException e) {
            e.printStackTrace();
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
        Platform.runLater(() -> resultText.setText("Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA));
        Platform.runLater(() -> button.setDisable(false));
    }


}
