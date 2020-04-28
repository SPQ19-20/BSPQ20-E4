package server.easyFilminDAO;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import server.easyFilminData.Comment;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.Genre;
import server.easyFilminData.User;
import server.easyFilminData.WatchList;
import server.easyFilminData.Watched;


public class EasyFilminJDO implements IEasyFilminDAO{
	
	private PersistenceManagerFactory pmf = null;

	static Logger logger = Logger.getLogger(EasyFilminJDO.class.getName());

	private ArrayList<Film>  allFilms;
	/**
	 * Initializes the attribute pmf (PersistenceManagerFactory).
	 * It is necessary in order to execute all the operations related to a JDO Database.
	 */
	public EasyFilminJDO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties"); 
	}

	
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.debug("Insert users in the DB");			
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
	public User loadUser(String username) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving users");			
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
			logger.error(" $ Error retrieving users using a 'Query': " + ex.getMessage());
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
	public void saveActor(Actor actor) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.debug("Insert actors in the DB");			
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
			tx.commit();
			
			
			return actor;
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
			logger.debug("Insert films in the DB");			
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
			tx.commit();
			
			
			return film;
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
			tx.commit();
			
			
			return director;
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

					//End the transaction
					tx.commit();
					
					
					return watched;
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


	@Override
	public List<Comment> loadComments(String filmTitle) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving comments");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();

			Query<Comment> query = pm.newQuery(Comment.class);
			query.setFilter("filmTitle == '" + filmTitle + "'"); 
			@SuppressWarnings("unchecked")
			List<Comment> comments = (List<Comment>) query.execute();

			//End the transaction
			tx.commit();
			
			
			return comments;
		} catch (Exception ex) {
			logger.error(" $ Error retrieving comments using a 'Query': " + ex.getMessage());
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

			//End the transaction
			tx.commit();
			
			
			return filmlist;
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
	public void saveComment(Comment comment) {
		// TODO Auto-generated method stub
		PersistenceManager pm = null;
		Transaction tx = null;
				
		try {
			logger.debug("Insert comment in the DB");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();
					
			pm.makePersistent(comment);
					
					
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
	public ArrayList<FilmList> getUserLists(String username) {
		// TODO Auto-generated method stub
		
		PersistenceManager pm = null;
		Transaction tx = null;
		
		try {
			logger.info("- Retrieving lists created by this user");			
			//Get the Persistence Manager
			pm = pmf.getPersistenceManager();
			//Obtain the current transaction
			tx = pm.currentTransaction();		
			//Start the transaction
			tx.begin();

			Query<FilmList> query = pm.newQuery(FilmList.class);
			query.setFilter("nickname == '" + username+ "'"); 
			@SuppressWarnings("unchecked")
			ArrayList<FilmList> userLists = (ArrayList<FilmList>) query.execute();

			//End the transaction
			tx.commit();
			
			
			return userLists;
		} catch (Exception ex) {
			logger.error(" $ Error retrieving this user's film lists: " + ex.getMessage());
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
}
