package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.repository.InvestigadorRepository;

/**
 * Clase InvestigadorDAO.
 */
@Service
public class InvestigadorDAO {

	/** Repository para investigadores. */
	@Autowired
	InvestigadorRepository investigadorRepository;

	/**
	 * Obtiene todos los investigadores.
	 *
	 * @return lista con todos los investigadores
	 */
	public List<Investigador> findAll() {
		return investigadorRepository.findAll();
	}

	/**
	 * Obtiene un investigador especificado por un id.
	 *
	 * @param invId el id del investigador
	 * @return el investigador por el id
	 */
	public Investigador findOne(Long invId) {
		return investigadorRepository.findOne(invId);
	}

	/**
	 * Obtiene los integrantes de una entidad específica.
	 *
	 * @param tipo el tipo de entidad (f: Facultad, p: Programa, c: Centro, g: Grupo
	 *             de Investigación)
	 * @param id   el id de la entidad
	 * @return lista de integrantes de la entidad
	 */
	public List<Investigador> getIntegrantes(String tipo, Long id) {
		if (tipo.equals("g")) {
			return investigadorRepository.integrantesGrupo(id);
		} else if (tipo.equals("p")) {
			return investigadorRepository.integrantesPrograma(id);
		} else if (tipo.equals("c")) {
			return investigadorRepository.integrantesCentro(id);
		} else if (tipo.equals("f")) {
			return investigadorRepository.integrantesFacultad(id);
		} else {
			return investigadorRepository.integrantesGeneral();
		}

	}

	/**
	 * Obtiene los investigadores emeritos especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores emeritos de la facultad
	 */
	public List<Investigador> getInvestigadoresEmeritosFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresEmeritosFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores senior especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores senior de la facultad
	 */
	public List<Investigador> getInvestigadoresSeniorFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresSeniorFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores asociados especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores asociados de la facultad
	 */
	public List<Investigador> getInvestigadoresAsociadosFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresAsociadosFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores junior especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores junior de la facultad
	 */
	public List<Investigador> getInvestigadoresJuniorFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresJuniorFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores sin categoria de la facultad
	 */
	public List<Investigador> getInvestigadoresSinCategoriaFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresSinCategoriaFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores internos con formación doctoral especificado por
	 * un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación doctoral de la facultad
	 */
	public List<Investigador> getInvestigadoresInternosDoctoresFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresInternosDoctoresFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores internos con formación magister especificado por
	 * un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación magister de la facultad
	 */
	public List<Investigador> getInvestigadoresInternosMagisterFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresInternosMagisterFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores internos con formación especialista especificado
	 * por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación especialista de la facultad
	 */
	public List<Investigador> getInvestigadoresInternosEspecialistasFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresInternosEspecialistasFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores internos con formación pregrado especificado por
	 * un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación pregrado de la facultad
	 */
	public List<Investigador> getInvestigadoresInternosPregradoFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresInternosPregradoFacultad(facultadid);
	}
	

	/**
	 * Obtiene los investigadores internos de la facultad
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores de la facultad
	 */
	public List<Investigador> getInvestigadoresFacultad(Long facultadid) {
		return investigadorRepository.getInvestigadoresFacultad(facultadid);
	}

	/**
	 * Obtiene los investigadores emeritos de la Universidad
	 * 
	 * @return lista de investigadores emeritos de la facultad
	 */
	public List<Investigador> getAllInvestigadoresEmeritos() {
		return investigadorRepository.getAllInvestigadoresEmeritos();
	}

	/**
	 * Obtiene los investigadores senior de la universidad
	 *
	 * @return lista de investigadores senior de la facultad
	 */
	public List<Investigador> getAllInvestigadoresSenior() {
		return investigadorRepository.getAllInvestigadoresSenior();
	}

	/**
	 * Obtiene los investigadores asociados de la Universidad
	 * 
	 * @return lista de investigadores asociados de la facultad
	 */
	public List<Investigador> getAllInvestigadoresAsociado() {
		return investigadorRepository.getAllInvestigadoresAsociado();
	}

	/**
	 * Obtiene los investigadores junior de la universidad.
	 *
	 * @return lista de investigadores junior de la universidad
	 */
	public List<Investigador> getAllInvestigadoresJunior() {
		return investigadorRepository.getAllInvestigadoresJunior();
	}

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de facultad.
	 *
	 * @return lista de investigadores sin categoria de la universidad
	 */
	public List<Investigador> getAllInvestigadoresSinCategoria() {
		return investigadorRepository.getAllInvestigadoresSinCategoria();
	}

	/**
	 * Obtiene los investigadores internos con formación doctoral de la universidad
	 * 
	 * @return lista de investigadores con formación doctoral de la universidad
	 */
	public List<Investigador> getAllInvestigadoresInternosDoctores() {
		return investigadorRepository.getAllInvestigadoresInternosDoctores();
	}

	/**
	 * Obtiene los investigadores internos con formación magister de la universidad
	 * 
	 * @return lista de investigadores con formación magister de la universidad
	 */
	public List<Investigador> getAllInvestigadoresInternosMagister() {
		return investigadorRepository.getAllInvestigadoresInternosMagister();
	}

	/**
	 * Obtiene los investigadores internos con formación especialista de la
	 * universidad
	 * 
	 * @return lista de investigadores con formación especialista de la universidad
	 */
	public List<Investigador> getAllInvestigadoresEspecialistas() {
		return investigadorRepository.getAllInvestigadoresEspecialistas();
	}

	/**
	 * Obtiene los investigadores internos con formación pregrado de la universidad
	 *
	 * @return lista de investigadores con formación pregrado de la facultad
	 */
	public List<Investigador> getAllInvestigadoresPregrado() {
		return investigadorRepository.getAllInvestigadoresPregrado();
	}
	
	/**
	 * Obtiene los investigadores internos de la universidad
	 *
	 * @return lista de investigadores internos de la facultad
	 */
	public Object getAllInvestigadoresInternos() {
		return investigadorRepository.getAllInvestigadoresInternos();
	}
}
