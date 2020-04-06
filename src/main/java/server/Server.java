package server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import serialization.DirectedMessage;
import serialization.MessageData;
import serialization.UserData;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import easyFilminDAO.EasyFilminJDO;
import easyFilminDAO.IEasyFilminDAO;
import easyFilminData.Message;
import easyFilminData.User;

@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
public class Server {

	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;

	public Server() {
//		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
//		this.pm = pmf.getPersistenceManager();
//		this.tx = pm.currentTransaction();
	}
/*
 * There is no connection with the DB yet, so all methods involving Transactions or PersistenceManagers won't 
 * work for now.
 * 
 * However, it is perfectly working for methods that we can use as trials and practice.
 */

	@POST
	@Path("/sayMessage")
	public Response sayMessage(DirectedMessage directedMessage) {
		User user = null;
		try{
			tx.begin();
			System.out.println("Creating query ...");
			
			Query<User> q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE login == \"" + directedMessage.getUserData().getLogin() + "\" &&  password == \"" + directedMessage.getUserData().getPassword() + "\"");
			q.setUnique(true);
			user = (User)q.execute();
			
			System.out.println("User retrieved: " + user);
			if (user != null)  {
				Message message = new Message(directedMessage.getMessageData().getMessage());
				user.getMessages().add(message);
				pm.makePersistent(user);					 
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		
		if (user != null) {
			cont++;
			System.out.println(" * Client number: " + cont);
			MessageData messageData = new MessageData();
			messageData.setMessage(directedMessage.getMessageData().getMessage());
			return Response.ok(messageData).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Login details supplied for message delivery are not correct").build();
		}
	}
	
	@POST
	@Path("/register")
	public Response registerUser(UserData userData) {
		
		User user = null;
		user = new User(userData.getLogin(), userData.getIcon(), userData.getEmail(),userData.getPassword());
		IEasyFilminDAO iDAO = new EasyFilminJDO();
		iDAO.saveUser(user);
		return Response.ok().build();	
        
        
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hola. Mensaje desde el servidor").build();
	}
	
}


