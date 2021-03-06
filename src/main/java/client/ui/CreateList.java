package client.ui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.controller.EasyFilmController;
import serialization.FilmListData;
import serialization.UserData;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class CreateList extends JFrame {

	private EasyFilmController controller;
	private UserData user;
	private String listName;
	
	private ArrayList<String> allFilms;
	private FilmListData newList;
	
	private JList<String> lAllFilms;
	public static DefaultListModel<String> dlmAllFilms;
	private JList<String> lNewList;
	public static DefaultListModel<String> dlmNewList;
	private JTextField newListName;

	private int allEditionPos;
	private int allSelectPos;
	private int newEditionPos;
	private int newSelectPos;

	private boolean editing;
	
	static Logger logger = Logger.getLogger(CreateList.class.getName());
	ResourceBundle resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
	
	/** This class will be used to create an empty list and to edit an old one
	 * @param user - UserData for the user that's logged
	 * @param controller - controller 
	 * @param listName - Name of the list to be edited / "null" if new
	 */
	public CreateList(UserData user, EasyFilmController controller, String listName) {		
		/** This is the part that contains the info of the window
		 * 
		 */
		this.user = user;
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
		newList = null;
		if(listName != null) { //Different process IF we are editing or creating 
			editing = true;
			newList = controller.getFilmList(user, listName); // EDITING
		}
		
		//NORTH PART
		
		JPanel pNorte = new JPanel();

		pNorte.setBackground(SystemColor.textHighlight);
		logger.error("Mini problema de las indentaciones con AvailableFilms/your New List");
		JLabel l1 = new JLabel("Available Films                                   ");
		l1.setForeground(SystemColor.menu);
		JLabel l2 = new JLabel("                                	 Your New List");
		l2.setForeground(SystemColor.menu);
//		JLabel l1 = new JLabel(resourceBundle.getString("available_films_label"));
//		JLabel l2 = new JLabel(resourceBundle.getString("your_new_list_label"));
		pNorte.add(l1, "West");
		pNorte.add(l2, "East");
		l1.setFont(new Font("Tahoma", Font.BOLD, 10));
		l2.setFont(new Font("Tahoma", Font.BOLD, 10));
		logger.error("Put this strings directly from resourceBundler");
		getContentPane().add(pNorte,"North");
		
		//SOUTH PART
		JPanel pSur= new JPanel();
		pSur.setBackground(SystemColor.textHighlight);
		JButton bBack = new JButton("");
		bBack.setOpaque(false);
		bBack.setIcon(new ImageIcon("src\\main\\resources\\Back.png"));
		bBack.setContentAreaFilled(false);
		bBack.setBorderPainted(false);
		JButton bSave = new JButton((""));
		bSave.setOpaque(false);
		bSave.setIcon(new ImageIcon("src\\main\\resources\\save.png"));
		bSave.setContentAreaFilled(false);
		bSave.setBorderPainted(false);
		bSave.setFont(new Font("Tahoma", Font.BOLD, 10));
		pSur.add(bBack);
		if(!editing) {
			newListName = new JTextField();
			newListName.setPreferredSize(new Dimension(100 ,30));
			pSur.add(newListName);			
		}else {
			JLabel oldListName = new JLabel(listName);
			oldListName.setPreferredSize(new Dimension(100 ,30));
			pSur.add(oldListName);
		}
		pSur.add(bSave);		 

		getContentPane().add(pSur,"South");
		
		/** This buttons allow to move films to one list to the other
		 * 
		 */
		
		//CENTER PART
		JPanel pCentro = new JPanel(new BorderLayout());

		pCentro.setBackground(SystemColor.textHighlight);
		JButton bRemove = new JButton("");
		bRemove.setOpaque(false);
		bRemove.setIcon(new ImageIcon("src\\main\\resources\\remove.png"));
		bRemove.setContentAreaFilled(false);
		bRemove.setBorderPainted(false);
		JLabel instructions = new JLabel("<html>Select a film and press '&gt' to send it to your list. Press '&lt' to send it back from the list</html>");		
		instructions.setPreferredSize(new Dimension(50,50));
		JButton bAdd = new JButton("");
		bAdd.setOpaque(false);
		bAdd.setContentAreaFilled(false);
		bAdd.setIcon(new ImageIcon("src\\main\\resources\\add.png"));
		bAdd.setBorderPainted(false);
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
		pCentral.setBackground(SystemColor.textHighlight);
		pCentral.add(scrollPane);
		
		getContentPane().add(pCentral, "West");
		
		//EAST PART
		dlmNewList = new DefaultListModel<>();
		lNewList = new JList<String>(dlmNewList);
		if(newList != null) {
			if(!newList.getFilmList().isEmpty()) {
				for(String s: newList.getFilmList()) dlmNewList.addElement(s);
			}else {
				logger.error("No FILMS in the list");
			}
		}
		JScrollPane scrollPane1 = new JScrollPane(lNewList);
		scrollPane1.setPreferredSize(new Dimension(150, 342));
		JPanel pCentral1 = new JPanel();
		pCentral1.setBackground(SystemColor.textHighlight);
		pCentral1.add(scrollPane1);

		getContentPane().add(pCentral1, "East");

		bBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(editing) {
					ArrayList<FilmListData> lists = controller.getAllLists(user.getLogin());
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
				
				if(!editing) {
					saveList(newListName.getText(), newList);
					logger.error("Saving new list: "+newListName.getText()+" to user: "+user.getLogin());					
				}else {
					saveList(listName,newList);
					logger.error("Saving list: "+listName+" to user: "+user.getLogin());					
				}
				dispose();
				//controller.saveList(user, listName);
				
				logger.error("To UserUI, we can maybe do it to last window");
				UserUI ui = new UserUI(user, controller);
				ui.setVisible(true);					
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
		if(newList == null) {
			newList = new FilmListData();
			newList.setFilmList(new ArrayList<>());
		}
		dlmNewList.addElement(dlmAllFilms.get(pos));
		dlmAllFilms.remove(pos);
		newList.getFilmList().add(allFilms.get(pos));
		allFilms.remove(pos);
		repaint();
		revalidate();
	}
	void removeFromList(int pos) {
		dlmAllFilms.addElement(dlmNewList.get(pos));
		dlmNewList.remove(pos);
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
		newList.setName(name);
		newList.setFilmList(list.getFilmList());
		try {
			controller.saveFilmList(user, newList);
		}catch(Exception e) {
			e.getStackTrace();
		}


	}

	public static void main(String[] args) {
//		PruebaListas ui = new PruebaListas();
//		ui.setVisible(true);
	}

}

