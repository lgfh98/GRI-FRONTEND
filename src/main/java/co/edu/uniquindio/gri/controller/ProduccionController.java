package co.edu.uniquindio.gri.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.utilities.Util;
import co.edu.uniquindio.gri.bonitaapi.GestorDeCasosBonita;
import co.edu.uniquindio.gri.dao.CasoRevisionProduccionDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.exception.EstadoInvalidoException;
import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

/**
 * Clase ProduccionController.
 */
@RestController
@RequestMapping("/rest/service")
public class ProduccionController {

	/** DAO para producciones. */

	@Autowired
	ProduccionDAO produccionDAO;

	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

	@Autowired
	GestorDeCasosBonita gestorDeCasosBonita;

	private static final Logger log = LoggerFactory.getLogger(ProduccionController.class);

	/**
	 * Obtiene las producciones de una entidad específica.
	 *
	 * @param type     el tipo de la entidad (f: Facultad, p: Programa, c: Centro,
	 *                 g: Grupo de Investigación i: Investigador)
	 * @param entityId el id de la entidad
	 * @param tipoId   el tipo de la producción a obtener
	 * @return lista de producciones
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/producciones/{type}/{id}/{tipo}")
	public List getProducciones(@PathVariable("type") String type, @PathVariable("id") Long entityId,
			@PathVariable("tipo") Long tipoId) {
		return produccionDAO.getProducciones(type, entityId, tipoId);
	}

	/**
	 * Obtiene las producciones de acuerdo a una cadena de búsqueda.
	 *
	 * @param tipo,  el tipo de búsqueda a realizar (i: CvLAC, g: GrupLAC)
	 * @param cadena la cadena de búsqueda
	 * @return lista de producciones correspondientes con la cadena de búsqueda.
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/busqueda/{type}/{cadena}")
	public List getProduccionBBusqueda(@PathVariable("type") String tipo, @PathVariable("cadena") String cadena) {
		String busqueda = cadena.replaceAll("\\+", " ").toUpperCase();
		return produccionDAO.getProduccionBusqueda(tipo, busqueda);
	}

	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o
	 * no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param tipo,   identifica si la producción es o no bibliográfica
	 * @param estado, el estado de la producción. 0 si no se encuentra en custodia.
	 *                1 en caso contrario.
	 * @param prodId, el identificador de la producción en base de datos.
	 * @return true, si la actualización se realizó satisfactoriamente.
	 * @deprecated use {@link #actualizarInfoProduccion()} instead.
	 */
	@Deprecated
	@PostMapping("/producciones/{tipo}/{estado}/{prodId}")
	public boolean updateInfoProduccion(@PathVariable("tipo") String tipo, @PathVariable("estado") int estado,
			@PathVariable("prodId") Long prodId) {
		return produccionDAO.actualizarProducciones(tipo, estado, prodId);
	}

