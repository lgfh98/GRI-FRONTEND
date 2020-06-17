package co.edu.uniquindio.gri.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.CasoRevisionProduccionDAO;

@RestController
@RequestMapping("/rest/service")
public class CasoRevisionProduccionController {

	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

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
	
	
}
