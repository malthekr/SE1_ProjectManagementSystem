package softwarehuset.management.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectRepository{
	private List<Project> projectRepository;
	
	public ProjectRepository() {
		projectRepository = new ArrayList<>();
	}
	
	public List<Project> getProjectRepository() {
		return projectRepository;
	}
	
	public void addProject(Project project) throws OperationNotAllowedException {
		if(checkIfProjectExists(project)){
			throw new OperationNotAllowedException("Project already exists");
		}
		projectRepository.add(project);
	}
	
	public void removeProject(Project project) throws OperationNotAllowedException {
		if(!checkIfProjectExists(project)){
			throw new OperationNotAllowedException("Project doesn't exists");
		}
		projectRepository.remove(project);
	}
	
	public Project findProjectByID(int id) throws OperationNotAllowedException {
		Project project = projectRepository.stream().filter(u -> u.getProjectID() == id).findAny().orElse(null);
		
		if(project == null){
			throw new OperationNotAllowedException("Project ID does not exist");
		}
		
		return project;
	}
	
	// boolean return if project exists in project repository
	public boolean checkIfProjectExists(Project project) {
		return projectRepository.stream().filter(u -> u.getProjectID() == project.getProjectID()).findAny().orElse(null) != null ? true : false ;
	}
	
	
	// Search for all projects in project repository by key word
	public List<Project> searchProject(String searchText) {
		return projectRepository.stream()
				.filter(b -> b.match(searchText))
				.collect(Collectors.toList());
	}
}