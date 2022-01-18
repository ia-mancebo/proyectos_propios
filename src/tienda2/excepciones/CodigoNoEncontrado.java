package excepciones;

public class CodigoNoEncontrado extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9006937854486327576L;
	public CodigoNoEncontrado(String str) {
		super(str);
	}
	public CodigoNoEncontrado() {
		super();
	}
}
