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
import server.easyFilminData.Director;

public class DirectorTest {
	
	private static Director d1;

	
	
	static Logger logger = Logger.getLogger(DirectorTest.class.getName());
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(DirectorTest.class);
		}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		d1 = new Director("David Lynch","david-pic.jpg","1952-10-15");
		logger.info("Set up before class finished");
	}
	
	@Test
	@PerfTest(invocations = 5000)
	@Required(average = 50)
	public void testCreateDirector() {
		Director d2 = new Director();
		d2.setName("David Lynch");
		d2.setPic("david-pic.jpg");
		d2.setBday("1952-10-15");
		
		assertEquals(d1.getName(),d2.getName());
		assertEquals(d1.getBday(),d2.getBday());
		assertEquals(d1.getPic(),d2.getPic());
		logger.info("CreateDirector test ended");
	}
}