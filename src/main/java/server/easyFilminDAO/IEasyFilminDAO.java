package server.easyFilminDAO;

import java.util.ArrayList;
import java.util.List;

import server.easyFilminData.Actor;
import server.easyFilminData.Comment;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.User;
import server.easyFilminData.WatchList;
import server.easyFilminData.Watched;

/**@package server.easyFilminDAO
 * @brief This is the documentation for the Java package server.easyFilminDAO intended to work as a Data Access Layer for the EasyFilmin Project.
 * This package is composed by 2 classes. IEasyFilminDAO and EasyFilminJDO. The first one is intended to work as a DAO interface from where all the DB methods can be accessed.
 * The second class is for using the storage type JDO. Currently there is only 1 available but in the future there could be more ways to save the information (such as MongoDB).
 * Any new way of storage would require a new class that implements the easyFilminDAO interface.
 */

/** 
 * @author BSPQ20E4
 * @version 2.1
 * @since 2020-05-15
 * <p> This interface serves to access the data storing, retrieving and deleting methods. Currently the only storage option is JDO. </p>
 */
public interface IEasyFilminDAO {
	
	/**
	 * Stores the user received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param user the user object whose data will be stored
	 * @see User
	 */
	public void saveUser(User user /**< [in] The user object whose data will be stored */);
	
	/**
	 * Returns a user object after searching for it in the DB. 
	 * @param username a string that matches the name of the returned user
	 * @return         the user that matches the username.
	 */
	public User loadUser(String username /**< [out] The user object retrieved that matches the username */);
	
	/**
	 * Deletes a user from the DataBase
	 * @param username The username that matches the user wished to be deleted
	 */
	public void deleteUser(String username);
	
	/**
	 * Stores the actor received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param actor the actor object whose data will be stored.
	 * @see Actor
	 */
	public void saveActor(Actor actor);
	
	/**
	 * Returns an actor object after searching for it in the DB. 
	 * @param name a string that matches the name of the returned actor.
	 * @return     the actor that matches the name.
	 */
	public Actor loadActor(String name);
	
	/**
	 * Stores the film received in a Database.
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param film the film object whose data will be stored.
	 * @see Film
	 */
	public void saveFilm(Film film);
	
	/**
	 * Returns an film object after searching for it in the DB. 
	 * @param title a string that matches the title of the returned film.
	 * @return      the film that matches the title.
	 */
	public Film loadFilm(String title);
	
	/**
	 * Returns a director object after searching for it in the DB
	 * @param name a string that matches the name of the returned director. 
	 * @return     the director that matches the name
	 */
	public Director loadDirector(String name);
	
	/**
	 * Stores the director received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param director the director object whose data will be stored.
	 * @see Director
	 */
	public void saveDirector(Director director);
	
	/**
	 * Returns a watched object after searching for it in the DB
	 * @param name name of the watched filmList
	 * @return the watched fimList
	 */
	public Watched loadWatched(String name);
	
	/**
	 * Stores the watched received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param watched the watched object whose data will be stored.
	 * @see Watched
	 */
	public void saveWatched(Watched watched);
	
	/**
	 * Deletes form the DB the Watched film list. 
	 * @param name name if the watched film list to be deleted
	 */
	public void deleteWatched(String name);
	
	/**
	 * Returns a watchList object after searching for it in the DB
	 * @param name name of the watchList filmList
	 * @return the watchList fimList
	 */
	public WatchList loadWatchList(String name);
	
	/**
	 * Stores the watchList received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param watchlist the watchList object whose data will be stored.
	 */
	public void saveWatchList(WatchList watchlist);
	
	/**
	 * Deletes form the DB the WatchList film list. 
	 * @param name name of the WatchList film list to be deleted.
	 */
	public void deleteWatchList(String name);
	
	/**
	 * Returns a filmList matching the name received.
	 * @param listName String of the filmList. 
	 * @return a FilmList
	 */
	public FilmList loadFilmList(String listName);
	
	/**
	 * Stores the filmList received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param watchlist the filmList object whose data will be stored.
	 */
	public void saveFilmList(FilmList filmList);
	
	/**
	 * Deletes form the DB the film list. 
	 * @param name name of the  film list to be deleted.
	 */
	public void deleteFilmList(String name);
	
	/**
	 * Stores the comment received in a Database. 
	 * The type of storage may differ depending on the instance of this interface created.
	 * @param comment the comment object whose data will be stored.
	 * @see Comment
	 */
	public void saveComment(Comment comment);
	
	/**
	 * Returns all the comments associated with a film 
	 * @param filmTitle String with the title of the film whose comments will be returned
	 * @return ArrayList of comments from the selected film
	 */
	public List<Comment> loadComments(String filmTitle);
	
	/**
	 *
	 * @return all films stored in the DB
	 */
	public List<Film> getAllFilms();

// 
//	/** COMMENTED METHOD BECAUSE WE DO THIS WITH ANOTHER APPROACH RIGHT NOW
//	 * 
//	 * @param username String with the name of the user whose lists we want to get
//	 * @return all filmsLists (both watched and watchlist)created by the user
//	 */
//	public ArrayList<FilmList> getUserLists(String username);
//	

/**
 * THESE DETELING METHODS ARE ONLY CREATED FOR JUNIT
 */
	
	/** Deleting a movie 
	 * @param moviename - name of the movie to be removed
	 */
	public void deleteFilm(String moviename);
	/** Deleting an actor
	 * @param name - name of the actor to be removed
	 */
	public void deleteActor(String name);
	/** Deleting a director
	 * @param director - name of the director to be removed
	 */
	public void deleteDirector(String director);
	
	
	/**
	 * Loads the DB initially with predetermined data.
	 * This data mainly consists in Actors, Films and Directors.
	 */
	public void startBD();
	
	/*Deletes all the data currently stored in the DB*/
	public void cleanBD();
}