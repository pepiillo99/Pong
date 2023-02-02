package me.pepe.Pong;

import me.pepe.GameAPI.GameAPI;
import me.pepe.GameAPI.Game.Game;
import me.pepe.Pong.Game.Screen.GameScreen;

public class Pong {
	public static void main(String[] args) {
		new Pong();
	}
	public Pong() {
		GameAPI gameAPI = new GameAPI();
		Game game = new Game("Pong", 900, 600, null);
		game.getWindows().setCanFullScreen(false);
		game.getWindows().setResizable(false);
		game.setMaxFPS(60);
		game.setMaxTPS(60);
		game.getScreenManager().registerNewScreen("game", new GameScreen(game.getWindows(), game));
		game.setScreen("game");
		game.start();
	}
}
