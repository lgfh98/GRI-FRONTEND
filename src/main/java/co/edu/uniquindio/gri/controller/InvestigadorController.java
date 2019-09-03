package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.model.Investigador;

/**
 * Clase InvestigadorController.
 */
@RestController
@RequestMapping("/rest/service")
public class InvestigadorController {

	/** DAO para investigador. */
	@Autowired
	InvestigadorDAO investigadorDAO;
	
	/**
	 * Obtiene todos los investigadores.
	 *
	 * @return lista con todos los investigadores
	 */
	@GetMapping("/investigadores")
	public List<Investigador> getAllInvestigadores(){
		return investigadorDAO.findAll();
	}
	
	/**
	 * Obtiene un investigador especificado por un id.
	 *
	 * @param invId el id del investigador
	 * @return el investigador por el id
	 */
	@GetMapping("/investigadores/{id}")
	public ResponseEntity<Investigador> getInvestigadorById(@PathVariable(value="id") Long invId){
		
		Investigador inv=investigadorDAO.findOne(invId);
		
		if(inv==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(inv);
		
	}
	
	/**
	 * Obtiene los integrantes de una entidad específica.
	 *
	 * @param tipo el tipo de entidad (f: Facultad, p: Programa, c: Centro, g: Grupo de Investigación)
	 * @param id el id de la entidad
	 * @return lista de integrantes de la entidad
	 */
	@GetMapping("/integrantes/{type}/{id}")
	public List<Investigador> getIntegrantes(@PathVariable(value="type") String tipo, @PathVariable(value="id") Long id){	
		return investigadorDAO.getIntegrantes(tipo, id);
	}
	
}
