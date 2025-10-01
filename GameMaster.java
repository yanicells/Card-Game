import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
        The GameMaster class manages the gameplay between two players, keeping track of game state, player actions, and managing the deck.
        It facilitates player actions such as attack, swap, and my add-ons heal and ultimate abilities, while managing the game's deck and turn sequence.

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

public class GameMaster {
    private Player player1;
    private Player player2;
    private boolean randomCard;
    private boolean newDeck;
    private boolean hasWinner;
    private int turnCounter;
    private ArrayList<Card> deck;

    /**
     * Constructor initializes the game with two players and assembles a deck of cards.
     * It initializes that there is no winner and the turn counter starts at 1.
     *
     * @param a the first player
     * @param b the second player
     **/
    public GameMaster(Player a, Player b) {
        this.player1 = a;
        this.player2 = b;
        deck = new ArrayList<>();
        assembleDeck();
        this.hasWinner = false;
        this.turnCounter = 1;
    }

    /**
     * Constructor initializes the game with two players and allows for a random card setup.
     * This constructor calls another constructor which handles the initialization.
     *
     * @param a the first player
     * @param b the second player
     * @param randomCard boolean flag to determine if the card setup should be random
     **/
    public GameMaster(Player a, Player b, boolean randomCard) {
        this(a, b, randomCard, false);
    }

    /**
     * Constructor initializes the game with two players, random card setup, and option to use a new deck.
     * It initializes that there is no winner and the turn counter starts at 1.
     *
     * @param a the first player
     * @param b the second player
     * @param r boolean flag to determine if the card setup should be random
     * @param n boolean flag to determine if a new deck should be created
     **/
    public GameMaster(Player a, Player b, boolean r, boolean n) {
        this.player1 = a;
        this.player2 = b;
        this.randomCard = r;
        this.newDeck = n;
        this.hasWinner = false;
        this.turnCounter = 1;
        deck = new ArrayList<>();
        if (this.newDeck) {
            newCardDeck();
        } else {
            assembleDeck();
        }
    }

