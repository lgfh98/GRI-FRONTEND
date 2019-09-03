package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Programa;
import co.edu.uniquindio.gri.repository.ProgramaRepository;

/**
 * Clase ProgramaDAO.
 */
@Service
public class ProgramaDAO {

	/** Repository para programas. */
	@Autowired
	ProgramaRepository programaRepository;
	
	/**
	 * Obtiene todos los programas.
	 *
	 * @return lista con todos los programas.
	 */
	public List<Programa> getAllProgramas(){
		return programaRepository.findAll();
	}
	
	/**
	 * Obtiene un programa especificado por un id.
	 *
	 * @param programaId el id del programa
	 * @return el programa especificado por el id
	 */
	public Programa getProgramaById(Long programaId){
		return programaRepository.findOne(programaId);
	}
	
	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	public List<Programa> getProgramasFacultad(Long facultadId){
		return programaRepository.getProgramasFacultad(facultadId);
	}
}
