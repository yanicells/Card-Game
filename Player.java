/**
        The Player class represents a player with a name, tokens, and a set of cards.
        It provides methods to draw cards, claim tokens, discard cards, and manage the player's hand.

        @author Edrian Miguel E. Capistrano (240939)
        @version December 5, 2024

        I have not discussed the Java language code in my program
        with anyone other than my instructor or the teaching assistants
        assigned to this course.

        I have not used Java language code obtained from another student,
        or any other unauthorized source, either modified or unmodified.

        If any Java language code or documentation used in my program
        was obtained from another source, such as a textbook or website,
        that has been clearly noted with a proper citation in the comments
        of my program.
 */

public class Player {
    private String name;
    private int tokens;
    private boolean isHandFull;
    private Card[] cards;

    /**
     * Constructor initializes the player with a name and an empty hand of cards.
     *
     * @param n the name of the player
     **/
    public Player(String n) {
        this.name = n;
        this.tokens = 0;
        cards = new Card[5];
        this.isHandFull = false;
    }

    /**
     * Increases the player's token count by 1.
     **/
    public void claimToken() {
        this.tokens += 1;
    }

    /**
     * Discards the player's active card and shifts remaining cards.
     **/
    public void discard() {
        cards[0] = null;
        for (int i = 0; i < cards.length - 1; i++) {
            cards[i] = cards[i + 1];
        }
        cards[cards.length - 1] = null;
        this.isHandFull = false;
    }

    /**
     * Draws a card and adds it to the player's hand where there is a vacant.
     *
     * @param c the card to draw
     **/
    public void drawCard(Card c) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = c;
                isHandFull = (i == cards.length - 1);
                return;
            }
        }
        isHandFull = true;
    }

    /**
     * Returns the active card in the player's hand.
     *
     * @return the active card
     **/
    public Card getActiveCard() {
        return cards[0];
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the number of tokens the player has.
     *
     * @return the player's token count
     **/
    public int getTokens() {
        return tokens;
    }

    /**
     * Returns whether the player's hand is full.
     *
     * @return true if the hand is full, false otherwise
     **/
    public boolean handIsFull() {
        return isHandFull;
    }

    /**
     * Returns a status report of the player's cards, displaying their name and health.
     *
     * @return a string representing the status of the player's cards
     **/
    public String statusReport() {
        String display = String.format("%s\n%11s : %3s\n%11s : %3s\n%11s : %3s\n%11s : %3s\n%11s : %3s\n",
                getName().toUpperCase(), cards[0].getName(), cards[0].getHealth(),
                cards[1].getName(), cards[1].getHealth(),
                cards[2].getName(), cards[2].getHealth(),
                cards[3].getName(), cards[3].getHealth(),
                cards[4].getName(), cards[4].getHealth());
        return display;
    }

    /**
     * Swaps the active card with another card based on the highest determining power. It makes use of the private method find() if the player has
     * another card to swap with.
     *
     * @return a string indicating the result of the swap
     **/
    public String swap() {
        int tracker = 0;
        for (Card card : cards) {
            if (card != null) {
                tracker++;
            }
        }
        if (tracker < 2) {
            return this.getName() + " has no other card to swap with. Turn forfeited.\n";
        }
        int index = findCard();
        if (index != -1) {
            Card temp = cards[index];
            cards[index] = cards[0];
            cards[0] = temp;
            return cards[0].getName() + " is now active with " + cards[0].getHealth() + " health.\n";
        } else {
            return this.getName() + " has no other card to swap with. Turn forfeited.\n";
        }
    }

    /**
     * Finds the best card to swap with based on health and power.
     *
     * @return the index of the card to swap with, or -1 if no suitable card is found
     **/
    private int findCard() {
        int indexTracker = -1;
        int largestProduct = 0;
        for (int i = 1; i < cards.length; i++) {
            if (cards[i] != null && cards[i].getHealth() > 0) {
                int product = cards[i].getHealth() * cards[i].getPower();
                if (product > largestProduct) {
                    largestProduct = product;
                    indexTracker = i;
                }
            }
        }
        return indexTracker;
    }

    /**
     * Returns the player's cards. This will be used for my GUI add-on.
     *
     * @return the array of cards the player holds
     **/
    public Card[] getCards(){
        return this.cards;
    }
}
