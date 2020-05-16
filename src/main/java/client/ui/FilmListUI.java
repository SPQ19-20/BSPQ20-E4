package client.ui;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.text.Position;

import serialization.FilmData;
import serialization.FilmListData;
import serialization.UserData;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.controller.EasyFilmController;

public class FilmListUI extends JFrame{

	private EasyFilmController controller;
	
	private JList<String> liFilms;
	public static DefaultListModel<String> dlmFilms;
	private JButton backbtn;
	private JButton addFilm;
	private JButton deleteFilm;
	private FilmListData filmList;
	
	private int editionPos;
	private int selectPos;

	static Logger logger = Logger.getLogger(FilmListUI.class.getName());

	public FilmListUI(UserData us, FilmListData flData, EasyFilmController controller) {
		this.filmList = flData;
		this.setTitle(flData.getName());
		this.controller = controller;
		
		/** This is the information of the creation of the window
		 * 
		 */
		
		getContentPane().setBackground(SystemColor.textHighlight);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución
		setSize(640,380);
		setLocation(600,175);
		setResizable(false);
		
		//Buttons
		backbtn = new JButton("");
		backbtn.setIcon(new ImageIcon("src\\main\\resources\\Back.png"));
		backbtn.setOpaque(false);
		backbtn.setContentAreaFilled(false);
		backbtn.setBorderPainted(false);
		backbtn.setBounds(10, 10, 70, 40);
		addFilm = new JButton("");
		addFilm.setBounds(525, 260, 50, 50);
		addFilm.setOpaque(false);
		addFilm.setIcon(new ImageIcon("src\\main\\resources\\addNew.png"));
		addFilm.setContentAreaFilled(false);
		addFilm.setBorderPainted(false);
		deleteFilm = new JButton("");
		deleteFilm.setBounds(50, 260, 50, 55);
		deleteFilm.setOpaque(false);
		deleteFilm.setIcon(new ImageIcon("src\\main\\resources\\Bin.png"));
		deleteFilm.setContentAreaFilled(false);
		deleteFilm.setBorderPainted(false);
		getContentPane().add(backbtn);
		getContentPane().add(addFilm);
		getContentPane().add(deleteFilm);
		
		
		dlmFilms = new DefaultListModel<>();
		liFilms = new JList<String>(dlmFilms);
		/*if(!filmList.getFilmList().isEmpty()) {
			for(String f: filmList.getFilmList()) dlmFilms.addElement(f);		
			logger.info("Displaying FILMS of FilmList "+filmList.getName() );
		}else {
			//This logger doesnt work yet
			logger.info("No Films in this FilmList yet");
		}*/
		liFilms.setModel(dlmFilms);
		JPanel pCentral = new JPanel();
		pCentral.setBackground(SystemColor.textHighlight);
		pCentral.add(liFilms);
		
		getContentPane().add(pCentral);
	
		backbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ArrayList<FilmListData> fl = controller.getAllLists(us.getLogin());
				
				MyLists u = new MyLists(us,fl,controller);
				u.setVisible(true);

			}
		});
		liFilms.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==1){
					editionPos = liFilms.locationToIndex(e.getPoint());
				}
				if (e.getClickCount()==2){
					if (liFilms.getSelectedIndex()!= -1) {
						selectPos = liFilms.locationToIndex(e.getPoint());
						toFilmUI(us);			
					}
				}
			}

		});
	}
	
	/** Calls the controller to open a FilmUI window with the info of this list
	 * @param us - Data of the User to be stored when returning to the profile
	 */
	private void toFilmUI(UserData us) {

		if (dlmFilms.getElementAt(selectPos) != null){
			String title = dlmFilms.getElementAt(selectPos);
			FilmData f = controller.getFilm(title);
			FilmUI u = new FilmUI(us, f, controller);
		}
		
	}
	public static void main(String[] args) {
		
		FilmListData flData = new FilmListData();
		ArrayList<String> fl = new ArrayList<>();
		EasyFilmController e = new EasyFilmController("127.0.0.1","8080");
		UserData u = new UserData();
		for(int i=0;i<5;i++) {
			fl.add("Peli"+i);
		}
		flData.setFilmList(fl);
		flData.setName("A1");
		FilmListUI ui = new FilmListUI(u, flData, e);
		
		ui.setVisible(true);
	}
	
}
