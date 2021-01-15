package system;

import enums.ColorPalette;
import enums.GameState;

import java.awt.*;

public class MenuController {

    public MenuController() {}

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        switch(Game.state) {
            case start_screen:
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g.setColor(ColorPalette.black.color);
                g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g.setFont(new Font("Tetris", Font.PLAIN, 30));
                g.setColor(ColorPalette.light_blue.color);
                g.drawString("Snake", Game.TILESIZE*2, Game.TILESIZE*3);

                g.setFont(new Font("Tetris", Font.PLAIN, 20));
                g.drawString("Press [SPACE] to play...", Game.TILESIZE*2, Game.TILESIZE*15);

                g.setFont(new Font("Tetris", Font.PLAIN, 20));
                g.drawString("Highscores :", 12*Game.TILESIZE+8, 64);
                g.setFont(new Font("Tetris", Font.PLAIN, 15));
                for(int i=0; i<5; i++) {
                    if(i == 0) {
                        g.setColor(ColorPalette.yellow.color);
                    } else g.setColor(ColorPalette.white.color);
                    if(i < Game.highscores.size()) {
                        g.drawString(i+1 + " : " + Game.highscores.get(i) + (Game.highscores.get(i) == Game.gameController.getScore() ? "<-- YOU" : ""), 12*Game.TILESIZE+8, 96 + i*20);
                    } else {
                        g.drawString(i+1 + " : ---", 12*Game.TILESIZE+8, 96 + i*20);
                    }
                }

                break;
            case game:
                g.setFont(new Font("Tetris", Font.PLAIN, 20));
                g.setColor(ColorPalette.light_blue.color);

                g.drawString("Score: " + Game.gameController.getScore(), Game.TILESIZE+Game.TILESIZE/2, Game.TILESIZE*2);
                break;
            case end_screen:
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g.setColor(ColorPalette.black.color);
                g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g.setFont(new Font("Tetris", Font.PLAIN, 30));
                g.setColor(ColorPalette.light_blue.color);
                g.drawString("Game Over!", Game.TILESIZE*2, Game.TILESIZE*3);

                g.setFont(new Font("Tetris", Font.PLAIN, 20));
                g.drawString("Score: " + Game.gameController.getScore(), Game.TILESIZE*2, Game.TILESIZE*5);
                g.drawString("Press [SPACE] to continue...", Game.TILESIZE*2, Game.TILESIZE*15);

                if(Game.highscores.size() > 0) {
                    if(Game.gameController.getScore() == Game.highscores.get(0)) {
                        g.setFont(new Font("Tetris", Font.PLAIN, 20));
                        g.setColor(ColorPalette.yellow.color);
                        g.drawString("NEW HIGHSCORE!", Game.TILESIZE*2, 8*Game.TILESIZE);
                    }
                }

                g.setFont(new Font("Tetris", Font.PLAIN, 20));
                g.drawString("Highscores :", 12*Game.TILESIZE+8, 64);
                g.setFont(new Font("Tetris", Font.PLAIN, 15));
                for(int i=0; i<5; i++) {
                    if(i == 0) {
                        g.setColor(ColorPalette.yellow.color);
                    } else g.setColor(ColorPalette.white.color);
                    if(i < Game.highscores.size()) {
                        g.drawString(i+1 + " : " + Game.highscores.get(i) + (Game.highscores.get(i) == Game.gameController.getScore() ? "<-- YOU" : ""), 12*Game.TILESIZE+8, 96 + i*20);
                    } else {
                        g.drawString(i+1 + " : ---", 12*Game.TILESIZE+8, 96 + i*20);
                    }
                }
                break;
        }

        g.setFont(new Font("Tetris", Font.PLAIN, 15));
        g.setColor(ColorPalette.light_blue.color);
        g.drawString("Created by: NielzosFilms", 8, Game.SCREEN_HEIGHT-Game.TILESIZE-15);
    }
}
