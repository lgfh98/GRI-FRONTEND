package co.edu.uniquindio.gri.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Esta clase permite generar contraseñas encriptadas para permitir la creación
 * de cuentas directamente en la base de datos.
 */
public class PassGenerator {

	/**
	 * Método principal para la generación de contraseñas encriptadas. Debe
	 * ejecutarse como aplicación Java.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		final String password = "12345";
		System.out.println(bCryptPasswordEncoder.encode(password));
	}

}
