package excepciones;

public class NoHayUnidadesException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3910658825239679474L;

	public NoHayUnidadesException(String str) {
		super(str);
	}
	public NoHayUnidadesException() {
		super();
	}
}
