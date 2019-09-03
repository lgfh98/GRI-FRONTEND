package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.GrupoDAO;
import co.edu.uniquindio.gri.model.Grupo;

/**
 * Clase GrupoController.
 */
@RestController
@RequestMapping("/rest/service")
public class GrupoController {

	/** El DAO para grupos. */
	@Autowired
	GrupoDAO grupoDAO;

	/**
	 * Obtiene todos los grupos de investigación de la base de datos.
	 *
	 * @return Lista con todos los grupos de investigación.
	 */
	@GetMapping("/grupos")
	public List<Grupo> getAllGrupos() {
		return grupoDAO.findAll();
	}

	/**
	 * Obtiene un grupo de investigación en función de un identificador único. 
	 *
	 * @param grupoId el identificador del grupo de investigación.
	 * @return el grupo de investigación con el identificador provisto.
	 */
	@GetMapping("/grupos/{id}")
	public ResponseEntity<Grupo> getGrupoById(@PathVariable(value = "id") Long grupoId) {

		Grupo grupo = grupoDAO.findOne(grupoId);

		if (grupo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(grupo);
	}
	
	/**
	 * Obtiene los grupos de investigación pertenecientes a un programa. 
	 *
	 * @param programaId el identificador del programa.
	 * @return Lista con los grupos pertenecientes al programa.
	 */
	@GetMapping("/gruposprograma/{id}")
	public List<Grupo> getGruposPrograma (@PathVariable(value = "id") Long programaId){
		return grupoDAO.getGruposPrograma(programaId);
	}
	
	/**
	 * Obtiene los grupos de investigación pertenecientes a un centro de investigaciones. 
	 *
	 * @param centroId el identificador del centro de investigaciones. 
	 * @return Lista con los grupos pertenecientes al centro.
	 */
	@GetMapping("/gruposcentro/{id}")
	public List<Grupo> getGruposCentro (@PathVariable(value = "id") Long centroId){
		return grupoDAO.getGruposCentro(centroId);
	}
	
	/**
	 * Obtiene los grupos de investigación pertenecientes a una facultad. 
	 *
	 * @param facultadId el identificador de la facultad. 
	 * @return Lista con los grupos pertenecientes a la facultad.
	 */
	@GetMapping("/gruposfacultad/{id}")
	public List<Grupo> getGruposFacultad (@PathVariable(value = "id") Long facultadId){
		return grupoDAO.getGruposFacultad(facultadId);
	}
}
