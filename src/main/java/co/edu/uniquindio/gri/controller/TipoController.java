package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.TipoDAO;
import co.edu.uniquindio.gri.model.Tipo;

/**
 * Clase TipoController.
 */
@RestController
@RequestMapping("/rest/service")
public class TipoController {

	/** DAO para tipos. */
	@Autowired
	TipoDAO tipoDAO;
	
	/**
	 * Obtiene todos los tipos de producción.
	 *
	 * @return lista con todos los tipos de producción.
	 */
	@GetMapping("/tipos")
	public List<Tipo> getAllTipos(){
		return tipoDAO.getAllTipos();
	}
	
	/**
	 * Obtiene un tipos de producción especificado por un id.
	 *
	 * @param programaId el id del tipo
	 * @return el tipo de producción especificado por el id
	 */
	@GetMapping("/tipos/{id}")
	public ResponseEntity<Tipo> getTipoById(@PathVariable("id") Long tipoId){
		Tipo tipo = tipoDAO.getTipoById(tipoId);
		
		if(tipo == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(tipo);
	}
}

