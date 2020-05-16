package client.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import client.controller.EasyFilmController;
import serialization.UserData;
import server.easyFilminData.User;

public class UserReg extends JFrame {
	
	private EasyFilmController controller;
	
	private JTextField tfUser;
	private JTextField tfEmail;
	private JPasswordField pfPass;
	private WebTarget webtarget; 
	private JButton bBack;
	private JButton bRegister;
	private Client client;
	private JLabel lblUser;
	private JLabel lblpass;
	private JLabel lblEmail;
	
	ResourceBundle resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
	
	public UserReg(EasyFilmController controller) {
		this.controller = controller;
		getContentPane().setBackground(SystemColor.textHighlight);
		
		/** This is the information of the creation of the window
		 * 
		 */
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución
		setSize(500,300);
		setLocation(600,175);
		setResizable(false);
		getContentPane().setLayout(null);
		
		/** Here the user is informed with all the information he needs to provide
		 * 
		 */
		
		lblUser = new JLabel(resourceBundle.getString("username_label_msg"));
		lblUser.setFont(new Font("Verdana", Font.BOLD, 12));
		lblUser.setForeground(SystemColor.menu);
		lblUser.setBounds(72, 73, 80, 23);
		getContentPane().add(lblUser);
		
		lblpass = new JLabel(resourceBundle.getString("password_label_msg"));
		lblpass.setFont(new Font("Verdana", Font.BOLD, 12));
		lblpass.setForeground(SystemColor.menu);
		lblpass.setBounds(72, 106, 80, 23);
		getContentPane().add(lblpass);
		
		lblEmail = new JLabel(resourceBundle.getString("email_label_msg"));
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEmail.setForeground(SystemColor.menu);
		lblEmail.setBounds(72, 141, 80, 23);
		getContentPane().add(lblEmail);

		/** Here the user is able to enter the information for the registration
		 * 
		 */

		tfUser = new JTextField();
		tfUser.setBounds(162, 72, 267, 23);
		tfUser.setBackground(new Color(255,255,0));
		getContentPane().add(tfUser);
		tfUser.setColumns(10);
		
		pfPass = new JPasswordField();
		pfPass.setBounds(162, 108, 267, 22);
		pfPass.setBackground(new Color(255,255,0));
		getContentPane().add(pfPass);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBackground(new Color(255,255,0));
		tfEmail.setBounds(162, 140, 267, 23);
		getContentPane().add(tfEmail);
		
		/** This button allows the user to revert to the previous window
		 * 
		 */
		
		bBack = new JButton();
		bBack.setBackground(new Color(255, 255, 255));
		bBack.setFont(new Font("Verdana", Font.BOLD, 12));
		bBack.setBounds(10, 10, 70, 40);
		bBack.setIcon(new ImageIcon("src\\main\\resources\\Back.png"));
		bBack.setOpaque(false);
		bBack.setContentAreaFilled(false);
		bBack.setBorderPainted(false);
		getContentPane().add(bBack);
		
		/** With this button the user is able to register 
		 * 
		 */
		
		bRegister = new JButton();
		bRegister.setFont(new Font("Verdana", Font.BOLD, 12));
		bRegister.setBounds(410, 190, 50, 50);
		bRegister.setOpaque(false);
		bRegister.setIcon(new ImageIcon("src\\main\\resources\\SignUp.png"));
		bRegister.setContentAreaFilled(false);
		bRegister.setBorderPainted(false);
		getContentPane().add(bRegister);
		
		/** This part is the one that implements the listeners of the different buttons
		 * 
		 */
		
		bBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserLog u = new UserLog(controller);
				u.setVisible(true);
			}
		});
		
		bRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = tfUser.getText();
				String password = pfPass.getSelectedText();
				String email = tfEmail.getText();
				controller.registerUser(username,password,username,email);
				dispose();
				UserData us = new UserData(username, password, email);
				UserUI u = new UserUI(us, controller);
				u.setVisible(true);
			}
		});
		
		tfUser.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				tfUser.setBackground(new Color(255,255,255));
				tfUser.setForeground(new Color(0,0,0));
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tfEmail.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				tfEmail.setBackground(new Color(255,255,255));
				tfEmail.setForeground(new Color(0,0,0));
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		pfPass.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				pfPass.setBackground(new Color(255,255,255));
				pfPass.setForeground(new Color(0,0,0));
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
				
	}
}
