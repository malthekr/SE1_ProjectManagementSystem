package softwarehuset.management.app;

public class OperationNotAllowedException extends Exception {
    
	private static final long serialVersionUID = -1456886084929874194L;

	public OperationNotAllowedException(String errorMessage) {
		super(errorMessage);
	}
}
