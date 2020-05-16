package server.easyFilminData;


import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.log4j.Logger;

import client.ui.MyLists;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;



/**
 * Definition of a film. Extends Comparable for sorting purposes.
 * @author BSPQ20E4
 * @version 1.1
 * @since 2020-04-14
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)	
public class Film implements Comparable<Film> {
	
	/**
	 * This variable represents the title of a film
	 */
	protected String title;
	
	/**
	 * This variable represents the poster of a film
	 */
	protected String poster;
	
	/**
	 * This variable represents the release date of a film
	 */
	protected String release;
	
	/**
	 * This variable represents a quick summary of the plot of the film
	 */
	protected String description;
	
	/**
	 * This variable represents the genre to which a film belongs
	 */
	@Persistent(defaultFetchGroup="true")
	protected Genre genre;
	
	/**
	 * This variable represents the average rating of a film
	 */
	protected double rating;
	
	/**
	 * This variable represents the actors that have starred in a film
	 */
	@Persistent(defaultFetchGroup="true")
	@Join
	protected ArrayList<Actor> actors;
	
	/**
	 * This variable represents the directors that have taken part in a film
	 */
	@Persistent(defaultFetchGroup="true")
	@Join
	protected ArrayList<Director> director;
	
	/**
	 * This variable represents the trailer of a film
	 */
	protected String trailer;
	/**
	 * This variable represents the duration of a film (in minutes)
	 */
	protected int dur;
	/**
	 * This variable represents all the rates given by users
	 */
	
	private  double totalRates=0;
	/**
	 * This variable represents the number of times the film's been reviewed
	 */
	private int nReviews=0;
	
	
	static Logger logger = Logger.getLogger(Film.class.getName());


	/**
	 * Constructor for the Film class. Initializes an "empty" Film instance.
	 */
	public Film() {
		
		this.title= "";
		this.poster="";
		this.release=null;
		this.description= "";
		this.dur=0;
		this.genre=null;
		this.rating=0;
		this.actors=new ArrayList<>();
		this.director=new ArrayList<>();
		
		}
	
	
	
	
	/**
	 * Parameterized constructor for the Film class receiving as parameters:
	 * @param title       String corresponding to the title of a film
	 * @param poster	  String corresponding to the poster of a film
	 * @param release     String corresponding to the release date of a film
	 * @param description String corresponding to the summary of the film
	 * @param dur		  int corresponding to the duration of a film in minutes
	 * @param genre       Genre to which the film belongs
	 * @param rating      double corresponding to the average rating of a film
	 * @param actors      ArrayList corresponding to the actors that have starred in a film
	 * @param director    ArrayList corresponding to the directors that have taken part in the film
	 */
	
	public Film(String title,String poster, String release, String description,int dur, 
			Genre genre, double rating, ArrayList<Actor> actors, ArrayList<Director> director) {
		
		this.title= title;
		this.poster=poster;
		this.release=release;
		this.description= description;
		this.dur=dur;
		this.genre=genre;
		this.rating=rating;
		this.actors=actors;
		this.director=director;
		
		}
	
	/**
	 * Getter for the duration of a film
	 * @return the duration of a film in minutes
	 */
	public int getDur() {
		return dur;
	}
	/**
	 * Setter for the duration of a film
	 * @param dur int corresponding to the duration of a film in minutes
	 */
	public void setDur(int dur) {
		this.dur = dur;
	}
	/** 
	 * Getter for the title of a film
	 * @return a String with the title of the film
	 */
	public String getTitle()
	{
	    return title;
	}
	/**
	 * Getter for the poster of a film
	 * @return a String with the poster of the film
	 */
	public String getPoster()
	{
	    return poster;
	}
	/**
	 * Getter for the duration of a film
	 * @return a String with the release date of the film
	 */
	public String getRelease()
	{
	    return release;
	}
	/**
	 * Getter for the description of a film
	 * @return a String with the description of the film
	 */
	public String getDescription()
	{
	    return description;
	}
	/**
	 * Getter for the genre of a film
	 * @return Genre instance corresponding to the genre of the film
	 */
	public Genre getGenre()
	{
	    return genre;
	}
	/**
	 * Getter for the rating of a film
	 * @return double with the average rating of the film
	 */
	public double getRating()
	{
	    return rating;
	}
	/**
	 * Getter for the actors of a film
	 * @return ArrayList of actors that starred in the film
	 */
	public ArrayList<Actor> getActors() 
	{
	    return actors;
	}
	/**
	 * Getter for the directors of a film
	 * @return ArrayList of directors that have taken part in the film
	 */
	public ArrayList<Director> getDirector()
	{
	    return director;
	}
	/**
	 * Getter for the trailer of a film
	 * @return String with the trailer of the film
	 */
	public String getTrailer() {
		return trailer;
	}
	/**
	 * Setter for the trailer of a film
	 * @param trailer String with the trailer of a film
	 */
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	/**
	 * Setter for the title of a film
	 * @param title String with the title of a film
	 */	
	public void setTitle(String title)
	{
	    this.title = title;
	}
	/**
	 * Setter for the poster of a film
	 * @param poster String with the poster of a film
	 */
	public void setPoster(String poster)
	{
	    this.poster = poster;
	}
	/**
	 * Setter for the release of a film
	 * @param release String with the release date of a film
	 */
	public void setRelease(String release)
	{
	    this.release = release;
	}
	/**
	 * Setter for the description of a film
	 * @param description String with the description of a film
	 */
	public void setDescription(String description)
	{
	    this.description = description;
	}
	
	/**
	 * Setter for the trailer of a film
	 * @param rating double with the average rating of a film
	 */
	public void setRating(double rating) {
	
	    this.rating = rating;
	}
	/**
	 * Setter for the genre of a film
	 * @param genre Genre to which a film belongs
	 */
	public void setGenre(Genre genre)
	{
	    this.genre = genre;
	}
	/**
	 * Setter for the actors of a film
	 * @param actors ArrayList of actors that have starred in a film
	 */
	public void setActors(ArrayList<Actor> actors)
	{
	    this.actors = actors;
	}

	/**
	 * Setter for the directors of a film
	 * @param director ArrayList of directors that have taken part on a film
	 */
	public void setDirector(ArrayList<Director> director)
	{
	    this.director = director;
	}
	
	/**
	 * Takes the rate a user gives to a film and adds it to previous rates 
	 * and divides it by the number of times the film's been reviewed, 
	 * it returns an average rating. 
	 * @param rate the mark a user gives to a film
	 * @return updated movie's rating given the average rating 
	 */
	public double rateFilm(double rate) {
		nReviews++;
		totalRates= totalRates + rate;
		return rating= totalRates/nReviews;
	 }
	
	/**
	 * Compares a Film object to another Film object. The criteria to compare is alphabetical by title.
	 * @param f Film to which the current Film object will be compared to.
	 * @return returns 0 if both title are the same, -1 if it is before in the alphabet and 1 if it is later.
	 */
	
	public int compareTo(Film f) {
		int i = this.title.compareTo(f.getTitle());
		
		if(i == 0) {
			return 0;
		}else if(i < 0){
			return -1;
		}else {
			return 1;
		}
		
	}
		
}

