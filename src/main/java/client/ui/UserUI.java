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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;


public class UserUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8709399093642804047L;
	private JTextField busqueda;
	private JButton nuevaLista;
	private JButton misListas;
	private EasyFilmController controller;
	
	static Logger logger = Logger.getLogger(UserUI.class.getName());
	
	public UserUI(UserData user, EasyFilmController controller) {
		
		/** This is the information of the creation of the window
		 * 
		 */
		
		this.setTitle( "EasyFilmin User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución	
		setSize(740,480);
		setLocation(600,175);
		this.controller = controller;
		getContentPane().setLayout(new BorderLayout());
		
		busqueda = new JTextField();
		busqueda.setColumns(10);
		JLabel buscar = new JLabel("");
		JPanel pBusqueda = new JPanel();
		JPanel pSuperior = new JPanel();
		buscar.setIcon(new ImageIcon("src\\main\\resources\\Lupa.png"));
		pBusqueda.setLayout(new GridLayout(1,2));
		pBusqueda.add(buscar);
		pBusqueda.add(busqueda);
		pSuperior.setLayout(new BorderLayout(10,20));
		pSuperior.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 20));
		
		pSuperior.add(pBusqueda, BorderLayout.EAST);
		getContentPane().add(pSuperior, BorderLayout.NORTH);		
		
		nuevaLista = new JButton("New List");
		nuevaLista.setFont(new Font("Arial", Font.PLAIN, 15));
		getContentPane().add(nuevaLista);
		
		
		misListas = new JButton("My Lists");
		misListas.setBounds(100, 242, 150, 30);
		getContentPane().add(misListas);
		
		// Bottom panel 
		JPanel pCentral = new JPanel();
		JPanel pOpt = new JPanel();
		JPanel pPic = new JPanel();
		JLabel profile = new JLabel(user.getLogin());
		JLabel image = new JLabel("");
		String pathPic = user.getIcon();
		
		pCentral.setLayout(new FlowLayout(FlowLayout.CENTER));
		pPic.setLayout(new GridLayout(1,2));
		pOpt.setLayout(new GridLayout(2,1));
		pPic.add(image);pPic.add(profile);
		pOpt.add(nuevaLista); pOpt.add(misListas);		
		image.setSize(68, 68);
		
		//CAREFUL IF THERE IS NO PIC IN THE getIcon() 
		image.setIcon(new ImageIcon(pathPic));
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
				for(int i=0; i<fl.size();i++) {
					lists.add(fl.get(i).getName());
				}
				
				MyLists u = new MyLists(user,lists,controller);
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
