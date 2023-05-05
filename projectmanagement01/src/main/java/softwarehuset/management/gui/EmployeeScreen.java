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
import softwarehuset.management.app.OperationNotAllowedException;

//import softwarehuset.management.app.OperationNotAllowedException;
//import dtu.library.domain.Medium;
//import javax.swing.JemployeeField;

public class EmployeeScreen implements Observer {

	private MainScreen parentWindow;
	private ManagementSystemApp ManagementSystem; 
	private CreateActivityScreen CreateActivity;
	private FindActivityScreen findActivity;
	private EditActivityScreen editActivity;
	private FindProjectScreen findProject;
	private EditProjectScreen editProject;
	private GetProjectStatusReport getStatus;
	private FindEmployeeScreen findEmployee;
	
	private JPanel panelEmployeeFunctions;
	private JButton btnBack;
	//private JButton btnFindActivity;
	private JLabel lblPassword;
	private JTextField employeeField;
	private JLabel lblEnterPasswordStatus = new JLabel("");
	private JLabel lblLoginStatus;
	private JButton btnLogout;
	//private JButton btnPayFine;
	//private JButton btnCreateActivity;
	//private JButton btnUnregisterEmployee;
	private JButton btnCreateActivity;
	private JButton btnFindEmployee;
	private JButton btnFindActivity;
	private JButton btnJoinProject;
	
	private int buttonPos = 90;

	public EmployeeScreen(ManagementSystemApp ManagementSystem, MainScreen parentWindow, CreateActivityScreen CreateAndFindActivity, EditActivityScreen editActivity, FindProjectScreen findProject, EditProjectScreen editProject, FindEmployeeScreen findEmployee, GetProjectStatusReport getStatus) {
		this.ManagementSystem = ManagementSystem;
		this.parentWindow = parentWindow;
		this.CreateActivity = CreateAndFindActivity;
		this.editActivity = editActivity;
		this.findProject = findProject;
		this.editProject = editProject;
		this.findEmployee = findEmployee;
		this.getStatus = getStatus;
		initialize();
	}
	
	public void initialize() {
		panelEmployeeFunctions = new JPanel();
		parentWindow.addPanel(panelEmployeeFunctions);
		panelEmployeeFunctions.setLayout(null);
		panelEmployeeFunctions.setBorder(BorderFactory.createTitledBorder("Employee Functions"));
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				employeeField.setText("");
				lblEnterPasswordStatus.setText("");
				setVisible(false);
				ManagementSystem.employeeLogout();
				parentWindow.setVisible(true);
			}
		});
		
		btnBack.setBounds(21, 28, 74, 29);
		panelEmployeeFunctions.add(btnBack);

		lblPassword = new JLabel("Login employee:");
		lblPassword.setBounds(130, 33, 140, 16);
		panelEmployeeFunctions.add(lblPassword);

		employeeField = new JTextField();
		employeeField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean LogIn;
				try {
					LogIn = ManagementSystem.employeeLogin(employeeField.getText());
				} catch (Exception ignore) {
					LogIn = false;
				}
				if (LogIn) {
					lblEnterPasswordStatus.setText("");
				} else {
					lblEnterPasswordStatus.setText("Employee ID does not exist");
				}
			}
		});
		employeeField.setBounds(252, 28, 130, 26);
		panelEmployeeFunctions.add(employeeField);
		
		lblLoginStatus = new JLabel("");
		lblLoginStatus.setBounds(135, 102, 200, 16);
		panelEmployeeFunctions.add(lblLoginStatus);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagementSystem.employeeLogout();
				employeeField.setText("");
			}
		});
		btnLogout.setEnabled(false);
		btnLogout.setBounds(120, newButtonPos(), 170, 29);
		panelEmployeeFunctions.add(btnLogout);
		
		
		btnJoinProject = new JButton("Find Project");
		btnJoinProject.setBounds(120, newButtonPos(), 170, 29);
		btnJoinProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				findProject.setVisible(true);
			}
		});
		btnJoinProject.setEnabled(false);
		panelEmployeeFunctions.add(btnJoinProject);
		
		btnCreateActivity = new JButton("Create Activity");
		btnCreateActivity.setBounds(120, newButtonPos(), 170, 29);;
		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				CreateActivity.setVisible(true);
			}
		});
		btnCreateActivity.setEnabled(false);
		panelEmployeeFunctions.add(btnCreateActivity);
		
		btnFindActivity = new JButton("Find Activity");
		btnFindActivity.setEnabled(false);
		btnFindActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				findActivity.setVisible(true);
			}
		});
		btnFindActivity.setBounds(120, newButtonPos(), 170, 29);
		panelEmployeeFunctions.add(btnFindActivity);
		
		
		btnFindEmployee = new JButton("Find Employee");
		btnFindEmployee.setEnabled(false);
		btnFindEmployee.setBounds(120, newButtonPos(), 170, 29);;
		btnFindEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				findEmployee.setVisible(true);
			}
		});
		panelEmployeeFunctions.add(btnFindEmployee);
		
		
		lblEnterPasswordStatus.setBounds(120, 74, 200, 16);
		panelEmployeeFunctions.add(lblEnterPasswordStatus);
	
		disableButtons();
		displayLoginStatus();
		ManagementSystem.addObserver(this);
		CreateActivity = new CreateActivityScreen(ManagementSystem, this, parentWindow);
		findActivity = new FindActivityScreen(ManagementSystem, this, parentWindow, editActivity);
		findProject = new FindProjectScreen(ManagementSystem, this, parentWindow, editProject, getStatus);
		findEmployee = new FindEmployeeScreen(ManagementSystem, this, parentWindow);
	}
	
	private int newButtonPos(){
		this.buttonPos += 41;
		return this.buttonPos;
	}
		
	public boolean Login(String id) throws OperationNotAllowedException {
		return ManagementSystem.employeeLogin(id);
	}
	
	private void enableButtons() {
		setEnableButtons(true);
	}

	private void disableButtons() {
		setEnableButtons(false);
	}

	private void setEnableButtons(boolean enabled) {
		//btnFindActivity.setEnabled(enabled);
		btnLogout.setEnabled(enabled);
		btnCreateActivity.setEnabled(enabled);
		btnFindActivity.setEnabled(enabled);
		btnJoinProject.setEnabled(enabled);
		btnFindEmployee.setEnabled(enabled);
		//btnUnregisterEmployee.setEnabled(enabled);
		//btnPayFine.setEnabled(enabled);
	}
	
	public void setVisible(boolean visible) {
		if (!visible) {
			disableButtons();
		}
		panelEmployeeFunctions.setVisible(visible);
	}
	
	public void setVisibleInside(boolean visible) {
		if (visible) {
			enableButtons();
		}
		panelEmployeeFunctions.setVisible(visible);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		boolean loggedIn = ManagementSystem.employeeLogged();
		if (loggedIn) {
			enableButtons();
			employeeField.setEnabled(false);
			
		} else {
			disableButtons();
			employeeField.setEnabled(true);
		}
		displayLoginStatus();
	}

	protected void displayLoginStatus() {
		boolean loggedIn = ManagementSystem.employeeLogged();
		if (loggedIn) {
			lblLoginStatus.setText("Employee logged in");
		} else {
			lblLoginStatus.setText("Employee logged off");
		}
	}
}
