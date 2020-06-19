package co.edu.uniquindio.gri.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.CasoRevisionProduccionDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
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

	/**
	 * Servicio REST que se encarga de crear un caso de revisión y subida de
	 * producciones en la base de datos del GRI con su estado por defecto que es "EN
	 * CURSO"
	 * 
	 * @param id
	 * @param idProduccion
	 * @param tipoProduccion
	 * @return
	 */
	@PostMapping("/casos/revisionproduccion")
	public String crearCaso(@RequestParam("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion) {
		log.info(
				"Iniciando caso con id " + id + " para la producción " + idProduccion + " de tipo " + tipoProduccion);
		boolean estadoDeTransaccionIniciarCaso = casoRevisionProduccionDAO.archivarCaso(id, idProduccion, tipoProduccion);
		boolean estadoDeTransaccionActualizarEstadoDeProduccion = produccionDAO
				.actualizarEstadoDeProduccion(idProduccion, tipoProduccion, Util.EN_PROCESO);
		return  (estadoDeTransaccionIniciarCaso && estadoDeTransaccionActualizarEstadoDeProduccion)+ "";
	}

	/**
	 * Servicio REST que se encarga de actualizar un caso de revisión y subida de
	 * producciones en la base de datos del GRI, también actualiza el estado de la
	 * producción asociada a "en custodia"
	 * 
	 * @param id
	 * @param idProduccion
	 * @param tipoProduccion
	 * @param estado
	 * @return
	 */

	@PutMapping("/casos/revisionproduccion/{id}")
	public String finalizarCaso(@PathVariable("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion) {
		log.info(
				"Finalizando caso con id " + id + " para la producción " + idProduccion + " de tipo " + tipoProduccion);
		boolean estadoDeTransaccionActualizarEstadoDeProduccion = produccionDAO
				.actualizarEstadoDeProduccion(idProduccion, tipoProduccion, Util.EN_CUSTODIA);
		boolean estadoDeTransaccionFinalizarCaso = casoRevisionProduccionDAO.archivarCaso(id, idProduccion,
				tipoProduccion, Util.BONITA_CASO_FINALIZADO);
		return (estadoDeTransaccionFinalizarCaso && estadoDeTransaccionActualizarEstadoDeProduccion) + "";
	}

//	@DeleteMapping("/casos/revisionproduccion")
//	public String eliminarCasoDeUnaProduccion(@RequestParam("idproduccion") long idProduccion, @RequestParam("tipo") String tipoProduccion) {
//		
//		BonitaConnectorAPI bonita = new BonitaConnectorAPI(servidor);
//		try {
//			bonita.iniciarClienteHttp();
//			bonita.iniciarSesionEnBonita(usuario, password);
//			System.out.println(casoRevisionProduccionDAO.toString());
//			System.out.println(casoRevisionProduccionDAO.getCasoPorProduccion(idProduccion, tipoProduccion).toString());
//			bonita.eliminarCaso(casoRevisionProduccionDAO.getCasoPorProduccion(idProduccion, tipoProduccion).getId());
//		} catch (URISyntaxException | IOException e) {
//			log.error(e.getMessage());
//			return "false";
//		}
//		
//		return (produccionDAO.actualizarEstadoDeProduccion(idProduccion, tipoProduccion, 1);
//	}

}
