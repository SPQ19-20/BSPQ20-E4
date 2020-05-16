package client.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

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
		
		/** This is the information of the creation of the window
		 * 
		 */
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra la ventana y se para la ejecución
		setSize(500,325);
		setLocation(600,175);
		setResizable(false);

		getContentPane().setLayout(null);
		
		/** Here the user is informed with all the information he needs to provide
		 * 
		 */
		
		lblUser = new JLabel(resourceBundle.getString("username_label_msg"));
		lblUser.setFont(new Font("Verdana", Font.BOLD, 12));
		lblUser.setBounds(72, 73, 80, 23);
		getContentPane().add(lblUser);
		
		lblpass = new JLabel(resourceBundle.getString("password_label_msg"));
		lblpass.setFont(new Font("Verdana", Font.BOLD, 12));
		lblpass.setBounds(72, 106, 80, 23);
		getContentPane().add(lblpass);
		
		lblEmail = new JLabel(resourceBundle.getString("email_label_msg"));
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEmail.setBounds(72, 141, 80, 23);
		getContentPane().add(lblEmail);

		/** Here the user is able to enter the information for the registration
		 * 
		 */

		tfUser = new JTextField();
		tfUser.setBounds(162, 72, 267, 23);
		getContentPane().add(tfUser);
		tfUser.setColumns(10);
		
		pfPass = new JPasswordField();
		pfPass.setBounds(162, 108, 267, 22);
		getContentPane().add(pfPass);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(162, 140, 267, 23);
		getContentPane().add(tfEmail);
		
		/** This button allows the user to revert to the previous window
		 * 
		 */
		
		
		bBack = new JButton();
		bBack.setText("<-");
		bBack.setFont(new Font("Verdana", Font.BOLD, 12));
		bBack.setBounds(100, 208, 100, 30);
		getContentPane().add(bBack);
		
		/** With this button the user is able to register 
		 * 
		 */
		
		bRegister = new JButton();
		bRegister.setText(resourceBundle.getString("create_buton_msg"));
		bRegister.setFont(new Font("Verdana", Font.BOLD, 12));
		bRegister.setBounds(300, 208, 100, 30);
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
				
	}
}
