package client.main;

import client.controller.EasyFilmController;
import client.ui.UserLog;
import server.easyFilminDAO.EasyFilminJDO;
import server.easyFilminData.User;

/**@package client.main
 * @brief This is the documentation for the Java package client.main intended to work as a wrapper for the Domain Object Model (DOM) of the EasyFilmin Project.
 * This package is composed by 2 classes, EasyFilmin and EasyFilminAdmin.
 * The purpose of this classes is to run the User interface or the Admin interface.
 */

/** This class is the main class of our project, gets the arguments of the 
 * @author BSPQ-E4
 *
 */
public class EasyFilmin {
	public static void main(String[] args) {
		EasyFilmController e = new EasyFilmController(args[0], args[1]); 
//		e.registerUser("egui2", "src/main/resources/jimmy.jpg", "11111@opendeusto.es","1234"); 
//		e.registerUser("Marcos", "Image3", "33333@opendeusto.es","1235");
//		e.registerUser("a", "Image3", "33333@opendeusto.es","a");
				
		UserLog u = new UserLog(e);
		u.setVisible(true);

	}
}


