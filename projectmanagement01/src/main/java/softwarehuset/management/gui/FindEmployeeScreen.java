package softwarehuset.management.gui;

import java.awt.Color;
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

//import softwarehuset.management.app.OperationNotAllowedException;
//import dtu.library.domain.Medium;
import javax.swing.JPasswordField;

public class FindEmployeeScreen {
	private MainScreen parentparentWindow; 
	private EmployeeScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	
	private JPanel panelFindEmployee;
	private JTextField searchField;
	private JList<Employee> listSearchResult;
	private DefaultListModel<Employee> searchResults;
	private JLabel lblFindResultDetail;
	
	private JLabel EnterErrorStatus = new JLabel("");

	private JButton btnShow;
	private JButton btnBack;
	 
	private boolean b;

	public FindEmployeeScreen(ManagementSystemApp ManagementSystem, EmployeeScreen parentWindow, MainScreen parentparentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		initialize();
	}

	public void initialize() {
		panelFindEmployee = new JPanel();
		parentparentWindow.addPanel(panelFindEmployee);
		panelFindEmployee.setLayout(null);
		panelFindEmployee.setBorder(BorderFactory.createTitledBorder(
                "Find Employee"));
		
		//Error Message
		EnterErrorStatus.setBounds(110, 10, 300, 16);
		panelFindEmployee.add(EnterErrorStatus);
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchEmployee();
			}
		});
		searchField.setBounds(138, 28, 130, 26);
		panelFindEmployee.add(searchField);
		searchField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchEmployee();
			}
		});
		btnSearch.setBounds(21, 68, 117, 29);
		panelFindEmployee.add(btnSearch);
		btnSearch.getRootPane().setDefaultButton(btnSearch);
		
		searchResults = new DefaultListModel<>();
		listSearchResult = new JList<Employee>(searchResults);
		listSearchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSearchResult.setSelectedIndex(0);
		listSearchResult.addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent e) {
		        if (e.getValueIsAdjusting() == false) {
		        	
		            if (listSearchResult.getSelectedIndex() == -1) {
		            	lblFindResultDetail.setText("");

		            } else {
		            	lblFindResultDetail.setText(ManagementSystem.getPrintDetails().getStatusOfEmployee(listSearchResult.getSelectedValue(),b));
		            	
		            }
		        }
			}
		});
		listSearchResult.setVisibleRowCount(7);
        JScrollPane listScrollPane = new JScrollPane(listSearchResult);
        
        listScrollPane.setBounds(21, 109, 361, 149);
		panelFindEmployee.add(listScrollPane);
		
		JPanel panelSearchResult = new JPanel();
		//21, 270, 361, 175
		panelSearchResult.setBounds(21, 270, 361, 175);
		panelFindEmployee.add(panelSearchResult);
		panelSearchResult.setBorder(BorderFactory.createTitledBorder(
                "Time Table"));
		panelSearchResult.setLayout(null);
		
		
		lblFindResultDetail = new JLabel("");
		lblFindResultDetail.setVerticalAlignment(SwingConstants.TOP);
		lblFindResultDetail.setHorizontalAlignment(SwingConstants.LEFT);
		lblFindResultDetail.setBounds(23, 19, 318, 137);
		panelSearchResult.add(lblFindResultDetail); 
	
		JScrollPane resScrollPane = new JScrollPane(lblFindResultDetail);
		resScrollPane.setBounds(26, 283, 350, 158);
		resScrollPane.getViewport().setBackground(panelFindEmployee.getBackground());
		panelFindEmployee.add(resScrollPane);
		
		btnShow = new JButton("Show Active");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b = btnShow.getText() == "Show Active" ? true : false;
				
				if(b) {
					btnShow.setText("Show all");
					if(listSearchResult.getSelectedValue() != null) {
					  lblFindResultDetail.setText(ManagementSystem.getPrintDetails().getStatusOfEmployee(listSearchResult.getSelectedValue(),b));
					}
					
				} else {
					btnShow.setText("Show Active");
					if(listSearchResult.getSelectedValue() != null) {
					  lblFindResultDetail.setText(ManagementSystem.getPrintDetails().getStatusOfEmployee(listSearchResult.getSelectedValue(),b));
					}
				}
			}
		});
		btnShow.setBounds(270, 68, 117, 29);
		panelFindEmployee.add(btnShow);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnterErrorStatus.setText("");
				setVisible(false);
				clear();
				parentWindow.setVisibleInside(true);
			}
		});
		btnBack.setBounds(21, 28, 59, 29);
		panelFindEmployee.add(btnBack);
		
			}
	
	public void setVisible(boolean visible) {
		panelFindEmployee.setVisible(visible);
	}
	
	protected void searchEmployee() {
		searchResults.clear();
		ManagementSystem.searchEmployee(searchField.getText())
		.forEach((m) -> {searchResults.addElement(m);});
	}
	
	public void clear() {
		searchField.setText("");
		searchResults.clear();
	}
}
