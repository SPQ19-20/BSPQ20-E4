package testing;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import client.controller.EasyFilmController;
import client.main.EasyFilmin;
import junit.framework.JUnit4TestAdapter;
import serialization.UserData;
import server.easyFilminDAO.EasyFilminJDO;
import server.easyFilminDAO.IEasyFilminDAO;
import server.easyFilminData.User;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

/**@package testing
 * @brief This is the documentation for the Java package testing intended to work as a tester for the correctness and performance of the EasyFilmin project's methods.
 * This package is composed by several classes, EasyFilmControllerTest, EasyFilminJDOTest, FilmListTest and SerializationTest. 
 * Each one tests the methods for one part of the EasyFilmin project. ALl tests implement performance testing through Contiperf.
 */
public class EasyFilmControllerTest {
	
	private static String[] arg = {"127.0.0.1", "8080"};
	private static Thread jettyServerThread = null;
	private static Server server; 
	
	private int num1 = 2;
	private int num2 = 0;
	private static UserData us1;
	private static UserData us2;
	
	private static EasyFilmController controller;
	private static IEasyFilminDAO iDAO;
	@Rule
	public ContiPerfRule i = new ContiPerfRule();
	private static Logger logger = Logger.getLogger(EasyFilmControllerTest.class.getName());
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(EasyFilmControllerTest.class);
		}
	
	@BeforeClass static public void setUp() throws Exception {
		us1 = new UserData("example","example","example");
		iDAO = new EasyFilminJDO();
		//Launch the jetty server
		server = new Server(8080);

		ServletContextHandler servletContextHandler = new ServletContextHandler(NO_SESSIONS);

		servletContextHandler.setContextPath("/");
		server.setHandler(servletContextHandler);

		ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/rest/*");
		servletHolder.setInitOrder(0);
		servletHolder.setInitParameter("jersey.config.server.provider.packages","server");

		controller = new EasyFilmController(arg[0], arg[1]);
		Thread.sleep(2500);

		try {
			server.start();
			//server.join();
		} catch (Exception ex) {
			logger.error("Error occurred while starting Jetty", ex);
			System.exit(1);
		}
	}
	@Test public void testRegisterLoginDelete() {
		//First not Registered
		us2 = controller.getUser("example");
		assertNull(us2);
		controller.registerUser("example", null, "example", "example");
		// Then registered and retrieved successfully
		assertNotNull(iDAO.loadUser("example"));
		us2 = controller.getUser("example");
		// Login
		assertTrue(controller.login(us2.getLogin(), us2.getPassword()));
		controller.deleteUser("example");
		us2 = controller.getUser("example");
		//Finally deleted
		assertNull(us2);

	}
	
	@AfterClass static public void tearDown() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}


