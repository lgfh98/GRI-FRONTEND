package co.edu.uniquindio.gri.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.utilities.Util;
import co.edu.uniquindio.gri.bonitaapi.BonitaConnectorAPI;
import co.edu.uniquindio.gri.bonitaapi.GestorDeCasosBonita;
import co.edu.uniquindio.gri.dao.CasoRevisionProduccionDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
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

	@Value("${bonita.nombre.proceso}")
	private String nombreDelProcesoBonita;
	@Value("${bonita.usuario}")
	private String usuario;
	@Value("${bonita.password}")
	private String password;
	@Value("${bonita.servidor.base}")
	private String servidor;

	@Autowired
	ProduccionDAO produccionDAO;
	
	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

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
	 * Actualiza el estado de una producción científica al estado enviado por
	 * parámetro en función si se encuentra o no en custodia física de la
	 * Vicerrectoría de Investigaciones.
	 *
	 * @param tipo,   identifica si la producción es o no bibliográfica
	 * @param estado, el estado de la producción. 0 si no se encuentra en custodia.
	 *                1 en caso contrario.
	 * @param prodId, el identificador de la producción en base de datos.
	 * @return true, si la actualización se realizó satisfactoriamente.
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	@PutMapping("/producciones/actualizarestado/{prodId}")
	public String actualizarEstadoProduccion(@PathVariable("prodId") Long prodId,
			@RequestParam("estado") int nuevoEstado, @RequestParam("tipo") String tipo) throws ClientProtocolException, URISyntaxException, IOException {
		
		ProduccionGrupo produccionGrupo = null;
		ProduccionBGrupo produccionBGrupo = null;
		int estadoAnterior = 0;
		if (tipo.equals("generica")) {
			produccionGrupo = produccionDAO.getProduccion(prodId);
			estadoAnterior = produccionGrupo.getEstado();
		} else if (tipo.equals("bibliografica")) {
			produccionBGrupo = produccionDAO.getProduccionB(prodId);
			estadoAnterior = produccionBGrupo.getEstado();
		}
		
		if (estadoAnterior == Util.SIN_CUSTODIA) {
			if (nuevoEstado == Util.EN_CUSTODIA) {
				log.info("Tomando en custodia la producción con id: " + prodId);
				return produccionDAO.actualizarEstadoDeProduccion(prodId, tipo, nuevoEstado) + "";
			} else if (nuevoEstado == Util.EN_PROCESO) {
				return generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(prodId, produccionBGrupo, produccionGrupo);
			}
		} else if (estadoAnterior == Util.EN_CUSTODIA) {
			if (nuevoEstado == Util.SIN_CUSTODIA) {
				log.info("Eliminando del inventario de custodia la producción con id: " + prodId);
				return produccionDAO.actualizarEstadoDeProduccion(prodId, tipo, nuevoEstado) + "";
			} else if (nuevoEstado == Util.EN_PROCESO) {
				return generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(prodId, produccionBGrupo, produccionGrupo);
			}
		} else if (estadoAnterior == Util.EN_PROCESO) {
			if (nuevoEstado == Util.SIN_CUSTODIA) {
				
			} else if (nuevoEstado == Util.EN_CUSTODIA) {
				CasoRevisionProduccion c = casoRevisionProduccionDAO.getCasoPorProduccion(prodId, tipo); 
				casoRevisionProduccionDAO.archivarCaso(c.getId(), prodId, tipo);
			}
		}

	}

	public String generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(Long prodId, ProduccionBGrupo produccionBGrupo, ProduccionGrupo produccionGrupo) throws ClientProtocolException, URISyntaxException, IOException {
		log.info("Generando nuevo caso para la producción con id: " + prodId);
		BonitaConnectorAPI b = new BonitaConnectorAPI(servidor);
		b.iniciarClienteHttp();
		b.iniciarSesionEnBonita(usuario, password);
		GestorDeCasosBonita g = new GestorDeCasosBonita();
		return g.generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(b, produccionBGrupo, produccionGrupo, b.obtenerIdDelProceso(nombreDelProcesoBonita), nombreDelProcesoBonita);
	}
	
}
