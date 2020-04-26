package serialization;

import java.util.ArrayList;

import easyFilminData.FilmList;
import easyFilminData.User;

public class UserData {

    private String login;
    private String password;
    private String icon;
    private String email;
    private ArrayList<String> lists;

    //Default public constructor for serialization
	public UserData() {

    }
	public UserData(User u) {
		this.login = u.getNickname();
		this.password = u.getPassword();
		this.icon = u.getIcon();
		this.email = u.getEmail();
		//We add the names of the lists of the user to the userData
		lists = new ArrayList<>();
		for(FilmList f : u.getLists()) {
			this.lists.add(f.getName()); 
		}
    }

	/** Constructor to use in the server 
	 * @param login -login of the user
	 * @param password - pass of the user
	 * @param icon - icon of the user
	 * @param email - email of the user
	 */
	public UserData(String login, String password, String icon, String email) {
		this.login = login;
		this.password = password;
		this.icon = null; //CHANGE WHEN WE HAVE A WAY TO INSERT PROFILE IMAGES
		this.email = email;
//		lists = new ArrayList<>();
//		for(FilmList f : u.getLists()) {
//			this.lists.add(f.getName()); 
//		}

	}
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "[login=" + login + ", password=" + password + "]";
    }
    
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<String> getLists() {
		return lists;
	}
	public void setLists(ArrayList<String> lists) {
		this.lists = lists;
	}

}