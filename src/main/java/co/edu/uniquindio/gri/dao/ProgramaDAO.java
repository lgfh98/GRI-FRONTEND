package co.edu.uniquindio.gri.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
		return programaRepository.findById(programaId).orElse(null);
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
	 *         tipo de programa academico
	 */
	public List<Programa> getProgramasAcademicosFacultad(long facultadId) {
		return programaRepository.getProgramasAcademicosFacultad(facultadId);
	}
	
	/**
	 * Obtiene los programas de doctorado de una facultad específica
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad y el
	 *         tipo de programa doctorado
	 */
	public List<Programa> getProgramasDoctoradoFacultad(long facultadId) {
		return programaRepository.getProgramasDoctoradoFacultad(facultadId);
	}
	
	/**
	 * Obtiene los programas de maestria de una facultad específica
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad y el
	 *         tipo de programa maestria
	 */
	public List<Programa> getProgramasMaestríaFacultad(long facultadId) {
		return programaRepository.getProgramasMaestriaFacultad(facultadId);
	}
	
	/**
	 * Obtiene los programas doctorados de una facultad específica
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad y el
	 *         tipo de programa especializacion
	 */
	public List<Programa> getProgramasEspecializacionFacultad(long facultadId) {
		return programaRepository.getProgramasEspecializacionFacultad(facultadId);
	}
	
	/**
	 * Obtiene los programas academicos de la universidad
	 *
	 * @return lista de los programas academicos
	 */
	public List<Programa> getProgramasAcademicos() {
		return programaRepository.getProgramasAcademicos();
	}
	
	/**
	 * Obtiene los programas de doctorado de la universidad
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas de doctorado
	 */
	public List<Programa> getProgramasDoctorado() {
		return programaRepository.getProgramasDoctorado();
	}
	
	/**
	 * Obtiene los programas de maestria de la universidad
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas de maestria
	 */
	public List<Programa> getProgramasMaestria() {
		return programaRepository.getProgramasMaestria();
	}
	
	/**
	 * Obtiene los programas doctorados de la universidad
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de los programas de especializacion
	 */
	public List<Programa> getProgramasEspecializacion() {
		return programaRepository.getProgramasEspecializacion();
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
	 * @param Long programaId, id del programa.
	 *
	 * @return lista con los totales anteriores
	 */
	public List<BigInteger> getResumenGeneralPrograma(Long programaId){
		return programaRepository.getResumenGeneralPrograma(programaId);
	}

}
