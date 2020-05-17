package client.ui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import serialization.FilmListData;
import serialization.UserData;
import server.easyFilminData.FilmList;
import server.easyFilminData.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import client.controller.EasyFilmController;

public class MyLists extends JFrame{
	private EasyFilmController controller;
	private JButton back;
	private JButton bEdit;
	
	private JLabel info;
	private JList<String> list;
	public static DefaultListModel<String> dlmLists;
	
	private int editionPos;
	private int selectPos;
	
	static Logger logger = Logger.getLogger(MyLists.class.getName());
	
	/** This ui class displays all the different lists of a user
	 * @param usData - user
	 * @param lists - lists of that user
	 * @param cont - controller 
	 */
	public MyLists(UserData usData, ArrayList<FilmListData> lists, EasyFilmController cont) {
		this.controller = cont;
		
		/** This is the part that contains the info of the window
		 * 
		 */
		
		getContentPane().setBackground(SystemColor.textHighlight);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500,400);
		setLocation(600,175);
		setResizable(false);
		//getContentPane().setLayout(null);
		getContentPane().setLayout(new BorderLayout());
		
		/** This window allows the user to see all his/her lists
 		 * 
		 */
				
		dlmLists = new DefaultListModel<>();
		list = new JList<String>(dlmLists);
		if(lists != null && !lists.isEmpty()) {
			for(int i=0; i<lists.size();i++) dlmLists.addElement(lists.get(i).getName());		
			logger.info("Displaying Lists of User "+usData.getLogin() );
			logger.info("Lists of User: ");
			for(int i=0; i<lists.size();i++)logger.info(lists.get(i));	

		}else {
			//This logger doesnt work yet
			logger.info("No Lists yet");
		}
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(150,300));
		JPanel pCentral = new JPanel();
		pCentral.setBackground(SystemColor.textHighlight);
		pCentral.add(scrollPane);
		
		getContentPane().add(pCentral, "Center");
		
		info = new JLabel("My Lists:");
		info.setFont(new Font("Tahoma", Font.BOLD, 10));
		info.setForeground(SystemColor.menu);
		
		/** This button allows the user to go to the previous window
		 * 
		 */

		back = new JButton("");
		back.setIcon(new ImageIcon("src\\main\\resources\\BackSmall.png"));
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		bEdit = new JButton("EDIT");
		bEdit.setBounds(525, 260, 50, 50);
		bEdit.setOpaque(false);
		bEdit.setContentAreaFilled(false);
		bEdit.setBorderPainted(false);
		getContentPane().add(bEdit, "South");


		JPanel pSuperior = new JPanel();
		pSuperior.setBackground(SystemColor.textHighlight);
		pSuperior.setLayout(new GridLayout(0, 2, 0, 0));
		pSuperior.add(back);
		pSuperior.add(info);
		
		getContentPane().add(pSuperior, "North");
		/** This buttons allow to control the window
		 * 
		 */
		
		
		/** This part contains the different listeners of the window
		 * 
		 */
		
		list.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==1){
					editionPos = list.locationToIndex(e.getPoint());
				}
				if (e.getClickCount()==2){
					if (list.getSelectedIndex()!= -1) {
						dispose();
						selectPos = list.locationToIndex(e.getPoint());
						FilmListData fl = controller.getFilmList(usData, dlmLists.get(selectPos));
						FilmListUI f = new FilmListUI(usData, fl,controller, true);
						f.setVisible(true);
						
					}
				}
			}

		});
	
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserUI u = new UserUI(usData, cont);
				u.setVisible(true);
			}
		});
		
		bEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dlmLists.get(editionPos)!= null) {
					String editList = dlmLists.get(editionPos); 
					CreateList cl = new CreateList(usData, cont, editList);
					dispose();
					cl.setVisible(true);
					
				}
				
			}
		});
		
	}
	
	public static void main(String[] args) {
		EasyFilmController e = new EasyFilmController("127.0.0.1","8080");
		UserData u = new UserData();
		u.setLogin("nickPrueba");
		ArrayList<String> a = new ArrayList<String>();
		for(int i = 0; i<5;i++) {
			a.add("Lista"+i);	
		}
//		MyLists ui = new MyLists(u,a,e);
//		ui.setVisible(true);
	}

}
