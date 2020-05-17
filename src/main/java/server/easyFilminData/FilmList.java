package server.easyFilminData;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.log4j.Logger;

import serialization.FilmListData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;


/**
 * Represents a list of films a user can have
 * @author  BSPQ20E4
 * @version 1.0
 * @since   2020-04-14
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)

public class FilmList {
	
	static Logger logger = Logger.getLogger(FilmList.class.getName());

	/**
	 * This variable represents the name of the FilmList
	 */
	private String name;
	/**
	 * This variable represents the list of the film. It stores objects of the Film class
	 */
	@Persistent(defaultFetchGroup="true")
	@Join
	protected ArrayList<Film> filmList;
	
	/** 
	 * Parameterized constructor for the FilmList class receiving as parameters:
	 * @param name String corresponding to the name of the FilmList
	 */
	public FilmList(String name) {
		this.name = name;
		this.filmList = new ArrayList<Film>();
	}

	/** We create a FilmList out of a FLData, but we dont initialize the filmList here. We do it in Server.java
	 * @param fl
	 */
	public FilmList(FilmListData fl) {
		this.name = fl.getName();
		this.filmList = new ArrayList<Film>();
	}
	
	/** 
	 * Adds a film to the filmList attribute
	 * @param film the Film object to be added to the filmList
	 */
	public void addFilm(Film film) {
		
		this.filmList.add(film);
		
	}
	
	/** Searches and removes a film from the filmList attribute
	 * @param title String that matches the title of the film to be removed
	 */
	public void removeFilm(String title){
		
		Iterator<Film> itr = this.filmList.iterator();
		while(itr.hasNext()) {
			Film f = (Film) itr.next();
			
			if(f.getTitle().equals(title)) {
				itr.remove();
				return;
			}
			
		}
		logger.error("The film trying to be removed could not be found");
	}
	
	/**
	 * Sorts the film in the fimlList by alphabetical order
	 * @see Film
	 */
	public void sortFilmList() {
		Collections.sort(this.filmList);
	}
	
	
	
	/**
	 * Getter for the name of the FilmList
	 * @return A String with the name of the list
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Getter for the list of films
	 * @return an ArrayList of the films 
	 */
	public ArrayList<Film> getFilmList() {
		return filmList;
	}


	/**
	 * Setter for the name of the film list
	 * @param name String with the name of the filmList
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Setter for the ArrayList of films
	 * @param filmList ArrayList of films with the films to be inserted
	 */
	public void setFilmList(ArrayList<Film> filmList) {
		this.filmList = filmList;
	}
}
