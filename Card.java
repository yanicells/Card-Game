/**
        The Card class represents a card with a type, name, health, and power.
        It provides methods to modify health and power, and to retrieve card details.

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

public class Card {
    private String type;
    private String name;
    private int health;
    private int power;

    /**
     * Constructor initializes a card with specified type, name, health, and power.
     *
     * @param t the type of the card
     * @param n the name of the card
     * @param h the health value of the card
     * @param p the power value of the card
     **/
    public Card(String t, String n, int h, int p) {
        this.type = t;
        this.name = n;
        this.health = h;
        this.power = p;
    }

    /**
     * Returns the health of the card.
     *
     * @return the current health of the card
     **/
    public int getHealth() {
        return health;
    }

    /**
     * Returns the name of the card.
     *
     * @return the name of the card
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the power of the card.
     *
     * @return the power of the card
     **/
    public int getPower() {
        return power;
    }

    /**
     * Returns the type of the card.
     *
     * @return the type of the card
     **/
    public String getType() {
        return type;
    }

    /**
     * Reduces the health of the card by the specified damage value.
     *
     * @param d the amount of damage to apply
     **/
    public void takeDamage(int d) {
        this.health -= d;
    }

    /**
     * Modifies the health of the card by the specified amount. This method will be used for my add-ons.
     *
     * @param health the amount to add or subtract from the health
     **/
    public void changeHealth(int health) {
        this.health += health;
    }

    /**
     * Modifies the power of the card, ensuring it does not fall below 1. This method will be used for my add-ons.
     *
     * @param power the amount to adjust the card's power
     **/
    public void changePower(int power) {
        if ((this.power + power) > 1) {
            this.power += power;
        } else {
            this.power = 1;
        }
    }
}
