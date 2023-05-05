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
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import softwarehuset.management.app.Activity;
import softwarehuset.management.app.Employee;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;

public class GetProjectStatusReport implements Observer {
	private MainScreen parentparentWindow; 
	private FindProjectScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	
	private Project project;
	
	// Panel
	private JPanel panelGetStatusReport;
	
	// Label
	private JLabel EnterErrorMessage = new JLabel("");
	
	// Buttons
	private JButton btnBack;
	
	// Button position tracker
	private int buttonPos = 28;
	
	public GetProjectStatusReport(ManagementSystemApp ManagementSystem, FindProjectScreen parentWindow, MainScreen parentparentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		
		initPanel();
		initButtons();
		addEventListeners();
		addButtonsToPanel();
		finalInit();
	}
	
	public void initPanel() {
		panelGetStatusReport = new JPanel();
		parentparentWindow.addPanel(panelGetStatusReport);
		panelGetStatusReport.setLayout(null);
		// panel text is set under setProject();
	}
	
	public void initButtons() {
		
	}
	
	public void addEventListeners() {
		
	}
	
	public void addButtonsToPanel() {
		
	}
	
	public void finalInit() {
		//Error Message
		EnterErrorMessage.setBounds(110, 10, 300, 16);
		panelGetStatusReport.add(EnterErrorMessage);

		//Back Button
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnterErrorMessage.setText("");
				setVisible(false);
				parentWindow.setVisible(true);
			}
		});
		btnBack.setBounds(21, 28, 59, 29);
		panelGetStatusReport.add(btnBack);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	public void setVisible(boolean visible) {
		panelGetStatusReport.setVisible(visible);
	}
	
	public JTextField addTextField(){
		JTextField txtField = new JTextField();
		txtField.setBounds(120, newButtonPos(), 170, 29);
		return txtField;
	};
	
	public JButton addButton(String label){
		JButton btn = new JButton(label);
		btn.setEnabled(true);
		btn.setBounds(120, newButtonPos(), 170, 29);
		
		return btn;
	}
	
	private int newButtonPos(){
		int pos = this.buttonPos;
		this.buttonPos += 41;
		return pos;
	}
	
	public void setProject(Project project) {
		this.project = project;
		panelGetStatusReport.setBorder(BorderFactory.createTitledBorder("Status report of " + project.getProjectID() + " " + project.getProjectName()));
	}
}
