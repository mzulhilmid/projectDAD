package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import model.User;
import net.miginfocom.swing.MigLayout;

public class Login {

	private JFrame loginFrame;

	/**
	 * Launch the application.
	 */	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loginFrame = new JFrame();
		loginFrame.setVisible(true);
		loginFrame.setBounds(100, 100, 450, 200);
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(new MigLayout("", "[][][][][grow][][][][grow][][][][]", "[][][][][][][][]"));
		
		JLabel lblLabReservationSystem = new JLabel(".:: L A B  R E S E R V A T I O N  S Y S T E M ::.");
		loginFrame.getContentPane().add(lblLabReservationSystem, "cell 6 1 2 1");
		
		JLabel lblUsername = new JLabel("USERNAME :");
		loginFrame.getContentPane().add(lblUsername, "cell 6 3,alignx trailing");
		
		JTextField fieldUsername = new JTextField();
		loginFrame.getContentPane().add(fieldUsername, "cell 7 3,growx");
		fieldUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD :");
		loginFrame.getContentPane().add(lblPassword, "cell 6 4,alignx trailing");
		
		JPasswordField fieldPassword = new JPasswordField();
		loginFrame.getContentPane().add(fieldPassword, "cell 7 4,growx");
		
		JButton btnLogin = new JButton("LOGIN");
		UserController userController = new UserController();
		loginFrame.getContentPane().add(btnLogin, "cell 9 7");
		
		JLabel lblIncorrect = new JLabel("Incorrect username or password entered");
		lblIncorrect.setVisible(false);
		loginFrame.getContentPane().add(lblIncorrect, "cell 7 9");
		
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
						User user = UserController.searchByUser(fieldUsername.getText());
						if(user==null)
							lblIncorrect.setVisible(true);
						else {
							userController.setUser(user);
							if(user.getPassword().equals(fieldPassword.getText())) {
								lblIncorrect.setVisible(false);
								fieldUsername.setText("");
								fieldPassword.setText("");
								String userID = userController.getUser().getUserID();
								String position = userController.getUser().getPosition();
								if(position.matches("ADMIN")) {
									new AdminView(user.getUserID());
									loginFrame.dispose();
								}
									
								if(position.matches("STAFF")) {
									new StaffView(userID);
									loginFrame.dispose();
								}
									
								if(position.matches("LECTURER") || position.matches("STUDENT")) {
									new UserView(userID,position);
									loginFrame.dispose();
								}
							}
							else
								lblIncorrect.setVisible(true);	
						
						}
						
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
