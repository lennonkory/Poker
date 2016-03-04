package Game;

import Deck.Card;
import Deck.NotEnoughCardsException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kory
 */
public class Texas extends Game {

    enum State {

        PREFLOP, FLOP, TURN, RIVER, NEXTHAND
    };

    private State state;

    public Texas() {
        super();
        state = State.PREFLOP;
        deck.shuffle();
    }

    @Override
    public void nextHand() {

        setPlayersInHand();

        tl.clearCards();

        deck.shuffle();

        dealer = getNextActivePlayer(dealer);//change!!

        info.reset();//puts pot to zero

        getBlindsAnttes();

        playerTurn = getNextActivePlayer(dealer + 2);

        active = playersInHand.get(playerTurn);

        tl.update("DEALING PREFLOP");

        handOver = false;

        System.out.println("dealer: " + playersInHand.get(dealer).name);

        dealPreflop();

    }

    @Override
    public void getBlindsAnttes() {

        int smallLocation = getNextActivePlayer(dealer);
        int bigLocation = getNextActivePlayer(smallLocation);

        int result = playersInHand.get(smallLocation).removeChips(info.small);
        playersInHand.get(smallLocation).called = result;
        info.pot += result;

        result = playersInHand.get(bigLocation).removeChips(info.big);
        playersInHand.get(bigLocation).called = result;
        info.pot += result;

        currentBet = info.big;

    }

    private String createMessage() {

        String options = "fold;";

        if (currentBet == 0) {
            options += " check; bet " + info.big;
        } else {
            options += " call " + (currentBet - active.called) + "; raise " + (currentBet * 2);
        }

        return options;
    }

    @Override
    public void command(Bet bet) throws NotEnoughCardsException{

        switch (bet.choice) {

            case "fold":
                tl.removeCards(active.id);
                removePlayerFromHand(playerTurn);
                break;

            case "call": {
                callNumber++;
                int called = active.called(currentBet);
                info.pot += called;
                tl.updatePot(info.pot);
                break;

            }
            case "check": {
                callNumber++;
                break;
            }
            case "bet": {
                callNumber = 1;
                currentBet = bet.betAmount;
                int called = active.called(currentBet);
                info.pot += called;
                tl.updatePot(info.pot);

                break;
            }
            case "raise": {
                callNumber = 1;
                currentBet = bet.betAmount;
                int called = active.called(currentBet);
                info.pot += called;
                tl.updatePot(info.pot);
                break;
            }
        }

        if (tl.getType().equals("GUI")) {
            active.activate();
            active.updateView(currentBet - active.called, currentBet * 2);
        }

        playerTurn = getNextActivePlayer(playerTurn);
        active = playersInHand.get(playerTurn);
        active.updateView(currentBet - active.called, currentBet * 2);

        if (callNumber == playersStillInHand()) {//move to next turn

            resetCalled();
            callNumber = 0;

            playerTurn = getNextActivePlayer(dealer);
            active = playersInHand.get(playerTurn);

            if (state == State.PREFLOP) {
                tl.update("\nDEALING FLOP\n");
                dealFlop();
                state = State.FLOP;
            } else if (state == State.FLOP) {
                tl.update("\nDEALING TURN\n");
                state = State.TURN;
                dealTurnRiver();

            } else if (state == State.TURN) {
                tl.update("\nDEALING RIVER\n");
                state = State.RIVER;
                dealTurnRiver();
            } else {
                declareWinners();
                state = State.PREFLOP;
                tl.updatePot(0);
                nextHand();
                return;
            }
        }

        if (playersStillInHand() == 1) {
            for (Player p : playersInHand) {
                if (p.isInHand()) {
                    tl.update(p.name + " Wins!");
                    p.addToChips(info.pot);
                }
            }

            nextHand();
            return;
        }

        this.updatePlayer(active.name + " Your turn. " + createMessage());
        active.activate();//Activate Next Player

    }

    @Override
    public void changeState() {
    }

    @Override
    public void notifyPlayer() {

        this.updatePlayer(active.name + " Your turn. " + createMessage());
        active.updateView(currentBet - active.called, currentBet * 2);
        tl.updatePot(info.pot);
        System.out.println("Active: NOTIFY " + active.name);
        active.activate();

    }

    private void dealPreflop() {

        List<Boolean> inHand = getPlayerInHand();

        String cards[] = new String[2];
        this.tl.dealPreflop(playerTurn, inHand);

        for (Player p : playersInHand) {
            if (p.isInHand()) {
                Card one;
                try {
                    one = deck.dealCard();
                    cards[0] = one.getName();
                    p.addCard(one);
                } catch (NotEnoughCardsException ex) {
                    Logger.getLogger(Texas.class.getName()).log(Level.SEVERE, null, ex);
                }
                Card two;
                try {
                    two = deck.dealCard();
                    cards[1] = two.getName();
                    p.addCard(two);
                } catch (NotEnoughCardsException ex) {
                    Logger.getLogger(Texas.class.getName()).log(Level.SEVERE, null, ex);
                }
                          
                p.setCards();
                p.viewCards(p.id, cards);
                
            }
        }
    }

    public void dealFlop() throws NotEnoughCardsException {

        currentBet = 0;

        Collection<Card> cards = deck.dealCards(3);
        String cardNames[] = new String[3];

        int i = 0;

        for (Card c : cards) {
            cardNames[i] = c.getName();
            i++;
        }

        playersInHand.stream().forEach((p) -> {
            p.addCards(cards);
        });

        this.tl.dealFlop(cardNames);

    }

    private void dealTurnRiver() throws NotEnoughCardsException {

        currentBet = 0;

        Card card = deck.dealCard();

        playersInHand.stream().forEach((p) -> {
            p.addCard(card);
        });

        if (state == State.TURN) {
            this.tl.dealTurn(card.getName());
        }

        if (state == State.RIVER) {
            this.tl.dealRiver(card.getName());
        }

    }

}
