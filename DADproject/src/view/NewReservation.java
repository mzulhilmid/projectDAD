package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import controller.LabController;
import controller.ReservationController;
import net.miginfocom.swing.MigLayout;

public class NewReservation {

	private JFrame frame;
	DefaultTableModel model;
	String userID;
	String position;
	String labID;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public NewReservation(DefaultTableModel model, String userID, String position) {
		this.model = model;
		this.userID = userID;
		this.position = position;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.setVisible(true);
		frame.setBounds(100, 100, 500, 340);
		frame.getContentPane().setLayout(new MigLayout("", "[][][][][grow][][13.00][231.00][][63.00][][][grow][][][][]", "[][][][][][20.00][][][58.00][]"));
		
		JLabel lblLabReservationSystem = new JLabel(".:: L A B  R E S E R V A T I O N  S Y S T E M ::.");
		frame.getContentPane().add(lblLabReservationSystem, "cell 6 1 2 1");
		
		JLabel lblDate = new JLabel("Date:");
		frame.getContentPane().add(lblDate, "cell 6 2,alignx right");
		
		JSpinner dateInput = new JSpinner();
		dateInput.setModel(new SpinnerDateModel(new java.util.Date(), null,null, Calendar.DATE));
		dateInput.setEditor(new JSpinner.DateEditor(dateInput,"yyyy-MM-dd"));
		frame.getContentPane().add(dateInput, "cell 7 2,growx");
		
		JLabel lblTimeStart = new JLabel("Time start:");
		frame.getContentPane().add(lblTimeStart, "cell 6 3,alignx right");
		JSpinner timeInputStart = new JSpinner();
		timeInputStart.setModel(new SpinnerDateModel(new java.util.Date(), null,null, Calendar.HOUR_OF_DAY));
		timeInputStart.setEditor(new JSpinner.DateEditor(timeInputStart,"HH:mm"));
		frame.getContentPane().add(timeInputStart, "cell 7 3,growx");		
		
		JLabel lblTimeEnd= new JLabel("Time end:");
		frame.getContentPane().add(lblTimeEnd, "cell 6 4,alignx right");		
		JSpinner timeInputEnd = new JSpinner();
		
		timeInputEnd.setModel(new SpinnerDateModel(new java.util.Date(), null,null, Calendar.HOUR_OF_DAY));
		timeInputEnd.setEditor(new JSpinner.DateEditor(timeInputEnd,"HH:mm"));
		frame.getContentPane().add(timeInputEnd, "cell 7 4,growx");
		
		JLabel lblLabName = new JLabel("Lab Name:");
		frame.getContentPane().add(lblLabName, "cell 6 6,alignx right");
		
		JComboBox<String> labName = new JComboBox<String>();
		labName.setEnabled(true);
		frame.getContentPane().add(labName, "cell 7 6,growx");
		
		JLabel lblLabType = new JLabel("Lab Type:");
		frame.getContentPane().add(lblLabType, "cell 6 5,alignx right");
		JComboBox<String> labType = new JComboBox<String>();
		
		labType.setModel(new DefaultComboBoxModel<String>(new String[] {"Choose...", "Artificial Intelligence", "Multimedia", "Networking", "Software Engineering", "Database", "Gaming"}));
		frame.getContentPane().add(labType, "cell 7 5,growx");

		
		JLabel lblPurpose = new JLabel("Purpose :");
		frame.getContentPane().add(lblPurpose, "cell 6 7,alignx right");
		JTextArea purposePane = new JTextArea();
		purposePane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		purposePane.setLineWrap(true);
		frame.getContentPane().add(purposePane, "cell 7 7 1 2,grow");
		
		JButton btnSubmit = new JButton("NEXT");
		btnSubmit.setEnabled(true);
		
		frame.getContentPane().add(btnSubmit, "cell 9 9");
		//Action listeners
		btnSubmit.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					ReservationController reservationController = new ReservationController(LabController.searchLabByName(labName.getSelectedItem().toString()));
					SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
					Time timeStart = Time.valueOf(formatTime.format(timeInputStart.getValue()));
					timeStart.setSeconds(0);
					Time timeEnd = Time.valueOf(formatTime.format(timeInputEnd.getValue()));
					timeEnd.setSeconds(0);
					reservationController.addNewReservation(timeStart, timeEnd, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateInput.getValue())), userID, purposePane.getText(),position);
					JOptionPane.showMessageDialog(null, "Successfully placed reservation.");
					model.setRowCount(0);
					if(position=="STAFF")
						for(Object[] a : ReservationController.getReservedByStaff(userID,"IN REVIEW"))
							model.addRow(a);
					else
						for(Object[] a : ReservationController.getReservedByUser(userID, labID))
							model.addRow(a);
					frame.dispose();
				} catch(Exception reservationE) {
					reservationE.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error occurred, check that the database is connected.");
				}
			}
		});
		dateInput.addChangeListener(new ChangeListener() {
			@SuppressWarnings("deprecation")
			public void stateChanged(ChangeEvent arg0) {
				labName.setEnabled(true);
				labName.removeAllItems();
				try {
					ReservationController reservationController;
					for(model.Lab lab : LabController.searchLabByType(labType.getSelectedItem().toString())) {
						reservationController = new ReservationController(lab);
						SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
						Time timeStart = Time.valueOf(formatTime.format(timeInputStart.getValue()));
						timeStart.setSeconds(0);
						Time timeEnd = Time.valueOf(formatTime.format(timeInputEnd.getValue()));
						timeEnd.setSeconds(0);
						if(reservationController.checkIfAvailable(timeStart, timeEnd, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateInput.getValue())))) 
							labName.addItem(lab.getName());
					}
					if(labName.getItemCount()==0) {
						labName.addItem("No lab available for selected time");
						labName.setEnabled(true);
						btnSubmit.setEnabled(true);
					}
					else
						btnSubmit.setEnabled(true);
				} catch (Exception e) {
					labName.setEnabled(true);
					labName.addItem("No lab available for selected time");
					btnSubmit.setEnabled(true);
				}
			}
		});
		timeInputStart.addChangeListener(new ChangeListener() {
			@SuppressWarnings("deprecation")
			public void stateChanged(ChangeEvent arg0) {
				labName.setEnabled(true);
				labName.removeAllItems();
				try {
					ReservationController reservationController;
					for(model.Lab lab : LabController.searchLabByType(labType.getSelectedItem().toString())) {
						reservationController = new ReservationController(lab);
						SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
						Time timeStart = Time.valueOf(formatTime.format(timeInputStart.getValue()));
						timeStart.setSeconds(0);
						Time timeEnd = Time.valueOf(formatTime.format(timeInputEnd.getValue()));
						timeEnd.setSeconds(0);
						if(reservationController.checkIfAvailable(timeStart, timeEnd, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateInput.getValue())))) 
							labName.addItem(lab.getName());
					}
					if(labName.getItemCount()==0) {
						labName.addItem("No lab available for selected time");
						labName.setEnabled(true);btnSubmit.setEnabled(true);
					}
					else
						btnSubmit.setEnabled(true);
				} catch (Exception e) {
					labName.setEnabled(true);
					labName.addItem("No lab available for selected time");btnSubmit.setEnabled(false);
				}
			}
		});
		timeInputEnd.addChangeListener(new ChangeListener() {
			@SuppressWarnings("deprecation")
			public void stateChanged(ChangeEvent arg0) {
				labName.setEnabled(true);
				labName.removeAllItems();
				try {
					ReservationController reservationController;
					for(model.Lab lab : LabController.searchLabByType(labType.getSelectedItem().toString())) {
						reservationController = new ReservationController(lab);
						SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
						Time timeStart = Time.valueOf(formatTime.format(timeInputStart.getValue()));
						timeStart.setSeconds(0);
						Time timeEnd = Time.valueOf(formatTime.format(timeInputEnd.getValue()));
						timeEnd.setSeconds(0);
						if(reservationController.checkIfAvailable(timeStart, timeEnd, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateInput.getValue())))) 
							labName.addItem(lab.getName());
					}
					if(labName.getItemCount()==0) {
						labName.addItem("No lab available for selected time");
						labName.setEnabled(false);btnSubmit.setEnabled(false);
					}
					else
						btnSubmit.setEnabled(true);
				} catch (Exception e) {
					labName.setEnabled(true);
					labName.addItem("No lab available for selected time");btnSubmit.setEnabled(false);
				}
			}
		});
		labType.addItemListener(new ItemListener() {
			@SuppressWarnings("deprecation")
			public void itemStateChanged(ItemEvent arg0) {
				labName.setEnabled(true);
				labName.removeAllItems();
				try {
					ReservationController reservationController;
					for(model.Lab lab : LabController.searchLabByType(labType.getSelectedItem().toString())) {
						reservationController = new ReservationController(lab);
						SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
						Time timeStart = Time.valueOf(formatTime.format(timeInputStart.getValue()));
						timeStart.setSeconds(0);
						Time timeEnd = Time.valueOf(formatTime.format(timeInputEnd.getValue()));
						timeEnd.setSeconds(0);
						if(reservationController.checkIfAvailable(timeStart, timeEnd, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(dateInput.getValue())))) 
							labName.addItem(lab.getName());
					}
					if(labName.getItemCount()==0) {
						labName.addItem("No lab available for selected time");
						labName.setEnabled(true);
						btnSubmit.setEnabled(true);
					}
					else
						btnSubmit.setEnabled(true);
				} catch (Exception e) {
					labName.setEnabled(true);
					labName.addItem("No lab available for selected time");btnSubmit.setEnabled(true);
				}
			}
		});
	}

}
