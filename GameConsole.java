import java.util.Scanner;

/**
        The GameConsole class serves as the main entry point for the game, initializing the game setup and interacting with the user through console input.
        It handles player input for game actions and displays game events, like dealing cards and processing actions such as "Attack" and "Swap."

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

public class GameConsole {
    /**
     * The main method for initializing and running the game. It processes command-line arguments to set up
     * the players, determine if random card dealing is enabled, and decide whether to use a new deck.
     * This is also the method where the cards are dealt to the players.
     * The game alternates turns between the players, allowing them to choose between "Attack" or "Swap" actions.
     * The game continues until a winner is determined, at which point a summary of the game is displayed.
     *
     *
     * @param args the command-line arguments specifying the players and game options (optional... e.g., random card, new deck)
     **/
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameMaster gameMaster;

        if(args.length == 4){
            gameMaster = new GameMaster(new Player(args[0]), new Player(args[1]), args[2].equals("random"), args[3].equals("new"));
            System.out.printf("Welcome, %s and %s!\n", args[0], args[1]);
        }else if(args.length == 3){
            gameMaster = new GameMaster(new Player(args[0]), new Player(args[1]), args[2].equals("random"));
            System.out.printf("Welcome, %s and %s!\n", args[0], args[1]);
        }else{
            gameMaster = new GameMaster(new Player(args[0]), new Player(args[1]));
            System.out.printf("Welcome, %s and %s!\n", args[0], args[1]);
        }
        System.out.println("The game begins.\n");

        if(gameMaster.isRandomCard()){
            for(int i = 0; i < 10; i++){
                System.out.println(gameMaster.dealRandomCard() + "\n");
            }
        }else{
            for(int i = 0; i < 10; i++){
                System.out.println(gameMaster.dealCard() + "\n");
            }
        }

        while(!gameMaster.hasWinner()){
            System.out.print("Attack or Swap? ");
            String action = scanner.next().toLowerCase();
            System.out.println(gameMaster.play(action));
        }
        System.out.print(gameMaster.gameReport());
    }
}
