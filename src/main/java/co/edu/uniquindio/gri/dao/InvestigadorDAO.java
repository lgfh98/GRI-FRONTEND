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
	public List<Investigador> findAll(){
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
	 * @param tipo el tipo de entidad (f: Facultad, p: Programa, c: Centro, g: Grupo de Investigación)
	 * @param id el id de la entidad
	 * @return lista de integrantes de la entidad
	 */
	public List<Investigador> getIntegrantes(String tipo, Long id){
		if(tipo.equals("g")){
			return investigadorRepository.integrantesGrupo(id);
		} 
		else if(tipo.equals("p")){
			return investigadorRepository.integrantesPrograma(id);
		} 
		else if(tipo.equals("c")){
			return investigadorRepository.integrantesCentro(id);
		} 
		else if(tipo.equals("f")){
			return investigadorRepository.integrantesFacultad(id);
		} 
		else {
			return investigadorRepository.integrantesGeneral();
		}
		
	}
	
	/**
	 * Obtiene los investigadores internos del centro
	 *
	 * @param grupoid el id del centro
	 * @return lista de investigadores del centro
	 */
	public List<Investigador> getInvestigadoresCentro(Long grupoid) {
		return investigadorRepository.getInvestigadoresCentro(grupoid);
	}

	/**
	 * Obtiene los investigadores emeritos especificado por un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores emeritos del centro
	 */
	public List<Investigador> getInvestigadoresEmeritosCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresEmeritosCentro(centroid);
	}

	/**
	 * Obtiene los investigadores senior especificado por un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores senior del centro
	 */
	public List<Investigador> getInvestigadoresSeniorCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresSeniorCentro(centroid);
	}

	/**
	 * Obtiene los investigadores asociados especificado por un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores asociados del centro
	 */
	public List<Investigador> getInvestigadoresAsociadosCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresAsociadosCentro(centroid);
	}

	/**
	 * Obtiene los investigadores junior especificado por un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores junior del centro
	 */
	public List<Investigador> getInvestigadoresJuniorCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresJuniorCentro(centroid);
	}

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores sin categoria del centro
	 */
	public List<Investigador> getInvestigadoresSinCategoriaCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresSinCategoriaCentro(centroid);
	}

	/**
	 * Obtiene los investigadores internos con formación doctoral especificado por
	 * un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores con formación doctoral del centro
	 */
	public List<Investigador> getInvestigadoresInternosDoctoresCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresInternosDoctoresCentro(centroid);
	}

	/**
	 * Obtiene los investigadores internos con formación magister especificado por
	 * un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores con formación magister del centro
	 */
	public List<Investigador> getInvestigadoresInternosMagisterCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresInternosMagisterCentro(centroid);
	}

	/**
	 * Obtiene los investigadores internos con formación especialista especificado
	 * por un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores con formación especialista del centro
	 */
	public List<Investigador> getInvestigadoresInternosEspecialistasCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresInternosEspecialistasCentro(centroid);
	}

	/**
	 * Obtiene los investigadores internos con formación pregrado especificado por
	 * un id de centro.
	 *
	 * @param centroid el id del centro
	 * @return lista de investigadores con formación pregrado del centro
	 */
	public List<Investigador> getInvestigadoresInternosPregradoCentro(Long centroid) {
		return investigadorRepository.getInvestigadoresInternosPregradoCentro(centroid);
	}
	

	/**
	 * Obtiene los investigadores internos del grupo
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores del grupo
	 */
	public List<Investigador> getInvestigadoresGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresGrupo(grupoid);
	}
	
	/**
	 * Obtiene los investigadores emeritos especificado por un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores emeritos del grupo
	 */
	public List<Investigador> getInvestigadoresEmeritosGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresEmeritosGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores senior especificado por un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores senior del grupo
	 */
	public List<Investigador> getInvestigadoresSeniorGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresSeniorGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores asociados especificado por un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores asociados del grupo
	 */
	public List<Investigador> getInvestigadoresAsociadosGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresAsociadosGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores junior especificado por un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores junior del grupo
	 */
	public List<Investigador> getInvestigadoresJuniorGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresJuniorGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores sin categoria del grupo
	 */
	public List<Investigador> getInvestigadoresSinCategoriaGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresSinCategoriaGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores internos con formación doctoral especificado por
	 * un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores con formación doctoral del grupo
	 */
	public List<Investigador> getInvestigadoresInternosDoctoresGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresInternosDoctoresGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores internos con formación magister especificado por
	 * un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores con formación magister del grupo
	 */
	public List<Investigador> getInvestigadoresInternosMagisterGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresInternosMagisterGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores internos con formación especialista especificado
	 * por un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores con formación especialista del grupo
	 */
	public List<Investigador> getInvestigadoresInternosEspecialistasGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresInternosEspecialistasGrupo(grupoid);
	}

	/**
	 * Obtiene los investigadores internos con formación pregrado especificado por
	 * un id de grupo.
	 *
	 * @param grupoid el id del grupo
	 * @return lista de investigadores con formación pregrado del grupo
	 */
	public List<Investigador> getInvestigadoresInternosPregradoGrupo(Long grupoid) {
		return investigadorRepository.getInvestigadoresInternosPregradoGrupo(grupoid);
	}
	
	/**
	 * Obtiene los investigadores internos del programa
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores del programa
	 */
	public List<Investigador> getInvestigadoresPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresPrograma(programaid);
	}
	
	/**
	 * Obtiene los investigadores emeritos especificado por un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores emeritos del programa
	 */
	public List<Investigador> getInvestigadoresEmeritosPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresEmeritosPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores senior especificado por un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores senior del programa
	 */
	public List<Investigador> getInvestigadoresSeniorPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresSeniorPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores asociados especificado por un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores asociados del programa
	 */
	public List<Investigador> getInvestigadoresAsociadosPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresAsociadosPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores junior especificado por un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores junior del programa
	 */
	public List<Investigador> getInvestigadoresJuniorPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresJuniorPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores sin categoria del programa
	 */
	public List<Investigador> getInvestigadoresSinCategoriaPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresSinCategoriaPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores internos con formación doctoral especificado por
	 * un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores con formación doctoral del programa
	 */
	public List<Investigador> getInvestigadoresInternosDoctoresPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresInternosDoctoresPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores internos con formación magister especificado por
	 * un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores con formación magister del programa
	 */
	public List<Investigador> getInvestigadoresInternosMagisterPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresInternosMagisterPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores internos con formación especialista especificado
	 * por un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores con formación especialista del programa
	 */
	public List<Investigador> getInvestigadoresInternosEspecialistasPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresInternosEspecialistasPrograma(programaid);
	}

	/**
	 * Obtiene los investigadores internos con formación pregrado especificado por
	 * un id de programa.
	 *
	 * @param programaid el id del programa
	 * @return lista de investigadores con formación pregrado del programa
	 */
	public List<Investigador> getInvestigadoresInternosPregradoPrograma(Long programaid) {
		return investigadorRepository.getInvestigadoresInternosPregradoPrograma(programaid);
	}
}
