package view;

import util.Keyword;

import java.io.BufferedReader;
import java.io.IOException;

public class GameView {

    private final BufferedReader inputReader;

    public GameView(final BufferedReader inputReader) {
        this.inputReader = inputReader;
    }

    public String getInput() throws IOException {
        return inputReader.readLine();
    }

    public boolean askWantMoreCard(final String name) throws IOException {
        AskView.askWantMoreCard(name);

        String answer = getInput();
        while (!Keyword.isValidCommand(answer)) {
            AskView.askWantMoreCard(name);
            answer = getInput();
        }

        return Keyword.isCommandYes(answer);
    }
}
