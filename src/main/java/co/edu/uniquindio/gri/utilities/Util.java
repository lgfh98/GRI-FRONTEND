package co.edu.uniquindio.gri.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.dao.PertenenciaDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.Pertenencia;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

@Service
public class Util {

	@Autowired
	PertenenciaDAO pertenenciaDAO;

	@Autowired
	ProduccionDAO produccionDAO;

	public static final String PERTENENCIA_INDEFINIDO = "INDEFINIDO";
	public static final String PERTENENCIA_DOCENTE_PLANTA = "DOCENTE PLANTA";
	public static final String PERTENENCIA_DOCENTE_CATEDRATICO = "DOCENTE CATEDRÁTICO";
	public static final String PERTENENCIA_DOCENTE_OCASIONAL = "DOCENTE OCASIONAL";
	public static final String PERTENENCIA_ADMINISTRATIVO = "ADMINISTRATIVO";
	public static final String PERTENENCIA_EXTERNO = "INVESTIGADOR EXTERNO";
	public static final String PERTENENCIA_ESTUDIANTE = "ESTUDIANTE INVESTIGADOR";
	public static final String GENERO_MASCULINO = "MASCULINO";
	public static final String GENERO_FEMENINO = "FEMENINO";
	public static final String GENERO_INDEFINIDO = "NO ESPECIFICADO";

	public static final String CANTIDAD_FACULTADES = "cantFacultades";
	public static final String CANTIDAD_CENTROS = "cantCentros";
	public static final String CANTIDAD_PROGRAMAS = "cantProgramas";
	public static final String CANTIDAD_GRUPOS = "cantGrupos";
	public static final String CANTIDAD_INVESTIGADORES = "cantInves";
	public static final String ESTATICA_ESTADISTICAS = "estadisticas";

	public static final int CANTIDAD_FACULTADES_ID = 0;
	public static final int CANTIDAD_CENTROS_ID = 1;
	public static final int CANTIDAD_PROGRAMAS_ID = 2;
	public static final int CANTIDAD_GRUPOS_ID = 3;
	public static final int CANTIDAD_INVESTIGADORES_ID = 4;

	public static final String PARAM_TYPE = "type";
	public static final String PARAM_SUBTYPE = "subType";
	public static final String PARAM_ID = "id";

	public static final String UNIVERSITY_PARAM_ID = "u";
	public static final String FACULTY_PARAM_ID = "f";
	public static final String CENTER_PARAM_ID = "c";
	public static final String PROGRAM_PARAM_ID = "p";
	public static final String GROUP_PARAM_ID = "g";
	public static final String RESEARCHER_PARAM_ID = "i";

	public static final String UNDERGRADUATE_PROGRAM_PARAM_ID = "pa";

	public static final String PARAM_UNIVERSITY_LEVEL_ID = "0";

	public static final String PARAM_UNIVERSITY_LEVEL = "idUniquindio";

	public static final String ADMIN_BELOGNING_PARAM_ID = "adm";
	
	public static final int PRODUCCION_EN_CUSTODIA = 1;
	public static final int PRODUCCION_SIN_CUSTODIA = 0;
	public static final int PRODUCCION_EN_PROCESO = 2;
	
	public static final String BONITA_CASO_FINALIZADO = "FINALIZADO";
	public static final String BONITA_CASO_EN_CURSO = "EN CURSO";
	
	public static final String PRODUCCION_BIBLIOGRAFICA = "bibliografica";
	public static final String PRODUCCION_GENERICA = "generica";

	/**
	 * constructor de la clase Util
	 */
	public Util() {

	}

