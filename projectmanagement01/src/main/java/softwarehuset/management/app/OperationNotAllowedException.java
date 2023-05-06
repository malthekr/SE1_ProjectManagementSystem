package softwarehuset.management.app;

public class OperationNotAllowedException extends Exception {
    
	private static final long serialVersionUID = -1456886084929874194L;
	
	// Throws OperationNotAllowedException with an error message
	public OperationNotAllowedException(String errorMessage) {
		super(errorMessage);
	}
}
