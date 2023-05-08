package softwarehuset.management.app;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

// [MALTHE]
public class EmployeeRepository {
	private List<Employee> employeeRepository; // List containing the employees of the system
	
	public EmployeeRepository() {
		employeeRepository = new ArrayList<>();
	}
	
	public List<Employee> getEmployeeRepository() {
		return employeeRepository;
	}
	
	// Add an employee to repository
	public void addEmployee(Employee employee) throws OperationNotAllowedException {
		
		//Check if ID alreadt exists
		if(checkIfEmployeeExists(employee.getId())) {
			throw new OperationNotAllowedException("Employee ID already taken");
		} 
		
		//Check if ID is too long
		if(employee.getId().length() > 4 || employee.getId().length() < 1){
			throw new OperationNotAllowedException("Employee ID is too long");
		}
		
		//Check if ID has spaces in it:
		if(employee.getId().contains(" ")){
			throw new OperationNotAllowedException("Employee ID can't contain whitespaces");
		}
		
		//Check if special characters are used:
		if(!employee.getId().matches("[a-zæøåA-ZÆØÅ]+")){
			throw new OperationNotAllowedException("Employee ID can't contain special characters");
		}
		
		employeeRepository.add(employee);
	}
	
	// Remove an employee from repository
	public void removeEmployee(Employee employee) throws OperationNotAllowedException {
		if(!checkIfEmployeeExists(employee.getId())) {
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
		employeeRepository.remove(employee);
	}
	
	// boolean return if employee exists in employee repository
	public boolean checkIfEmployeeExists(String id) {
		return employeeRepository.stream().anyMatch(e -> e.getId().equals(id));
	}
	
	// Finds an employee based on their employee id
	public Employee findEmployeeByID(String id) throws OperationNotAllowedException {
		Employee employee = employeeRepository.stream().filter(u -> u.getId().equals(id)).findAny().orElse(null);
		
		if(employee == null){
			throw new OperationNotAllowedException("Employee ID does not exist");
		}
		
		return employee;
	}
	
	// Search for all employees in system by key word
	public List<Employee> searchEmployee(String searchText) {
		return employeeRepository.stream()
				.filter(b -> b.match(searchText))
				.collect(toList());
	}
}