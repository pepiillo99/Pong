package me.pepe.Pong.Game.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.ScreenObjects.Label;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.LabelAligment;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.GameAPI.Utils.InteligentDimensions.PixelInteligentDimension;
import me.pepe.GameAPI.Utils.InteligentPositions.PixelInteligentPosition;
import me.pepe.GameAPI.Windows.KeyInput;
import me.pepe.GameAPI.Windows.Windows;
import me.pepe.Pong.Game.Objects.BallObject;
import me.pepe.Pong.Game.Objects.PlatformObject;

public class GameScreen extends Screen {
	private BallObject ball;
	private PlatformObject player1;
	private PlatformObject player2;
	private Label marcador;
	private int turn = 2;
	private int hitCount = 0;
	private boolean paused = true;
	public GameScreen(Windows windows, Game game) {
		super(windows, game);
		setKeyInput(new KeyInput(this) {
			@Override
			public void tick() {
				if (getKeyPresseds().contains(KeyEvent.VK_UP)) {
					player2.setVelY(-5);
				} else if (getKeyPresseds().contains(KeyEvent.VK_DOWN)) {
					player2.setVelY(5);
				} else {
					player2.setVelY(0);
				}
				if (getKeyPresseds().contains(KeyEvent.VK_W)) {
					player1.setVelY(-5);
				} else if (getKeyPresseds().contains(KeyEvent.VK_S)) {
					player1.setVelY(5);
				} else {
					player1.setVelY(0);
				}
			}
			@Override
			public void onKeyPressed(int key) {
				if (key == KeyEvent.VK_CONTROL) {
					System.out.println(ball.getY() + " - " + ball.getVelY());
				} else if (key == KeyEvent.VK_ESCAPE) {
					restartGame();
				} else if (key == KeyEvent.VK_SPACE) {
					if (paused) {
						onStart();
					}
				}
			}
			@Override
			public void onKeyReleased(int key) {}			
		});
	}
	@Override
	public void onOpen() {
		player1 = new PlatformObject(0, Color.RED, getGame());
		player2 = new PlatformObject(getWindows().getXToPaint() - 20, Color.BLUE, getGame());
		marcador = new Label("0          0", Color.WHITE, new Font("Aria", Font.BOLD, 50), new PixelInteligentPosition((getGame().getWindows().getXToPaint() / 2) - 200, 50), getGame(), new PixelInteligentDimension(400, 50));
		marcador.setAligment(LabelAligment.CENTER);
		addGameObject(marcador);
		addGameObject(player1);
		addGameObject(player2);
		restartGame();
	}
	@Override
	public void internalTick() {}
	@Override
	protected void paintLevel(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(getWindows().getXToPaint() / 2, 0, getWindows().getXToPaint() / 2, getWindows().getYToPaint());
		if (paused) {
			marcador.setText("PULSA ESPACIO PARA JUGAR");
		} else {
			marcador.setText(player2.getGoals() + "          " + player1.getGoals());
		}
	}
	public void restartGame() {
		restartObjects(BallObject.class);
		paused = true;
		turn = 2;
		if (ball != null) {
			removeGameObject(ball);
		}
		ball = new BallObject(new GameLocation((getWindows().getXToPaint() / 2) - (20 / 2), getWindows().getYToPaint() / 2), getGame(), new ObjectDimension(20, 20), this);
		addGameObject(ball);
		player1.setY(getWindows().getYToPaint()/2);
		player2.setY(getWindows().getYToPaint()/2);
	}
	public void onStart() {
		paused = false;
		ball.setVelX(2);
		ball.setVelY(Utils.randomDouble(-3, 3));
	}
	public PlatformObject getPlayer1() {
		return player1;
	}
	public PlatformObject getPlayer2() {
		return player2;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void addHitCount() {
		hitCount++;
	}
	public boolean isPaused() {
		return paused;
	}
}
