package client.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import client.controller.EasyFilmController;
import serialization.UserData;
import server.easyFilminData.User;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**@package client.ui
 * @brief This is the documentation for the Java package client.ui intended to work as an user interface for the EasyFilmin Project client side.
 * This package is composed by several classes, AdminUI, CreateList, FilmListUI, MyLists, UserLog, UserReg and UserUI.
 * Each class represents one different window. The user will be able to navigate through these with the interface
 * There is one class (AdminUI) only for the administrator. Ordinary users will not see it.
 */

public class UserLog extends JFrame{
	private EasyFilmController control;
	private JTextField textField;
	private JButton btnLogin;
	private JPasswordField passwordField;
	private JButton btnCreate;
	private JLabel lblUser;
	private JLabel lblpass;
	
	ResourceBundle resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
	
	public UserLog(EasyFilmController controller) {
		
		this.control = controller;
		getContentPane().setBackground(SystemColor.textHighlight);
		this.setTitle( "EasyFilmin Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución
		
		/** This is the part where the information of the window is shown
		 * 
		 */
		
		setSize(500,350);
		setLocation(600,175);
		setResizable(false);
		getContentPane().setLayout(null);
		
		/** This part allows the user to enter the username and password
		 * 
		 */
		
		lblUser = new JLabel(resourceBundle.getString("username_label_msg"));
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblUser.setForeground(SystemColor.menu);
		lblUser.setBounds(40, 75, 80, 23);
		getContentPane().add(lblUser);
		
		lblpass = new JLabel(resourceBundle.getString("password_label_msg"));
		lblpass.setForeground(SystemColor.menu);
		lblpass.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblpass.setBounds(40, 120, 80, 23);
		getContentPane().add(lblpass);
		
		textField = new JTextField();
		textField.setBounds(110, 75, 325, 23);
		textField.setBackground(new Color(255,255,0));
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(110, 120, 325, 23);
		passwordField.setBackground(new Color(255,255,0));
		getContentPane().add(passwordField);
		
		/** This button allows the user to login into his/her account 
		 * 
		 */
		
		btnLogin = new JButton();
		btnLogin.setOpaque(false);
		btnLogin.setIcon(new ImageIcon("src\\main\\resources\\Login.png"));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorderPainted(false);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnLogin.setBounds(400, 200, 50, 50);
		getContentPane().add(btnLogin);
		
		/** If no account is owned, with this button the user will be redirected to the registration window 
		 * 
		 */
		
		btnCreate = new JButton();
		btnCreate.setOpaque(false);
		btnCreate.setIcon(new ImageIcon("src\\main\\resources\\SignUp.png"));
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCreate.setBounds(30, 200, 50, 50);
		getContentPane().add(btnCreate);
		
		/** This part is the one that implements the listeners of the different buttons
		 * 
		 */
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// FUNCTIONALITY not implemented yet 
					// ATTENTION THIS MUST BE REMOVED AFTER DOING THESE TRIALS
				String pass = new String(passwordField.getPassword());
					// END OF REMOVABLE PART
				if(controller.login(textField.getText(), pass)) {
					dispose();
					UserData us = controller.getUser(textField.getText());
					UserUI vg = new UserUI(us, controller);
					vg.setSize(720, 480);
					vg.setVisible(true);
				}else {
					textField.setText("Login or Pass incorrect");
				}
				

			}
		});
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserReg u = new UserReg(controller);
				u.setVisible(true);
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		
		textField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				textField.setBackground(new Color(255,255,255));
				textField.setForeground(new Color(0,0,0));
				
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
		
		passwordField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				passwordField.setBackground(new Color(255,255,255));
				passwordField.setForeground(new Color(0,0,0));
				
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
