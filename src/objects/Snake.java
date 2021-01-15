package objects;

import enums.ColorPalette;
import enums.Direction;
import enums.ID;
import enums.SoundEffect;
import system.Game;
import system.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Snake extends GameObject {
    private LinkedList<Tail> tail_pieces = new LinkedList<>();
    private Direction current_direction = Direction.up;
    private boolean can_update_direction = true;
    private Point last_position;

    public Snake(int x, int y) {
        super(x, y, ID.snake);
        last_position = new Point(x, y);

        tail_pieces.add(new Tail(x, y+TILESIZE));
        tail_pieces.add(new Tail(x, y+TILESIZE*2));
    }

    @Override
    public void tick() {
        for(int i=0; i<tail_pieces.size(); i++) {
            tail_pieces.get(i).tick();
        }
    }

    @Override
    public void render(Graphics g) {
        for(int i=0; i<tail_pieces.size(); i++) {
            tail_pieces.get(i).render(g);
        }
        Game.renderCube(g, x, y, ColorPalette.light_green.color, ColorPalette.dark_green.color);
    }

    public void addTailPiece() {
        Tail last_tail = tail_pieces.get(tail_pieces.size()-1);
        tail_pieces.add(new Tail(last_tail.getX(), last_tail.getY()));
    }

    public void move() {
        last_position = new Point(x, y);
        switch(current_direction) {
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
        updateTail();
        can_update_direction = true;
    }

    private void updateTail() {
        for(int i=tail_pieces.size()-1; i>0; i--) {
            Tail next_tail = tail_pieces.get(i-1);
            tail_pieces.get(i).setX(next_tail.getX());
            tail_pieces.get(i).setY(next_tail.getY());
        }
        tail_pieces.get(0).setX(last_position.x);
        tail_pieces.get(0).setY(last_position.y);
    }

    public void setDirection(Direction direction) {
        if(can_update_direction) {
            SoundEffect.blip.play();
            current_direction = direction;
            can_update_direction = false;
        }
    }

    public Direction getDirection() {
        return current_direction;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, TILESIZE, TILESIZE);
    }

    public LinkedList<Tail> getTail_pieces() {
        return tail_pieces;
    }
}
