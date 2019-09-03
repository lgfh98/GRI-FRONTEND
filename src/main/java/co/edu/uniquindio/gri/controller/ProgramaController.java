package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.ProgramaDAO;
import co.edu.uniquindio.gri.model.Programa;

/**
 * Clase ProgramaController.
 */
@RestController
@RequestMapping("/rest/service")
public class ProgramaController {

	/** DAO para programas. */
	@Autowired
	ProgramaDAO programaDAO;
	
	/**
	 * Obtiene todos los programas.
	 *
	 * @return lista con todos los programas.
	 */
	@GetMapping("/programas")
	public List<Programa> getAllProgramas(){
		return programaDAO.getAllProgramas();
	} 
	
	/**
	 * Obtiene un programa especificado por un id.
	 *
	 * @param programaId el id del programa
	 * @return el programa especificado por el id
	 */
	@GetMapping("/programas/{id}")
	public ResponseEntity<Programa> getProgramaById(@PathVariable(value = "id") Long programaId){
		Programa programa = programaDAO.getProgramaById(programaId);
		
		if(programa == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(programa);
	}
	
	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@GetMapping("/programasfacultad/{id}")
	public List<Programa> getProgramasFacultad (@PathVariable(value="id") Long facultadId){
		return programaDAO.getProgramasFacultad(facultadId);
	}
}
