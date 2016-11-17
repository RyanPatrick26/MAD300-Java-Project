package server;

import java.io.Serializable;

public class Game implements Serializable {
	private String id;
	private String gameName;
	private String gameRating;
	private String gameDescription;
	public String getId() {
		return id;
	}
	public Game(String id, String gameName, String gameRating, String gameDescription) {
		super();
		this.id = id;
		this.gameName = gameName;
		this.gameRating = gameRating;
		this.gameDescription = gameDescription;
	}
	public Game() {
		super();
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameRating() {
		return gameRating;
	}
	public void setGameRating(String gameRating) {
		this.gameRating = gameRating;
	}
	public String getGameDescription() {
		return gameDescription;
	}
	public void setGameDescription(String gameDescription) {
		this.gameDescription = gameDescription;
	}
}
