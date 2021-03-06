package client.ui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import serialization.FilmData;
import serialization.FilmListData;
import serialization.UserData;
import server.easyFilminData.FilmList;
import server.easyFilminData.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.controller.EasyFilmController;


public class PruebaListas extends JFrame {

	private EasyFilmController controller;
	private UserData user;
	private String listName;
	
	private ArrayList<String> allFilms;
	private FilmListData newList;
	
	private JList<String> lAllFilms;
	public static DefaultListModel<String> dlmAllFilms;
	private JList<String> lNewList;
	public static DefaultListModel<String> dlmNewList;

	private int allEditionPos;
	private int allSelectPos;
	private int newEditionPos;
	private int newSelectPos;

	private boolean editing;
	
	static Logger logger = Logger.getLogger(CreateList.class.getName());
	
	/** This ui class displays all the different lists of a user
	 * @param usData - user
	 * @param lists - lists of that user
	 * @param cont - controller 
	 */
	public PruebaListas(UserData us, EasyFilmController controller, String listName) {
		
		/** This is the part that contains the info of the window
		 * 
		 */
		this.user = us;
		this.listName = listName;
		this.controller = controller;
		editing = false;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500,450);
		setLocation(600,175);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		
		//Loading the data of the 2 lists
		allFilms = new ArrayList<>();
		allFilms = controller.getAllFilms();
		newList = new FilmListData();
		if(listName != null) { //Different process IF we are editing or creating 
			editing = true;
			newList = controller.getFilmList(us, listName); // EDITING
		}
		
		//NORTH PART
		JPanel pNorte = new JPanel();
		JLabel l1 = new JLabel("Available Films                                   ");
		JLabel l2 = new JLabel("                                	 Your New List");
		pNorte.add(l1, "West");
		pNorte.add(l2, "East");
		
		getContentPane().add(pNorte,"North");
		
		//SOUTH PART
		JPanel pSur= new JPanel();
		JButton bBack = new JButton("Back");
		JTextField newListName = new JTextField();
		newListName.setPreferredSize(new Dimension(100 ,30));
		JButton bSave = new JButton("Save");
		pSur.add(bBack);
		pSur.add(newListName);
		pSur.add(bSave);
		
		getContentPane().add(pSur,"South");
		
		
		//CENTER PART
		JPanel pCentro = new JPanel(new BorderLayout());
		JButton bRemove = new JButton("<");
		JLabel instructions = new JLabel("<html>Select a film and press '&gt' to send it to your list. Press '&lt' to send it back from the list</html>");
		
		instructions.setPreferredSize(new Dimension(50,50));
		JButton bAdd = new JButton(">");
		pCentro.add(bRemove, "North");
		pCentro.add(instructions, "Center");
		pCentro.add(bAdd, "South");
		
		getContentPane().add(pCentro,"Center");
		
		//WEST PART
		dlmAllFilms = new DefaultListModel<>();
		lAllFilms = new JList<String>(dlmAllFilms);
		if(allFilms != null && !allFilms.isEmpty()) {
			for(String s: allFilms) dlmAllFilms.addElement(s);			
		}else {
			logger.error("No FILMS in the DB");
		}
		JScrollPane scrollPane = new JScrollPane(lAllFilms);
		scrollPane.setPreferredSize(new Dimension(150, 342));
		JPanel pCentral = new JPanel();
		pCentral.add(scrollPane);
		
		getContentPane().add(pCentral, "West");
		
		//EAST PART
		dlmNewList = new DefaultListModel<>();
		lNewList = new JList<String>(dlmNewList);
		if(newList != null && !newList.getFilmList().isEmpty()) {
			for(String s: newList.getFilmList()) dlmNewList.addElement(s);			
		}else {
			logger.error("No FILMS in the list");
		}
		JScrollPane scrollPane1 = new JScrollPane(lNewList);
		scrollPane1.setPreferredSize(new Dimension(150, 342));
		JPanel pCentral1 = new JPanel();
		pCentral1.add(scrollPane1);

		getContentPane().add(pCentral1, "East");


		bBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(editing) {
					logger.error("Change MyLists constructor from Al<String> to Al<FilmListData> and make the conversion inside");
					ArrayList<String> flists = new ArrayList<>();
					ArrayList<FilmListData> lists = controller.getAllLists(user.getLogin());
					for(int i=0; i<lists.size();i++) {
						flists.add(lists.get(i).getName());
					}
					MyLists mui = new MyLists(user, lists, controller);
					mui.setVisible(true);
				}else {
					UserUI ui = new UserUI(user, controller);
					ui.setVisible(true);					
				}
			}
		});
		
		bSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveList(newListName.getText(), newList);
				logger.error("To UserUI, we can maybe do it to last window");
			}
		});
		
		bAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				moveToList(allEditionPos);
				
			}
		});
		
		bRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeFromList(newEditionPos);
				
			}
		});

		lAllFilms.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==1){
					allEditionPos = lAllFilms.locationToIndex(e.getPoint());
				}
				if (e.getClickCount()==2){
					if (lAllFilms.getSelectedIndex()!= -1) {
						allSelectPos = lAllFilms.locationToIndex(e.getPoint());
						moveToList(allSelectPos);			
					}
				}
			}

		});

		lNewList.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==1){
					newEditionPos = lNewList.locationToIndex(e.getPoint());
				}
				if (e.getClickCount()==2){
					if (lNewList.getSelectedIndex()!= -1) {
						newSelectPos = lNewList.locationToIndex(e.getPoint());
						moveToList(newSelectPos);			
					}
				}
			}

		});

	}
	
	void moveToList(int pos) {
		newList.getFilmList().add(allFilms.get(pos));
		allFilms.remove(pos);
		repaint();
		revalidate();
	}
	
	void removeFromList(int pos) {
		allFilms.add(newList.getFilmList().get(pos));
		newList.getFilmList().remove(pos);
		repaint();
		revalidate();
	}
	/** Save List to DB and to a particular user when Save pressed
	 * @param name - name of the list (null if we are editing)
	 * @param list - FilmListData 
	 */
	void saveList(String name, FilmListData list) {
		//TODO guardar lista en DB relacionándola con User
		if(!editing){
			newList.setName(name);
		}
		newList.setFilmList(list.getFilmList());
	}

	public static void main(String[] args) {
//		PruebaListas ui = new PruebaListas();
//		ui.setVisible(true);
	}

}

