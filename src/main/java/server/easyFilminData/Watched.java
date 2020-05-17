package server.easyFilminData;

import java.util.ArrayList;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Represents a list of films the user has watched. Extends basic FilmList class.
 * @author BSPQ20E4
 * @version 1.0
 * @since 2020-04-16
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Watched extends FilmList {

	public Watched(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		this.filmList=new ArrayList<>();
	}

}
