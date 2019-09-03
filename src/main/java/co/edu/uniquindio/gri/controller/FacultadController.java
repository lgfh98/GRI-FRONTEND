package co.edu.uniquindio.gri.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.FacultadDAO;
import co.edu.uniquindio.gri.model.Facultad;

/**
 * Clase FacultadController.
 */
@RestController
@RequestMapping("/rest/service")
public class FacultadController {  

	/** El DAO para facultades. */
	@Autowired
	FacultadDAO facultadDAO;

	/**
	 * Obtiene todas las facultades.
	 *
	 * @return lista con todas las facultades.
	 */
	@GetMapping("/facultades")
	public List<Facultad> getAllFacultades() {
		return facultadDAO.getAllFacultades();
	}

	/**
	 * Obtiene una facultad especificada por id
	 *
	 * @param idFacultad el id de la facultad
	 * @return la facultad especificada por el idFacultad
	 */
	@GetMapping("/facultades/{id}")
	public ResponseEntity<Facultad> getFacultadById(@PathVariable(value = "id") Long idFacultad) {
		Facultad facultad = facultadDAO.getFacultadById(idFacultad);

		if (facultad == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(facultad);
	}
	
	/**
	 * Obtiene las estadísticas del sistema.
	 *
	 * @return lista con las estadísticas del sistema. 
	 */
	@GetMapping("/stats")
	public List<BigInteger> getStats(){
		return facultadDAO.getStats();
	}
}

