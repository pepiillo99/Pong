package me.pepe.Pong.Game.Objects;

import java.awt.Color;
import java.awt.Graphics;

import me.pepe.GameAPI.Game.Game;
import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.GameLocation;
import me.pepe.GameAPI.Utils.ObjectDimension;

public class PlatformObject extends GameObject {
	private int goals = 0;
	private Color color;
	public PlatformObject(int x, Color color, Game game) {
		super(new GameLocation(x, (game.getWindows().getY()/2) - (80/2)), game, new ObjectDimension(20, 80));
		this.color = color;
		setWindowsPassable(false);
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int) getX(), (int) getY(), (int) getDimension().getX(), (int) getDimension().getY());
	}
	public int getGoals() {
		return goals;
	}
	public void addGoal() {
		goals++;
	}
}