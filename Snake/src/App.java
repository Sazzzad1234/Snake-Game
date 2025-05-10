// App.java
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardWidth = 600;
        int boardHeight = 600;

        // Create the main game window
        JFrame frame = new JFrame("Snake Game");
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);

        frame.add(snakeGame);
        frame.pack(); // Automatically sizes the frame to fit the preferred size of the SnakeGame panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);

        // Request focus to ensure keyboard input is captured
        SwingUtilities.invokeLater(() -> snakeGame.requestFocusInWindow());
    }
}
