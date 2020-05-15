package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.Comment;

import org.apache.log4j.Logger;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import serialization.FilmListData;
import server.easyFilminDAO.EasyFilminJDO;
import server.easyFilminDAO.IEasyFilminDAO;
import server.easyFilminData.Actor;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.Genre;
import server.easyFilminData.User;
import server.easyFilminData.WatchList;
import server.easyFilminData.Watched;

public class EasyFilminJDOTest {
	static Logger logger = Logger.getLogger(EasyFilminJDOTest.class.getName());
	
	private User user = new User("UserTest1", "23", "test@opentest.test", "321");
	private Actor a = new Actor("ActorTest0","pic1","1965-03-16");
	private Director d = new Director("DirectorTest1","pic2","1954-09-10");
	private static Film f = new Film();
	private static Film fprueba = new Film("prueba", "","","",1,new Genre("Drama"),2.5, null, null);
	private IEasyFilminDAO iDAO = new EasyFilminJDO();
	static LocalDate ld;
	
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	public EasyFilminJDOTest() {
		// TODO Auto-generated constructor stub

	}
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(EasyFilminJDOTest.class);
		}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		f.setTitle("Film1");
		f.setDescription("NiceFilm10/10");
		f.setRelease("1954-09-10");
		f.setRating(5);
		
	}
	
	
	@Test
	@PerfTest(invocations = 5)
	@Required(max = 2500)
	public void testSaveLoadUser() {
		iDAO = new EasyFilminJDO();
		User userLoaded = null;
		iDAO.saveUser(user);
		userLoaded = iDAO.loadUser("UserTest1");
		iDAO.deleteUser("UserTest1"); 
		assertEquals("UserTest1", userLoaded.getNickname());
		assertEquals("23", userLoaded.getIcon()); 
		assertEquals("test@opentest.test", userLoaded.getEmail());
		assertEquals("321", userLoaded.getPassword());
		assertEquals("Watched", userLoaded.getWatched().getName());
		assertEquals("Watched", userLoaded.getLists().get(0).getName());
		assertTrue(userLoaded.getLists().get(0).getFilmList().isEmpty());
		//FilmListData fg = new FilmListData(userLoaded.getLists().get(0));
		//assertEquals("Watched", fg.getName());
		//assertTrue(fg.getFilmList().isEmpty());
		logger.debug("Save Load User tested");

	}
	
	@Test
	@PerfTest(invocations = 20, threads = 2)
	@Required(average = 700)
	public void testSaveLoadActor() {
		iDAO = new EasyFilminJDO();
		Actor actorLoaded1=null;
		iDAO.saveActor(a); 
		actorLoaded1 =iDAO.loadActor("ActorTest0");
		iDAO.deleteActor("ActorTest0");
		assertEquals("ActorTest0", actorLoaded1.getName());
		assertEquals("pic1", actorLoaded1.getPic());
		assertEquals("1965-03-16", actorLoaded1.getBday());
		logger.debug("Save Load Actor tested");
	}
	@Test
	@PerfTest(invocations = 20, threads = 2)
	@Required(average = 700)
	public void testSaveLoadDirector() {
		iDAO = new EasyFilminJDO();
		Director directorLoaded1=null;
		iDAO.saveDirector(d); 
		directorLoaded1 =iDAO.loadDirector("DirectorTest1");
		iDAO.deleteDirector("DirectorTest1");
		assertEquals("DirectorTest1", directorLoaded1.getName());
		assertEquals("pic2", directorLoaded1.getPic());
		assertEquals("1954-09-10", directorLoaded1.getBday());
		logger.debug("Save Load Director tested");
	}
	
	@Test
	@PerfTest(invocations = 20)
	@Required(average = 700)
	public void testSaveLoadFilm() {
		iDAO = new EasyFilminJDO();
		Film fLoaded1 = null;
		iDAO.saveFilm(f); 
		fLoaded1 = iDAO.loadFilm("Film1");
		iDAO.deleteFilm("Film1");
		assertEquals("Film1", fLoaded1.getTitle());
		assertEquals("NiceFilm10/10", fLoaded1.getDescription());
		assertEquals("1954-09-10", fLoaded1.getRelease());
		assertEquals(5, fLoaded1.getRating(), 0.2);
		logger.debug("Save Load Film tested");
	}


	@Test
	@PerfTest(invocations = 5)
	@Required(max = 5000)
	public void testSaveLoadWatched() {
		iDAO = new EasyFilminJDO();
		Watched w = new Watched("Watched1");
		w.addFilm(fprueba);
		Watched wLoaded = null;
		iDAO.saveWatched(w); 
		wLoaded =iDAO.loadWatched("Watched1");
		iDAO.deleteWatched("Watched1");
		iDAO.deleteFilm("prueba");
		assertEquals(wLoaded.getFilmList().get(0).getTitle(), w.getFilmList().get(0).getTitle());
		logger.debug("Save Load Watched tested");
	}


	@Test
	@PerfTest(invocations = 5)
	@Required(max = 5000)
	public void testSaveLoadWatchList() {
		iDAO = new EasyFilminJDO();
		WatchList w = new WatchList("WatchList1");
		w.addFilm(fprueba);
		WatchList wLoaded = null;
		iDAO.saveWatchList(w); 
		wLoaded =iDAO.loadWatchList("WatchList1");
		iDAO.deleteWatchList("WatchList1");
		iDAO.deleteFilm("prueba");
		assertEquals(wLoaded.getFilmList().get(0).getTitle(), w.getFilmList().get(0).getTitle());
		logger.debug("Save Load WatchList tested");
	}

	//CANNOT INSTANTIATE COMMENT ERROR 
//	@Test
//	@PerfTest(invocations = 15)
//	@Required(max = 5000)
//	public void testSaveLoadComments() {
//		iDAO = new EasyFilminJDO();
//		ld = LocalDate.of(2020, 5, 1);
//		ArrayList<Comment> ac = new ArrayList<>();
//		ac.add(new Comment("prueba", "Comment1", ld));
//		Film prueba = new Film("prueba", "","","",1,new Genre("Drama"),2.5, null, null);
//		prueba.setComments(comments);
//	}
	
	
	@Test
	@PerfTest(invocations = 20)
	@Required(average = 5000)
	public void startBDgetAllFilmsTest() {
		iDAO = new EasyFilminJDO();
		iDAO.startBD();
		List<Film> alFilms = iDAO.getAllFilms();
		List<Film> alPrueba = null;
		BufferedReader bufferedReader;
	    int count = 0;
		try {
			bufferedReader = new BufferedReader(new FileReader("src/main/resources/filmsPRUEBA.csv"));
		    String input;
		    while((input = bufferedReader.readLine()) != null)count++;
		    logger.info("Count : "+count);

		} catch (IOException e) {
			logger.error("CSV FILE NOT FOUND");
			e.printStackTrace();
		}
		assertEquals(alFilms.size(), count-2);
		iDAO.cleanBD();
		logger.debug("get All Films tested");
	}
	
	
	@Test
	@PerfTest(invocations = 5)
	@Required(max = 5000)
	public void testSaveloadFilmList() {
		iDAO = new EasyFilminJDO();
		FilmList f = new FilmList("NoName");
		f.addFilm(fprueba);
		FilmList fLoaded = null;
		iDAO.saveFilmList(f); 
		fLoaded =iDAO.loadFilmList("NoName");
		iDAO.deleteFilmList("NoName");
		iDAO.deleteFilm("prueba");
		assertEquals(fLoaded.getFilmList().get(0).getTitle(), f.getFilmList().get(0).getTitle());
		logger.debug("Save Load FilmList tested");
	}
	
}
