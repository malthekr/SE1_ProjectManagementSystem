package softwarehuset.management.app;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

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
		if(checkIfEmployeeExists(employee.getId())) {
			throw new OperationNotAllowedException("Employee ID already exists");
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
			throw new OperationNotAllowedException("Employee does not exist");
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