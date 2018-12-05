package view;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import controller.ReservationController;
import net.miginfocom.swing.MigLayout;

public class StaffView {

	private JFrame frame;
	String userID;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public StaffView(String userID) {
		this.userID = userID;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		frame.setBounds(100, 100, 800, 340);
		frame.setResizable(true);
		
		frame.getContentPane().setLayout(new MigLayout("", "[730px,grow]", "[][277px][][][][][][][]"));
		
		Label lblHeader = new Label(".:: L A B  R E S E R V A T I O N  S Y S T E M ::.");
		frame.getContentPane().add(lblHeader, "cell 0 0");
		
		DefaultTableModel model = new DefaultTableModel() ;
		model.addColumn("Lab Name");
		model.addColumn("Start time");
		model.addColumn("End time");
		model.addColumn("Date");
		model.addColumn("Reserved By");
		model.addColumn("Reason");
		model.addColumn("Approval Status");
		model.addColumn("R.ID");
		@SuppressWarnings("serial")
		JTable tableReservation = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==6)
					return true;
				else
					return false;
			}
		};
		
		
		tableReservation.setColumnSelectionAllowed(false);
		tableReservation.setEnabled(true);
		JComboBox<String> approvalStatusCombo = new JComboBox<String>();
		approvalStatusCombo.addItem("APPROVED");
		approvalStatusCombo.addItem("DENIED");
		approvalStatusCombo.addItem("IN REVIEW");
		try {
			for(Object[] a : ReservationController.getReservedByStaff(userID,"IN REVIEW")) {
				model.addRow(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tableReservation.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(approvalStatusCombo));
		
		tableReservation.getTableHeader().setReorderingAllowed(false);
		tableReservation.setFillsViewportHeight(true);
		tableReservation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableReservation.getColumnModel().getColumn(0).setResizable(true);
		tableReservation.getColumnModel().getColumn(0).setPreferredWidth(156);
		tableReservation.getColumnModel().getColumn(1).setResizable(false);
		tableReservation.getColumnModel().getColumn(2).setResizable(false);
		tableReservation.getColumnModel().getColumn(3).setResizable(false);
		tableReservation.getColumnModel().getColumn(3).setPreferredWidth(83);
		tableReservation.getColumnModel().getColumn(4).setResizable(false);
		tableReservation.getColumnModel().getColumn(5).setResizable(true);
		tableReservation.getColumnModel().getColumn(6).setResizable(false);
		tableReservation.getColumnModel().getColumn(6).setPreferredWidth(98);
		tableReservation.getColumnModel().getColumn(7).setResizable(false);
		tableReservation.getColumnModel().getColumn(7).setPreferredWidth(25);
		tableReservation.getModel().addTableModelListener(new TableModelListener() {
		      public void tableChanged(TableModelEvent e) {
		         try {
					ReservationController.changeStatus(tableReservation.getModel().getValueAt(e.getFirstRow(), 7).toString(), 
							tableReservation.getModel().getValueAt(e.getFirstRow(), e.getColumn()).toString());
					JOptionPane.showMessageDialog(null, "Successfully changed");
					
					
				} catch (Exception e1) {
				}
		      }
		    });
		
		
		JScrollPane scrollPane = new JScrollPane(tableReservation);
		frame.getContentPane().add(scrollPane, "cell 0 1,grow");
		
		JButton btnDeleteSelected = new JButton("Delete selected");
		
		btnDeleteSelected.setEnabled(false);
		
		tableReservation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDeleteSelected.setEnabled(true);
			}
		});
		
		btnDeleteSelected.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ReservationController.deleteReservation(tableReservation.getValueAt(tableReservation.getSelectedRow(), 7).toString());
						JOptionPane.showMessageDialog(null, "Successfully deleted");
					model.setRowCount(0);
					for(Object[] a : ReservationController.getReservedByStaff(userID,"IN REVIEW"))
						model.addRow(a);
					btnDeleteSelected.setEnabled(false);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to delete.");
					btnDeleteSelected.setEnabled(false);
				}
				
			}
		});
		JButton btnAddNewReservation = new JButton("Add new reservation");
		btnAddNewReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewReservation(model, userID,"STAFF");
			}
		});
		frame.getContentPane().add(btnAddNewReservation, "flowx,cell 0 5");
		
		frame.getContentPane().add(btnDeleteSelected, "cell 0 6");
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btnLogout= new JButton("Logout");
		menuBar.add(btnLogout);
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.dispose();
				new Login();
			}
		});
		
		JButton btnViewApproved = new JButton("View approved");
		btnViewApproved.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				try {
						model.setRowCount(0);
						for(Object[] a : ReservationController.getReservedByStaff(userID,"APPROVED")) {
							model.addRow(a);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
		menuBar.add(btnViewApproved);
		
		JButton btnViewPending = new JButton("View Pending");
		btnViewPending.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					model.setRowCount(0);
					for(Object[] a : ReservationController.getReservedByStaff(userID,"IN REVIEW")) {
						model.addRow(a);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menuBar.add(btnViewPending);
		
		JButton btnViewDenied = new JButton("View Denied");
		btnViewDenied.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					model.setRowCount(0);
					for(Object[] a : ReservationController.getReservedByStaff(userID,"DENIED")) {
						model.addRow(a);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		menuBar.add(btnViewDenied);
	}
}
