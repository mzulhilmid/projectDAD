package view;

import java.awt.EventQueue;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import controller.LabController;
import controller.UserController;
import net.miginfocom.swing.MigLayout;

public class EditLab {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditLab window = new EditLab();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditLab() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 609, 289);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][grow][][][][][][][][][]", "[][grow][][][][][][][grow][][][]"));
		
		JLabel lblLA = new JLabel(".:: L A B  R E S E R V A T I O N  S Y S T E M ::.");
		frame.getContentPane().add(lblLA, "cell 2 0");
		
		
		
		DefaultTableModel model = new DefaultTableModel() ;
		model.addColumn("Lab ID");
		model.addColumn("Lab Name");
		model.addColumn("Lab Abbrev");
//		model.addColumn("Lab Type");
		model.addColumn("Lab Location");
		//model.addColumn("Staff Username");
		
//		JComboBox<String> labTypeBox = new JComboBox<String>();
//		labTypeBox.setModel(new DefaultComboBoxModel<String>(new String[] {"ARTIFICIAL INTELLIGENCE", "MULTIMEDIA", "NETWORKING", "SOFTWARE ENGINEERING", "DATABASE", "GAMING"}));
//		JComboBox<String> staffBox = new JComboBox<String>();
//		try {
//			for(String staffUsername : UserController.listAllUsername(UserController.searchByPosition("STAFF")))
//				staffBox.addItem(staffUsername);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1 11 8,grow");
		@SuppressWarnings("serial")
		JTable tableLab = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==0)
					return false;
				else
					return true;
			}
		};
		try {
			for(Object[] a : LabController.getAllLab()) {
				model.addRow(a);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JLabel lblErrorStatus = new JLabel("All changes saved.");
		frame.getContentPane().add(lblErrorStatus, "cell 1 9");
		tableLab.getModel().addTableModelListener(new TableModelListener() {
		      public void tableChanged(TableModelEvent e) {
		         try {
					LabController.updateLab(tableLab.getModel().getValueAt(e.getFirstRow(), 0).toString(), 
							tableLab.getModel().getValueAt(e.getFirstRow(), 2).toString(), 
							tableLab.getModel().getValueAt(e.getFirstRow(), 1).toString(), 
							tableLab.getModel().getValueAt(e.getFirstRow(), 4).toString(), 
							UserController.searchByUser(tableLab.getModel().getValueAt(e.getFirstRow(), 5).toString()).getUserID(), 
							tableLab.getModel().getValueAt(e.getFirstRow(), 3).toString());
						lblErrorStatus.setText("All changes saved.");
					
						lblErrorStatus.setText("Error occurred, check for duplicate value.");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		      }
		    });
		scrollPane.setViewportView(tableLab);
		
		tableLab.setColumnSelectionAllowed(false);
		tableLab.setEnabled(true);
		
		
		
//		tableLab.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(labTypeBox));
//		tableLab.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(staffBox));
	}

}
