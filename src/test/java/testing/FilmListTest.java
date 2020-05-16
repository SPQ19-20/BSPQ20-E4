package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import junit.framework.JUnit4TestAdapter;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;


public class FilmListTest {
	
	private String listName;
	private Film film1;
	private Film film2;
	private static Film film3;
	private static Film film4;
	private static FilmList list1;
	private FilmList list2;
	private static FilmList list3;
	
	ResourceBundle resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
	
	static Logger logger = Logger.getLogger(FilmListTest.class.getName());
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(FilmListTest.class);
		}

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		film3 = new Film();
		film3.setTitle("Attack");
		film4 = new Film();
		film4.setTitle("Counter");
		list1 = new FilmList("Favourite films");
		list3 = new FilmList("Favourite films");
		ArrayList<Film> films = new ArrayList<Film>();
		ArrayList<Film> films2 = new ArrayList<Film>();
		films.add(film3);
		films.add(film4);
		list1.setFilmList(films);
		films2.add(film3);
		list3.setFilmList(films2);
		logger.info("Set up before class finished");
	}
	/*
	@Before
	public void setUp() throws Exception {
		list2 = new FilmList("Favourite films");
		logger.info("Set up finished");
	}
	*/
	@Test
	@PerfTest(invocations = 1000)
	@Required(max = 1500)
	public void testAddFilm() {
		list2 = new FilmList("Favourite films");
		list2.addFilm(film3);
		list2.addFilm(film4);
		assertEquals(list1.getFilmList(), list2.getFilmList());
		logger.debug("Add Film tested");
	}
	
	@Test
	@PerfTest(invocations = 1000)
	@Required(average = 70)
	public void testRemoveFilm() {
		list2 = new FilmList("Favourite films");
		list2.addFilm(film3);
		list2.addFilm(film4);
		list2.removeFilm("Counter");
		assertEquals(list3.getFilmList(), list2.getFilmList());
		logger.debug("Remove Film tested");
	}
	
	@Test
	@PerfTest(duration = 5000)
	@Required(throughput = 10)
	public void testSortFilmList() {
		list2 = new FilmList("Favourite films");
		list2.addFilm(film4);
		list2.addFilm(film3);
		list2.sortFilmList();
		assertEquals(list1.getFilmList(), list2.getFilmList());
		logger.debug("Sort Film tested");
	}
	
	@Test
	@PerfTest(invocations = 1000)
	@Required(throughput = 10)
	public void testSetName() {
		list2 = new FilmList("Favourite films");
		list2.setName("MyFilmList");
		assertEquals(list2.getName(), "MyFilmList");
		logger.debug("Set Name tested");
	}

}
