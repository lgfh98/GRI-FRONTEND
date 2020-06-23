package co.edu.uniquindio.gri.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.CasoRevisionProduccionDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.utilities.Util;

@RestController
@RequestMapping("/rest/service")
public class CasoRevisionProduccionController {

	@Value("${bonita.servidor.base}")
	private String servidor;
	@Value("${bonita.usuario}")
	private String usuario;
	@Value("${bonita.password}")
	private String password;

	private static final Logger log = LoggerFactory.getLogger(CasoRevisionProduccionController.class);

	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

	@Autowired
	ProduccionDAO produccionDAO;
	
	@Autowired
	Util utilidades = new Util();

	/**
	 * Servicio REST para uso exclusivo externo por parte del servidor bonita,se
	 * encarga de crear un caso de revisión y subida de producciones en la base de
	 * datos del GRI con su estado por defecto que es "EN CURSO" y de actualizar el
	 * estado de la producción a 2 (este método es la única unidad de código que
	 * puede asignar dicho estado a una producción)
	 * 
	 * @param id
	 * @param idProduccion
	 * @param tipoProduccion
	 * @return
	 */
	@PostMapping("/casos/revisionproduccion")
	public String crearCaso(@RequestParam("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion) {
		log.info("Iniciando caso desde bonita con id " + id + " para la producción " + idProduccion + " de tipo "
				+ tipoProduccion);
		boolean estadoDeTransaccionIniciarCaso = casoRevisionProduccionDAO.archivarCaso(id, idProduccion,
				tipoProduccion);
		boolean estadoDeTransaccionActualizarEstadoDeProduccion = produccionDAO
				.actualizarEstadoDeProduccion(idProduccion, tipoProduccion, Util.PRODUCCION_EN_PROCESO);
		return (estadoDeTransaccionIniciarCaso && estadoDeTransaccionActualizarEstadoDeProduccion) + "";
	}

	/**
	 * Servicio REST para uso exclusivo externo por parte del servidor bonita, se
	 * encarga de actualizar un caso de revisión y subida de producciones en la base
	 * de datos del GRI, también actualiza el estado de la producción asociada a "en
	 * custodia"
	 * 
	 * @param id
	 * @param idProduccion
	 * @param tipoProduccion
	 * @param estado
	 * @return uan cadena con true, o false en caso de que se genere un error
	 */

	@PutMapping("/casos/revisionproduccion/{id}")

	public String finalizarCaso(@PathVariable("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion) {
		log.info(
				"Finalizando caso con id " + id + " para la producción " + idProduccion + " de tipo " + tipoProduccion);
		boolean estadoDeTransaccionActualizarEstadoDeProduccion = produccionDAO
				.actualizarEstadoDeProduccion(idProduccion, tipoProduccion, Util.PRODUCCION_EN_CUSTODIA);
		boolean estadoDeTransaccionFinalizarCaso = casoRevisionProduccionDAO.archivarCaso(id, idProduccion,
				tipoProduccion, Util.BONITA_CASO_FINALIZADO);
		return (estadoDeTransaccionFinalizarCaso && estadoDeTransaccionActualizarEstadoDeProduccion) + "";
	}
	/**
	 * Obtiene las recolecciones de una entidad específica.
	 *
	 * @param type     el tipo de la entidad (f: Facultad, p: Programa, c: Centro,
	 *                 g: Grupo de Investigación i: Investigador)
	 * @param entityId el id de la entidad
	 * @param tipoId   el tipo de la producción a obtener
	 * @return lista de recolecciones
	 */

	@GetMapping("/recolecciones/{type}/{id}")
	public List<CasoRevisionProduccion> getProducciones(@PathVariable("type") String type,
			@PathVariable("id") Long entityId) {

		List <ProduccionBGrupo> produccionesb = utilidades.obtenerBibliograficas(type, entityId);
		List <ProduccionGrupo> producciones = utilidades.obtenerGenericas(type, entityId);
		List<CasoRevisionProduccion> casos = casoRevisionProduccionDAO.getRecolecciones();
		
		return utilidades.obtenerCasosPorListas(casos, produccionesb, producciones);
	}

}
