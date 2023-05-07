package softwarehuset.management.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import softwarehuset.management.app.Activity;
import softwarehuset.management.app.ManagementSystemApp;
import softwarehuset.management.app.OperationNotAllowedException;
import softwarehuset.management.app.Project;

public class EditActivityScreen<Employee> {
	private MainScreen parentparentparentWindow; 
	private EmployeeScreen parentparentWindow;
	private FindActivityScreen parentWindow;
	private ManagementSystemApp ManagementSystem;
	private EditActivityScreen editActivity;
	
	private JPanel panelEditActivity;
	
	private JLabel EnterErrorMessage = new JLabel("");
	
	private JTextField userInput;
	
	private JButton addEmployeeToActivity;
	private JButton removeEmployeeFromActivity;
	private JButton joinActivity;
	private JButton leaveActivity;
	private JButton addWorkedHours;
	private JButton editExpectedHours;
	private JButton editDescription;
	private JButton editStartDate;
	private JButton editEndDate;

	private JButton btnBack;
	
	private Activity activity;
	
	private int buttonPos = 28;
	
	public EditActivityScreen(ManagementSystemApp ManagementSystem, FindActivityScreen parentWindow, EmployeeScreen parentparentWindow, MainScreen parentparentparentWindow) {
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
		panelEditActivity.add(EnterErrorMessage);

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
		panelEditActivity.add(btnBack);
	}
	
	public void initPanel() {
		panelEditActivity = new JPanel();
		parentparentparentWindow.addPanel(panelEditActivity);
		panelEditActivity.setLayout(null);
		panelEditActivity.setBorder(BorderFactory.createTitledBorder("Edit activity"));
	}
	
	public void addButtonsToPanel() {
		panelEditActivity.add(userInput);
		panelEditActivity.add(addEmployeeToActivity);
		panelEditActivity.add(removeEmployeeFromActivity);
		panelEditActivity.add(joinActivity);
		panelEditActivity.add(leaveActivity);
		panelEditActivity.add(addWorkedHours);
		panelEditActivity.add(editExpectedHours);
		panelEditActivity.add(editDescription);
		panelEditActivity.add(editStartDate);
		panelEditActivity.add(editEndDate);
	}
	
	public void initButtons() {	
		userInput = addTextField();
		addEmployeeToActivity = addButton("Add emplyoee to activity");
		removeEmployeeFromActivity = addButton("Remove employee from activity");
		joinActivity = addButton("Join activity");
		leaveActivity = addButton("Leave activity");
		addWorkedHours = addButton("Add worked hours");
		editExpectedHours = addButton("Edit expected hours");
		editDescription = addButton("Edit description");
		editStartDate = addButton("Edit start date");
		editEndDate = addButton("Edit end date");
	}
	
