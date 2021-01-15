package objects;

import enums.ColorPalette;
import enums.ID;
import system.GameObject;

import java.awt.*;

public class Food extends GameObject {
    public Food(int x, int y) {
        super(x, y, ID.food);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(ColorPalette.orange.color);
        g.fillOval(x+5, y+5, TILESIZE-10, TILESIZE-10);
        g.setColor(ColorPalette.red.color);
        g.drawOval(x+5, y+5, TILESIZE-10, TILESIZE-10);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, TILESIZE, TILESIZE);
    }
}
