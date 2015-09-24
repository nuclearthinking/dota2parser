package dota2kda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by onifent
 */

public class UserInputHelper {

    public long getAccountId() throws IOException {
        int ticket = 0;
        String input;
        boolean isCorrectString = false;

        while (!isCorrectString) {
            System.out.println("Enter your Steam ID ");
            input = inputString();
            isCorrectString = checkUserInput(input);
            if (isCorrectString) {
                ticket = Integer.parseInt(input);
                break;
            }
            System.out.println("Icorrect input:(");
            System.out.println("");
        }
        System.out.println("Taking data from VALVE ...");
        return ticket;
    }

    protected String inputString() throws IOException {
        String inputLine;
        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
        inputLine = is.readLine();

        return inputLine.toLowerCase();
    }

    protected boolean checkUserInput(String input) {
        if (input == null) {
            return false;
        }
        try {
            long tryLong = Long.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;

    }
}
