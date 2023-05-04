package softwarehuset.management.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import softwarehuset.management.app.Activity;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;

public class EditProjectScreen {
	private MainScreen parentparentparentWindow; 
	private EmployeeScreen parentparentWindow;
	private FindProjectScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private EditProjectScreen editActivity;
	
	private JPanel panelEditProject;
	
	private JLabel EnterErrorMessage = new JLabel("");
	
	private JTextField userInput;
	
	private JButton addEmployeeToProject;
	private JButton removeEmployeeFromActivity;
	private JButton joinProject;
	private JButton leaveProject;
	private JButton setProjectToActiv;
	private JButton editExpectedHours;
	private JButton editNameOfProject;
	private JButton editStartDate;
	private JButton editEndDate;
	private JButton toggleOngoing;

	private JButton btnBack;
	
	private Project project;
	
	private int buttonPos = 28;
	
	public EditProjectScreen(ManagementSystemApp ManagementSystem, FindProjectScreen parentWindow, EmployeeScreen parentparentWindow, MainScreen parentparentparentWindow) {
		this.ManagementSystem = ManagementSystem;
		this.parentWindow = parentWindow;
		this.parentparentWindow = parentparentWindow;
		this.parentparentparentWindow = parentparentparentWindow;

		initPanel();
		initButtons();
		addEventListeners();
		addButtonsToPanel();
		finalInit();
	}
	
	public void finalInit(){
		//Error Message
		EnterErrorMessage.setBounds(110, 10, 300, 16);
		panelEditProject.add(EnterErrorMessage);

		//Back Button
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userInput.setText("");
				EnterErrorMessage.setText("");
				setVisible(false);
				parentWindow.setVisible(true);
			}
		});
		btnBack.setBounds(21, 28, 59, 29);
		panelEditProject.add(btnBack);
	}
	
	public void initPanel() {
		panelEditProject = new JPanel();
		parentparentparentWindow.addPanel(panelEditProject);
		panelEditProject.setLayout(null);
		panelEditProject.setBorder(BorderFactory.createTitledBorder("Edit project"));
	}
	
	public void addButtonsToPanel() {
		panelEditProject.add(toggleOngoing);
		panelEditProject.add(userInput);
		panelEditProject.add(addEmployeeToProject);
		panelEditProject.add(removeEmployeeFromActivity);
		panelEditProject.add(joinProject);
		panelEditProject.add(leaveProject);
		//panelEditProject.add(setProjectToActiv);
		panelEditProject.add(editExpectedHours);
		panelEditProject.add(editNameOfProject);
		panelEditProject.add(editStartDate);
		panelEditProject.add(editEndDate);

	}
	
	public void initButtons() {	
		userInput = addTextField();
		toggleOngoing = addButton("Toggle project status");
		addEmployeeToProject = addButton("Add emplyoee to project");
		removeEmployeeFromActivity = addButton("Remove employee from project");
		joinProject = addButton("Join project");
		leaveProject = addButton("Leave project");
		//setProjectToActiv = addButton("toggle ongoing");
		editExpectedHours = addButton("Edit expected hours");
		editNameOfProject = addButton("Edit project name");
		editStartDate = addButton("Edit start date");
		editEndDate = addButton("Edit end date");
	}
	
	public void addEventListeners(){
		// Toggle project status - change project "onGoing" parameter
		toggleOngoing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText();
				try {
					ManagementSystem.toggleProjectOngoing(project);
					userInput.setText("");
					
					String status = project.getOngoingProject() ? "Active" : "Inactive";
					EnterErrorMessage.setText("Project status: " + status);
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});
		
		// Add employee to project
		addEmployeeToProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					ManagementSystem.addEmployeeToProject(project.getProjectID(), input);
					userInput.setText("");
					EnterErrorMessage.setText("Successfully added employee to project");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		 
		// Remove employee from project
		removeEmployeeFromActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					ManagementSystem.removeEmployeeWithIdFromProject(project.getProjectID(), input);
					userInput.setText("");
					EnterErrorMessage.setText("Successfully removed employee to project");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		// Join Project
		joinProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ManagementSystem.addEmployeeToProject(
						project.getProjectID(), 
						ManagementSystem.currentEmployee().getId());
					userInput.setText("");
					EnterErrorMessage.setText("Successfully joined project");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		// Leave project
		leaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ManagementSystem.removeEmployeeWithIdFromProject(
						project.getProjectID(),
						ManagementSystem.currentEmployee().getId());
					userInput.setText("");
					EnterErrorMessage.setText("Successfully left project");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		// Edit expected hours
		editExpectedHours.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText();
				if(!isDouble(input)) {
					EnterErrorMessage.setText("Please enter a number");
					return;
				}
				try {
					ManagementSystem.UpdateExpectedHours(project.getProjectID(), stringToDouble(input));
					userInput.setText("");
					EnterErrorMessage.setText("Successfully changed expected hours"); 
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});
		
		// Edit name of project
		editNameOfProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					if(input.isEmpty()) {
						EnterErrorMessage.setText("Please enter new project name");
						return;
					}
					ManagementSystem.editProjectName(project.getProjectID(), input);
					EnterErrorMessage.setText("Successfully changed project name"); 
				}  catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});
		
		// Edit start date
		editStartDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					if(!input.matches("\\d{2}-\\d{2}-\\d{4}")) {
						EnterErrorMessage.setText("Please enter date as format \"dd-mm-yyyy\"");
						return;
					}
					String[] date = input.split("-");
					
					int dd = Integer.parseInt(date[0]);
					int mm = Integer.parseInt(date[1]) - 1;
					int yyyy = Integer.parseInt(date[2]);
					
					ManagementSystem.UpdateStartDateProject(dd, mm, yyyy,project.getProjectID());
					
					EnterErrorMessage.setText("Successfully changed start date for project"); 
				}  catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});
		
		// Edit end date
		editEndDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					if(!input.matches("\\d{2}-\\d{2}-\\d{4}")) {
						EnterErrorMessage.setText("Please enter date as format \"dd-mm-yyyy\"");
						return;
					}
					String[] date = input.split("-");
					
					int dd = Integer.parseInt(date[0]);
					int mm = Integer.parseInt(date[1]) - 1;
					int yyyy = Integer.parseInt(date[2]);
					
					ManagementSystem.UpdateEndDateProject(dd, mm, yyyy,project.getProjectID());
					
					EnterErrorMessage.setText("Successfully changed end date for project"); 
				}  catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});
		
		
		
/*		// View hours report (USE LATER)
		editStartDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					ManagementSystem.getStatus(activity);
					
					EnterErrorMessage.setText("Successfully changed description for activity"); 
				}  catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});*/
	}
		
	public void setVisible(boolean visible) {
		panelEditProject.setVisible(visible);
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
	}
	
	private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	private double stringToDouble(String str) {
		try {
			double v = Double.parseDouble(str);
			return v - v % 0.5;
		} catch (Exception e) {
			return 0;
		}
	}
}