	public void addEventListeners(){
		// Add employee to activity
		addEmployeeToActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					
					Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
					
					ManagementSystem.checkAuth(project);
					
					ManagementSystem.addEmployeeToActivity(
						ManagementSystem.getEmployeeRepository().findEmployeeByID(input),
						project,
						activity.getDescription());
					
					userInput.setText("");
					EnterErrorMessage.setText("Successfully added employee to activity");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		
		// Remove employee from activity
		removeEmployeeFromActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
					
					ManagementSystem.checkAuth(project);
					
					ManagementSystem.removeEmployeeFromActivity(
						ManagementSystem.getEmployeeRepository().findEmployeeByID(input),
						project,
						activity.getDescription());
					
					userInput.setText("");
					EnterErrorMessage.setText("Successfully removed employee to activity");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		// Join Activity
		joinActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
					String id = ManagementSystem.getLoginSystem().getCurrentLoggedID();
					
					ManagementSystem.checkAuth(project);
					
					ManagementSystem.addEmployeeToActivity(
						 ManagementSystem.getEmployeeRepository().findEmployeeByID(id),
						 project,
						 activity.getDescription()
					);
					
					
					userInput.setText("");
					EnterErrorMessage.setText("Successfully joined activity");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		// Leave Activity
		leaveActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
					String id = ManagementSystem.getLoginSystem().getCurrentLoggedID();
					
					ManagementSystem.checkAuth(project);
					
					ManagementSystem.removeEmployeeFromActivity(
						ManagementSystem.getEmployeeRepository().findEmployeeByID(id),
						project,
						activity.getDescription());
					
					
					//activity.removeEmployee(ManagementSystem.getEmployeeRepository().findEmployeeByID(id));
					
					userInput.setText("");
					EnterErrorMessage.setText("Successfully left activity");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
				}
			}
		});
		
		// Add worked hours
		addWorkedHours.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText();
				if(!isDouble(input)) {
					EnterErrorMessage.setText("Please enter a number");
					return;
				}
				
				try{
				
				ManagementSystem.addHourToActivity(activity, stringToDouble(input));
				
				
				userInput.setText("");
				EnterErrorMessage.setText("Successfully changed worked hours");
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
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
					Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
					
					ManagementSystem.checkAuth(project);
					
					activity.setExpectedHours(stringToDouble(input));
			
					userInput.setText("");
					EnterErrorMessage.setText("Successfully changed expected hours"); 
				} catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
				
			}
		});
		
		// Edit description
		editDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = userInput.getText();
					if(input.isEmpty()) {
						EnterErrorMessage.setText("Please enter new description");
						return;
					}
					Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
					
					ManagementSystem.checkAuth(project);
					
					activity.setDescrption(input);
					
					EnterErrorMessage.setText("Successfully changed description for activity"); 
				}  catch (OperationNotAllowedException p) {
					EnterErrorMessage.setText(p.getMessage());
					return;
				}
			}
		});
		
		// Edit start date
		editStartDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText();
				if(!input.matches("\\d{2}-\\d{2}-\\d{4}")) {
					EnterErrorMessage.setText("Please enter date as format \"dd-mm-yyyy\"");
					return;
				}
				
				try{
				Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
				ManagementSystem.checkAuth(project);
				String[] date = input.split("-");
				
				int dd = Integer.parseInt(date[0]);
				int mm = Integer.parseInt(date[1]) - 1 ;
				int yyyy = Integer.parseInt(date[2]);

				Calendar cal = createDate(dd,mm,yyyy);
				activity.setStartDate(cal);
				
				EnterErrorMessage.setText("Successfully changed start date");
				} catch (OperationNotAllowedException l){
					EnterErrorMessage.setText(l.getMessage());
					return;
				}
			}
		});
		
		// Edit end date
		editEndDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText();
				if(!input.matches("\\d{2}-\\d{2}-\\d{4}")) {
					EnterErrorMessage.setText("Please enter date as format \"dd-mm-yyyy\"");
					return;
				}
				try{
				Project project = ManagementSystem.getProjectRepository().findProjectByID(activity.getProjectId());
				ManagementSystem.checkAuth(project);
				String[] date = input.split("-");
				
				int dd = Integer.parseInt(date[0]);
				int mm = Integer.parseInt(date[1]) - 1;
				int yyyy = Integer.parseInt(date[2]);
				
				Calendar cal = createDate(dd,mm,yyyy);
				activity.setEndDate(cal);
				
				EnterErrorMessage.setText("Successfully changed end date");
				} catch (OperationNotAllowedException m){
					EnterErrorMessage.setText(m.getMessage());
					return;
				}
			}
		});
	}
		
	public void setVisible(boolean visible) {
		panelEditActivity.setVisible(visible);
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
	
	public void setActivity(Activity activity) {
		this.activity = activity;
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
	
	private Calendar createDate(int dd, int mm, int yyyy) {
		Calendar calendar = new GregorianCalendar();;
		calendar.set(Calendar.YEAR, yyyy);
		calendar.set(Calendar.MONTH, mm);
		calendar.set(Calendar.DAY_OF_MONTH, dd);
		return calendar;
	}
}
