package co.edu.uniquindio.gri.exception;

/**
 * Excepción personalizada para indicar que se va a crear un caos para una producción que ya tiene asociada un caso
 * 
 * @author Jhon Sebastian Montes R
 *
 */
public class CasoBonitaDuplicado extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Método constructor
	 * @param msg
	 */
	public CasoBonitaDuplicado(String msg) {
		super(msg);
	}

}