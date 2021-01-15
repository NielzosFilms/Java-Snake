package system;

import enums.ColorPalette;
import enums.GameState;
import enums.SoundEffect;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable {
	public static final int TILESIZE = 32;
	public static final String TITLE = "Snake | NielzosFilms";
	public static final int PLAYSPACE_WIDTH = 20, PLAYSPACE_HEIGHT = 20;
	public static final int SCREEN_WIDTH = TILESIZE*PLAYSPACE_WIDTH, SCREEN_HEIGHT = TILESIZE*(PLAYSPACE_HEIGHT+1);
	public static Thread thread;
	public static Canvas canvas;
	public static boolean running = true;
	public static int current_fps = 0;
	public static Handler handler;
	public static Controller gameController;
	public static MenuController menuController;
	public static KeyInput keyInput;
	public static final BasicStroke stroke = new BasicStroke(4, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);

	public static LinkedList<Integer> highscores = new LinkedList<>();

	public static GameState state = GameState.start_screen;

	public Game() {
		handler = new Handler();
		gameController = new Controller();
		menuController = new MenuController();
		keyInput = new KeyInput();

		this.addKeyListener(keyInput);
		new Window(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE, this);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				current_fps = frames;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
		if(state == GameState.start_screen) {

		} else if(state == GameState.game) {
			gameController.tick();
		} else if(state == GameState.end_screen) {

		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(stroke);
		g.setColor(ColorPalette.black.color);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		handler.render(g);
		menuController.render(g);

		g.setFont(new Font("Tetris", Font.PLAIN, 15));
		g.setColor(ColorPalette.white.color);
		g.drawString("FPS: " + Game.current_fps, 8, 18);

		g.dispose();
		g2d.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		loadHighScores();
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("Tetris.ttf")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		SoundEffect.init();
		canvas = new Game();
	}

	public static void renderCube(Graphics g, int x, int y, Color bg, Color border) {
		int line_width = (int) stroke.getLineWidth();

		g.setColor(bg);
		g.fillRect(x+ line_width/2, y+line_width/2, TILESIZE-line_width, TILESIZE-line_width);
		g.setColor(border);
		g.drawRect(x+line_width/2, y+line_width/2, TILESIZE-line_width, TILESIZE-line_width);
	}

	public static void addScore(int current_score) {
		if(current_score == 0) return;
		int index = 0;
		boolean place_score = false;
		for(int i=highscores.size()-1; i>=0; i--) {
			if(current_score > highscores.get(i)) {
				index = i;
				place_score = true;
			}
		}
		if(place_score) {
			highscores.add(index, current_score);
			while(highscores.size() > 5) {
				highscores.remove(5);
			}
		} else {
			highscores.add(current_score);
		}
	}

	public static void saveHighScores() {
		try {
			File file = new File("./snake_highscores.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file, false);
			PrintWriter pwOb = new PrintWriter(writer, false);
			pwOb.flush();
			for(int score : highscores) {
				pwOb.println(score);
			}
			pwOb.close();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadHighScores() {
		try {
			File file = new File("./snake_highscores.txt");
			//FileWriter writer = new FileWriter(highscores);
			if(file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				for(String line; (line = br.readLine()) != null; ) {
					highscores.add(Integer.parseInt(line));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
