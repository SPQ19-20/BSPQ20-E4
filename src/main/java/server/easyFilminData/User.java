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
	
	
	//AQUÍ HAY QUE METER LAS LISTAS + WATCHLIST y WATCHED
	@Persistent(defaultFetchGroup="true")
	@Join
	ArrayList<FilmList> lists ;
	@Persistent(defaultFetchGroup="true")
	Watched watched;
	@Persistent(defaultFetchGroup="true")
	WatchList watchList;
	
	static Logger logger = Logger.getLogger(User.class.getName());
	
	public User() {
		this.nickname = "";
		this.icon = "";
		this.email = "";
		this.password = "";
	}
	
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
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	
	 public String toString() {
		StringBuffer messagesStr = new StringBuffer();
        return "User: login --> " + this.nickname + ", password -->  " + this.password + ", messages --> [" + messagesStr + "]";
    }
}

