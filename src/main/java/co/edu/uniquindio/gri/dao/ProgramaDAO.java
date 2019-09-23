package co.edu.uniquindio.gri.dao;

import java.math.BigInteger;
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
	public List<Programa> getAllProgramas() {
		return programaRepository.getProgramas();
	}

	/**
	 * Obtiene la cantidad total de programas.
	 *
	 * @return total de los programas.
	 */
	public BigInteger getStats() {
		return programaRepository.getStats();
	}

	/**
	 * Obtiene un programa especificado por un id.
	 *
	 * @param programaId el id del programa
	 * @return el programa especificado por el id
	 */
	public Programa getProgramaById(Long programaId) {
		return programaRepository.findOne(programaId);
	}

	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	public List<Programa> getProgramasFacultad(Long facultadId) {
		return programaRepository.getProgramasFacultad(facultadId);
	}

	/**
	 * Obtiene los programas academicos de una facultad específica
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad y el
	 *         tipo de programa type
	 */
	public List<Programa> getProgramasAcademicosFacultad(long facultadId) {
		return programaRepository.getProgramasAcademicosFacultad(facultadId);
	}
	
	/**
	 * Obtiene el resumen general de la facultad en números
	 * la lista en cada posición obtiene lo siguiente:
	 * 0 . Cantidad grupos de investigación 
	 * 1 - Cantidad de lineas investigación 
	 * 2 - Cantidad investigadores
	 * 3 - Grupos categoria A1
	 * 4 - Grupos categoria A
	 * 5 - Grupos categoria B
	 * 6 - Grupos categoria C
	 * 7 - Grupos reconocidos
	 * 8 - Grupos no reconocidos
	 * 9 - Investigadores emeritos
	 * 10 - Investigador senior
	 * 11 - Investigador asociados
	 * 12 - Investigador junior
	 * 13 - Investigador sin categoria
	 * 14 - Docentes con doctorado
	 * 15 - Docentes con magister
	 * 16 - Docentes especialistas
	 * 17 - Docentes pregrado
	 * 
	 * @param Long facultadId, id del programa.
	 *
	 * @return lista con los totales anteriores
	 */
}
