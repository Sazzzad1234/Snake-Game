// SnakeGame.java
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x, y;
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth, boardHeight;
    int tileSize = 25;
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile food;
    Random random;
    Image backgroundImage;

    Timer gameLoop;
    int velocityX = 0, velocityY = 0;
    boolean isRunning = false;
    boolean ispaused = false;
    int score = 0;

    Image snakeHeadImage;
    Image foodImage;
        SoundPlayer soundPlayer = new SoundPlayer();


    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        backgroundImage = new ImageIcon("ground2.png").getImage();

        snakeHeadImage = new ImageIcon("snake.png").getImage();
        foodImage  = new ImageIcon("nest.png").getImage();

        soundPlayer.backmusic("music.wav");

        addKeyListener(this);

        initGame();

        gameLoop = new Timer(100, this);
        gameLoop.start();

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    public void initGame() {
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        random = new Random();
        food = new Tile(0, 0);
        placeFood();
        velocityX = 0;
        velocityY = 0;
        isRunning = false;
        score = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundImage != null){
        g.drawImage(backgroundImage,0,0,boardWidth,boardHeight,this);
        }
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
       /*  for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }
        */
        if(ispaused){
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("PAUSED", boardWidth/2-70, boardHeight/2);
        }

       /* g.setColor(Color.RED);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        */
        if(foodImage != null){
            g.drawImage(foodImage, food.x*tileSize, food.y*tileSize, tileSize,tileSize,this);
        }

        //g.setColor(Color.GREEN);
       // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

       if(snakeHeadImage !=null){
                    g.drawImage(snakeHeadImage, snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize,tileSize,this);

       }

        for (Tile part : snakeBody) {
            if(snakeHeadImage != null){
             g.drawImage(snakeHeadImage,part.x * tileSize, part.y * tileSize, tileSize,tileSize,this);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);

        if (!isRunning && velocityX == 0 && velocityY == 0) {
            g.drawString("Press Arrow Key to Start", boardWidth / 2 - 100, boardHeight / 2);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile a, Tile b) {
        return a.x == b.x && a.y == b.y;
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            score++;
            placeFood();
            soundPlayer.playOnce("sortmusic.wav");
        }

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        if (!snakeBody.isEmpty()) {
            snakeBody.get(0).x = snakeHead.x;
            snakeBody.get(0).y = snakeHead.y;
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
            snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
            gameOver();
        }

        for (Tile part : snakeBody) {
            if (collision(snakeHead, part)) {
                gameOver();
                break;
            }
        }
    }

    public void gameOver() {
        isRunning = false;
        gameLoop.stop();
        JOptionPane.showMessageDialog(this, "Game Over!\nScore: " + score, "Snake", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning && !ispaused) {
            move();
            repaint();
        } else {
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_P){
            if(isRunning){
                ispaused = !ispaused;
            }
            return;
        }

        if (!isRunning && (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)) {
            isRunning = true;
            if (velocityX == 0 && velocityY == 0) {
                velocityX = 0;
                velocityY = 1;
            }
        }
        if(ispaused)
        return;

        if (key == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (key == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (key == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (key == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public void setDirection(int dx, int dy) {
        if ((dx == 0 && dy == -1 && velocityY != 1) ||
            (dx == 0 && dy == 1 && velocityY != -1) ||
            (dx == -1 && dy == 0 && velocityX != 1) ||
            (dx == 1 && dy == 0 && velocityX != -1)) {
            velocityX = dx;
            velocityY = dy;
        }
    }
}
