package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import co.edu.uniquindio.gri.model.RecononocimientosInvestigador;
import co.edu.uniquindio.gri.utilities.Util;

@RestController
@RequestMapping("/rest/service")
public class CasoRevisionProduccionController {

	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

	@Autowired
	ProduccionDAO produccionDAO;
	
	@Autowired
	Util utilidades = new Util();

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
		return casoRevisionProduccionDAO.archivarNuevoCaso(id, idProduccion, tipoProduccion) + "";
	}

	/**
	 * Servicio REST que se encarga de actualizar un caso de revisión y subida de
	 * producciones en la base de datos del GRI
	 * 
	 * @param id
	 * @param idProduccion
	 * @param tipoProduccion
	 * @param estado
	 * @return
	 */

	@PutMapping("/casos/revisionproduccion/{id}")
	public String actualizarCaso(@PathVariable("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion, @RequestParam("estado") String estado) {
		return casoRevisionProduccionDAO.archivarNuevoCaso(id, idProduccion, tipoProduccion, estado) + "";
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
