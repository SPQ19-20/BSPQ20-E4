package client.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;import java.awt.Component;
import java.awt.Graphics;
import java.awt.List;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import client.controller.EasyFilmController;
import serialization.FilmData;
import serialization.UserData;
import server.easyFilminData.Actor;
import server.easyFilminData.Director;
import server.easyFilminData.Film;
import server.easyFilminData.FilmList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Font;
import javax.swing.JComboBox;

public class FilmUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel poster;
	private JButton backbtn;
	private JButton exitbtn;
	private JLabel titleLabel;
	private JLabel titleName;
	private JLabel directorLabel;
	private JLabel directorName;
	private JLabel actorLabel;
	private JLabel actorName;
	private JButton addWatchlist;
	private JButton addWatched;
	private JButton post;
	private JList<String> list;
	private DefaultListModel<String> dlmComments;
	private JButton addToList;
	private JTextField commentField;
	private JButton upbtn;
	private JButton downbtn;
	private JLabel rating;
	private JComboBox<FilmList> listSelection;
	private FilmData film;

	private EasyFilmController controller;
	
	public FilmUI(UserData us, FilmData film, EasyFilmController controller) {
		this.film = film;
		this.controller = controller;
		
		/** This is the information of the creation of the window
		 * 
		 */
		
		getContentPane().setBackground(SystemColor.textHighlight);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución
		setSize(650,460);
		setLocation(600,175);
		setResizable(false);
		
		getContentPane().setLayout(null);
		
		/** Creates an UI to be able to see every film available in our DB
		 * 
		 */
		
		poster = new JLabel();
		poster.setBounds(10, 50, 145, 200);
		poster.setIcon(new ImageIcon("src\\main\\resources\\inglorious_basterds.png")); //Example
		//Uncomment when FilmData is retrieved from server
		//poster.setIcon(new ImageIcon(film.getPoster()));
		getContentPane().add(poster);
		
		/** This is the part that allows the control of the window to exit an go back
		 * 
		 */
		
		backbtn = new JButton("");
		backbtn.setBounds(10, 5, 70, 40);
		getContentPane().add(backbtn);
		backbtn.setIcon(new ImageIcon("src\\main\\resources\\Back.png"));
		backbtn.setOpaque(false);
		backbtn.setContentAreaFilled(false);
		backbtn.setBorderPainted(false);
		
		/** This part is where all the info related to the film is displayed
		 * 
		 */
		
		titleLabel = new JLabel("Title:");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		titleLabel.setBounds(165, 50, 45, 13);
		titleLabel.setForeground(SystemColor.menu);
		getContentPane().add(titleLabel);
		
		titleName = new JLabel("Inglorious Basterds");
		titleName.setFont(new Font("Tahoma", Font.PLAIN, 10));
		titleName.setBounds(220, 50, 285, 13);
		titleName.setForeground(SystemColor.menu);
		getContentPane().add(titleName);
		
		directorLabel = new JLabel("Director:");
		directorLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		directorLabel.setBounds(165, 73, 54, 13);
		directorLabel.setForeground(SystemColor.menu);
		getContentPane().add(directorLabel);
		
		directorName = new JLabel("Quentin Tarantino");
		directorName.setForeground(SystemColor.menu);//Example
		//Uncomment when FilmData is retrieved from server
//		String directors ="";
//		for(Director a : film.getDirector()) {
//			if(directors.equals("")) {
//				directors += a.getName();	
//			}else {
//				directors += ", " + a.getName();
//			}
//		}		
//		directorName = new JLabel(directors);
		directorName.setFont(new Font("Tahoma", Font.PLAIN, 10));
		directorName.setBounds(220, 73, 285, 13);
		getContentPane().add(directorName);
		
		actorLabel = new JLabel("Actors:");
		actorLabel.setForeground(SystemColor.menu);
		actorLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		actorLabel.setBounds(165, 96, 45, 13);
		getContentPane().add(actorLabel);
		
		actorName = new JLabel("Brad Pitt, Christoph Waltz");
		actorName.setForeground(SystemColor.menu);//Example
		//Uncomment when FilmData is retrieved from server
//		String actors ="";
//		for(Actor a : film.getActors()) {
//			if(actors.equals("")) {
//				actors += a.getName();	
//			}else {
//				actors += ", " + a.getName();
//			}
//		}		
//		actorName = new JLabel(actors);
		actorName.setFont(new Font("Tahoma", Font.PLAIN, 10));
		actorName.setBounds(220, 96, 285, 13);
		getContentPane().add(actorName);
		
		/** This is the part that implements a button to add films to a Watchlist
		 * And an icon that shows if the film has been watched or not
		 * 
		 */

		addWatched = new JButton("");
		addWatched.setIcon(new ImageIcon("src\\main\\resources\\Watch.png")); 
		addWatched.setBounds(220, 229, 35, 35);
		addWatched.setOpaque(false);
		addWatched.setContentAreaFilled(false);
		addWatched.setBorderPainted(false);
		getContentPane().add(addWatched);

		addWatchlist = new JButton("");
		addWatchlist.setIcon(new ImageIcon("src\\main\\resources\\Watchlist.png")); 
		addWatchlist.setBounds(165, 229, 35, 35);
		addWatchlist.setOpaque(false);
		addWatchlist.setContentAreaFilled(false);
		addWatchlist.setBorderPainted(false);
		getContentPane().add(addWatchlist);
				
		/**This part implements a way to write, post and read comments
		 * 
		 */
		
		commentField = new JTextField();
		commentField.setBounds(10, 300, 575, 30);
		getContentPane().add(commentField);
		commentField.setColumns(10);
		
		/* COMMENTS - JList of comments that stores the comments 
		 * 
		 */
		dlmComments = new DefaultListModel();
		list = new JList(dlmComments);
		JScrollPane spComments = new JScrollPane(list);
		list.setBounds(10, 335, 615, 75);
		spComments.setBounds(10, 335, 615, 75);
//		for(Comment c: film.getComments()) {
//			dlmComments.addElement(c.getText());	
//		}
		getContentPane().add(spComments);
		
		post = new JButton("");
		post.setOpaque(false);
		post.setContentAreaFilled(false);
		post.setBorderPainted(false);
		post.setBounds(600, 300, 25, 25);
		post.setIcon(new ImageIcon("src\\main\\resources\\Post.png")); 
		getContentPane().add(post);
		
		/** This is the part that allows the user to rate films
		 * 
		 */
		
		upbtn = new JButton("");
		upbtn.setBounds(10, 270, 30, 30);
		upbtn.setOpaque(false);
		upbtn.setContentAreaFilled(false);
		upbtn.setBorderPainted(false);
		upbtn.setIcon(new ImageIcon("src\\main\\resources\\Up.png")); 
		getContentPane().add(upbtn);
		
		rating = new JLabel("5");
		rating.setBounds(42, 275, 20, 13);
		rating.setForeground(SystemColor.menu);
		getContentPane().add(rating);
		
		downbtn = new JButton("");
		downbtn.setBounds(50, 270, 30, 30);
		downbtn.setOpaque(false);
		downbtn.setContentAreaFilled(false);
		downbtn.setBorderPainted(false);
		downbtn.setIcon(new ImageIcon("src\\main\\resources\\Down.png")); 
		getContentPane().add(downbtn);
		
		
		/** This part allows the user to add films to the selected list
		 * 
		 */
		
		addToList = new JButton("");
		addToList.setBounds(590, 230, 35, 35);
		addToList.setIcon(new ImageIcon("src\\main\\resources\\NewFilmListSmall.png")); 
		addToList.setOpaque(false);
		addToList.setContentAreaFilled(false);
		addToList.setBorderPainted(false);
		getContentPane().add(addToList);
		
		listSelection = new JComboBox<FilmList>();
		listSelection.setBounds(350, 230, 225, 35);
		getContentPane().add(listSelection);
		
		/** This part is the one that implements the listeners of the different buttons
		 * 
		 */
		
		/* This buttons allows to control the app 
		 * exitbtn allows to close the application 
		 * backbtn allows to go to the previous application
		 * */
		
		backbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserUI ui = new UserUI(null, controller);
				ui.setVisible(true);
			}
		});
		
		/* This buttons allows to control and get info of your films
		 * addWatched adds the film to the watched list of the user
		 * addWatchlist adds the film to your personal Watchlist
		 */
		
		addWatched.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addToList("watched", film.getTitle());
				
			}
		});
		addWatchlist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addToList("watchlist", film.getTitle());
				
			}
		});
		
		/* This buttons allows to control the rating of the film from the user
		 * upbtn adds 1 to the number of the rating
		 * downbtn substracts 1 to the number of the rating
		 */
		
		upbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s_number = rating.getText();
				int i_number = Integer.parseInt(s_number);
				if (i_number < 5) {
					int n_number = (i_number + 1);
					String sn_number = String.valueOf(n_number);
					rating.setText(sn_number);
				}
				
				else 
					JOptionPane.showMessageDialog(null, "The rating cannot be higher than 5");
	
				}
			});
		
		downbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s_number1 = rating.getText();
				int i_number1 = Integer.parseInt(s_number1);
			if(i_number1 > 1) {
				int n_number1 = (i_number1 - 1);
				String sn_number1 = String.valueOf(n_number1);
				rating.setText(sn_number1);
			}
			else
				JOptionPane.showMessageDialog(null, "The rating cannot be lower than 1");
			}
		});
		
		/* This buttons allows the user to post comments
		 * post adds the comment of the user to the list of comments of the film 
		 */
		
		post.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInternalMessageDialog(null, "Thank you for posting a comment");
				String comment = commentField.getText();
				int index = dlmComments.getSize() - 1;
				dlmComments.add(index + 1, comment);
				list.setModel(dlmComments);
				commentField.setText("");
				
				
			}
		});
		
	}
	public static void main(String[] args) {
		FilmData f = new FilmData();
		EasyFilmController e = new EasyFilmController("127.0.0.1","8080");
		UserData u = new UserData();
		u.setLogin("nickPrueba");
		ArrayList<String> a = new ArrayList<String>();
		for(int i = 0; i<5;i++) {
			a.add("Lista"+i);	
		}

		FilmUI fu = new FilmUI(u, f, e);
		fu.setVisible(true);
	}
}