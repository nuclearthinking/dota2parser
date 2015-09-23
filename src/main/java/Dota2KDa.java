import json.MatchDetails;
import json.MatchHistory;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dota2KDa extends JDialog {
    private static final int MATCHES_AMOUNT = 20;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel text;
    private JButton calculateButton;
    private ApiController api = new ApiController();
    private KdaCalculator calc = new KdaCalculator();

    public Dota2KDa() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
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
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Dota2KDa dialog = new Dota2KDa();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
