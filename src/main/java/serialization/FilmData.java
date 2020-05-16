package serialization;

import java.util.ArrayList;


import server.easyFilminData.Actor;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.Genre;

public class FilmData {

	protected String title;
	protected String poster;
	protected String release;
	protected String description;
	protected Genre genre;
	protected double rating;
	protected ArrayList<Actor> actors;
	protected ArrayList<Director> director;
	protected String trailer;
	protected int dur;
	private  double totalRates=0;
	private int nReviews=0;

	//Use this constructor or create a different one to deal with your particular case of Data displaying in the client
	public FilmData() {

		
	}
	public FilmData(Film f) {
		this.title = f.getTitle();
		this.poster = f.getPoster();
		this.release = f.getRelease();
		this.description = f.getDescription();
		this.genre = f.getGenre();
		this.rating = f.getRating();
		this.actors = f.getActors();
		this.director = f.getDirector();
		this.dur = f.getDur();
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public ArrayList<Actor> getActors() {
		return actors;
	}
	public void setActors(ArrayList<Actor> actors) {
		this.actors = actors;
	}
	public ArrayList<Director> getDirector() {
		return director;
	}
	public void setDirector(ArrayList<Director> director) {
		this.director = director;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public int getDur() {
		return dur;
	}
	public void setDur(int dur) {
		this.dur = dur;
	}
	public double getTotalRates() {
		return totalRates;
	}
	public void setTotalRates(double totalRates) {
		this.totalRates = totalRates;
	}
	public int getnReviews() {
		return nReviews;
	}
	public void setnReviews(int nReviews) {
		this.nReviews = nReviews;
	}	
}
