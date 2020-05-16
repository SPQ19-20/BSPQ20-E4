package testing;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import server.easyFilminData.Actor;

public class ActorTest {
	
	private static Actor a1;

	
	
	static Logger logger = Logger.getLogger(ActorTest.class.getName());
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ActorTest.class);
		}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		a1 = new Actor("Jake Gyllenhaal","jake-pic.jpg","1982-08-05");
		logger.info("Set up before class finished");
	}
	
	@Test
	@PerfTest(invocations = 5000)
	@Required(average = 50)
	public void testCreateActor() {
		Actor a2 = new Actor();
		a2.setName("Jake Gyllenhaal");
		a2.setPic("jake-pic.jpg");
		a2.setBday("1982-08-05");
		
		assertEquals(a1.getName(),a2.getName());
		assertEquals(a1.getBday(),a2.getBday());
		assertEquals(a1.getPic(),a2.getPic());
		logger.info("CreateActor test ended");
	}
}