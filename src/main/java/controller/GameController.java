package controller;

import model.deck.Deck;
import model.player.dto.PlayerResponse;
import model.players.Players;
import view.AlertView;
import view.AskView;
import view.GameView;
import view.ScoreBoardView;
import view.StatusView;

import java.io.IOException;
import java.util.List;

import static util.Keyword.DEALER;
import static util.Keyword.LOSE;
import static util.Keyword.SAME;
import static util.Keyword.WIN;
import static util.Rule.DEALER_MORE_SCORE;
import static util.Rule.GOAL_SCORE;
import static util.Rule.INIT_GIVE_CARDS;

public class GameController {

    private final Deck deck;
    private final GameView view;
    private final Players players;

    public GameController(final Deck deck, final GameView view) throws IOException {
        this.deck = deck;
        this.view = view;
        this.players = joinPlayers();
    }

    public void play() throws IOException {
        giveInitialCards();
        printPlayersInitStatus();

        moreCardsGive();

        alertDealerCard();

        alertEachPlayerScore();
        alertResult();
    }

    private void giveInitialCards() {
        AlertView.alertGiveInitCard(DEALER.getValue(), players.getPlayerNamesExceptDealer(), INIT_GIVE_CARDS.getValue());
        players.giveInitialCards(deck, INIT_GIVE_CARDS.getValue());
    }

    private void printPlayersInitStatus() {
        for (PlayerResponse response : players.playersResponse()) {
            if (response.isDealerResponse()) {
                StatusView.printPersonDefaultStatus(response.getNameValue(), response.getCardsNameWithSecret());
                continue;
            }
            StatusView.printPersonDefaultStatus(response.getNameValue(), response.getCardsName());
        }
        System.out.println();
    }

    private void moreCardsGive() throws IOException {
        for (String player : players.getPlayerNameValuesExceptDealer()) {
            while (players.isNotExceed(player, GOAL_SCORE.getValue()) && view.askWantMoreCard(player)) {
                players.giveOneCard(deck, player);
                PlayerResponse response = players.getPlayerResponseByName(player);
                StatusView.printPersonDefaultStatus(response.getNameValue(), response.getCardsName());
            }
        }
    }

    private void alertDealerCard() {

        if (players.isEnoughThanDealerScore(DEALER.getValue(), DEALER_MORE_SCORE.getValue())) {
            AlertView.alertDealerEnough(DEALER.getValue(), DEALER_MORE_SCORE.getValue());
            return;
        }
        giveOneCardToDealer();
    }

    private void giveOneCardToDealer() {
        AlertView.alertGiveDealerCard(DEALER.getValue(), DEALER_MORE_SCORE.getValue());
        players.giveOneCard(deck, DEALER.getValue());
    }

    private void alertEachPlayerScore() {
        for (PlayerResponse response : players.playersResponse()) {
            StatusView.printPlayerResultStatus(response.getNameValue(), response.getCardsName(), response.getScore());
        }
    }

    private void alertResult() {
        AlertView.alertFinalGrade();

        List<PlayerResponse> playerResponsesWithGrade = players.calculateEachGradeWithGoal(GOAL_SCORE.getValue());
        PlayerResponse dealerResponse = playerResponsesWithGrade.get(0);
        for (PlayerResponse response : playerResponsesWithGrade) {
            if (response.equals(dealerResponse)) {
                ScoreBoardView.printDealerScoreBoard(response.getNameValue(),
                        players.getDealerWin(dealerResponse.getGrade()),
                        players.getDealerSame(dealerResponse.getGrade()),
                        players.getDealerLose(dealerResponse.getGrade()));
                continue;
            }
            if (response.isUpThanDealer(dealerResponse)) {
                ScoreBoardView.printGrade(response.getNameValue(), WIN.getValue());
                continue;
            }
            if (response.isSameWithDealer(dealerResponse)) {
                ScoreBoardView.printGrade(response.getNameValue(), SAME.getValue());
                continue;
            }
            ScoreBoardView.printGrade(response.getNameValue(), LOSE.getValue());
        }
    }

    private Players joinPlayers() throws IOException {
        AskView.askPlayerNames();
        String nameInput = view.getInput();
        return Players.from(nameInput);
    }
}
