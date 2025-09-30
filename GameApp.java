/**
        The GameApp class is the entry point of the game application. It is responsible
        for initializing the game GUI and launching the graphical user interface of the game.

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
public class GameApp {
    /**
     * The main method is the entry point of the program. It initializes an instance
     * of the GameGUI class and calls the launchGUI method
     * to display the game start up game interface.
     *
     * @param args command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        GameGUI GUI = new GameGUI();
        GUI.launchGUI();
    }
}
