package testing;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;
import serialization.ActorData;
import serialization.DirectorData;
import serialization.FilmData;
import serialization.FilmListData;
import serialization.UserData;
import server.easyFilminData.Actor;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;
import server.easyFilminData.User;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;

public class SerializationTest {
	
	private static Actor a1,a2;
	private static Director d1;
	private static Film f1,f2,f3;
	private static FilmList fl;
	private static User u1;

	
	
	
	static Logger logger = Logger.getLogger(SerializationTest.class.getName());
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(SerializationTest.class);
		}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		
		ArrayList<Film> films = new ArrayList<>();
		ArrayList<FilmList> lists = new ArrayList<>();
		ArrayList<Actor> actors = new ArrayList<>();
		ArrayList<Director> directors = new ArrayList<>();
	
		
		a1 = new Actor("Emma Watson");
		a2 = new Actor("Zendaya");
		d1 = new Director("Tim Burton");
		actors.add(a1);
		actors.add(a2);
		
		directors.add(d1);
		
		f1 = new Film();
		f2 = new Film();
		f3 = new Film();
		f1.setTitle("Avengers");
		f1.setRating(6.7);;
		f1.setDur(145);
		f1.setDirector(directors);
		f1.setActors(actors);
		
		
		f2.setTitle("Narnia");
		f3.setTitle("Cinderella");
		
		
		
		films.add(f1);
		films.add(f2);
		films.add(f3);
		fl = new FilmList("MyList");
		fl.setFilmList(films);
		lists.add(fl);
		
		u1 = new User("pruebaelena",null,"prueba@elena.es","012345");
		u1.setLists(lists);
		
		LocalDate now = LocalDate.now();
		System.out.println(now);		
		
		logger.info("Set up before class finished");
	}
	
	
	
	@Test
	@PerfTest(invocations = 1000)
	@Required(max = 1500)
	public void testUserData() {
		UserData ud= new UserData(u1);
		assertTrue(ud.getLogin().equals(u1.getNickname()));
		assertTrue(ud.getEmail().equals(u1.getEmail()));
		assertTrue(ud.getPassword().equals(u1.getPassword()));
		assertTrue((ud.getLists().size() == 1));
	}
	@Test
	@PerfTest(invocations = 1000)
	@Required(max = 1500)
	public void testActorData() {
		ActorData ad = new ActorData(a1);
		assertTrue(ad.getName().contentEquals(a1.getName()));
		}
	
	@Test
	@PerfTest(invocations = 1000)
	@Required(max = 1500)
	public void testDirectorData() {
		DirectorData dd = new DirectorData(d1);
		assertTrue(dd.getName().contentEquals(d1.getName()));
		
	}
	@Test
	@PerfTest(invocations = 1000)
	@Required(max = 1500)
	public void testFilmData() {
		FilmData fd = new FilmData(f1);
		assertTrue(fd.getTitle().equals(f1.getTitle()));
		assertTrue(fd.getDur() == (f1.getDur()));
		assertTrue(fd.getRating()==(f1.getRating()));
		assertTrue((fd.getActors().size() == 2));
		assertTrue((fd.getDirector().size() == 1));
		
		
				
	}
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@PerfTest(invocations = 1000)
	@Required(max = 1500)
	public void testFilmListData() {
		
		FilmListData fld = new FilmListData(fl);
		assertTrue(fld.getName().equals(fl.getName()));

		String f= fl.getFilmList().get(0).getTitle();
			
			assertTrue(fld.getFilmList().get(0).equals(f));
		}
	
}