    /**
     * Executes a player's action and processes the turn logic, such as attacking, swapping or using the add-ons.
     * The method determines the attack and target player. It will then perform statements depending on the action.
     * If the action is swap then it will call the swap method.
     * If the action is attack then it will trigger the deal damage method. Allowing the checking of weakness and resistance as well.
     * This method also implements the add-on where the action can be a heal or ultimate.
     * Depending on the card type, healing and ultimate will do different things.
     * Healing and Ultimate abilities will call on the changePower(int power) and changeHealth(int health) methods accordingly with their respective parameters.
     * Human types do not have healing or ultimates.
     * For fairy types. When using heal, they heal themselves +20, and the other cards by +5.
     * When using ultimate. The player must have at least 1 token. Fairy's ultimate heals the team +15 each and attacks once
     * For dragon types. When using heal, it only heals itself +35.
     * When using ultimate. The player must have at least 2 tokens. Dragon's ultimate attacks the opponent twice.
     * For Ghost Cards. When using heal, it heals itself +50 but its power is reduced by 10, this does not go below 1.
     * When using ultimate. There is no quota for tokens. It can be used anytime. Ghost's ultimate reduced enemy card power by 15.
     *
     * After processing the action, it will check if there are cards eliminated.
     * This awards the player with a token.
     * Checking for a winner will also be done in this method.
     *
     *
     * @param action the action to be performed by the player (e.g., "attack", "swap", "heal", etc.)
     * @return a string message describing the outcome of the action
     **/
    public String play(String action) {
        String swapMessage = "";
        String attackMessage = "";
        String damagesText = "";
        String discardMessage = "";
        String reDraw = "";
        String tokenMessage = "";
        String playerWins = "";
        String addOnText = "";

        Player attackingPlayer = (turnCounter % 2 == 0) ? player2 : player1;
        Player targetPlayer = (turnCounter % 2 == 0) ? player1 : player2;

        if (action.equals("swap")) {
            swapMessage = String.format("   %s swaps out %s...\n   %s", attackingPlayer.getName(), attackingPlayer.getActiveCard().getName(), attackingPlayer.swap());
        } else if (action.equals("attack")) {
            attackMessage = String.format("   %s attacks with %s.\n", attackingPlayer.getName(), attackingPlayer.getActiveCard().getName());
            damagesText = dealDamage(attackingPlayer.getActiveCard(), targetPlayer.getActiveCard());
        } else if (action.equals("heal")) {
            Card[] temp = attackingPlayer.getCards();
            attackMessage = String.format("   %s uses healing ability.\n", attackingPlayer.getName());
            if (attackingPlayer.getActiveCard().getType().equals("Fairy")) {
                attackingPlayer.getActiveCard().changeHealth(20);
                addOnText = String.format("   Type: %s. Heals itself +20, heals the team +5.\n      " +
                        "%s's Health: %d.\n", attackingPlayer.getActiveCard().getType(), attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getHealth());
                for (int i = 1; i < temp.length; i++) {
                    temp[i].changeHealth(5);
                    addOnText += String.format("      %s's Health: %d.\n", temp[i].getName(), temp[i].getHealth());
                }
            } else if (attackingPlayer.getActiveCard().getType().equals("Dragon")) {
                attackingPlayer.getActiveCard().changeHealth(35);
                addOnText = String.format("   Type: %s. Heals itself +35.\n      %s's Health: %d.\n",
                        attackingPlayer.getActiveCard().getType(), attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getHealth());
            } else if (attackingPlayer.getActiveCard().getType().equals("Ghost")) {
                attackingPlayer.getActiveCard().changeHealth(50);
                attackingPlayer.getActiveCard().changePower(-10);
                addOnText = String.format("   Type: %s. Heals itself +50, attack power drops by 10.\n      %s's Health: %d | Power: %d.\n",
                        attackingPlayer.getActiveCard().getType(), attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getHealth(),
                        attackingPlayer.getActiveCard().getPower());
            } else {
                attackMessage = String.format("   %s of type %s has no heal ability.\n   %s's turn forfeited.\n", attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType(),
                        attackingPlayer.getName());
            }
        } else if (action.equals("ultimate")) {
            attackMessage = String.format("   %s uses ultimate ability.\n", attackingPlayer.getName());
            if (attackingPlayer.getActiveCard().getType().equals("Ghost")) {
                targetPlayer.getActiveCard().changePower(-15);
                addOnText = String.format("   %s uses Ultimate Ability!\n   %s's ability weakens enemy damage by 15.\n      Opponent | %s's Power: %d\n",
                        attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType(), targetPlayer.getActiveCard().getName(), targetPlayer.getActiveCard().getPower());
            } else if (attackingPlayer.getActiveCard().getType().equals("Fairy")) {
                if (attackingPlayer.getTokens() > 0) {
                    addOnText = String.format("   %s uses Ultimate Ability!\n   %s's Ultimate Ability attacks then heals team +15.\n",
                            attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType());
                    damagesText = dealDamage(attackingPlayer.getActiveCard(), targetPlayer.getActiveCard());
                    Card[] temp = attackingPlayer.getCards();
                    for (int i = 1; i < temp.length; i++) {
                        temp[i].changeHealth(15);
                        addOnText += String.format("      %s heals %s. %s's Health: %d.\n",
                                attackingPlayer.getActiveCard().getName(), temp[i].getName(), temp[i].getName(), temp[i].getHealth());
                    }
                } else {
                    addOnText = String.format("   Ultimate not ready.\n   Player must have at least 1 token\n   to use %s's (Type: %s) ultimate. \n   Current number of tokens: %d.\n   %s's turn forfeited.\n",
                            attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType(), attackingPlayer.getTokens(), attackingPlayer.getName());
                }
            } else if (attackingPlayer.getActiveCard().getType().equals("Dragon")) {
                if (attackingPlayer.getTokens() > 1) {
                    addOnText = String.format("   %s uses Ultimate Ability!\n   %s's Ultimate Ability attacks the opponent twice.\n",
                            attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType());
                    damagesText = dealDamage(attackingPlayer.getActiveCard(), targetPlayer.getActiveCard()) +
                            dealDamage(attackingPlayer.getActiveCard(), targetPlayer.getActiveCard());
                } else {
                    addOnText = String.format("   Ultimate not ready.\n   Player must have at least 2 tokens\n   to use %s's (Type: %s) ultimate. \n   Current number of tokens: %d.\n   %s's turn forfeited.\n",
                            attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType(), attackingPlayer.getTokens(), attackingPlayer.getName());
                }
            } else {
                attackMessage = String.format("   %s of type %s has no ultimate ability.\n   %s's turn forfeited.\n", attackingPlayer.getActiveCard().getName(), attackingPlayer.getActiveCard().getType(),
                        attackingPlayer.getName());
            }
        }
        if (targetPlayer.getActiveCard().getHealth() <= 0) {
            discardMessage = String.format("   %s discards %s.\n", targetPlayer.getName(), targetPlayer.getActiveCard().getName());
            targetPlayer.discard();
            attackingPlayer.claimToken();
            tokenMessage = String.format("   %s gets a token!\n", attackingPlayer.getName());
            if (attackingPlayer.getTokens() >= 3) {
                hasWinner = true;
                if (hasWinner()) {
                    playerWins = String.format("%s wins!!!", attackingPlayer.getName());
                }
            } else {
                turnCounter++;
            }
            if (deck.size() == 1) {
                Card singleCard = deck.get(0);
                deck.remove(0);
                targetPlayer.drawCard(singleCard);
                reDraw = String.format("   %s draws %s.\n", targetPlayer.getName(), singleCard.getName());
            } else if (deck.size() >= 2) {
                int firstCardPower = deck.get(0).getPower() * deck.get(0).getHealth();
                int secondCardPower = deck.get(1).getPower() * deck.get(1).getHealth();
                if (firstCardPower > secondCardPower) {
                    targetPlayer.drawCard(deck.get(0));
                    reDraw = String.format("   %s draws %s.\n", targetPlayer.getName(), deck.get(0).getName());
                    Card secondCard = deck.get(1);
                    deck.remove(1);
                    deck.add(secondCard);
                    deck.remove(0);
                } else if (secondCardPower > firstCardPower) {
                    targetPlayer.drawCard(deck.get(1));
                    reDraw = String.format("   %s draws %s.\n", targetPlayer.getName(), deck.get(1).getName());
                    deck.remove(1);
                    Card firstCard = deck.get(0);
                    deck.remove(0);
                    deck.add(firstCard);
                }
            } else {
                reDraw = "   The deck is empty.";
            }
        } else {
            turnCounter++;
        }
        return String.format("%s%s%s%s%s%s%s%s", attackMessage, swapMessage, addOnText, damagesText, discardMessage, reDraw, tokenMessage, playerWins);
    }

