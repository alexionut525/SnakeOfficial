package main.org.alexs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

@SuppressWarnings("serial")
public class Gameplay extends JPanel implements ActionListener {

    private ImageIcon titleImage;
// TODO: Implement a way for the player to win

    // Holds height and width of the window
    private final static int BOARDWIDTH = 600;
    private final static int BOARDHEIGHT = 570;

    // Used to represent pixel size of food & our snake's joints
    private final static int PIXELSIZE = 25;

// The total amount of pixels the game could possibly have.
// We don't want less, because the game would end prematurely.
// We don't more because there would be no way to let the player win.

    private final static int TOTALPIXELS = (BOARDWIDTH * BOARDHEIGHT)
            / (PIXELSIZE * PIXELSIZE);

    // Check to see if the game is running
    private boolean inGame = true;

    // Timer used to record tick times
    private Timer timer;

    // Used to set game speed, the lower the #, the faster the snake travels
// which in turn
// makes the game harder.
    private static int speed = 65;

    // Instances of our snake & food so we can use their methods
    private Snake snake = new Snake();
    private Food food = new Food();
    private ImageIcon foodimage,rightmounth,leftmounth,upmounth,downmounth,snakeimage;
    public Gameplay() {

        addKeyListener(new Keys());
        setBackground(new Color(23, 79, 3));
        setFocusable(true);

        setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));

        initializeGame();
    }

    // Used to paint our components to the screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }

    // Draw our Snake & Food (Called on repaint()).
    void draw(Graphics g) {
        // Only draw if the game is running / the snake is alive
        if (inGame == true) {
            foodimage = new ImageIcon("enemy.png");
            foodimage.paintIcon(this,g,food.getFoodX(),food.getFoodY()); //food image

            // Draw our snake.
           for (int i = 0; i < snake.getJoints(); i++) {
               rightmounth = new ImageIcon("rightmouth.png");
               rightmounth.paintIcon(this, g, snake.getSnakeX(0), snake.getSnakeY(0));
               for ( i = 0; i < snake.getJoints(); i++) {

                   if (i == 0 && snake.isMovingLeft()) {
                       leftmounth = new ImageIcon("leftmouth.png");
                       leftmounth.paintIcon(this, g, snake.getSnakeX(i), snake.getSnakeY(i));
                   }
                   if (i == 0 && snake.isMovingUp()) {
                       upmounth = new ImageIcon("upmouth.png");
                       upmounth.paintIcon(this, g, snake.getSnakeX(i), snake.getSnakeY(i));
                   }
                   if (i == 0 && snake.isMovingDown()) {
                       downmounth = new ImageIcon("downmouth.png");
                       downmounth.paintIcon(this, g, snake.getSnakeX(i), snake.getSnakeY(i));
                   }
                   if (i != 0) {
                      snakeimage = new ImageIcon("snakeimage.png");
                       snakeimage.paintIcon(this, g, snake.getSnakeX(i), snake.getSnakeY(i));
                   }

               }


           }
            // Sync our graphics together
            Toolkit.getDefaultToolkit().sync();
        } else {
            // If we're not alive, then we end our game
            endGame(g);
        }
    }

    void initializeGame() {
        snake.setJoints(3); // set our snake's initial size

        // Create our snake's body
        for (int i = 0; i < snake.getJoints(); i++) {
            snake.setSnakeX(BOARDWIDTH / 2);
            snake.setSnakeY(BOARDHEIGHT / 2);
        }
        // Start off our snake moving right
        snake.setMovingRight(true);

        // Generate our first 'food'
        food.createFood();

        // set the timer to record our game's speed / make the game move
        timer = new Timer(speed, this);
        timer.start();
    }

    // if our snake is in the close proximity of the food..
    void checkFoodCollisions() {

        if ((proximity(snake.getSnakeX(0), food.getFoodX(), 20))
                && (proximity(snake.getSnakeY(0), food.getFoodY(), 20))) {

            System.out.println("intersection");
            // Add a 'joint' to our snake
            snake.setJoints(snake.getJoints() + 1);
            // Create new food
            food.createFood();
        }
    }

    // Used to check collisions with snake's self and board edges
    void checkCollisions() {

        // If the snake hits its' own joints..
        for (int i = snake.getJoints(); i > 0; i--) {

            // Snake cant intersect with itself if it's not larger than 5
            if ((i > 5)
                    && (snake.getSnakeX(0) == snake.getSnakeX(i) && (snake
                    .getSnakeY(0) == snake.getSnakeY(i)))) {
                inGame = false; // then the game ends
            }
        }

        // If the snake intersects with the board edges..
        if (snake.getSnakeY(0) >= BOARDHEIGHT) {
            inGame = false;
        }

        if (snake.getSnakeY(0) < 0) {
            inGame = false;
        }

        if (snake.getSnakeX(0) >= BOARDWIDTH) {
            inGame = false;
        }

        if (snake.getSnakeX(0) < 0) {
            inGame = false;
        }

        // If the game has ended, then we can stop our timer
        if (!inGame) {
            timer.stop();
        }
    }

    void endGame(Graphics g) {

        // Create a message telling the player the game is over
        String message = "Game over";
        String message2 ="Press SPACE to RESTART";

        // Create a new font instance
        Font font = new Font("Times New Roman", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);

        // Set the color of the text to red, and set the font
        g.setColor(Color.WHITE);
        g.setFont(font);

        // Draw the message to the board
        g.drawString(message, (BOARDWIDTH - metrics.stringWidth(message)) / 2,
                BOARDHEIGHT / 3);
        g.drawString(message2, (BOARDWIDTH - metrics.stringWidth(message2)) / 2,
                BOARDHEIGHT / 2);
        System.out.println("Game Ended");

    }

    // Run constantly as long as we're in game.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame == true) {

            checkFoodCollisions();
            checkCollisions();
            snake.move();

            System.out.println(snake.getSnakeX(0) + " " + snake.getSnakeY(0)
                    + " " + food.getFoodX() + ", " + food.getFoodY());
        }
        // Repaint or 'render' our screen
        repaint();
    }

    private class Keys extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!snake.isMovingRight())) {
                snake.setMovingLeft(true);
                snake.setMovingUp(false);
                snake.setMovingDown(false);
            }

            if ((key == KeyEvent.VK_RIGHT) && (!snake.isMovingLeft())) {
                snake.setMovingRight(true);
                snake.setMovingUp(false);
                snake.setMovingDown(false);
            }

            if ((key == KeyEvent.VK_UP) && (!snake.isMovingDown())) {
                snake.setMovingUp(true);
                snake.setMovingRight(false);
                snake.setMovingLeft(false);
            }

            if ((key == KeyEvent.VK_DOWN) && (!snake.isMovingUp())) {
                snake.setMovingDown(true);
                snake.setMovingRight(false);
                snake.setMovingLeft(false);
            }

            if ((key == KeyEvent.VK_SPACE) && (inGame == false)) {

                inGame = true;
                snake.setMovingDown(false);
                snake.setMovingRight(false);
                snake.setMovingLeft(false);
                snake.setMovingUp(false);

                initializeGame();
            }
        }
    }

    private boolean proximity(int a, int b, int closeness) {
        return Math.abs((long) a - b) <= closeness;
    }

    public static int getAllDots() {
        return TOTALPIXELS;
    }

    public static int getDotSize() {
        return PIXELSIZE;
    }
}
