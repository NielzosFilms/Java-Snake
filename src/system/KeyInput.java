package system;

import enums.Direction;
import enums.GameState;
import objects.Snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	private Handler handler;
	private Controller gameController;

	public KeyInput() {
		handler = Game.handler;
		gameController = Game.gameController;
	}

	public void keyPressed(KeyEvent e) {
		if(Game.state == GameState.game) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (gameController.getSnake().getDirection() != Direction.down)
						gameController.getSnake().setDirection(Direction.up);
					break;
				case KeyEvent.VK_DOWN:
					if (gameController.getSnake().getDirection() != Direction.up)
						gameController.getSnake().setDirection(Direction.down);
					break;
				case KeyEvent.VK_LEFT:
					if (gameController.getSnake().getDirection() != Direction.right)
						gameController.getSnake().setDirection(Direction.left);
					break;
				case KeyEvent.VK_RIGHT:
					if (gameController.getSnake().getDirection() != Direction.left)
						gameController.getSnake().setDirection(Direction.right);
					break;
				case KeyEvent.VK_R:
					gameController.resetGame();
					break;
			}
		} else if(Game.state == GameState.start_screen) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				gameController.resetGame();
				Game.state = GameState.game;
			}
		} else if(Game.state == GameState.end_screen) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				Game.state = GameState.start_screen;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	}
}
