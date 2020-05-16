package server.easyFilminData;



import java.util.Set;


import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;

import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.ui.MyLists;

/**@package server.easyFilminData
 * @brief This is the documentation for the Java package server.easyFilminData intended to work as the Domain Object Model (DOM) for the EasyFilmin project.
 * This package is composed by several classes, Actor, Comment, Director, Film, FilmList, Genre, Message, User, Watched and WatchList.
 * All of them are related and will be used as base for the project.
 * The classes do also have JDO annotations, in order to be able to store and work with them.
 */

@PersistenceCapable(detachable = "true")
public class User {
	@PrimaryKey
	String nickname=null;
	String icon;
	String email;
	String password=null;
	
	
	@Persistent(defaultFetchGroup="true", mappedBy="user", dependentElement="true")
	@Join
	Set<Message> messages = new HashSet<Message>();
	ArrayList<Film> favMovies = new ArrayList<>();
	ArrayList<Comment> comments = new ArrayList<>();
	
	//AQUÍ HAY QUE METER LAS LISTAS + WATCHLIST y WATCHED
	@Persistent(defaultFetchGroup="true")
	@Join
	ArrayList<FilmList> lists ;
	@Persistent(defaultFetchGroup="true")
	Watched watched;
	@Persistent(defaultFetchGroup="true")
	WatchList watchList;
	
	static Logger logger = Logger.getLogger(User.class.getName());
	
	public User(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
		lists = new ArrayList<>();
		watched = new Watched("Watched");
		watchList = new WatchList("WatchList");
		lists.add(watched);
		lists.add(watchList);
	}
	public User(String nickname,String icon, String email, String password) {
		this.nickname = nickname;
		this.icon = icon;
		this.email = email;
		this.password = password;
		watched = new Watched("Watched");
		watchList = new WatchList("WatchList");
		lists = new ArrayList<>();
		lists.add(watched);
		lists.add(watchList);
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
	}
	/**
	 * 
	 * @param c: comment posted by a user when clicking 'Post' button,
	 * comments will be displayed in a film's profile showing the user (nickname and icon) who wrote it
	 */
	public void postComment(Comment c) {
		comments.add(c);
	}
	/*
	 * deletes a comment posted by a user and will be deleted both from a film's and user's profile
	 */
	public void deleteComment(Comment c) {
		comments.remove(c);
	}
	
	/**
	 * 
	 * @param movie: the film is added into 'Favourites'
	 */
	public void addFavoriteMovie(Film movie) { 
		favMovies.add(movie);
	}
	/**
	 * 
	 * @param movie: the film is removed from 'Favorites'
	 */
	public void removeFavoriteMovie(Film movie) { 
		favMovies.remove(movie);
	}
	public ArrayList<Film> getFavMovies() { //will be replaced by Watched/WatchList
		return favMovies;
	}
	/**
	 * displays a user's list of posted comments
	 * @return all comments written by a user in different movie profiles
	 */
	public ArrayList<Comment> getComments() { 
		return comments;
	}

	public void setFavMovies(ArrayList<Film> movies) { 
		this.favMovies = movies;
	}

	public String getNickname() {
		return this.nickname;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIcon() {
		return this.icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	 public ArrayList<FilmList> getLists() {
		logger.info("THIS IS NOT SUPPOSED TO BE DONE HERE I THINK");
		return lists;
	}
	public FilmList getWatched() {
		return watched;
	}
	public void setWatched(Watched watched) {
		this.watched = watched;
	}
	public FilmList getWatchList() {
		return watchList;
	}

	public void setWatchList(WatchList watchList) {
		this.watchList = watchList;
	}
	public void setLists(ArrayList<FilmList> lists) {
		this.lists = lists;
	}
	
	public void addFilmList(FilmList f) {
		this.lists.add(f);
	}
	
	public Set<Message> getMessages() {return this.messages;}
	 
	 public String toString() {
		StringBuffer messagesStr = new StringBuffer();
		for (Message message: this.messages) {
			messagesStr.append(message.toString() + " - ");
		}
        return "User: login --> " + this.nickname + ", password -->  " + this.password + ", messages --> [" + messagesStr + "]";
    }
}

