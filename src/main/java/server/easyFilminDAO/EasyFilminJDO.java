package server.easyFilminDAO;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import client.ui.FilmListUI;
import server.easyFilminData.Actor;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.Genre;
import server.easyFilminData.User;
import server.easyFilminData.WatchList;
import server.easyFilminData.Watched;


public class EasyFilminJDO implements IEasyFilminDAO{
	
	private PersistenceManagerFactory pmf = null; /** This variable declares a PMF, which is initialized by the constructor as soon as an instance of the class is created */
	ResourceBundle resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault()); /** This variable sets the resourceBundle to the default language configured in the PC */
	static Logger logger = Logger.getLogger(EasyFilminJDO.class.getName());

	private ArrayList<Film>  allFilms;
	/**
	 * Initializes the attribute pmf (PersistenceManagerFactory).
	 * It is necessary in order to execute all the operations related to a JDO Database.
	 */
	public EasyFilminJDO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties"); 
		//resourceBundle = ResourceBundle.getBundle("SystemMessages",	Locale.forLanguageTag("en"));
	}

	
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info(resourceBundle.getString("persisting_users_msg"));			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(user);
			
			
			//End the transaction
			tx.commit();
			logger.info(resourceBundle.getString("persisted_users_msg"));
			
		} catch (Exception ex) {
			logger.error(resourceBundle.getString("persisting_users_error") + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.info("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
		
	}

	@Override
	public User loadUser(String username) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info(resourceBundle.getString("retrieving_users_msg"));			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<User> query = pm.newQuery(User.class);
			query.setFilter("nickname == '" + username + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			User user = (User) query.execute();

			//End the transaction
			User duser = pm.detachCopy(user);
			tx.commit();
			
			
			return duser;
		} catch (Exception ex) {
			logger.error(resourceBundle.getString("retrieving_exception_msg") + ex.getMessage());
			
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
		return null;
	}
	
	@Override
	public void deleteUser(String username) {

		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info(resourceBundle.getString("deleting_users_msg"));			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<User> query = pm.newQuery(User.class);
			query.setFilter("nickname == '" + username + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			User user = (User) query.execute();

			//End the transaction
			pm.deletePersistent(user);
			tx.commit();
			
			
			
		} catch (Exception ex) {
			logger.error(" $ Error deleting users using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}
	
	
	/**
	 * THE FOLLOWING DELETING METHODS ARE CREATED ONLY FOR JUNIT TESTING PORPUSES
	 * SO THAT WE DO NOT HAVE USELESS TEST OBJECTS
	 * ----NOT VERY LIKELY TO DELETE MOVIES AND SO FROM OUR EASYFILMIN DATABSE
	 * @param moviename String with the name of the movie wished to be deleted
	 */
	public void deleteFilm(String moviename) {

		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Deleting film");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<Film> query = pm.newQuery(Film.class);
			query.setFilter("title == '" + moviename + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Film film = (Film) query.execute();

			//End the transaction
			pm.deletePersistent(film);
			tx.commit();
			
			
			
		} catch (Exception ex) {
			logger.error(" $ Error deleting movie using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}
	public void deleteActor(String name) {

		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Deleting actor");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<Actor> query = pm.newQuery(Actor.class);
			query.setFilter("name == '" + name + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Actor actor = (Actor) query.execute();

			//End the transaction
			pm.deletePersistent(actor);
			tx.commit();
			
			
			
		} catch (Exception ex) {
			logger.error(" $ Error deleting actor using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}
	
	public void deleteDirector(String name) {

		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Deleting director");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<Director> query = pm.newQuery(Director.class);
			query.setFilter("name == '" + name + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Director director = (Director) query.execute();

			//End the transaction
			pm.deletePersistent(director);
			tx.commit();
			
			
			
		} catch (Exception ex) {
			logger.error(" $ Error deleting director using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}
	
	
	

	

	@Override
	public void saveActor(Actor actor) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("Insert actors in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(actor);
						
			//End the transaction
			tx.commit();
			logger.debug("Changes committed");
			
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.debug("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
		
	}

	@Override
	public Actor loadActor(String name) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving actors");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();

			Query<Actor> query = pm.newQuery(Actor.class);
			query.setFilter("name == '" + name + "'"); //we find the actor by his name
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Actor actor = (Actor) query.execute();

			//End the transaction
			Actor dactor = pm.detachCopy(actor);
			tx.commit();
			return dactor;
		} catch (Exception ex) {
			System.err.println(" $ Error retrieving actors using a 'Query': " + ex.getMessage());
			
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		return null;
	}


	@Override
	public void saveFilm(Film film) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("Insert films in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(film);
			
			
			//End the transaction
			tx.commit();
			logger.debug("Changes committed");
			
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.debug("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
		
	}


	@Override
	public Film loadFilm(String title) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving films");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();

			Query<Film> query = pm.newQuery(Film.class);
			query.setFilter("title == '" + title + "'"); //we find the film by his title
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Film film = (Film) query.execute();

			//End the transaction
			
			Film dfilm = pm.detachCopy(film);
			tx.commit();
			return dfilm;
		} catch (Exception ex) {
			logger.error(" $ Error retrieving films using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		return null;
	}

	
	@SuppressWarnings("null")
	@Override
	public void startBD() {
		// TODO Auto-generated method stub
		
		/**This method reads data from csv files to save it into the DB.
		 * 
		 * @param actores --> arrayList containing the actors' names which have been read from the 'Actors' field in films.csv
		 * @param directores --> arrayList containing the directors' names which have been read from the 'Directors' field in films.csv
		 * 
		 * each time a new row is read both arrayLists are set null so that it does not store actors/directors from previously read films
		 * 
		 * 
		 * 
		 * @param actors/directors -> arrayList used specifically to saveActor/Director (might not be used later on)
		 */
			
		try {
						
			CSVReader readFilms = new CSVReaderBuilder(new FileReader("src\\main\\resources\\filmsPRUEBA.csv")).withSkipLines(1).build(); 
			CSVReader readActors = new CSVReaderBuilder(new FileReader("src\\main\\resources\\actors.csv")).withSkipLines(1).build();
			CSVReader readDirectors = new CSVReaderBuilder(new FileReader("src\\main\\resources\\directors.csv")).withSkipLines(1).build(); 
			 
			ArrayList<Film> films = new ArrayList<Film>();
			ArrayList<Actor> actors = new ArrayList<Actor>();
			ArrayList<Director> directors = new ArrayList<Director>();
			
			String[] values = null;
		    try {
				//Read ACTORS from CSV
				while ((values = readActors.readNext()) != null){
					if(values.length==2) {
						String name =values[0];
						String bday = values[1];
						
						actors.add(new Actor(name, bday));
					}
				}
				//Save Actors in BD
				for (Actor actor : actors) {
					saveActor(actor);
				}		
				//Read DIRECTORS from CSV
				while ((values = readDirectors.readNext()) != null){
					if(values.length==2) {
						String name =values[0];
						String bday = values[1];
						
						directors.add(new Director(name,null,bday));
					}
				}
				//Save Directors in BD
				for (Director director : directors) {
					saveDirector(director);
				}
				
				//Read FILMS from CSV
				ArrayList<Actor> actores =new ArrayList<>();
				ArrayList<Director> directores =new ArrayList<>();
				
				while ((values = readFilms.readNext()) != null){
					if(values.length==9) {
						logger.info("VALUES LENGTH: "+values.length);
						String title = values[0];
						String pic = values[1];
						String year = values[2];
						String desc = values[3];
						int dur = Integer.parseInt(values[4]);
						String gen = values[5];
						Genre g= new Genre(gen);
						double rate = Double.parseDouble(values[6]);
						
						String[] ac = values[7].split("[|]");
						for (String a : ac) {
							logger.info("String que se guarda como actor: "+a);
							actores.add(new Actor(a));
						}	
						String[] dr =values[8].split("[|]");
						for (String d : dr) {
							directores.add(new Director(d));
						}
						
						films.add(new Film(title, pic,year,desc,dur,g,rate,actores,directores));
					}
				}
				//Save Films to DB -- we only save films that are not in the DB
				for (Film film : films) {
					if (loadFilm(film.getTitle()) == null ) {
						saveFilm(film);	
						
				}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (com.opencsv.exceptions.CsvValidationException cve) {
				cve.printStackTrace();
			}
		    
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void saveDirector(Director director) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.debug("Insert directors in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(director);
			
			
			//End the transaction
			tx.commit();
			logger.debug("Changes committed");
			
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.debug("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
		
	}


	@Override
	public Director loadDirector(String name) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving directors");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();

			Query<Director> query = pm.newQuery(Director.class);
			query.setFilter("name == '" + name + "'"); //we find the film by his title
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Director director = (Director) query.execute();

			//End the transaction
			Director ddirector = pm.detachCopy(director);
			tx.commit();
			return ddirector;
		} catch (Exception ex) {
			logger.error(" $ Error retrieving directors using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		return null;
	}


	@Override
	public Watched loadWatched(String name) {
		// TODO Auto-generated method stub
				PersistenceManager pm = null;
				Transaction tx = null;
				
				try {
					logger.info("- Retrieving watched");			
					//Get the Persistence Manager
					pm = pmf.getPersistenceManager();
					//Obtain the current transaction
					tx = pm.currentTransaction();		
					//Start the transaction
					tx.begin();

					Query<Watched> query = pm.newQuery(Watched.class);
					query.setFilter("name == '" + name + "'"); 
					query.setUnique(true);
					@SuppressWarnings("unchecked")
					Watched watched = (Watched) query.execute();
					
					Watched dwatched = pm.detachCopy(watched);
					//End the transaction
					tx.commit();
					
					
					return dwatched;
				} catch (Exception ex) {
					logger.error(" $ Error retrieving directors using a 'Query': " + ex.getMessage());
				} finally {
					if (tx != null && tx.isActive()) {
						tx.rollback();
					}
					
					if (pm != null && !pm.isClosed()) {
						pm.close();
					}
				}
				return null;
		
	}


	@Override
	public void saveWatched(Watched watched) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.debug("Insert watched in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(watched);
			
			
			//End the transaction
			tx.commit();
			logger.debug("Changes committed");
			
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.debug("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
	}


	@Override
	public WatchList loadWatchList(String name) {
		// TODO Auto-generated method stub
				PersistenceManager pm = null;
				Transaction tx = null;
				
				try {
					logger.info("- Retrieving WatchList");			
					//Get the Persistence Manager
					pm = pmf.getPersistenceManager();
					//Obtain the current transaction
					tx = pm.currentTransaction();		
					//Start the transaction
					tx.begin();

					Query<WatchList> query = pm.newQuery(WatchList.class);
					query.setFilter("name == '" + name + "'"); 
					query.setUnique(true);
					@SuppressWarnings("unchecked")
					WatchList watchlist = (WatchList) query.execute();

					//End the transaction
					tx.commit();
					
					
					return watchlist;
				} catch (Exception ex) {
					logger.error(" $ Error retrieving WatchList using a 'Query': " + ex.getMessage());
				} finally {
					if (tx != null && tx.isActive()) {
						tx.rollback();
					}
					
					if (pm != null && !pm.isClosed()) {
						pm.close();
					}
				}
				return null;
		
	}


	@Override
	public void saveWatchList(WatchList watchlist) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.debug("Insert watchlist in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(watchlist);
			
			
			//End the transaction
			tx.commit();
			logger.debug("Changes committed");
			
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.debug("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
	}

	

	public List<Film> getAllFilms(){
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		
		try {
			logger.info("- Retrieving all films stored");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			
			Query<Film> query = pm.newQuery(Film.class);
		
			@SuppressWarnings("unchecked")
			List<Film> allFilms = (List<Film>) query.execute();
			
			
			/* ANOTHER WAYS IT COULD BE DONE

			 Extent<Film> e = pm.getExtent(Film.class, true); 
			 
			 //An Extent is a collection of objects of a particular 
			  * type of object that have been persisted.
			  * 
			    Iterator<Film> iter=e.iterator();
			    while (iter.hasNext())*/
			/*    {
			        Film film=(Film)iter.next();*/
			
			
			
		//	 allFilms.add(film);
			
			
		/*	Query<Film> query = pm.newQuery(Film.class);
			
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Film film = (Film) query.execute();
			users = (List<User>) query.execute();*/

			//End the transaction
			tx.commit();
			
			
			return  allFilms;
			  
		} catch (Exception ex) {
			logger.error(" $ Error retrieving all films: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		return null;
	}
	
	
	


	@Override
	public FilmList loadFilmList(String listName) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving FilmList");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();

			Query<FilmList> query = pm.newQuery(FilmList.class);
			query.setFilter("name == '" + listName + "'"); 
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			FilmList filmlist = (FilmList) query.execute();
			
			FilmList dfilmlist = pm.detachCopy(filmlist);
			//End the transaction
			tx.commit();
			
			
			return dfilmlist;
		} catch (Exception ex) {
			logger.error(" $ Error retrieving WatchList using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		return null;
	}


	@Override
	public void deleteWatched(String name) {
		
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Deleting watched");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<Watched> query = pm.newQuery(Watched.class);
			query.setFilter("name == '" + name + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			Watched watched = (Watched) query.execute();

			//End the transaction
			pm.deletePersistent(watched);
			tx.commit();
			
			
			
		} catch (Exception ex) {
			logger.error(" $ Error deleting Watched using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}


	@Override
	public void deleteWatchList(String name) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Deleting watchList");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<WatchList> query = pm.newQuery(WatchList.class);
			query.setFilter("name == '" + name + "'"); //we find the user by his username
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			WatchList watchList = (WatchList) query.execute();

			//End the transaction
			pm.deletePersistent(watchList);
			tx.commit();
			
			
			
		} catch (Exception ex) {
			logger.error(" $ Error deleting WatchList using a 'Query': " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}


	@Override
	public void saveFilmList(FilmList filmList) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.debug("Insert filmList in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			pm.makePersistent(filmList);
			
			
			//End the transaction
			tx.commit();
			logger.debug("Changes committed");
			
		} catch (Exception ex) {
			logger.error(" $ Error storing objects in the DB: " + ex.getMessage());
			ex.printStackTrace();
		
		}finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
				logger.debug("Changes rollbacked");
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
				logger.debug("Closing the connection");
				// ATTENTION -  Datanucleus detects that the objects in memory were changed and they are flushed to DB
			}
		}
		
	}


	@Override
	public void deleteFilmList(String name) {
		// TODO Auto-generated method stub
				PersistenceManager pm = null;
				Transaction tx = null;
				
				try {
					logger.info("- Deleting filmList");			
					//Get the Persistence Manager
					pm = pmf.getPersistenceManager();
					//pm.getFetchPlan().setMaxFetchDepth(4);
					//Obtain the current transaction
					tx = pm.currentTransaction();		
					//Start the transaction
					tx.begin();
					
					Query<FilmList> query = pm.newQuery(FilmList.class);
					query.setFilter("name == '" + name + "'"); 
					query.setUnique(true);
					@SuppressWarnings("unchecked")
					FilmList filmList = (FilmList) query.execute();

					//End the transaction
					pm.deletePersistent(filmList);
					
					tx.commit();
					
					
					
				} catch (Exception ex) {
					logger.error(" $ Error deleting filmList using a 'Query': " + ex.getMessage());
				} finally {
					if (tx != null && tx.isActive()) {
						tx.rollback();
					}
					
					if (pm != null && !pm.isClosed()) {
						pm.close();
					}
				}
		
	}


	@Override
	public void cleanBD() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
try {		
		tx.begin();	 
			
		Query<Film> queryF = pm.newQuery(Film.class);
		logger.info(" * '" + queryF.deletePersistentAll() + "' films deleted from the DB.");			
		
		Query<Actor> queryA = pm.newQuery(Actor.class);
		logger.info(" * '" + queryA.deletePersistentAll() + "' actors deleted from the DB.");
		
		Query<Director> queryD = pm.newQuery(Director.class);
		logger.info(" * '" + queryD.deletePersistentAll() + "' directors deleted from the DB.");
		
		Query<User> queryU = pm.newQuery(User.class);
		logger.info(" * '" + queryU.deletePersistentAll() + "' users deleted from the DB.");
		
		Query<Watched> queryW = pm.newQuery(Watched.class);
		logger.info(" * '" + queryW.deletePersistentAll() + "' watched deleted from the DB.");
		
		Query<WatchList> queryWl = pm.newQuery(WatchList.class);
		logger.info(" * '" + queryWl.deletePersistentAll() + "' watchList deleted from the DB.");
		
		tx.commit();
		
	} catch (Exception ex) {
		logger.error(" $ Error cleaning the DB: " + ex.getMessage());
		ex.printStackTrace();
	} finally {
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
		
		if (pm != null && !pm.isClosed()) {
			pm.close();
		}
	}
		
	}


	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info(resourceBundle.getString("retrieving_users_msg"));			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(4);
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
			
			Query<User> query = pm.newQuery(User.class);
			query.setFilter("nickname == '" + user.getNickname() + "'"); //we find the same user in the DB to update him
			query.setUnique(true);
			@SuppressWarnings("unchecked")
			User userLoaded = (User) query.execute();

			//Aquí haz los cambios que quieras a userLoaded (getters y setters o add, lo que sea)
			//Si necesitas aádir algún parametro más pues lo pasas y ya, lo que necesites
			
			
			//Todos los cambios que quieras que se guarden se deben hacer antes del tx.commit()
			tx.commit();
			
		} catch (Exception ex) {
			logger.error(resourceBundle.getString("retrieving_exception_msg") + ex.getMessage());
			
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
		
	}
	
}
//
//	@Override
//	public ArrayList<FilmList> getUserLists(String username) {
//		// TODO Auto-generated method stub
//		
//		PersistenceManager pm = null;
//		Transaction tx = null;
//		
//		try {
//			logger.info("- Retrieving lists created by this user");			
//			//Get the Persistence Manager
//			pm = pmf.getPersistenceManager();
//			//Obtain the current transaction
//			tx = pm.currentTransaction();		
//			//Start the transaction
//			tx.begin();
//
//			Query<FilmList> query = pm.newQuery(FilmList.class);
//			query.setFilter("nickname == '" + username+ "'"); 
//			@SuppressWarnings("unchecked")
//			ArrayList<FilmList> userLists = (ArrayList<FilmList>) query.execute();
//
//			//End the transaction
//			tx.commit();
//			
//			
//			return userLists;
//		} catch (Exception ex) {
//			logger.error(" $ Error retrieving this user's film lists: " + ex.getMessage());
//		} finally {
//			if (tx != null && tx.isActive()) {
//				tx.rollback();
//			}
//			
//			if (pm != null && !pm.isClosed()) {
//				pm.close();
//			}
//		}
//		return null;
//		
//	}
//}
