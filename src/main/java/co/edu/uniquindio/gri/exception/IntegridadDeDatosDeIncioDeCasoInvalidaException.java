package co.edu.uniquindio.gri.exception;

public class IntegridadDeDatosDeIncioDeCasoInvalidaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntegridadDeDatosDeIncioDeCasoInvalidaException() {
		super("La integridad de los parámetros para la generación del caso bonita es inadecuada, verifique que alguno de los parámetros no contenga un valor inválido o sea nulo");
	}

}
