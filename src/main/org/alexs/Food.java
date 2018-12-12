package main.org.alexs;


public class Food {

    private Snake snake = new Snake();
    private int foodX; // Stores X pos of our food
    private int foodY; // Stores Y pos of our food


    // Used to determine random position of food
    private final int RANDOMPOSITION = 20;

    public void createFood() {

        // Set our food's x and y position to a random position

        int location = (int) (Math.random() * RANDOMPOSITION);
        foodX = ((location * Gameplay.getDotSize()));

        location = (int) (Math.random() * RANDOMPOSITION);
        foodY = ((location * Gameplay.getDotSize()));

        if ((foodX == snake.getSnakeX(0)) && (foodY == snake.getSnakeY(0))) {
            createFood();
        }

    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }
    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }
}
