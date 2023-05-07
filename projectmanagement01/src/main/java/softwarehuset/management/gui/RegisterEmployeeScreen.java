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

public class RegisterEmployeeScreen implements Observer {
	private MainScreen parentparentWindow; 
	private AdministratorScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private JPanel panelRegisterEmployeeFunctions;
	
	private JLabel EnterEmployeeStatus = new JLabel("");
	
	private JLabel nameOfEmployee;
	private JTextField employeeField;
	
	private JLabel employeeId;
	private JTextField idField;
	/*
	private JLabel emp;
	private JTextField empField;
	
	private JLabel pm;
	private JTextField pmField;
	*/
	//private JLabel lblEnterPasswordStatus = new JLabel("");
	private JButton btnBack;
	private JButton btnRegisterEmployee;
	private JButton btnUnRegisterEmployee;
	

	public RegisterEmployeeScreen(ManagementSystemApp ManagementSystem, AdministratorScreen parentWindow, MainScreen parentparentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		initialize();
	}

	public void initialize() {
		panelRegisterEmployeeFunctions = new JPanel();
		parentparentWindow.addPanel(panelRegisterEmployeeFunctions);
		panelRegisterEmployeeFunctions.setLayout(null);
		panelRegisterEmployeeFunctions.setBorder(BorderFactory.createTitledBorder("Register/Unregister employee"));
		
		//Error Message
		EnterEmployeeStatus.setBounds(110, 33, 300, 16);
		panelRegisterEmployeeFunctions.add(EnterEmployeeStatus);
		
		//Name Field:
		nameOfEmployee = new JLabel("Name:");
		nameOfEmployee.setBounds(10, 75, 140, 16);
		
		employeeField = new JTextField();
		employeeField.setBounds(110, 70, 260, 26);
		panelRegisterEmployeeFunctions.add(employeeField);
		
		//Employee id:
		employeeId = new JLabel("Employee id:");
		employeeId.setBounds(10, 100, 140, 16);
		
		idField = new JTextField();
		idField.setBounds(110, 95, 260, 26);
		panelRegisterEmployeeFunctions.add(idField);
		
		//Create project:
		btnRegisterEmployee = new JButton("Register Employee");
		panelRegisterEmployeeFunctions.add(nameOfEmployee);
		panelRegisterEmployeeFunctions.add(employeeId);
		
		btnRegisterEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idField.getText().equals("")) {
					EnterEmployeeStatus.setText("Employee id required");
					return;
				}
				
				if(idField.getText().length() > 4) {
					EnterEmployeeStatus.setText("Employee id \"" + idField.getText() + "\" is too long");
					return;
				}
				
				if(idField.getText().matches(".*[0-9].*")) {
					EnterEmployeeStatus.setText("Employee id can't contain numbers");
					return;
				}
				
				if(ManagementSystem.getEmployeeRepository().checkIfEmployeeExists(idField.getText())) {
					EnterEmployeeStatus.setText("Employee id \"" + idField.getText() +"\" is already in system");
					return;
				}
				
				try {
					addEmployee(employeeField.getText().toLowerCase(), idField.getText());
				} catch (OperationNotAllowedException p) {
					EnterEmployeeStatus.setText(p.getMessage());
					return;
				}
				clear();
				setVisible(false);
				parentWindow.setVisibleInside(true);
			}
		});
		
		btnRegisterEmployee.setBounds(40, 400, 160, 29);
		panelRegisterEmployeeFunctions.add(btnRegisterEmployee);
		
		btnUnRegisterEmployee = new JButton("UnRegister Employee");
		btnUnRegisterEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(idField.getText().equals("")) {
					EnterEmployeeStatus.setText("Employee id required");
					return;
				}
				
				if(idField.getText().length() > 4) {
					EnterEmployeeStatus.setText("Employee id \"" + idField.getText() + "\" is too long");
					return;
				}
				
				if(idField.getText().matches(".*[0-9].*")) {
					EnterEmployeeStatus.setText("Employee id can't contain numbers");
					return;
				}
				
				if(!ManagementSystem.getEmployeeRepository().checkIfEmployeeExists(idField.getText())) {
					EnterEmployeeStatus.setText("Employee id \"" + idField.getText() +"\" is not in the system");
					return;
				}
				
				try {
					removeEmployee(idField.getText().toLowerCase());
				} catch (OperationNotAllowedException p) {
					EnterEmployeeStatus.setText(p.getMessage());
					return;
				}
				
				clear();
				setVisible(false);
				parentWindow.setVisibleInside(true);
			}
		});
		
		
		btnUnRegisterEmployee.setBounds(200, 400, 160, 29);
		panelRegisterEmployeeFunctions.add(btnUnRegisterEmployee);
		
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
		panelRegisterEmployeeFunctions.add(btnBack);
		
		//ManagementSystem.addObserver(this);
	}
	
	public void setVisible(boolean visible) {
		panelRegisterEmployeeFunctions.setVisible(visible);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	private void clear() {
		EnterEmployeeStatus.setText("");
		employeeField.setText("");
		idField.setText("");
	}
	
	private boolean checkIfEmployeeExists(String id) throws OperationNotAllowedException {
		Employee e = ManagementSystem.getEmployeeRepository().findEmployeeByID(id);
		return e == null ? true : false;
	}
	
	private void addEmployee(String name, String id) throws OperationNotAllowedException {
		Employee employee = new Employee(name, id);
		ManagementSystem.getEmployeeRepository().addEmployee(employee);
	}
	
	private void removeEmployee(String id) throws OperationNotAllowedException {
		Employee employee = ManagementSystem.getEmployeeRepository().findEmployeeByID(id);
		ManagementSystem.getEmployeeRepository().removeEmployee(employee);
	}
}
