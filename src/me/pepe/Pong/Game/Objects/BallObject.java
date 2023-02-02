package me.pepe.Pong.Game.Objects;

import java.awt.Color;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameAudio;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;
import me.pepe.GameAPI.Utils.Utils;
import me.pepe.Pong.Game.Screen.GameScreen;

public class BallObject extends GameObject {
	private boolean ghost = false;
	private GameScreen gameScreen;
	public BallObject(GameLocation gameLocation, Game game, ObjectDimension dimension, GameScreen gameScreen) {
		super(gameLocation, game, dimension);
		setWindowsPassable(false);
		this.gameScreen = gameScreen;
	}
	@Override
	public void tick() {
		if (!gameScreen.isPaused()) {
			if (!ghost) {
				if (getX() == 0) {
					gameScreen.getPlayer1().addGoal();
					gameScreen.restartGame();
				} else if (getX() == getGame().getWindows().getXToPaint() - getDimension().getX()) {
					gameScreen.getPlayer2().addGoal();
					gameScreen.restartGame();
				} else {
					boolean col1 = isCollision(gameScreen.getPlayer1());
					boolean col2 = isCollision(gameScreen.getPlayer2());
					if ((col1 && gameScreen.getTurn() == 1) || (col2 && gameScreen.getTurn() == 2)) {
						gameScreen.addHitCount();
						setVelX(-getVelX());
						setVelX(getVelX() * (1.25));
						setVelY(getVelY() * (1.25));
						/**
						if (col1 && gameScreen.getPlayer1().getVelY() != 0) {
							setVelY(getVelY() * (gameScreen.getPlayer1().getVelY() < 0 ? 0.25 : -0.25));
						} else if (col2  && gameScreen.getPlayer1().getVelY() != 0) {
							setVelY(getVelY() * (gameScreen.getPlayer2().getVelY() < 0 ? 0.25 : -0.25));
						}
						 */
						if (getVelX() >= 6) {
							setVelX(6);
						}
						if (getVelY() >= 6) {
							setVelY(6);
						}			
						new GameAudio("pong.wav") {
							@Override
							public void onFinish() {}				
						};
						if (col1) {
							gameScreen.setTurn(2);
						} else if (col2) {
							gameScreen.setTurn(1);
						}
						if (gameScreen.getHitCount() % 3 == 0) {
							for (int i = 0; i < Utils.random.nextInt(6) + 1; i++) {
								BallObject bo = new BallObject(new GameLocation(getX(), getY()), getGame(), getDimension(), gameScreen);
								bo.setVelX(getVelX());
								bo.setVelY(Utils.randomDouble(-3, 3));
								bo.setGhost(true);
								gameScreen.addGameObject(bo);
							}
						}
					}
				}
			} else {
				if (getX() == 0 || getX() == getGame().getWindows().getXToPaint() - getDimension().getX()) {
					getGame().getScreen().removeGameObject(this);
				}		
			}
			if (getY() == 0 || getY() == getGame().getWindows().getYToPaint() - getDimension().getY()) {
				setVelY(-getVelY());
			}
		}
	}
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 255, 255, ghost ? 200 : 255));
		g.fillOval((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
	}
	public boolean isGhost() {
		return ghost;
	}
	public void setGhost(boolean ghost) {
		this.ghost = ghost;
	}
}
