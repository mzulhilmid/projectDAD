package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.ReservationController;
import net.miginfocom.swing.MigLayout;

public class UserView {

	private JFrame frame;
	private String userID;
	private String position;
	private String labID;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public UserView(String userID, String position) {
		this.userID = userID;
		this.position = position;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 728, 403);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[117.00px][][grow][][][][][]", "[][20px,grow][][][grow][][][][]"));
		
		DefaultTableModel model = new DefaultTableModel() ;
		model.addColumn("Lab Name");
		model.addColumn("Start time");
		model.addColumn("End time");
		model.addColumn("Date");
		model.addColumn("Reason");
		model.addColumn("Approval Status");
		model.addColumn("ID");
		JTable tableReservation = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
					return false;
			}
		};
		
		try {
			for(Object[] a : ReservationController.getReservedByUser(userID, labID))
				model.addRow(a);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		tableReservation.setFillsViewportHeight(true);
		tableReservation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableReservation.setRowSelectionAllowed(false);
		
		tableReservation.getColumnModel().getColumn(0).setResizable(false);
		tableReservation.getColumnModel().getColumn(0).setPreferredWidth(156);
		tableReservation.getColumnModel().getColumn(1).setResizable(false);
		tableReservation.getColumnModel().getColumn(2).setResizable(false);
		tableReservation.getColumnModel().getColumn(3).setResizable(false);
		tableReservation.getColumnModel().getColumn(3).setPreferredWidth(83);
		tableReservation.getColumnModel().getColumn(5).setResizable(false);
		tableReservation.getColumnModel().getColumn(5).setPreferredWidth(98);
		tableReservation.getTableHeader().setReorderingAllowed(false);
		
		JLabel lblLblheader = new JLabel(".:: L A B  R E S E R V A T I O N  S Y S T E M ::.");
		frame.getContentPane().add(lblLblheader, "cell 1 0");
		frame.getContentPane().add(new JScrollPane(tableReservation), "cell 0 1 8 2,grow");
		
		JButton btnNewReservation = new JButton("New Reservation");
		btnNewReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewReservation(model, userID, position);
			}
		});
		frame.getContentPane().add(btnNewReservation, "cell 0 5,growx");
		
		//JButton btnDeleteSelected = new JButton("Delete selected");
		//frame.getContentPane().add(btnDeleteSelected, "cell 0 6");
		
	//	tableReservation.addMouseListener(new MouseAdapter() {
	//		@Override
	//		public void mouseClicked(MouseEvent e) {
	//			btnDeleteSelected.setEnabled(true);
	//		}
	//	});
		
	//	btnDeleteSelected.addMouseListener(new MouseAdapter() {
	//		@Override
	//		public void mouseClicked(MouseEvent e) {
	//			try {
	//				ReservationController.deleteReservation(tableReservation.getValueAt(tableReservation.getSelectedRow(), 6).toString());
	//					JOptionPane.showMessageDialog(null, "Successfully deleted");
	//				model.setRowCount(0);
	//				for(Object[] a : ReservationController.getReservedByUser(userID,labID))
	//					model.addRow(a);
	//				btnDeleteSelected.setEnabled(false);
	//			} catch (Exception e1) {
	//				e1.printStackTrace();
	//				JOptionPane.showMessageDialog(null, "Failed to delete.");
	//				btnDeleteSelected.setEnabled(false);
	//			}
				
		//	}
	//	});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btnLogout= new JButton("Logout");
		menuBar.add(btnLogout);
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Login();
				frame.dispose();
			}
		});
	}

}
