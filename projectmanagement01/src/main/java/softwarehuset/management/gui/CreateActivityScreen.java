package softwarehuset.management.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import softwarehuset.management.app.Activity;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;

import javax.swing.JPasswordField;

public class CreateActivityScreen {
	private MainScreen parentparentWindow; 
	private EmployeeScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private Project project;
	private JPanel panelCreateActivityFunctions;
	
	private JLabel EnterEmployeeStatus = new JLabel("");
	
	private JLabel projectId;
	private JTextField projectIdField;
	
	private JLabel descriptionOfActivity;
	private JTextField descriptionField;
	
	private JLabel employeeId;
	private JTextField idField;
	
	private JLabel expectedHour;
	private JTextField expField;
	
	private JLabel workedHour;
	private JTextField workedField;
	
	private JButton btnBack;
	private JButton btnCreateActivity;
	

	public CreateActivityScreen(ManagementSystemApp ManagementSystem, EmployeeScreen parentWindow, MainScreen parentparentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		initialize();
	}

	public void initialize() {
		panelCreateActivityFunctions = new JPanel();
		parentparentWindow.addPanel(panelCreateActivityFunctions);
		panelCreateActivityFunctions.setLayout(null);
		panelCreateActivityFunctions.setBorder(BorderFactory.createTitledBorder("Create activity"));
		
		//Error Message
		EnterEmployeeStatus.setBounds(110, 33, 300, 16);
		panelCreateActivityFunctions.add(EnterEmployeeStatus);
		
		//Project id:
		projectId = new JLabel("*Project id:");
		projectId.setBounds(10, 75, 140, 16);
				
		projectIdField = new JTextField();
		projectIdField.setBounds(110, 70, 260, 26);
		panelCreateActivityFunctions.add(projectIdField);
		
		//description of activity:
		descriptionOfActivity = new JLabel("Name:");
		descriptionOfActivity.setBounds(10, 100, 140, 16);
		
		descriptionField = new JTextField();
		descriptionField.setBounds(110, 95, 260, 26);
		panelCreateActivityFunctions.add(descriptionField);
		
		//Employee id:
		employeeId = new JLabel("Employee id:");
		employeeId.setBounds(10, 125, 140, 16);
		
		idField = new JTextField();
		idField.setBounds(110, 120, 260, 26);
		panelCreateActivityFunctions.add(idField);
		
		//Expected hour
		expectedHour = new JLabel("Expected hour:");
		expectedHour.setBounds(10, 150, 140, 16);
		
		expField = new JTextField();
		expField.setBounds(110, 145, 260, 26);
		panelCreateActivityFunctions.add(expField);
		
		//Worked hour
		workedHour = new JLabel("Worked hour:");
		workedHour.setBounds(10, 175, 140, 16);
		
		workedField = new JTextField();
		workedField.setBounds(110, 170, 260, 26);
		panelCreateActivityFunctions.add(workedField);
		
		
		//Create project:
		btnCreateActivity = new JButton("Create Activity");
		panelCreateActivityFunctions.add(projectId);
		panelCreateActivityFunctions.add(descriptionOfActivity);
		panelCreateActivityFunctions.add(employeeId);
		panelCreateActivityFunctions.add(expectedHour);
		panelCreateActivityFunctions.add(workedHour);
		
		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(projectIdField.getText().equals("")) {
					EnterEmployeeStatus.setText("Project id required");
					return;
				}
				
				if(!isInt(projectIdField.getText())) {
					EnterEmployeeStatus.setText("Project id is integer");
					return;
				}
				int pjId = numberInt(projectIdField.getText());
				
				if(idField.getText().matches(".*[0-9].*")) {
					EnterEmployeeStatus.setText("Employee id doesn't contain numbers");
					return;
				}
				
				if(!checkIfEmployeeExists(idField.getText()) && !idField.getText().equals("")) {
					EnterEmployeeStatus.setText("Employee id \"" + idField.getText() +"\" is not in the system");
					return;
				}
				
				if(!isDouble(expField.getText()) && !expField.getText().equals("")) {
					EnterEmployeeStatus.setText("Not valid expected hours");
					return;
				}
				
				if(!isDouble(workedField.getText()) && !workedField.getText().equals("")) {
					EnterEmployeeStatus.setText("Not valid worked hours");
					return;
				}
				
				try {
					Project project = ManagementSystem.getProjectRepository().findProjectByID(pjId);
					ManagementSystem.checkAuth(project);
					project.createActivity(descriptionField.getText());
					
				} catch (OperationNotAllowedException l) {
					EnterEmployeeStatus.setText(l.getMessage());
					return;
				}
				
				try {
					if(!idField.getText().equals("")) {
						Employee employee = ManagementSystem.getEmployeeRepository().findEmployeeByID(idField.getText());
						Project project = ManagementSystem.getProjectRepository().findProjectByID(pjId);
						
						ManagementSystem.addEmployeeToActivity(employee, project, descriptionField.getText());
					}
					Project project = ManagementSystem.getProjectRepository().findProjectByID(pjId);
					project.findActivityByDescription(descriptionField.getText()).setExpectedHours(numberDouble(expField.getText()));
					project.findActivityByDescription(descriptionField.getText()).addWorkedHours(numberDouble(workedField.getText()));
				} catch (OperationNotAllowedException p) {
					EnterEmployeeStatus.setText(p.getMessage());
					return;
				}
		
				clear();
				setVisible(false);
				parentWindow.setVisibleInside(true);
			}
		});
		
		btnCreateActivity.setBounds(40, 400, 160, 29);
		panelCreateActivityFunctions.add(btnCreateActivity);
		
		//button Back:
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				setVisible(false);
				parentWindow.setVisibleInside(true);
			}
		});
		btnBack.setBounds(21, 28, 74, 29);
		panelCreateActivityFunctions.add(btnBack);
	}
	
	public void setVisible(boolean visible) {
		panelCreateActivityFunctions.setVisible(visible);
	}
	
	private void clear() {
		EnterEmployeeStatus.setText("");
		projectIdField.setText("");
		descriptionField.setText("");
		idField.setText("");
		expField.setText("");
		workedField.setText("");
	}
	
	private boolean isInt(String id) {
		try {
			int v = Integer.parseInt(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private int numberInt(String id) {
		try {
			int v = Integer.parseInt(id);
			return v;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private boolean isDouble(String str) {
		try {
			double v = Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private double numberDouble(String str) {
		try {
			double v = Double.parseDouble(str);
			return v - v % 0.5;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private boolean checkIfEmployeeExists(String id) {
		boolean b = ManagementSystem.getEmployeeRepository().checkIfEmployeeExists(id);
		return b;
	}
}
