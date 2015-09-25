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
        new Thread(this::calculationTask).start();
    }

    protected void calculationTask() {
        double progress = 0.0;
        double step = 0;

        int accountId = Integer.parseInt(steamIdTextArea.getText());
        MatchHistory history = null;
        try {
            history = api.getMatchHistory(MATCHES_AMOUNT, accountId);
        } catch (RuntimeException e) {
            Platform.runLater(() -> resultText.setText(e.getLocalizedMessage()));
            Platform.runLater(() -> button.setDisable(false));
        }
        List<Long> matchesList = calc.matchIdList(history);
        step = (double) 1 / matchesList.size();
        ArrayList<MatchDetails> matchDetailses = new ArrayList<>();
        for (Long matchId : matchesList) {
            try {
                progress += step;
                matchDetailses.add(api.getMatchDetails(matchId));
                final double finalProgress = progress;
                Platform.runLater(() -> progressBar.setProgress(finalProgress));
            } catch (RuntimeException e) {
                progress += step;
                final double finalProgress = progress;
                Platform.runLater(() -> progressBar.setProgress(finalProgress));
            }
        }
        List<int[]> scoreList = calc.playerScore(matchDetailses, accountId);
        double averageKDA = calc.averageKda(scoreList);
        Platform.runLater(() -> resultText.setText("Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA));
        Platform.runLater(() -> button.setDisable(false));
    }


}
