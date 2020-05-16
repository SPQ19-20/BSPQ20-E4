package client.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import client.controller.EasyFilmController;
import serialization.UserData;
import server.easyFilminData.User;

import javax.swing.JPasswordField;
import javax.swing.JButton;

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
		lblUser.setBounds(72, 73, 80, 23);
		getContentPane().add(lblUser);
		
		lblpass = new JLabel(resourceBundle.getString("password_label_msg"));
		lblpass.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblpass.setBounds(72, 139, 80, 23);
		getContentPane().add(lblpass);
		
		textField = new JTextField();
		textField.setBounds(162, 72, 267, 23);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(162, 139, 267, 22);
		getContentPane().add(passwordField);
		
		/** This button allows the user to login into his/her account 
		 * 
		 */
		
		btnLogin = new JButton();
		btnLogin.setText(resourceBundle.getString("login_buton_msg"));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnLogin.setBounds(329, 247, 100, 30);
		getContentPane().add(btnLogin);
		
		/** If no account is owned, with this button the user will be redirected to the registration window 
		 * 
		 */
		
		btnCreate = new JButton();
		btnCreate.setText(resourceBundle.getString("register_buton_msg"));
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCreate.setBounds(40, 247, 100, 30);
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
		
	}
		
}
