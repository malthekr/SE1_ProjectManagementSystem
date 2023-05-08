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

public class GetProjectStatusReport {
	private MainScreen parentparentWindow; 
	private FindProjectScreen parentWindow;
	private ManagementSystemApp managementSystem;
	
	private Project project;
	
	// Panel
	private JPanel panelGetStatusReport;
	private JPanel panelShowStatusReport;
	
	// JLabel
	private JLabel labelStatusReport = new JLabel();;
	
	// Buttons
	private JButton btnBack;
	
	// Button position tracker
	private int buttonPos = 28;
	
	public GetProjectStatusReport(ManagementSystemApp managementSystem, FindProjectScreen parentWindow, MainScreen parentparentWindow) {
		this.managementSystem = managementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		
		initPanel(); 
		initStatusReport();
		addToPanel();
		finalInit();
	}
	
	public void initPanel() {
		panelGetStatusReport = new JPanel();
		parentparentWindow.addPanel(panelGetStatusReport);
		panelGetStatusReport.setLayout(null);
		// panel text is set under setProject();
	}
	
	public void initStatusReport() {
		// Label containing status report print
		labelStatusReport.setVerticalAlignment(SwingConstants.TOP);
		labelStatusReport.setHorizontalAlignment(SwingConstants.LEFT);
		labelStatusReport.setBounds(23, 19, 318, 318);
		
		
		panelShowStatusReport = new JPanel ();
		panelShowStatusReport.setBorder(BorderFactory.createTitledBorder(""));
		panelShowStatusReport.setBounds(21, 68, 361, 361);
		panelShowStatusReport.add(labelStatusReport);
		
		panelShowStatusReport.setLayout(null);
		
		JScrollPane resScrollPane = new JScrollPane(labelStatusReport);
		resScrollPane.setBounds(21, 68, 361, 361);
		resScrollPane.getViewport().setBackground(panelGetStatusReport.getBackground());
		panelGetStatusReport.add(resScrollPane);
	}
	
	public void addToPanel() {
		panelGetStatusReport.add(panelShowStatusReport);
	}
	
	public void finalInit() {
		
		//Back Button
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				parentWindow.setVisible(true);
			}
		});
		btnBack.setBounds(21, 28, 59, 29);
		panelGetStatusReport.add(btnBack);
	}
	
	public void setVisible(boolean visible) {
		panelGetStatusReport.setVisible(visible);
	}
	
	public void setProject(Project project) throws OperationNotAllowedException {
		this.project = project;
		String borderText = "Status report of " + project.getProjectID() + ", " + project.getProjectName() + ", " + project.getWorkedHours() + "h ~ " + project.getExpectedHours() + "h";
		panelGetStatusReport.setBorder(BorderFactory.createTitledBorder(borderText));
		labelStatusReport.setText(managementSystem.getPrintDetails().getStatusReport(project));
	}
}
