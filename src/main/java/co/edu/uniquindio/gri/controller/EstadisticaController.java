package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import co.edu.uniquindio.gri.dao.CentroDAO;
import co.edu.uniquindio.gri.dao.FacultadDAO;
import co.edu.uniquindio.gri.dao.GrupoDAO;
import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.dao.ProgramaDAO;
import co.edu.uniquindio.gri.dao.TipoDAO;
import co.edu.uniquindio.gri.model.Centro;

public class EstadisticaController {

	/** DAO para los centros de investigación. */
	@Autowired
	CentroDAO centroDAO;
	
	/** El DAO para facultades. */
	@Autowired
	FacultadDAO facultadDAO;
	
	/** El DAO para grupos. */
	@Autowired
	GrupoDAO grupoDAO;
	
	/** DAO para investigador. */
	@Autowired
	InvestigadorDAO investigadorDAO;
	
	/** DAO para producciones. */
	@Autowired
	ProduccionDAO produccionDAO;
	
	/** DAO para programas. */
	@Autowired
	ProgramaDAO programaDAO;
	
	/** DAO para tipos. */
	@Autowired
	TipoDAO tipoDAO;
	
	/**
	 * Obtiene todos los centros de investigación.
	 *
	 * @return lista con todos los centros de investigación.
	 */
	@GetMapping("/uniquindio")
	public List<Centro> getAllCentros(){
		return centroDAO.getAllCentros();
	}
	
	/**
	 * Obtiene un centro de investigación especificado por un id.
	 *
	 * @param centroId el id del centro de investigación
	 * @return el centro especificado por el id
	 */
	@GetMapping("/uniquindio/{id}")
	public ResponseEntity<Centro> getCentroById(@PathVariable("id") Long centroId){
		Centro centro = centroDAO.getCentroById(centroId);
		if(centro == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(centro);
	}
	
}
