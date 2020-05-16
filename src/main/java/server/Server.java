package server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import serialization.FilmData;
import serialization.FilmListData;
import serialization.MessageData;
import serialization.UserData;
import server.easyFilminDAO.EasyFilminJDO;
import server.easyFilminDAO.IEasyFilminDAO;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.Message;
import server.easyFilminData.User;
import server.easyFilminData.WatchList;
import server.easyFilminData.Watched;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.controller.EasyFilmController;

/**@package server
 * @brief This is the documentation for the Java package server intended to work as the server side for the EasyFilmin Project.
 * This package is composed by 1 class, Server. 
 * The purpose of server is to offer a series of methods that communicate with the DAO layer and respond to any requests sent by the client.
 * Mainly consists on methods to handle login/register requests and film manipulation.
 */

/**
 * This class is in charge of handling the calls from the controller and cast them to the Data Access Layer
 *
 */
@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
public class Server {

	private int cont = 0;
	private IEasyFilminDAO iDAO = null;
	private Logger logger = Logger.getLogger(Server.class.getName());

	public Server() {
		this.iDAO = new EasyFilminJDO();
		
	}


	/** Checks if the login is correct
	 * @param us - UserData for a particular User
	 * @return a - Response with an OK/Not OK message 
	 */
	@POST
	@Path("/login")
	public Response login(UserData us) {
		User user = null;
		user = iDAO.loadUser(us.getLogin());
		
		if (user != null && us.getPassword().equals(user.getPassword())) {
			cont++;
			logger.debug(" * Client number: " + cont);
			return Response.ok("Login OK").build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Login details supplied for message delivery are not correct").build();
		}
	}

	
	/** REGISTERS a new User in the db 
	 * @param userData - UserData in order to register a user
	 * @return Reponse - replying if Register is OK or NOT
	 */
	@POST
	@Path("/register")
	public Response registerUser(UserData userData) {
		logger.warn("THIS METHOD SHOULD CHECK WITH METHOD");
		User user = null;
//		try {
//			iDAO.loadUser(userData.getLogin());
//		}catch(Exception e) {
			//Works good creating its 2 default lists (watchlist, watched)
			user = new User(userData.getLogin(), userData.getIcon(), userData.getEmail(),userData.getPassword());
			iDAO.saveUser(user);
			return Response.ok().build();				
//		}
//        return Response.status(Status.BAD_REQUEST).entity("This user name is invalid").build();      
	}
	
	/** GETs a particular User by its nick
	 * @param login - nick of the user
	 * @return usData - return a UserData corresponding to the user
	 */
	@GET
	@Path("/getUser/{nick}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserData login(@PathParam("nick") String login) {
		User user = null;
		try {
			user = iDAO.loadUser(login);
			logger.debug(" * Client number: " + cont);
			UserData usData = new UserData(user);
			return usData;
		}catch(Exception e) {
			logger.debug(" No User with that nick, returning null");
			return null;
		}
	}

	/** DELETEs a particular User from the DB by its nick
	 * @param nick - nick of the user
	 * @return Response - If the deletion was successful Status.OK / Status.NOT_FOUND if not
	 */
	@DELETE
	@Path("/deleteUser/{nick}")
	public Response deleteUser(@PathParam("nick") String nick) {
		iDAO.deleteUser(nick);
		System.out.println("Deleting user...");
		return Response.status(Response.Status.OK).build();

	}
	
	/** GETs a list of all lists for a given user
	 * @param login - nick of the given user
	 * @return listsData - ArrayList of FilmListData
	 */
	@GET
	@Path("/getAllLists/{nick}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<FilmListData> getAllLists(@PathParam("nick") String login) {
		User user = null;
		user = iDAO.loadUser(login);
		ArrayList<FilmList> lists = user.getLists(); //Método persistente de JDO
		
		ArrayList<FilmListData> listsData = new ArrayList<FilmListData>();
		int i = 0;
		logger.error(user.getNickname());
		if(lists!=null) {
			logger.error("Obteniendo listas:");
			for(FilmList f : lists) {
				//logger.error(f.getName());
				listsData.add(new FilmListData(f));
				logger.error(listsData.get(i).getName());
				i++;
			}
			
		}
		
		return listsData;
	}


	/** GETs a particular film by title 
	 * @param name - title of the film
	 * @return fData - film (serialized)
	 */
	@GET
	@Path("/getFilm/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public FilmData getFilm(@PathParam("name") String name) {
		Film f = null;
		try {
			f = iDAO.loadFilm(name);
		}catch(Exception e) {
			logger.debug("Exception retrieving film "+f.getTitle());
			logger.debug("Possibly "+f.getTitle()+" is not on the DB");
		}
		logger.debug(" * Client number: " + cont);
		
		FilmData fData = new FilmData(f);
		return fData;
	}

