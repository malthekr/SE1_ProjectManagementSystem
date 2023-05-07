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

import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;

//import softwarehuset.management.app.OperationNotAllowedException;
//import dtu.library.domain.Medium;
import javax.swing.JPasswordField;

public class CreateProject implements Observer {
	private MainScreen parentparentWindow; 
	private AdministratorScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private JPanel panelCreateFunctions;
	
	private JLabel EnterEmployeeStatus = new JLabel("");
	
	private JLabel nameOfProject;
	private JTextField nameField;
	
	private JLabel expectedHours;
	private JTextField hourField;
	
	private JLabel emp;
	private JTextField empField;
	
	private JLabel pm;
	private JTextField pmField;
	
	//private JLabel lblEnterPasswordStatus = new JLabel("");
	private JButton btnBack;
	private JButton btnCreateProject;
	

	public CreateProject(ManagementSystemApp ManagementSystem, AdministratorScreen parentWindow, MainScreen parentparentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		initialize();
	}

	public void initialize() {
		panelCreateFunctions = new JPanel();
		parentparentWindow.addPanel(panelCreateFunctions);
		panelCreateFunctions.setLayout(null);
		panelCreateFunctions.setBorder(BorderFactory.createTitledBorder("Create Project"));
		
		//Message for if employee isn't in system
		EnterEmployeeStatus.setBounds(110, 33, 300, 16);
		panelCreateFunctions.add(EnterEmployeeStatus);
		
		//Name Field:
		nameOfProject = new JLabel("Name:");
		nameOfProject.setBounds(10, 75, 140, 16);
		
		nameField = new JTextField();
		nameField.setBounds(110, 70, 260, 26);
		panelCreateFunctions.add(nameField);
		
		//Expected hours:
		expectedHours = new JLabel("Expected Hour:");
		expectedHours.setBounds(10, 100, 140, 16);
		
		hourField = new JTextField();
		hourField.setBounds(110, 95, 260, 26);
		panelCreateFunctions.add(hourField);
		
		//Add Employee To Project
		emp = new JLabel("Employee id:");
		emp.setBounds(10, 125, 140, 16);
		
		empField = new JTextField();
		empField.setBounds(110, 120, 260, 26);
		panelCreateFunctions.add(empField);
		
		//Assign Project Manager
		pm = new JLabel("Assign PM:");
		pm.setBounds(10, 150, 140, 16);
		
		pmField = new JTextField();
		pmField.setBounds(110, 145, 260, 26);
		panelCreateFunctions.add(pmField);
		
		//Create project:
		btnCreateProject = new JButton("Create Project");
		panelCreateFunctions.add(nameOfProject);
		panelCreateFunctions.add(expectedHours);
		panelCreateFunctions.add(emp);
		panelCreateFunctions.add(pm);
		
		btnCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = !empField.getText().equals("") ? true : false;
				boolean k = !pmField.getText().equals("") ? true : false;
				
				Employee employee = findEmp(empField.getText());
				Employee PM = findEmp(pmField.getText());
				
				if(!isNumber(hourField.getText()) && !hourField.getText().equals("")) {
					EnterEmployeeStatus.setText("Not valid expected hours");
					return;
				}
				
				if(b) {
					if(employee == null) {
						EnterEmployeeStatus.setText("Employee \"" + empField.getText() + "\" not in system");
						return;
					}
				}
				
				if(k) {
					if(PM == null) {
						EnterEmployeeStatus.setText("Employee \"" + pmField.getText() + "\" not in system to be PM");
						return;
					}
				}
				
				try {
					ManagementSystem.getLoginSystem().checkAdminLoggedIn();
					Project project = createProject();
					
					if(employee != null) {
						project.addEmployee(employee);
						//addEmployee(project.getProjectID(), employee.getId());
					}
					
					if(PM != null) {
						project.promoteEmployee(PM.getId());
						//addPM(project.getProjectID(), PM.getId());
					}
					
				} catch (OperationNotAllowedException p) {
					EnterEmployeeStatus.setText(p.getMessage());
					return;
				}
				
				clear();
				setVisible(false);
				parentWindow.setVisibleInside(true);
			}
		});
		
		//button Back:
		btnCreateProject.setBounds(132, 400, 141, 29);
		panelCreateFunctions.add(btnCreateProject);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				setVisible(false);
				parentWindow.setVisibleInside(true);
			}
		});
		btnBack.setBounds(21, 28, 74, 29);
		panelCreateFunctions.add(btnBack);
		
		//ManagementSystem.addObserver(this);
	}
	
	public void setVisible(boolean visible) {
		panelCreateFunctions.setVisible(visible);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		//boolean loggedIn = ManagementSystem.adminLoggedIn();
		//enableButtons();
		/*
		if (loggedIn) {
			enableButtons();
			passwordField.setEnabled(false);
			
		} else {
			disableButtons();
			passwordField.setEnabled(true);
		}
		*/
		//displayLoginStatus();
	}
	private void clear() {
		EnterEmployeeStatus.setText("");
		nameField.setText("");
		hourField.setText("");
		empField.setText("");
		pmField.setText("");
	}
	
	private boolean isNumber(String str) {
		try {
			double v = Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private double number(String str) {
		try {
		double v = Double.parseDouble(str);
		return v - v % 0.5;
	} catch (Exception e) {
		return 0;
	}
	}
	
	private Employee findEmp(String str) {
	  try {
		return ManagementSystem.getEmployeeRepository().findEmployeeByID(str);
	} catch (OperationNotAllowedException e) {
		return null;
	}
}
	
	private Project createProject() throws OperationNotAllowedException {
		Calendar calendar = new GregorianCalendar();
		Calendar startDate = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		Calendar endDate = new GregorianCalendar();
		endDate.setTime(startDate.getTime());
		endDate.add(Calendar.DAY_OF_YEAR, 7);		
		Project project= new Project(nameField.getText(), number(hourField.getText()), startDate, endDate);
		ManagementSystem.createProject(project);
		return project;
	}
	
	private void addEmployee(int projectId, String id) throws OperationNotAllowedException {
		ManagementSystem.addEmployeeToProject(projectId, id);
	}
	
	private void addPM(int projectId, String id) throws OperationNotAllowedException {
		Project project = ManagementSystem.getProjectRepository().findProjectByID(projectId);
		Employee employee = ManagementSystem.getEmployeeRepository().findEmployeeByID(id);
	
		if(project.findEmployee(employee)){
			project.promoteEmployee(id);
			return;
		} 
		
		ManagementSystem.addEmployeeToProject(projectId, id);
		project.promoteEmployee(id);
	}
}
