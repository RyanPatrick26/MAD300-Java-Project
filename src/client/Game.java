package client;

import java.util.Calendar;

public class Game {
	private String name;
	private String category;
	private int rating;
	private String description;
	private Calendar lastPlayed;
	
	public Game(String name, String category, int rating, String description, Calendar lastPlayed){
		this.name = name;
		this.category = category;
		this.rating = rating;
		this.description = description;
		this.lastPlayed = lastPlayed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getLastPlayed() {
		return lastPlayed;
	}

	public void setLastPlayed(Calendar lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
	
}
