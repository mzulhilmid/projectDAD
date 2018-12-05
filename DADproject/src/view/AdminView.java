package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import net.miginfocom.swing.MigLayout;

public class AdminView {

	private JFrame frame;
	private String userID;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public AdminView(String userID) {
		this.userID = userID;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setBounds(100, 100, 278, 305);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][][][][][][][]", "[][][][][][][][][][][]"));
		
		JLabel lblLA = new JLabel(".:: L A B  R E S E R V A T I O N  S Y S T E M ::.");
		frame.getContentPane().add(lblLA, "cell 1 0");
		
		JButton btnEditLabInfo = new JButton("Edit Lab Info");
		btnEditLabInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new EditLab();
			}
		});
		frame.getContentPane().add(btnEditLabInfo, "cell 1 4,growx");
		
		JButton btnViewAllReservation = new JButton("View All Reservation Data");
		btnViewAllReservation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ViewReservation(userID, "ADMIN");
			}
		});
		frame.getContentPane().add(btnViewAllReservation, "cell 1 6,growx");
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new Login();
			}
		});
		menuBar.add(btnLogout);
	}

}
