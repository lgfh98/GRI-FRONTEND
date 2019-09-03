package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.CentroDAO;
import co.edu.uniquindio.gri.model.Centro;

/**
 * Clase CentroController.
 */
@RestController
@RequestMapping("/rest/service")
public class CentroController { 

	/** DAO para los centros de investigación. */
	@Autowired
	CentroDAO centroDAO;
	
	/**
	 * Obtiene todos los centros de investigación.
	 *
	 * @return lista con todos los centros de investigación.
	 */
	@GetMapping("/centros")
	public List<Centro> getAllCentros(){
		return centroDAO.getAllCentros();
	}
	
	/**
	 * Obtiene un centro de investigación especificado por un id.
	 *
	 * @param centroId el id del centro de investigación
	 * @return el centro especificado por el id
	 */
	@GetMapping("/centros/{id}")
	public ResponseEntity<Centro> getCentroById(@PathVariable("id") Long centroId){
		Centro centro = centroDAO.getCentroById(centroId);
		if(centro == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(centro);
	}
}

