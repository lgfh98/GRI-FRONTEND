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
}
