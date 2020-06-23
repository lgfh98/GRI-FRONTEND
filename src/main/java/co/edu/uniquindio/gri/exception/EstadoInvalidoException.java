package co.edu.uniquindio.gri.exception;

/**
 * Excepción personalizada para indicar un posible estado incorrecto que no es
 * 0, 1 o 2 según comos e define en Util
 * 
 * @author Jhon Sebastian Montes R
 *
 */
public class EstadoInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Método constructor
	 * @param msg
	 */
	public EstadoInvalidoException(String msg) {
		super(msg);
	}

}
