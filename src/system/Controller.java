package system;

import enums.GameState;
import enums.ID;
import enums.SoundEffect;
import objects.Food;
import objects.Snake;
import objects.Tail;
import objects.Wall;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Controller {
    private final int MOVE_TIMER_THRESHOLD = 15;
    private int move_timer = 0;
    Handler handler;

    private Snake snake;
    private Food food;

    private int score = 0;
    public Controller() {
        handler = Game.handler;
        int centerX = (Game.PLAYSPACE_WIDTH/2)*Game.TILESIZE;
        int centerY = (Game.PLAYSPACE_WIDTH/2)*Game.TILESIZE;
        snake = new Snake(centerX, centerY);
        handler.addObject(snake);

        food = new Food(0, 0);
        updateFoodLocation();
        handler.addObject(food);

        initPlayingField();
    }

    private void initPlayingField() {
        for(int x=0; x<Game.PLAYSPACE_WIDTH; x++) {
            handler.addObject(new Wall(x*Game.TILESIZE, 0));
            handler.addObject(new Wall(x*Game.TILESIZE, Game.PLAYSPACE_HEIGHT*Game.TILESIZE-Game.TILESIZE));
        }
        for(int y=1; y<Game.PLAYSPACE_HEIGHT-1; y++) {
            handler.addObject(new Wall(0, y*Game.TILESIZE));
            handler.addObject(new Wall(Game.PLAYSPACE_WIDTH*Game.TILESIZE-Game.TILESIZE, y*Game.TILESIZE));
        }
    }

    public void tick() {
        if(move_timer >= MOVE_TIMER_THRESHOLD) {
            if(snakeCanMove()) {
                snake.move();
            } else {
                SoundEffect.defeat.play();
                Game.addScore(score);
                Game.saveHighScores();
                Game.state = GameState.end_screen;
            }
            move_timer = 0;
        }
        move_timer++;

        if(snake.getBounds().intersects(food.getBounds())) {
            updateFoodLocation();
            snake.addTailPiece();
            score++;
            SoundEffect.tetris.play();
        }
    }

    public void resetGame() {
        handler.removeObject(snake);
        int centerX = (Game.PLAYSPACE_WIDTH/2)*Game.TILESIZE;
        int centerY = (Game.PLAYSPACE_WIDTH/2)*Game.TILESIZE;
        snake = new Snake(centerX, centerY);
        handler.addObject(snake);

        updateFoodLocation();
        score = 0;
    }

    private boolean snakeCanMove() {
        int x = snake.getX();
        int y = snake.getY();
        switch(snake.getDirection()) {
            case up:
                y -= Game.TILESIZE;
                break;
            case down:
                y += Game.TILESIZE;
                break;
            case left:
                x -= Game.TILESIZE;
                break;
            case right:
                x += Game.TILESIZE;
                break;
        }
        Rectangle snake_expected_bounds = new Rectangle(x, y, Game.TILESIZE, Game.TILESIZE);
        LinkedList<GameObject> objects = new LinkedList<>(handler.getObjects());
        objects.addAll(snake.getTail_pieces());
        for(int i=0; i<objects.size(); i++) {
            GameObject object = objects.get(i);
            if(object.getId() == ID.wall || object.getId() == ID.tail) {
                if (snake_expected_bounds.intersects(object.getBounds())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void updateFoodLocation() {
        int x = 0;
        int y = 0;
        boolean colliding = true;
        while(colliding) {
            x = Game.TILESIZE + new Random().nextInt(18) * Game.TILESIZE;
            y = Game.TILESIZE + new Random().nextInt(18) * Game.TILESIZE;
            colliding = !canPlaceFood(x, y);
        }
        food.setX(x);
        food.setY(y);
    }

    boolean canPlaceFood(int x, int y) {
        LinkedList<Tail> tail_pieces = new LinkedList<>(snake.getTail_pieces());
        if(snake.getBounds().intersects(x, y, Game.TILESIZE, Game.TILESIZE)) return false;
        for(int i=0; i<tail_pieces.size(); i++) {
            if(tail_pieces.get(i).getBounds().intersects(x, y, Game.TILESIZE, Game.TILESIZE)) {
                return false;
            }
        }
        return true;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getScore() {
        return score;
    }
}
