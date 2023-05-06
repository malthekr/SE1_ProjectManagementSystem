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

//import softwarehuset.management.app.OperationNotAllowedException;
//import dtu.library.domain.Medium;
import javax.swing.JPasswordField;

public class FindProjectScreen implements Observer {
	private MainScreen parentparentWindow; 
	private EmployeeScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private EditProjectScreen editProject;
	private GetProjectStatusReport getStatus;
	
	private JPanel panelFindProject;
	private JTextField searchField;
	private JList<Project> listSearchResult;
	private DefaultListModel<Project> searchResults;
	private JLabel lblFindResultDetail;
	
	private JLabel EnterEmployeeStatus = new JLabel("");
	
	private JButton btnDeleteProject;
	private JButton btnEditProject;
	private JButton getStatusReport;
	
	private JButton btnBack;
	
	private boolean isPM = false;
	

	public FindProjectScreen(ManagementSystemApp ManagementSystem, EmployeeScreen parentWindow, MainScreen parentparentWindow, EditProjectScreen editProject, GetProjectStatusReport getStatus) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		this.editProject = editProject;
		this.getStatus = getStatus;
		initialize();
	}

	public void initialize() {
		panelFindProject = new JPanel();
		parentparentWindow.addPanel(panelFindProject);
		panelFindProject.setLayout(null);
		panelFindProject.setBorder(BorderFactory.createTitledBorder("Find project"));
		
		//Error Message
		EnterEmployeeStatus.setBounds(110, 10, 300, 16);
		panelFindProject.add(EnterEmployeeStatus);
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProject();
			}
		});
		searchField.setBounds(138, 28, 130, 26);
		panelFindProject.add(searchField);
		searchField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProject();
			}
		});
		btnSearch.setBounds(21, 68, 117, 29);
		panelFindProject.add(btnSearch);
		btnSearch.getRootPane().setDefaultButton(btnSearch);
		
		searchResults = new DefaultListModel<>();
		listSearchResult = new JList<Project>(searchResults);
		listSearchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSearchResult.setSelectedIndex(0);
		listSearchResult.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(listSearchResult.getSelectedValue() != null){
					if(ManagementSystem.currentEmployee().equals(listSearchResult.getSelectedValue().getProjectManager()) || !listSearchResult.getSelectedValue().hasProjectManager()) {
						getStatusReport.setEnabled(true);
					} else {
						getStatusReport.setEnabled(false);
					}
				}
				
				
				if (e.getValueIsAdjusting() == false) {

		            if (listSearchResult.getSelectedIndex() == -1) {
		            	lblFindResultDetail.setText("");

		            } else {
		            	lblFindResultDetail.setText(listSearchResult.getSelectedValue().printDetail());
		            }
		        }
			}
		});
		listSearchResult.setVisibleRowCount(7);
        JScrollPane listScrollPane = new JScrollPane(listSearchResult);
        
        listScrollPane.setBounds(21, 109, 361, 149);
		panelFindProject.add(listScrollPane);
		
		JPanel panelSearchResult = new JPanel();
		panelSearchResult.setBounds(21, 270, 361, 175);
		panelFindProject.add(panelSearchResult);
		panelSearchResult.setBorder(BorderFactory.createTitledBorder(
                "Detail"));
		panelSearchResult.setLayout(null);
		
		lblFindResultDetail = new JLabel("");
		lblFindResultDetail.setVerticalAlignment(SwingConstants.TOP);
		lblFindResultDetail.setHorizontalAlignment(SwingConstants.LEFT);
		lblFindResultDetail.setBounds(23, 19, 318, 150);
		panelSearchResult.add(lblFindResultDetail);
		
		btnEditProject = new JButton("Edit Project");
		btnEditProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listSearchResult.getSelectedValue() != null) { 
					editProject.setProject(listSearchResult.getSelectedValue());
					setVisible(false);
					clear();
					editProject.setVisible(true);
				}
			}
		});
		btnEditProject.setBounds(148, 68, 117, 29);
		panelFindProject.add(btnEditProject);
		
		
		btnDeleteProject = new JButton("Delete project");
		btnDeleteProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(listSearchResult.getSelectedValue() != null){
						ManagementSystem.removeProject(listSearchResult.getSelectedValue());
						searchProject();
						EnterEmployeeStatus.setText("");
					} else {
						EnterEmployeeStatus.setText("");
						}
				} catch (OperationNotAllowedException m) {
					EnterEmployeeStatus.setText(m.getMessage());
					return;
				}
			}
		});
		btnDeleteProject.setBounds(270, 68, 117, 29);
		panelFindProject.add(btnDeleteProject);
		
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnterEmployeeStatus.setText("");
				setVisible(false);
				clear();
				parentWindow.setVisibleInside(true);
			} 
		});
		btnBack.setBounds(21, 28, 59, 29);
		panelFindProject.add(btnBack);
		
		editProject = new EditProjectScreen(ManagementSystem, this, parentWindow, parentparentWindow);
		
		// Get Status Report
		getStatusReport = new JButton("Status Report");
		getStatusReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listSearchResult.getSelectedValue() != null) {
						getStatus.setProject(listSearchResult.getSelectedValue());
						setVisible(false);
						clear();
						getStatus.setVisible(true);
				}
			} 
		});
		
		getStatusReport.setBounds(270, 26, 117, 29);
		panelFindProject.add(getStatusReport);

		getStatus = new GetProjectStatusReport(ManagementSystem, this, parentparentWindow);
		
	}
	
	public void setVisible(boolean visible) {
		panelFindProject.setVisible(visible);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	protected void searchProject() {
		searchResults.clear();
		
		ManagementSystem.searchProject(searchField.getText())
		.forEach((m) -> {searchResults.addElement(m);});
	}
	
	private int check(String str) {
		try {
			int v = Integer.parseInt(str);
			return v;
		} catch (Exception e) {
			return 23;
		}
	}
	
	public void clear() {
		searchField.setText("");
		searchResults.clear();
	}
}
