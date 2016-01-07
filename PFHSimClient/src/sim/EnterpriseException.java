package sim;

public class EnterpriseException extends Exception {
	private static final long serialVersionUID = -6542347752789060877L;
	
	private ExceptionCategorie cat;
	private Object src;

	public EnterpriseException(Object src, String string, ExceptionCategorie cat) {
		super(string);
		this.cat = cat;
		this.src = src;
	}
	
	public ExceptionCategorie getCategorie() {
		return cat;
	}
	
	public Object getSource() {
		return src;
	}

}
