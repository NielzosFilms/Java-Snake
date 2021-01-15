package objects;

import enums.ColorPalette;
import enums.ID;
import system.Game;
import system.GameObject;

import java.awt.*;

public class Tail extends GameObject {
    public Tail(int x, int y) {
        super(x, y, ID.tail);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Game.renderCube(g, x, y, ColorPalette.light_green.color, ColorPalette.dark_green.color);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, TILESIZE, TILESIZE);
    }
}
