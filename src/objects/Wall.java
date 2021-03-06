package objects;

import enums.ColorPalette;
import enums.ID;
import system.Game;
import system.GameObject;

import java.awt.*;

public class Wall extends GameObject {
    public Wall(int x, int y) {
        super(x, y, ID.wall);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Game.renderCube(g, x, y, ColorPalette.wall.color, ColorPalette.wall_border.color);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, TILESIZE, TILESIZE);
    }
}
