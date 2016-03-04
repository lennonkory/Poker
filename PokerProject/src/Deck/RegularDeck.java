package Deck;

import HandRankings.Hand;
import java.util.*;

/**
 * Controls a standard 52 card deck. There are 4 suits (clubs, spades, diamonds
 * and hearts) and 13 values (2-ace).
 *
 * @author Kory Bryson
 */
public class RegularDeck extends Deck {

    private final Random random;
    private boolean shuffled = false; //is the deck shuffled

    /**
     * Creates a new deck of regular cards.
     */
    public RegularDeck() {
        super();
        random = new Random();
    }

    @Override
    public void shuffle() {

        shuffled = true;
        deck.clear();

        for (StandardCard.Suit s : StandardCard.Suit.values()) {
            for (StandardCard.Value v : StandardCard.Value.values()) {
                deck.add(new StandardCard(s, v));
            }
        }

    }

    @Override
    public void printDeck() {

        deck.stream().forEach((_item) -> {
            System.out.println(_item.toString());
        });

    }

    @Override
    public Card dealCard() {

        shuffled = false;

        Card c;
        c = deck.get(random.nextInt(deck.size()));
        deck.remove(c);

        return c;
    }

    @Override
    public Collection<Card> dealCards(int numberOfCards) {
        Collection<Card> c = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) {
            c.add(this.dealCard());
        }
        return c;
    }

    /**
     * Gets a random hand. ONLY USED FOR TESTING
     *
     * @return
     */
    public Hand getRandomHand() {

        Hand h = new Hand();

        if (deck.isEmpty()) {
            shuffle();
        }

        for (int i = 0; i < 7; i++) {
            h.addCard(this.dealCard());
        }

        return h;

    }

    @Override
    public boolean removeCard(Card card) {
        return deck.remove(card);
    }

    @Override
    public String[] dealCardNames(int numberOfCards) {
        String cards[] = new String[numberOfCards];

        for (int i = 0; i < numberOfCards; i++) {
            cards[i] = this.dealCard().getName();
        }

        return cards;
    }

    /**
     *
     * @param args
     * @throws NotEnoughCardsException
     */
    public static void main(String[] args) throws NotEnoughCardsException {

        Deck d = new RegularDeck();
        System.out.println("Creating Deck: ");
        d.shuffle();
        System.out.println("Getting Cards: ");

        for (Card c : d.dealCards(3)) {
            System.out.println(c.toString());
        }
        System.out.println("********");
        d.printDeck();
        System.out.println("********");
        d.shuffle();
        d.printDeck();

    }

}
