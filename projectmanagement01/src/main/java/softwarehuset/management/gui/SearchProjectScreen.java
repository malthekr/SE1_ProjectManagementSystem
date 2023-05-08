package softwarehuset.management.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import softwarehuset.management.app.Project;

public class SearchProjectScreen {

	private MainScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private JPanel panelsearchProject;
	private JTextField searchField;
	private JList<Project> listSearchResult;
	private DefaultListModel<Project> searchResults;
	private JLabel lblSearchResultDetail;
	private JButton btnBack;
	
	public SearchProjectScreen(ManagementSystemApp ManagementSystem, MainScreen parentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentWindow = parentWindow;
		initialize();
	}
	
	public void initialize() {
		panelsearchProject = new JPanel();
		parentWindow.addPanel(panelsearchProject);
		panelsearchProject.setLayout(null);
		panelsearchProject.setBorder(BorderFactory.createTitledBorder(
                "Search Project"));
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProject();
			}
		});
		searchField.setBounds(138, 28, 130, 26);
		panelsearchProject.add(searchField);
		searchField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProject();
			}
		});
		btnSearch.setBounds(148, 68, 117, 29);
		panelsearchProject.add(btnSearch);
		btnSearch.getRootPane().setDefaultButton(btnSearch);
		
		searchResults = new DefaultListModel<>();
		listSearchResult = new JList<Project>(searchResults);
		listSearchResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSearchResult.setSelectedIndex(0);
		listSearchResult.addListSelectionListener(new ListSelectionListener() {
		
			//@Override
			public void valueChanged(ListSelectionEvent e) {
		        if (e.getValueIsAdjusting() == false) {

		            if (listSearchResult.getSelectedIndex() == -1) {
		            	lblSearchResultDetail.setText("");

		            } else {
		            	lblSearchResultDetail.setText(ManagementSystem.getPrintDetails().projectDetails(listSearchResult.getSelectedValue()));
		            }
		        }
			}
		});
		listSearchResult.setVisibleRowCount(6);
        JScrollPane listScrollPane = new JScrollPane(listSearchResult);
		
        listScrollPane.setBounds(21, 109, 361, 149);
		panelsearchProject.add(listScrollPane);
		
		JPanel panelSearchResult = new JPanel();
		panelSearchResult.setBounds(21, 270, 361, 175);
		panelsearchProject.add(panelSearchResult);
		panelSearchResult.setBorder(BorderFactory.createTitledBorder(
                "Detail")); 
		panelSearchResult.setLayout(null);
		
		lblSearchResultDetail = new JLabel("");
		lblSearchResultDetail.setVerticalAlignment(SwingConstants.TOP);
		lblSearchResultDetail.setHorizontalAlignment(SwingConstants.LEFT);
		lblSearchResultDetail.setBounds(23, 19, 318, 150);
		panelSearchResult.add(lblSearchResultDetail);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				clear();
				parentWindow.setVisible(true);
			}
		});
		btnBack.setBounds(21, 28, 59, 29);
		panelsearchProject.add(btnBack);


	}
	
	protected void searchProject() {
		searchResults.clear();
		ManagementSystem.searchProject(searchField.getText())
			.forEach((m) -> {searchResults.addElement(m);});
	}
	
	public void setVisible(boolean aFlag) {
		panelsearchProject.setVisible(aFlag);
	}
	
	public void clear() {
		searchField.setText("");
		searchResults.clear();
	}
}
