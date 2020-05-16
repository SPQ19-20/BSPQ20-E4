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
	import server.easyFilminData.User;
	import server.easyFilminData.WatchList;
	import server.easyFilminData.Watched;

	public class UserTest {
		
		private static User u1;

		
		
		static Logger logger = Logger.getLogger(UserTest.class.getName());
		@Rule
		public ContiPerfRule i = new ContiPerfRule();
		
		public static junit.framework.Test suite() {
			return new JUnit4TestAdapter(UserTest.class);
			}
		
		@BeforeClass
		public static void setUpBeforeClass() throws Exception{
			u1 = new User("javalover","cool-icon.jpg","hello@hotmail.es","123456789");
			
			logger.info("Set up before class finished");
		}
		
		@Test
		@PerfTest(invocations = 5000)
		@Required(average = 50)
		public void testCreateUser() {
			User u2 = new User();
			u2.setNickname("javalover");
			u2.setIcon("cool-icon.jpg");
			u2.setPassword("123456789");
			u2.setEmail("hello@hotmail.es");
			
			assertEquals(u1.getEmail(),u2.getEmail());
			assertEquals(u1.getIcon(),u2.getIcon());
			assertEquals(u1.getNickname(),u2.getNickname());
			assertEquals(u1.getPassword(),u2.getPassword());
			logger.info("CreateUser test ended");
		}
	

}