	/**
	 * valida si un objeto es nulo, sino es nulo retorna el texto
	 * 
	 * @param text
	 * @return
	 */
	public String validateStringOrNull(Object text) {

		if (text == null) {
			return "";
		}

		return text.toString();

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

	/**
	 * permite agregarle la pertenencia a los investigadors
	 * 
	 * @param investigadores
	 * @return
	 */
	public List<Investigador> agregarPertenenciaInves(List<Investigador> investigadores) {

		for (Investigador investigador : investigadores) {
			Pertenencia pertenecia_investigador = pertenenciaDAO.getPertenenciaByIdInves(investigador.getId());

			if (pertenecia_investigador != null) {

				if (pertenecia_investigador.getPertenencia().contains("CATED")) {

					investigador.setPertenencia("DOCENTE CATEDRÁTICO");

				} else {
					investigador.setPertenencia(pertenecia_investigador.getPertenencia());
				}
			} else {

				investigador.setPertenencia("INDEFINIDO");

			}

		}

		return investigadores;

	}

	/**
	 * Metodo que permite extraer de una lista los investigadores dependiendo de la
	 * pertenencia que se necesite
	 * 
	 * @param investigadores lista de investigadores
	 * @param pertenencia    tipo de pertenencia que se requiere
	 * @return una lista con los investigadores de dicha pertenencia
	 */
	public List<Investigador> seleccionarInvestigadoresPertenencia(List<Investigador> investigadores,
			String pertenencia) {

		List<Investigador> investigadores_resultantes = new ArrayList<Investigador>();

		for (Investigador investigador : investigadores) {

			if (investigador.getPertenencia().equals(pertenencia))
				investigadores_resultantes.add(investigador);

		}

		return investigadores_resultantes;

	}

	/**
	 * retorna un arreglo con la cantidad de generos de la lista recibida
	 * 
	 * @param investigadores
	 * @return tres numeros, masculino, femenino, indefinido
	 */
	public int[] obtenerCantidadGenerosInvesgitadores(List<Investigador> investigadores) {

		int[] resutado = new int[] { 0, 0, 0 };

		if (investigadores.size() == 0)
			return resutado;

		for (Investigador investigador : investigadores) {
			if (investigador.getSexo().equals(GENERO_MASCULINO)) {

				resutado[0] += 1;

			} else if (investigador.getSexo().equals(GENERO_FEMENINO)) {
				resutado[1] += 1;

			} else if (investigador.getSexo().equals(GENERO_INDEFINIDO)) {
				resutado[2] += 1;

			}
		}

		return resutado;
	}

	/**
	 * Método que retorna los casos de revisión segun las listas de producciones
	 * ingresadas
	 * 
	 * @param casos         los casos de produccion
	 * @param produccionesb las producciones
	 * @return lista de casos filtrados por las producciones
	 */
	public List<CasoRevisionProduccion> obtenerCasosPorListas(List<CasoRevisionProduccion> casos,
			List<ProduccionBGrupo> produccionesb, List<ProduccionGrupo> producciones) {
		ArrayList<Long> idsp = new ArrayList<Long>();
		ArrayList<Long> idspb = new ArrayList<Long>();
		ArrayList<CasoRevisionProduccion> casos_resultante = new ArrayList<CasoRevisionProduccion>();
		
		for (ProduccionGrupo produccion : producciones) {
			idsp.add(produccion.getId());
		}

		for (ProduccionBGrupo produccion : produccionesb) {
			idspb.add(produccion.getId());
		}

		for (CasoRevisionProduccion caso : casos) {
			if (idsp.contains(caso.getIdProduccion())) {
				casos_resultante.add(caso);
			} else if (idspb.contains(caso.getIdProduccion())) {
				casos_resultante.add(caso);
			}
		}
		return casos_resultante;
	}

	/**
	 * Método que retorna los nombres de casos de revisión segun las listas de
	 * producciones ingresadas
	 * 
	 * @param casos         los casos de produccion
	 * @param produccionesb las producciones bibliograficas
	 * @param producciones  las producciones genericas
	 * @return lista de casos filtrados por las producciones
	 */
	public List<CasoRevisionProduccion> obtenerNombresNumerosCasosPorListas(List<CasoRevisionProduccion> casos,
			List<ProduccionBGrupo> produccionesb, List<ProduccionGrupo> producciones, List<Integer> indices,
			List<String> nombres) {
		
		int index = 0;
		ArrayList<Long> idsp = new ArrayList<Long>();
		ArrayList<Long> idspb = new ArrayList<Long>();
		ArrayList<CasoRevisionProduccion> casos_resultante = new ArrayList<CasoRevisionProduccion>();
		
		for (ProduccionGrupo produccion : producciones) {
			idsp.add(produccion.getId());
		}

		for (ProduccionBGrupo produccion : produccionesb) {
			idspb.add(produccion.getId());
		}

		for (CasoRevisionProduccion caso : casos) {
			caso.setTipoProduccion(caso.getTipoProduccion().toUpperCase());
			if (idsp.contains(caso.getIdProduccion())) {
				casos_resultante.add(caso);
				indices.add(index);
				nombres.add(produccionDAO.findGenericasById(caso.getIdProduccion()).getReferencia());
				index++;
			} else if (idspb.contains(caso.getIdProduccion())) {
				casos_resultante.add(caso);
				indices.add(index);
				nombres.add(produccionDAO.findBibliograficasById(caso.getIdProduccion()).getReferencia());
				index++;
			}
		}
		return casos_resultante;
	}
	
	/**
	 * Obtiene las producciones bibliograficas a partir de codigo
	 * @param type el tipo a buscar (grupo, facultad, etc.)
	 * @param entityId el id de la entidad especificada en el tipo
	 * @return lista de producciones bibliograficas
	 */
	public List<ProduccionBGrupo> obtenerBibliograficas(String type, Long entityId){
		List<ProduccionBGrupo> produccionesb = produccionDAO.getProducciones(type, entityId, (long) 39);
		produccionesb.addAll(produccionDAO.getProducciones(type, entityId, (long) 40));
		
		for (int i = 15; i <= 23; i++) {
			produccionesb.addAll(produccionDAO.getProducciones(type, entityId, (long) i));
		}
		
		return produccionesb;
	}
	
	/**
	 * Obtiene las producciones genericas a partir de codigo
	 * @param type el tipo a buscar (grupo, facultad, etc.)
	 * @param entityId el id de la entidad especificada en el tipo
	 * @return lista de producciones genericas
	 */
	public List<ProduccionGrupo> obtenerGenericas(String type, Long entityId){
		List<ProduccionGrupo> producciones = produccionDAO.getProducciones(type, entityId, (long) 0);
		
		for (int i = 1; i <= 14; i++) {
			producciones.addAll(produccionDAO.getProducciones(type, entityId, (long) i));
		}
		
		for (int i = 24; i <= 38; i++) {
			producciones.addAll(produccionDAO.getProducciones(type, entityId, (long) i));
		}
		
		for (int i = 41; i <= 53; i++) {
			producciones.addAll(produccionDAO.getProducciones(type, entityId, (long) i));
		}
		
		return producciones;
	}

}
