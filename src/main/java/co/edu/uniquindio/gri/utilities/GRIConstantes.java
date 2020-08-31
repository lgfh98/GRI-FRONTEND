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
	public static final String RESPUESTA_CREAR_FACULTAD_CORRECTO="La Facultad se ha registrado con éxito";
	public static final String RESPUESTA_CREAR_FACULTAD_ERROR_YA_EXISTE="El nombre de la facultad ya se encuentra registrado";
	public static final String RESPUESTA_MODIFICAR_FACULTAD_ERROR_NO_EXISTE="La facultad que intenta modificar ya no existe";
	public static final String RESPUESTA_MODIFICAR_FACULTAD_CORRECTO="La Facultad se ha modificado exitosamente";
	public static final String RESPUESTA_CREAR_CENTRO_CORRECTO="El centro se ha registrado con éxito";
	public static final String RESPUESTA_MODIFICAR_CENTRO_CORRECTO="El centro se ha modificado exitosamente";
	public static final String RESPUESTA_CREAR_PROGRAMA_CORRECTO="El programa se ha registrado con éxito";
	public static final String RESPUESTA_MODIFICAR_PROGRAMA_CORRECTO="El programa se ha modificado exitosamente";

}
