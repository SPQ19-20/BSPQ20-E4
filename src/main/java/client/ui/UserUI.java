package client.ui;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

import client.controller.EasyFilmController;
import serialization.FilmListData;
import serialization.UserData;
import server.easyFilminData.User;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class UserUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8709399093642804047L;
	private JTextField busqueda;
	private JButton nuevaLista;
	private JButton misListas;
	private EasyFilmController controller;
	
	ResourceBundle resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
	static Logger logger = Logger.getLogger(UserUI.class.getName());
	
	public UserUI(UserData user, EasyFilmController controller) {
		
		/** This is the information of the creation of the window
		 * 
		 */
		
		this.setTitle(resourceBundle.getString("title_userUI"));
		this.setTitle( "EasyFilmin User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución	
		setSize(700,400);
		setLocation(600,175);
		this.controller = controller;
		getContentPane().setLayout(new BorderLayout());
		
		JLabel buscar = new JLabel("");
		JPanel pBusqueda = new JPanel();
		JPanel pSuperior = new JPanel();
		buscar.setIcon(new ImageIcon("src\\main\\resources\\Films.png"));
		pBusqueda.setLayout(new GridLayout(1,2));
		pBusqueda.add(buscar);
		pBusqueda.setBackground(SystemColor.textHighlight);
		pSuperior.setLayout(new BorderLayout(10,20));
		pSuperior.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 20));
		pSuperior.setBackground(SystemColor.textHighlight);
		
		pSuperior.add(pBusqueda, BorderLayout.EAST);
		getContentPane().add(pSuperior, BorderLayout.NORTH);		
		
		nuevaLista = new JButton("");
		nuevaLista.setOpaque(false);
		nuevaLista.setContentAreaFilled(false);
		nuevaLista.setBorderPainted(false);
		nuevaLista.setIcon(new ImageIcon("src\\main\\resources\\NewFilmList.png"));
		nuevaLista.setFont(new Font("Arial", Font.PLAIN, 15));
		nuevaLista.setBounds(100, 100, 60, 60);
		getContentPane().add(nuevaLista);
		
		
		misListas = new JButton("");
		misListas.setOpaque(false);
		misListas.setContentAreaFilled(false);
		misListas.setBorderPainted(false);
		misListas.setIcon(new ImageIcon("src\\main\\resources\\Filmlist.png"));
		misListas.setBounds(100, 200, 60, 60);
		getContentPane().add(misListas);
		
		// Bottom panel 
		JPanel pCentral = new JPanel();
		JPanel pOpt = new JPanel();
		pOpt.setBackground(SystemColor.textHighlight);
		JPanel pPic = new JPanel();
		pPic.setBackground(SystemColor.textHighlight);
		JLabel profile = new JLabel(user.getLogin());
		JLabel image = new JLabel("");
		String pathPic = user.getIcon();
		
		pCentral.setLayout(new FlowLayout(FlowLayout.CENTER));
		pCentral.setBackground(SystemColor.textHighlight);
		pPic.setLayout(new GridLayout(1,2));
		pOpt.setLayout(new GridLayout(2,1));
		pPic.add(image);pPic.add(profile);
		pOpt.add(nuevaLista); pOpt.add(misListas);		
		image.setSize(68, 68);
		
		//CAREFUL IF THERE IS NO PIC IN THE getIcon() 
		//image.setIcon(new ImageIcon(pathPic));
		pCentral.setMinimumSize(new Dimension(100,20));
		pCentral.setPreferredSize(new Dimension(100,20));
		pCentral.setBorder(BorderFactory.createEmptyBorder(60, 20, 0, 20));
		pCentral.add(pPic, "West");
		pCentral.add(pOpt, "East");
		getContentPane().add(pCentral, "Center");
		
		/** This part is the one that implements the listeners of the different buttons
		 * 
		 */
		
		nuevaLista.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				CreateList u = new CreateList(user, controller, null);
				u.setVisible(true);
			}
		});
		
		misListas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				logger.info("This could be a util method to have in a util class");
				ArrayList<String> lists = new ArrayList<>();
				ArrayList<FilmListData> fl = controller.getAllLists(user.getLogin());
				
				MyLists u = new MyLists(user,fl,controller);
				u.setVisible(true);
			}
		});
		
	}
	public static void main(String[] args) {
		User u = new User("nick","1234");
		EasyFilmController e = new EasyFilmController("127.0.0.1","8080");
		UserData uD = new UserData(u);
		
		UserUI ui = new UserUI(uD, e);
		ui.setVisible(true);
	}
	
}
