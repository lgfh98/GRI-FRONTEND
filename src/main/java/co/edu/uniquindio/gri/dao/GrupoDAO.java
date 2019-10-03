package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.repository.GrupoRepository;

/**
 * Clase GrupoDAO.
 */
@Service
public class GrupoDAO {

	/** Repository para grupos. */
	@Autowired
	GrupoRepository grupoRepository;

	/**
	 * Obtiene todos los grupos de investigación.
	 *
	 * @return lista con todos los grupos de investigación
	 */
	public List<Grupo> findAll() {
		return grupoRepository.findAll();
	}

	/**
	 * Obtiene un grupo de investigación especificado por un id.
	 *
	 * @param grupoId el id del grupo de investigación
	 * @return el grupo de investigación por el id
	 */
	public Grupo findOne(Long grupoId) {
		return grupoRepository.findOne(grupoId);
	}

	/**
	 * Obteniene los grupos pertenecientes a un programa, un centro de
	 * investigaciones o una facultad.
	 *
	 * @param id   el identificador de la entidad
	 * @param type el tipo de entidad
	 * @return Lista con los grupos pertenecientes a dicha entidad.
	 */
	public List<Grupo> getGruposPertenecientes(Long id, String type) {
		if (type.equals("f")) {
			return getGruposFacultad(id);

		} else if (type.equals("c")) {
			return getGruposCentro(id);

		} else if (type.equals("p")) {
			return getGruposPrograma(id);

		} else {
			return findAll();

		}
	}

	/**
	 * Obtiene los grupos pertenecientes a un programa específico.
	 *
	 * @param programaId el id del programa
	 * @return los grupos del programa
	 */
	public List<Grupo> getGruposPrograma(Long programaId) {
		return grupoRepository.getGruposPrograma(programaId);
	}

	/**
	 * Obtiene los grupos pertenecientes a un centro específico.
	 *
	 * @param centroId el id del centro
	 * @return los grupos del centro
	 */
	public List<Grupo> getGruposCentro(Long centroId) {
		return grupoRepository.getGruposCentro(centroId);
	}

	/**
	 * Obtiene los grupos pertenecientes a una facultad específica.
	 *
	 * @param facultadId el identificador de la facultad.
	 * @return Lista de grupos pertenecientes a la facultad.
	 */
	public List<Grupo> getGruposFacultad(Long facultadId) {
		List<Grupo> gruposC = grupoRepository.getGruposFacultadC(facultadId);
		List<Grupo> gruposP = grupoRepository.getGruposFacultadP(facultadId);

		for (Grupo grupo : gruposC) {
			if (!gruposP.contains(grupo)) {
				gruposP.add(grupo);
			}
		}
		return gruposP;
	}
	
