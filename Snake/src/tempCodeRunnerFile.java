import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardWidth = 600;
        int boardHeight = 600;

        JFrame frame = new JFrame("Snake Game");
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);

        frame.add(snakeGame);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}
