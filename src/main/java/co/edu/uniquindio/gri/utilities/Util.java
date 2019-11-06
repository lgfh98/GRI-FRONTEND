package co.edu.uniquindio.gri.utilities;

public class Util {

	public static final String CANTIDAD_LINEAS_INVESTIGACION = "cantidadLineasInvestigacion";
	/**
	 * constructor de la clase Util
	 */
	public Util() {

	}

	/**
	 * Metodo que permite convertir las cadena a estilo camel, con cada primera leta
	 * en mayuscula
	 * 
	 * @param texto a convertir
	 * @return texto en camel
	 */
	public String convertToTitleCaseIteratingChars(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		StringBuilder converted = new StringBuilder();

		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}

		return converted.toString();
	}
	
	
	

}
