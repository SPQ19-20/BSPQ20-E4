package testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import server.easyFilminData.Actor;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.Genre;

public class FilmTest {
	
	private static Film film1;
	private static ArrayList<Actor> actors1 = new ArrayList<Actor>();
	private static ArrayList<Director> directors1 = new ArrayList<Director>();
	
	
	static Logger logger = Logger.getLogger(FilmTest.class.getName());
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(FilmTest.class);
		}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		Actor actor1 = new Actor("Actor1");
		Director director1 = new Director("Director1");
		actors1.add(actor1);
		directors1.add(director1);
		
		film1 = new Film("Titulo1", "Poster1", "Release1", "Description1", 1, new Genre("Genre1"), 2, actors1, directors1);
		logger.info("Set up before class finished");
	}
	
	@Test
	@PerfTest(invocations = 5000)
	@Required(average = 50)
	public void testCreateFilm() {
		Film film2 = new Film();
		film2.setTitle("Titulo1");
		film2.setPoster("Poster1");
		film2.setRelease("Release1");
		film2.setDescription("Description1");
		film2.setDur(1);
		film2.setGenre(new Genre("Genre1"));
		film2.setRating(2);
		film2.setActors(actors1);
		film2.setDirector(directors1);
		
		assertEquals(film1.getTitle(), film2.getTitle());
		assertEquals(film1.getPoster(), film2.getPoster());
		assertEquals(film1.getRelease(), film2.getRelease());
		assertEquals(film1.getDescription(), film2.getDescription());
		assertEquals(film1.getDur(), film2.getDur());
		assertEquals(film1.getGenre().getName(), film2.getGenre().getName());
		assertEquals(film1.getRating(), film2.getRating(), 1);
		assertEquals(film1.getActors(), film2.getActors());
		assertEquals(film1.getDirector(), film2.getDirector());
		logger.info("CreateFilm test ended");
	}
	
	@Test
	@PerfTest(invocations = 5000)
	@Required(average = 20)
	public void testrateFilm() {
		Film film2 = new Film();
		film2.rateFilm(2);
		assertEquals(film1.getRating(), film2.getRating(), 1);
	}
}
