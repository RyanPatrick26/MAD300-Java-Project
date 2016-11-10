package client;

import java.util.ArrayList;
import java.util.Calendar;

public class Game {
	private String name;
	private ArrayList<String> category;
	private int rating;
	private String description;
	private Calendar lastPlayed;
	private String publisher;
	private String releaseYear;
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Game(String name, ArrayList<String> category){
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getCategory() {
		return category;
	}

	public void setCategory(ArrayList<String> category) {
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
