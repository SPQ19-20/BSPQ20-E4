package server.easyFilminData;


import javax.jdo.annotations.PersistenceCapable;


import java.util.Date;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

	
	@PersistenceCapable(detachable = "true")
	@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Actor {
	    protected String name;

	    protected String pic=null;

	    protected String bday=null;

	    public Actor()
	    {
	        this.name = "";
	        this.pic="";
	        this.bday="";
	    }

	    public Actor(String name, String pic, String bday)
	    {
	        this.name = name;
	        this.pic=pic;
	        this.bday=bday;
	    }
	    public Actor(String name, String bday)
	    {
	        this.name = name;
	        this.bday=bday;
	    }
	    public Actor(String name) {
	    	this.name = name;
	    }
	    

	    public String getName()
	    {
	        return name;
	    }

	    public String getPic()
	    {
	        return pic;
	    }

	    public String getBday()
	    {
	        return bday;
	    }

	    public void setBday(String bday)
	    {
	        this.bday = bday;
	    }

	    public void setName(String name)
	    {
	        this.name = name;
	    }

	    public void setPic(String pic)
	    {
	        this.pic = pic;
	    }

	  
	
}