	/** GETs a particular filmlist by name
	 * @param nick - nick of the user which stores this particular filmList
	 * @param name - name of the filmlist
	 * @return flData - filmListData (serialized)
	 */
	@GET
	@Path("/getFilmList/{user}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public FilmListData getFilmList(@PathParam("user") String nick, @PathParam("name") String name) {
		FilmList fl = null;
		logger.info("User nick "+ nick);
		User u = iDAO.loadUser(nick);
		ArrayList<FilmList> arrFl =u.getLists();
		int temp = -1;
		for(int i = 0;i<arrFl.size();i++) {
			logger.error(name);
			if(arrFl.get(i).getName().equals(name)) temp = i; 
		}
		if(temp>=0) {
			fl = arrFl.get(temp);
			logger.error(fl.getName());
		}else {
			logger.error("NO HAY UNA LISTA CON EL MISMO NOMBRE");
		}
		
		FilmListData flData = new FilmListData(fl);
		
		return flData;
	}

	@POST
	@Path("/saveFilmList/{nick}")
	public Response saveFilmList(FilmListData flData, @PathParam("nick") String nick) {
		boolean editing = false;
		FilmList newFL = null;
		User u = iDAO.loadUser(nick);
		ArrayList<FilmList> fl = u.getLists();
		logger.error("Here arrives !: ");
		for(FilmList list: fl) {
			if(flData.getName().equals(list.getName())) { /**< We see if it is a new or a already created filmList */
				newFL = list;
				fl.remove(list);
				editing = true;
			}else {
				newFL = new FilmList(flData);				
				logger.error("NEW FL : "+newFL.getName());
			}
		}
		for(int i = 0;i<flData.getFilmList().size(); i++) {
			Film film = iDAO.loadFilm(flData.getFilmList().get(i));
			if(editing){
				for(Film ff : newFL.getFilmList()) {
					if(!ff.getTitle().equals(film.getTitle())) {						
						newFL.addFilm(film);
						logger.error("Adding film :"+film.getTitle());
					}
				}			
			}else {
				newFL.addFilm(film);				
				logger.error("Adding film :"+film.getTitle());
			}
		}
		if(editing) {
			fl.add(newFL);
			u.setLists(fl);
		}else {
			u.addFilmList(newFL);			
		}

		logger.error("LISTS UPDATED--> new list is :"+newFL.getName());
		iDAO.saveUser(u);
		return Response.ok().build();			
//		try {
//			iDAO.saveUser(u);
//			return Response.ok().build();			
//		}catch(Exception e) {
//			return Response.status(Status.BAD_REQUEST).entity("NOPE").build();			
//		}
	}


	/** Adds a given film to a particular list (REGULAR LIST NOT IMPLEMENTED YET) 
	 * @param listName - the name of the list is passed implicitly as a String
	 * @param filmTitle - title of the film is passed explicitly in the path
	 * @return A Response whether is successfully added OK or not BAD.RESPONSE 
	 */
	@POST
	@Path("/addToList/{title}")
	public Response addToList(String listName, @PathParam("title") String filmTitle) {
		boolean repeated = false;
		Film film = iDAO.loadFilm(filmTitle);
		if(listName.contentEquals("watchlist")) {
			WatchList w = iDAO.loadWatchList("watchlist");
			for(Film f : w.getFilmList()) {
				if (f.getTitle().contentEquals(filmTitle)){ //If not repeated we add it to watchlist
					repeated = true;
					break;
				}
			}
			if(!repeated) w.addFilm(film);
			iDAO.saveWatchList(w);

		} else if(listName.contentEquals("watched")) {

			Watched w = iDAO.loadWatched("watched");
			for(Film f : w.getFilmList()) {
				if (f.getTitle().contentEquals(filmTitle)){
					repeated = true;
					break;
				}
				if(!repeated) {
					w.addFilm(film); //If not repeated we add it to watched
					WatchList wl = iDAO.loadWatchList("watchlist");
					for(Film fl : wl.getFilmList()) {
						if (fl.getTitle().contentEquals(filmTitle)){ 
							wl.removeFilm(filmTitle);
							break;
						}
					}
				}
				iDAO.saveWatched(w);
			}
		} else {
			FilmList fl = iDAO.loadFilmList(listName);
			fl.addFilm(film);
			//saveList() method?
			//iDAO.saveList();
						      
		}
		if(!repeated) return Response.ok().build();
		else return Response.status(Status.BAD_REQUEST).entity("This film is already on your filmList").build();
	}
	
	@GET
	@Path("/getAllFilms")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getAllFilms() {
		List<Film> list = null;
		try {
			list = iDAO.getAllFilms();
		}catch(Exception e) {
			logger.debug("Exception retrieving films ");
		}
		ArrayList<String> allFilms = new ArrayList<>();
		for(Film f: list) {
			allFilms.add(f.getTitle());
		}
		return allFilms;
	}

	// Example method
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hola. Mensaje desde el servidor").build();
	}
	
	public static void main(String[] args) {
		
		Server s1 = new Server();
		User u = new User("Marcos3", "123");
		UserData us = new UserData(u);
		s1.registerUser(us);
		s1.login(us);
	}
}


