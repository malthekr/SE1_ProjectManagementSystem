package softwarehuset.management.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import softwarehuset.management.app.ManagementSystemApp;
//import softwarehuset.management.app.OperationNotAllowedException;
//import dtu.library.domain.Medium;
import javax.swing.JPasswordField;

public class AdministratorScreen implements Observer {

	private MainScreen parentWindow;
	private CreateProject createProject;
	private RegisterEmployeeScreen RegisterEmployeeScreen;
	private ManagementSystemApp ManagementSystem;
	private JPanel panelAdministratorFunctions;
	private JButton btnBack;
	private JButton btnAddProject;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JLabel lblEnterPasswordStatus = new JLabel("");
	private JLabel lblLoginStatus;
	private JButton btnLogout;
	//private JButton btnPayFine;
	private JButton btnRegisterEmployee;
	//private JButton btnUnregisterEmployee;

	public AdministratorScreen(ManagementSystemApp ManagementSystem, MainScreen parentWindow, CreateProject createProject, RegisterEmployeeScreen RegisterEmployeeScreen) {
		this.ManagementSystem = ManagementSystem;
		this.parentWindow = parentWindow;
		this.createProject = createProject;
		this.RegisterEmployeeScreen = RegisterEmployeeScreen;
		
		initialize();
	}

	public void initialize() {
		panelAdministratorFunctions = new JPanel();
		parentWindow.addPanel(panelAdministratorFunctions);
		panelAdministratorFunctions.setLayout(null);
		panelAdministratorFunctions.setBorder(BorderFactory.createTitledBorder("Administrator Functions"));

		btnAddProject = new JButton("Add Project");
		btnAddProject.setEnabled(false);
		btnAddProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				createProject.setVisible(true);
			}
		});
		btnAddProject.setBounds(120, 212, 170, 29);
		panelAdministratorFunctions.add(btnAddProject);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ManagementSystem.adminLogout();
				parentWindow.setVisible(true);
			}
		});
		btnBack.setBounds(21, 28, 74, 29);
		panelAdministratorFunctions.add(btnBack);

		lblPassword = new JLabel("Password (admi):");
		lblPassword.setBounds(130, 33, 140, 16);
		panelAdministratorFunctions.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean loginOk = ManagementSystem.adminLogin(passwordField.getText());
				passwordField.setText("");
				if (loginOk) {
					lblEnterPasswordStatus.setText("");
				} else {
					lblEnterPasswordStatus.setText("login failed");
				}
			}
		});
		passwordField.setBounds(252, 28, 130, 26);
		panelAdministratorFunctions.add(passwordField);
		
		lblLoginStatus = new JLabel("");
		lblLoginStatus.setBounds(145, 102, 112, 16);
		panelAdministratorFunctions.add(lblLoginStatus);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagementSystem.adminLogout();
			}
		});
		btnLogout.setEnabled(false);
		btnLogout.setBounds(120, 130, 170, 29);
		panelAdministratorFunctions.add(btnLogout);
		
		
		btnRegisterEmployee = new JButton("Register/Un Employee");
		btnRegisterEmployee.setEnabled(false);
		btnRegisterEmployee.setBounds(120, 171, 170, 29);;
		btnRegisterEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegisterEmployeeScreen.setVisible(true);
			}
		});
		
		panelAdministratorFunctions.add(btnRegisterEmployee);
		
		
		lblEnterPasswordStatus.setBounds(145, 74, 112, 16);
		panelAdministratorFunctions.add(lblEnterPasswordStatus);
	
		disableButtons();
		displayLoginStatus();
		ManagementSystem.addObserver(this);
		createProject = new CreateProject(ManagementSystem, this, parentWindow);
		RegisterEmployeeScreen = new RegisterEmployeeScreen(ManagementSystem, this, parentWindow);
	}

	private void enableButtons() {
		setEnableButtons(true);
	}

	private void disableButtons() {
		setEnableButtons(false);
	}

	private void setEnableButtons(boolean enabled) {
		btnAddProject.setEnabled(enabled);
		btnLogout.setEnabled(enabled);
		btnRegisterEmployee.setEnabled(enabled);
		//btnUnregisterEmployee.setEnabled(enabled);
		//btnPayFine.setEnabled(enabled);
	}

	public void setVisible(boolean visible) {
		if (!visible) {
			disableButtons();
		}
		panelAdministratorFunctions.setVisible(visible);
	}
	
	public void setVisibleInside(boolean visible) {
		if (visible) {
			enableButtons();
		}
		panelAdministratorFunctions.setVisible(visible);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		boolean loggedIn = ManagementSystem.adminLoggedIn();
		if (loggedIn) {
			enableButtons();
			passwordField.setEnabled(false);
			
		} else {
			disableButtons();
			passwordField.setEnabled(true);
		}
		displayLoginStatus();
	}

	protected void displayLoginStatus() {
		boolean loggedIn = ManagementSystem.adminLoggedIn();
		if (loggedIn) {
			lblLoginStatus.setText("Admin logged in");
		} else {
			lblLoginStatus.setText("Admin logged off");
		}
	}
}
