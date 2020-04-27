package server.easyFilminData;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Represents a list of film the user wishes to watch. Extends basic FilmList class.
 * @author BSPQ20E4
 * @version 1.0
 * @since 2020-04-16
 */
@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class WatchList extends FilmList {

	public WatchList(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

}
