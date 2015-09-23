import json.MatchDetails;
import json.MatchHistory;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dota2ParserGUI extends JDialog {
    private static final int MATCHES_AMOUNT = 20;
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel text;
    private JButton calculateButton;
    private JProgressBar progressBar1;
    private JLabel steamID;
    private ApiController api = new ApiController();
    private KdaCalculator calc = new KdaCalculator();

    public Dota2ParserGUI() {
        setContentPane(contentPane);
        setModal(true);

        progressBar1.setValue(0);
        progressBar1.setMaximum(100);
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateButton.setEnabled(false);
                try {
                    onCalculate();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        textField1.addContainerListener(new ContainerAdapter() {
        });
    }

    private void onCalculate() throws IOException {
        String s1 = textField1.getText();
        int accountId = Integer.parseInt(s1);
        MatchHistory history = null;
        try {
            history = api.getMatchHistory(MATCHES_AMOUNT, accountId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Task can't be completed");
            throw new RuntimeException("Closing ...");
        }
        List<Long> matchesList = calc.matchIdList(history);
        ArrayList<MatchDetails> matchDetailses = calc.mathDetailsArray(matchesList);
        List<int[]> scoreList = calc.playerScore(matchDetailses, accountId);
        double averageKDA = calc.averageKda(scoreList);
        String s = "Average KDA for last " + MATCHES_AMOUNT + " rating games = " + averageKDA;
        text.setText(s);
    }


    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        Dota2ParserGUI dialog = new Dota2ParserGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