    /**
     * Checks the resistance between two card types based on the game’s rules.
     * Dragons are resistant to ghosts.
     * Ghosts are resistant to fairies.
     * Fairies are resistant to dragons.
     *
     * @param type1 the type of the first card
     * @param type2 the type of the second card
     * @return boolean value indicating whether the first card is resistant to the second
     **/
    public boolean checkResistance(String type1, String type2) {
        if (type1.equalsIgnoreCase("Dragon") && type2.equalsIgnoreCase("Ghost")) {
            return true;
        } else if (type1.equalsIgnoreCase("Ghost") && type2.equalsIgnoreCase("Fairy")) {
            return true;
        } else if (type1.equalsIgnoreCase("Fairy") && type2.equalsIgnoreCase("Dragon")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks the weakness between two card types based on the game’s rules.
     * Dragons are weak to fairies.
     * Fairies are weak to ghosts.
     * Ghosts are weak to dragons.
     * Human cards are weak to ghosts, fairies, and dragons.
     * Dragons, fairies, and ghosts are also weak to humans.
     *
     * @param type1 the type of the first card
     * @param type2 the type of the second card
     * @return boolean value indicating whether the first card is weak to the second
     **/
    public boolean checkWeakness(String type1, String type2) {
        if (type1.equalsIgnoreCase("Dragon") && (type2.equalsIgnoreCase("Fairy") || type2.equalsIgnoreCase("Human"))) {
            return true;
        } else if (type1.equalsIgnoreCase("Fairy") && (type2.equalsIgnoreCase("Ghost") || type2.equalsIgnoreCase("Human"))) {
            return true;
        } else if (type1.equalsIgnoreCase("Ghost") && (type2.equalsIgnoreCase("Dragon") || type2.equalsIgnoreCase("Human"))) {
            return true;
        } else if (type1.equalsIgnoreCase("Human") && (type2.equalsIgnoreCase("Ghost") || type2.equalsIgnoreCase("Fairy") || type2.equalsIgnoreCase("Dragon"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deals a card to the current player, updating the player's hand and the game state.
     * If the player's hand is full, the card will not be dealt.
     * The card is drawn from the top of the deck, and the deck is updated accordingly.
     * The turn counter is incremented after the card is dealt.
     *
     * @return A message indicating the player who drew the card and the name of the card drawn.
     */
    public String dealCard() {
        String cardName;
        Player currentPlayer = (turnCounter % 2 == 0) ? player2 : player1;

        if (!currentPlayer.handIsFull()) {
            currentPlayer.drawCard(deck.get(0));
            cardName = deck.get(0).getName();
            deck.remove(0);
            turnCounter++;
            return currentPlayer.getName() + " draws " + cardName + ".";
        } else {
            return currentPlayer.getName() + "'s hand is full.";
        }
    }

    /**
     * Deals a random card to the current player, updating the player's hand and the game state.
     * If the player's hand is full, the card will not be dealt.
     * The card is selected randomly from the deck, and the deck is updated accordingly.
     * The turn counter is incremented after the card is dealt.
     * This method will only be called when the randomCard field is true.
     *
     * @return A message indicating the player who drew the card and the name of the card drawn.
     */
    public String dealRandomCard(){
        Random random = new Random();
        int randomNumber = random.nextInt(deck.size());
        String cardName;
        Player currentPlayer = (turnCounter % 2 == 0) ? player2 : player1;
        if (!currentPlayer.handIsFull()) {
            currentPlayer.drawCard(deck.get(randomNumber));
            cardName = deck.get(randomNumber).getName();
            deck.remove(randomNumber);
            turnCounter++;
            return currentPlayer.getName() + " draws " + cardName + ".";
        } else {
            return currentPlayer.getName() + "'s hand is full.";
        }
    }

    /**
     * Deals damage from one card to another based on their types and attack power.
     * Resistance and weakness modifiers are applied based on the card types.
     * If a card is resistant, the damage is halved (integer division).
     * If the card is weak, the damage is doubled.
     * The target's health is updated after the damage is calculated.
     *
     * @param inPlay The card attacking the target.
     * @param target The card that is the target of the attack.
     * @return A message detailing the damage dealt, the effect of resistance or weakness, and the target's remaining health.
     */
    public String dealDamage(Card inPlay, Card target) {
        int attackPower = inPlay.getPower();
        boolean hasResistance = checkResistance(target.getType(), inPlay.getType());
        boolean hasWeakness = checkWeakness(target.getType(), inPlay.getType());
        String typeMessage = "";
        String damageMessage;
        String healthMessage;
        if (hasResistance) {
            attackPower = attackPower / 2;
            target.takeDamage(attackPower);
            typeMessage = String.format("      %s is resistant to %s.\n", target.getType(), inPlay.getType());
        } else if (hasWeakness) {
            attackPower = attackPower * 2;
            target.takeDamage(attackPower);
            typeMessage = String.format("      %s is weak to %s.\n", target.getType(), inPlay.getType());
        } else {
            target.takeDamage(attackPower);
        }
        damageMessage = String.format("   %s deals %d damage on %s.\n", inPlay.getName(), attackPower, target.getName());
        healthMessage = String.format("   %s has %d health left.\n", target.getName(), target.getHealth());
        return String.format("%s%s%s", typeMessage, damageMessage, healthMessage);
    }

    /**
     * Checks if the game has a winner based on the current game state.
     * The method returns true if a winner has been determined; otherwise, it returns false.
     *
     * @return A boolean indicating whether the game has a winner or not.
     */
    public boolean hasWinner() {
        return hasWinner;
    }

    /**
     * Generates a report of the game's summary, including the number of turns and the status of each player.
     * The report includes details about both players' health and their current status.
     *
     * @return A formatted string representing the summary of the game.
     */
    public String gameReport() {
        String report = String.format("---=== GAME SUMMARY ===---\nThis game lasted %d turns.\n%s\n%s", turnCounter, player1.statusReport(), player2.statusReport());
        return report;
    }

    /**
     * The assembleDeck() method is a private method.
     * It is given entirely to the student.
     * It must NOT be modified.
     */
    private void assembleDeck() {
        deck.add(new Card("Dragon", "Aquira", 174, 26));
        deck.add(new Card("Ghost", "Brawn", 130, 48));
        deck.add(new Card("Fairy", "Cerulea", 162, 29));
        deck.add(new Card("Dragon", "Demi", 147, 28));
        deck.add(new Card("Ghost", "Elba", 155, 37));
        deck.add(new Card("Fairy", "Fye", 159, 42));
        deck.add(new Card("Dragon", "Glyede", 129, 26));
        deck.add(new Card("Ghost", "Hydran", 163, 35));
        deck.add(new Card("Fairy", "Ivy", 146, 45));
        deck.add(new Card("Dragon", "Jet", 170, 24));
        deck.add(new Card("Ghost", "Kineti", 139, 21));
        deck.add(new Card("Fairy", "Levi", 160, 43));
        deck.add(new Card("Dragon", "Meadow", 134, 29));
        deck.add(new Card("Ghost", "Naidem", 165, 26));
        deck.add(new Card("Fairy", "Omi", 145, 21));
        deck.add(new Card("Dragon", "Puddles", 170, 34));
        deck.add(new Card("Ghost", "Quarrel", 151, 29));
        deck.add(new Card("Fairy", "Raven", 168, 32));
        deck.add(new Card("Dragon", "Surge", 128, 27));
        deck.add(new Card("Ghost", "Takiru", 140, 26));
        deck.add(new Card("Fairy", "Ustelia", 163, 47));
        deck.add(new Card("Dragon", "Verwyn", 145, 25));
        deck.add(new Card("Ghost", "Wyverin", 158, 32));
        deck.add(new Card("Fairy", "Xios", 155, 27));
        deck.add(new Card("Dragon", "Yora", 159, 44));
        deck.add(new Card("Ghost", "Zulu", 125, 46));
    }

    /**
     * Loads a new deck of cards from a file called "newCards.txt". Each line in the file represents a card
     * with its type, name, health, and power. The cards are added to the deck.
     * If the file is not found, an error message is displayed.
     * This method will be called instead of the assembleDeck() only if the newDeck instance field is true.
     *
     * @throws \FileNotFoundException If the file "newCards.txt" is not found.
     */
    public void newCardDeck() {
        try {
            FileReader reader = new FileReader("assets/newCards.txt");
            Scanner sc = new Scanner(reader);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] list = line.split(" ");
                deck.add(new Card(list[0], list[1], Integer.valueOf(list[2]), Integer.valueOf(list[3])));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found! ");
        }
    }

    /**
     * Returns the first player in the game.
     *
     * @return The Player object representing the first player.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Returns the second player in the game.
     *
     * @return The Player object representing the second player.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Returns the current turn count, which tracks the number of turns that have occurred in the game.
     *
     * @return The current turn count.
     */
    public int getTurnCounter() {
        return turnCounter;
    }

    /**
     * Checks whether the cards are being dealt randomly or in order.
     *
     * @return A boolean indicating whether the cards are being dealt randomly.
     */
    public boolean isRandomCard() {
        return randomCard;
    }
}
