package co.edu.uniquindio.gri.utilities;

public class GRIConstantes {
	
	
	public GRIConstantes() {
		super();
	}
	
	/**
	 * Codigos de respuesta
	 */
	public static final Integer CODIGO_RESPUESTA_EXITOSO=200;
	public static final Integer CODIGO_RESPUESTA_ERROR=400;
	
	/**
	 * Mensajes de respuesta
	 */

	public static final String RESPUESTA_CREAR_USUARIO_CORRECTO="El usuario se ha creado exitosamente";
	public static final String RESPUESTA_CREAR_USUARIO_ERROR_YA_EXISTE="El usuario ingresado ya se encuentra registrado";
	public static final String RESPUESTA_MODIFICAR_USUARIO_ERROR_NO_EXISTE="El usuario seleccionado no se encuentra registrado";
	public static final String RESPUESTA_MODIFICAR_USUARIO_CORRECTO="El usuario se ha modificado exitosamente";

}
