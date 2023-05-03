package softwarehuset.management.gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import softwarehuset.management.app.ManagementSystemApp;

public class MainScreen {
	
	ManagementSystemApp ManagementSystem;
	SearchProjectScreen searchProjectScreen;
	AdministratorScreen administratorFunctionsScreen;
	EmployeeScreen employeeFunctionScreen;
	CreateProject CreateProject;
	RegisterEmployeeScreen RegisterEmployeeScreen;
	CreateActivityScreen CreateActivityScreen;
	EditActivityScreen editActivity;
	FindProjectScreen findProject;
	
	private JFrame frame;
	private JPanel panelMenu;
	private JButton btnAdministratorFunctions;
	private JButton btnBorrowMedia;
	//private JButton btnRetunMedia;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen screen = new MainScreen();
					screen.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public MainScreen() throws Exception {
		ManagementSystem = new ManagementSystemApp();
		ManagementSystem.exampleDate();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 404, 486);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		panelMenu = new JPanel();
		//name_160236068959176
		frame.getContentPane().add(panelMenu, "name_69696969696969");
		panelMenu.setLayout(null);
		panelMenu.setBorder(BorderFactory.createTitledBorder(
                "Main Menu"));
		
		JButton btnSearchProject = new JButton("Search For Project");
		btnSearchProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				searchProjectScreen.setVisible(true);
				
			}
		});
		btnSearchProject.setBounds(104, 52, 193, 29);
		panelMenu.add(btnSearchProject);
		
		btnAdministratorFunctions = new JButton("Administrator Functions");
		btnAdministratorFunctions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				administratorFunctionsScreen.setVisible(true);
			}
		});
		btnAdministratorFunctions.setBounds(104, 93, 193, 29);
		panelMenu.add(btnAdministratorFunctions);
		
		btnBorrowMedia = new JButton("Employee Functions");
		btnBorrowMedia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				employeeFunctionScreen.setVisible(true);
			}
		});
		btnBorrowMedia.setBounds(104, 134, 193, 29);
		panelMenu.add(btnBorrowMedia);
	
		
		administratorFunctionsScreen = new AdministratorScreen(ManagementSystem, this, CreateProject, RegisterEmployeeScreen);
		employeeFunctionScreen = new EmployeeScreen(ManagementSystem, this, CreateActivityScreen, editActivity, findProject);
		searchProjectScreen = new SearchProjectScreen(ManagementSystem, this);
	}
	
	public void setVisible(boolean aFlag) {
		panelMenu.setVisible(aFlag);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void addPanel(JPanel panel) {
		frame.getContentPane().add(panel);
	}
}