	/**
	 * API de uso exclusivo interno en GRI
	 * 
	 * Actualiza el estado de una producción científica al estado enviado por
	 * parámetro en función si se encuentra o no en custodia física de la
	 * Vicerrectoría de Investigaciones. Esta API también da la orden de inicio de
	 * casos de recolección de evidencias (Subida y revisión de evidencias) con los
	 * datos de la producción que fue dada por parámetro, gestiona el cambio de los
	 * estados y la integridad de la tabla de registros de casos bonita del lado del
	 * GRI mediante un autómata programado con condiciones.
	 *
	 * @param tipo,   identifica si la producción es o no bibliográfica
	 * @param estado, el estado de la producción. 0 si no se encuentra en custodia.
	 *                1 en caso contrario.
	 * @param prodId, el identificador de la producción en base de datos.
	 * @return true, si la actualización se realizó satisfactoriamente.
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis del identificador unico de recursos
	 *                                 (URI)
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws EstadoInvalidoException En caso de que el estado dado por parámetro
	 *                                 sea inválido
	 */
	@PutMapping("/producciones/actualizarestado/{prodId}")
	public String actualizarEstadoProduccion(@PathVariable("prodId") Long prodId,
			@RequestParam("estado") int nuevoEstado, @RequestParam("tipo") String tipo)
			throws ClientProtocolException, URISyntaxException, IOException, EstadoInvalidoException {

		ProduccionGrupo produccionGrupo = null;
		ProduccionBGrupo produccionBGrupo = null;

		// Identificación del estado actual de la producción con base en el tipo y el ID
		int estadoAnterior = 0;
		if (tipo.equals(Util.PRODUCCION_GENERICA)) {
			
			produccionGrupo = produccionDAO.getProduccion(prodId);
			estadoAnterior = produccionGrupo.getEstado();
			
		} else if (tipo.equals(Util.PRODUCCION_BIBLIOGRAFICA)) {
			
			produccionBGrupo = produccionDAO.getProduccionB(prodId);
			estadoAnterior = produccionBGrupo.getEstado();
			
		}

		// Autómata de estados
		if (estadoAnterior == Util.PRODUCCION_SIN_CUSTODIA) {

			if (nuevoEstado == Util.PRODUCCION_EN_CUSTODIA) {

				log.info("Tomando en custodia la producción con id: " + prodId);
				return produccionDAO.actualizarEstadoDeProduccion(prodId, tipo, nuevoEstado) + "";

			} else if (nuevoEstado == Util.PRODUCCION_EN_PROCESO) {
				
				// Se genera un nuevo caso en caso de seleccionar el estado a "EN PROCESO"
				return gestorDeCasosBonita.generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(prodId,
						produccionBGrupo, produccionGrupo) + "";

			} else {
				
				throw new EstadoInvalidoException("Nuevo estado inválido");
				
			}

		} else if (estadoAnterior == Util.PRODUCCION_EN_CUSTODIA) {

			// Verificar que no exista en la base de datos un caso asociado a la producción
			// a la cual se sacará de custodia, si existe, este registro del caso en GRI se
			// eliminará

			if (casoRevisionProduccionDAO.eliminarCaso(prodId, tipo)) {
				
				log.warn("Ya existe un registro de proceso previo para la recolección de la producción " + prodId
						+ " de tipo " + tipo + " este registro se eliminará");
				
			} else {
				
				log.info("No existen procesos previos finalziados y registrados en GRI para la recolección de la producción " + prodId
						+ " de tipo " + tipo);
				
			}

			if (nuevoEstado == Util.PRODUCCION_SIN_CUSTODIA) {

				log.info("Eliminando del inventario de custodia la producción con id: " + prodId);
				return produccionDAO.actualizarEstadoDeProduccion(prodId, tipo, nuevoEstado) + "";

			} else if (nuevoEstado == Util.PRODUCCION_EN_PROCESO) {
				
				// Se genera un nuevo caso en caso de seleccionar el estado a "EN PROCESO"
				return gestorDeCasosBonita.generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(prodId,
						produccionBGrupo, produccionGrupo) + "";

			} else {
				
				throw new EstadoInvalidoException("Nuevo estado inválido");
				
			}

		} else if (estadoAnterior == Util.PRODUCCION_EN_PROCESO) {

			// En caso de salir del estado "EN PROCESO" a cualquiera de los otros estados:
			// + Se da la orden de eliminar el caso en el servidor bonita
			// + Se elimina el registro del caso de lado del GRI
			// + Se actualiza el estado de la producción al deseado
			// Si falla alguno de estos pasos el resto de pasos no deben ejecutarse
			
			CasoRevisionProduccion c = casoRevisionProduccionDAO.getCasoPorProduccion(prodId, tipo);
			String msg;
			if (nuevoEstado == Util.PRODUCCION_SIN_CUSTODIA) {
				msg = " y dejando la producción sin custodia";

			} else if (nuevoEstado == Util.PRODUCCION_EN_CUSTODIA) {
				msg = " y dejando la producción en custodia";

			} else {
				throw new EstadoInvalidoException("Nuevo estado inválido");
			}

			log.info("Eliminando caso con el id " + c.getId() + " para la produccion " + prodId + " de tipo " + tipo
					+ msg);

			boolean estadoDeTransaccionEnBonita = gestorDeCasosBonita
					.eliminarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(c.getId());
			boolean estadoDeTransaccionEliminarCasoEnGRI = casoRevisionProduccionDAO.eliminarCaso(c.getId());
			boolean estadoDeTransaccionActualizarEstadoDeProduccion = produccionDAO.actualizarEstadoDeProduccion(prodId,
					tipo, nuevoEstado);

			return (estadoDeTransaccionEnBonita && estadoDeTransaccionEliminarCasoEnGRI
					&& estadoDeTransaccionActualizarEstadoDeProduccion) + "";
			
		} else {
			
			return "Estado anterior inválido";
			
		}

	}

}
