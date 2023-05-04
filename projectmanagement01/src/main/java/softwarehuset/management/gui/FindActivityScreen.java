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

public class FindActivityScreen implements Observer {
	private MainScreen parentparentWindow; 
	private EmployeeScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private EditActivityScreen editActivity;
	
	private JPanel panelFindActivity;
	private JTextField searchField;
	private JList<Activity> listSearchResult;
	private DefaultListModel<Activity> searchResults;
	private JLabel lblFindResultDetail;
	
	private JLabel EnterEmployeeStatus = new JLabel("");
	
	private JButton btnDeleteActivity;
	private JButton btnEditActivity;
	
	private JButton btnBack;
	

	public FindActivityScreen(ManagementSystemApp ManagementSystem, EmployeeScreen parentWindow, MainScreen parentparentWindow, EditActivityScreen editActivity) {
		this.ManagementSystem = ManagementSystem;
		this.parentparentWindow = parentparentWindow;
		this.parentWindow = parentWindow;
		this.editActivity = editActivity;
		initialize();
	}

	public void initialize() {
		panelFindActivity = new JPanel();
		parentparentWindow.addPanel(panelFindActivity);
		panelFindActivity.setLayout(null);
		panelFindActivity.setBorder(BorderFactory.createTitledBorder(
                "Find activity"));
		
		//Error Message
		EnterEmployeeStatus.setBounds(110, 10, 300, 16);
		panelFindActivity.add(EnterEmployeeStatus);
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchActivity();
			}
		});
		searchField.setBounds(138, 28, 130, 26);
		panelFindActivity.add(searchField);
		searchField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchActivity();
			}
		});
		btnSearch.setBounds(21, 68, 117, 29);
		panelFindActivity.add(btnSearch);
		btnSearch.getRootPane().setDefaultButton(btnSearch);
		
		searchResults = new DefaultListModel<>();
		listSearchResult = new JList<Activity>(searchResults);
		listSearchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSearchResult.setSelectedIndex(0);
		listSearchResult.addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent e) {
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
		panelFindActivity.add(listScrollPane);
		
		JPanel panelSearchResult = new JPanel();
		panelSearchResult.setBounds(21, 270, 361, 175);
		panelFindActivity.add(panelSearchResult);
		panelSearchResult.setBorder(BorderFactory.createTitledBorder(
                "Detail"));
		panelSearchResult.setLayout(null);
		
		lblFindResultDetail = new JLabel("");
		lblFindResultDetail.setVerticalAlignment(SwingConstants.TOP);
		lblFindResultDetail.setHorizontalAlignment(SwingConstants.LEFT);
		lblFindResultDetail.setBounds(23, 19, 318, 137);
		panelSearchResult.add(lblFindResultDetail);
		
		btnEditActivity = new JButton("Edit Activity");
		btnEditActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listSearchResult.getSelectedValue() != null) {
					editActivity.setActivity(listSearchResult.getSelectedValue());
					setVisible(false);
					clear();
					editActivity.setVisible(true);
				}
			}
		});
		btnEditActivity.setBounds(148, 68, 117, 29);
		panelFindActivity.add(btnEditActivity);
		
		
		btnDeleteActivity = new JButton("Delete Activity");
		btnDeleteActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(listSearchResult.getSelectedValue() != null){
						ManagementSystem.removeActivity(listSearchResult.getSelectedValue());
						searchActivity();
						EnterEmployeeStatus.setText("");
					} else {
						EnterEmployeeStatus.setText("");
						}
				} catch (OperationNotAllowedException m) {
					EnterEmployeeStatus.setText(m.getMessage());
				}
			}
		});
		btnDeleteActivity.setBounds(270, 68, 117, 29);
		panelFindActivity.add(btnDeleteActivity);
		
		
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
		panelFindActivity.add(btnBack);
		
		editActivity = new EditActivityScreen(ManagementSystem, this, parentWindow, parentparentWindow);
			}
	
	public void setVisible(boolean visible) {
		panelFindActivity.setVisible(visible);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	protected void searchActivity() {
		searchResults.clear();
		ManagementSystem.searchActivity(searchField.getText())
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