	/**
	 * Obtiene los grupos A1 de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos A1 especificado por el id de facultad
	 */
	public List<Grupo> getGruposA1Facultad(Long facultadId) {
		return grupoRepository.getGruposA1Facultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos A de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos A especificado por el id de facultad
	 */
	public List<Grupo> getGruposAFacultad(Long facultadId) {
		return grupoRepository.getGruposAFacultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos B de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos B especificado por el id de facultad
	 */
	public List<Grupo> getGruposBFacultad(Long facultadId) {
		return grupoRepository.getGruposBFacultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos C de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos C especificado por el id de facultad
	 */
	public List<Grupo> getGruposCFacultad(Long facultadId) {
		return grupoRepository.getGruposCFacultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos reconocidos de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos reconocidos especificado por el id de facultad
	 */
	public List<Grupo> getGruposReconocidosFacultad(Long facultadId) {
		return grupoRepository.getGruposReconocidosFacultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos no reconocidos de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos no reconocidos especificado por el id de facultad
	 */
	public List<Grupo> getGruposNoReconocidosFacultad(Long facultadId) {
		return grupoRepository.getGruposNoReconocidosFacultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos de investigacion reconocidos especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return los grupos especificado por el id de facultad
	 */
	public List<Grupo> getAllGruposFacultad(Long facultadId) {
		return grupoRepository.getAllGruposFacultad(facultadId);
	}
	
	/**
	 * Obtiene los grupos A1 de la Universidad del Quindío
	 * @return los grupos A1 de la Universidad del Quindío
	 */
	public List<Grupo> getAllGruposA1() {
		return grupoRepository.getAllGruposA1();
	}
	
	/**
	 * Obtiene los grupos A de la Universidad del Quindío
	 * @return los grupos A de la Universidad del Quindío
	 */
	public List<Grupo> getAllGruposA() {
		return grupoRepository.getAllGruposA();
	}
	
	/**
	 * Obtiene los grupos B de la Universidad del Quindío
	 * @return los grupos B de la Universidad del Quindío
	 */
	public List<Grupo> getAllGruposB() {
		return grupoRepository.getAllGruposB();
	}
	
	/**
	 * Obtiene los grupos C de la Universidad del Quindío
	 * @return los grupos C de la Universidad del Quindío
	 */
	public List<Grupo> getAllGruposC() {
		return grupoRepository.getAllGruposC();
	}
	
	/**
	 * Obtiene los grupos reconocidos de la Universidad del Quindío
	 * @return los grupos reconocidos de la Universidad del Quindío
	 */
	public List<Grupo> getAllGruposReconocidos() {
		return grupoRepository.getAllGruposReconocidos();
	}
	
	/**
	 * Obtiene los grupos no reconocidos de la Universidad del Quindío
	 * @return los grupos no reconocidos de la Universidad del Quindío
	 */
	public List<Grupo> getAllGruposNoReconocidos() {
		return grupoRepository.getAllGruposNoReconocidos();
	}
	
	/**
	 * Obtiene un grupo de investigación no reconocidos especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return el grupo especificado por el id de centro
	 */
	public List<Grupo> getAllGruposCentro(Long centroId) {
		return grupoRepository.getGruposCentro(centroId);
	}
	
	/**
	 * Obtiene los grupos A1 de investigacion reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return los grupos A1 especificado por el id de programa
	 */
	public List<Grupo> getGruposA1Programa(Long programaId) {
		return grupoRepository.getGruposA1Programa(programaId);
	}
	
	/**
	 * Obtiene los grupos A de investigacion reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return los grupos A especificado por el id de programa
	 */
	public List<Grupo> getGruposAPrograma(Long programaId) {
		return grupoRepository.getGruposAPrograma(programaId);
	}
	
	/**
	 * Obtiene los grupos B de investigacion reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return los grupos B especificado por el id de programa
	 */
	public List<Grupo> getGruposBPrograma(Long programaId) {
		return grupoRepository.getGruposBPrograma(programaId);
	}
	
	/**
	 * Obtiene los grupos C de investigacion reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return los grupos C especificado por el id de programa
	 */
	public List<Grupo> getGruposCPrograma(Long programaId) {
		return grupoRepository.getGruposCPrograma(programaId);
	}
	
	/**
	 * Obtiene los grupos reconocidos de investigacion reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id de la programa
	 * @return los grupos reconocidos especificado por el id de programa
	 */
	public List<Grupo> getGruposReconocidosPrograma(Long programaId) {
		return grupoRepository.getGruposReconocidosPrograma(programaId);
	}
	
	/**
	 * Obtiene los grupos no reconocidos de investigacion reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return los grupos no reconocidos especificado por el id de programa
	 */
	public List<Grupo> getGruposNoReconocidosPrograma(Long programaId) {
		return grupoRepository.getGruposNoReconocidosPrograma(programaId);
	}
	
	/**
	 * Obtiene un grupo de investigación no reconocidos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return el grupo especificado por el id de programa
	 */
	public List<Grupo> getAllGruposPrograma(Long programaId) {
		return grupoRepository.getAllGruposPrograma(programaId);
	}
}
