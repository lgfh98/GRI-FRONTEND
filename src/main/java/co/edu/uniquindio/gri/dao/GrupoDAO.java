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
	public List<Grupo> findAll(){
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
	 * Obteniene los grupos pertenecientes a un programa, un centro de investigaciones o una facultad. 
	 *
	 * @param id el identificador de la entidad
	 * @param type el tipo de entidad
	 * @return Lista con los grupos pertenecientes a dicha entidad. 
	 */
	public List<Grupo> getGruposPertenecientes(Long id, String type){
		if (type.equals("f")){
			return getGruposFacultad(id);
			
		} else if (type.equals("c")){
			return getGruposCentro(id);
			
		} else if (type.equals("p")){
			return getGruposPrograma(id);
			
		} else{	
			return findAll();
			
		}  
	}
	
	
	/**
	 * Obtiene los grupos pertenecientes a un programa específico.
	 *
	 * @param programaId el id del programa
	 * @return los grupos del programa
	 */
	public List<Grupo> getGruposPrograma(Long programaId){
		return grupoRepository.getGruposPrograma(programaId);
	}
	
	/**
	 * Obtiene los grupos pertenecientes a un centro específico.
	 *
	 * @param centroId el id del centro
	 * @return los grupos del centro
	 */
	public List<Grupo> getGruposCentro(Long centroId){
		return grupoRepository.getGruposCentro(centroId);
	}
	
	/**
	 * Obtiene los grupos pertenecientes a una facultad específica.
	 *
	 * @param facultadId el identificador de la facultad. 
	 * @return Lista de grupos pertenecientes a la facultad. 
	 */
	public List<Grupo> getGruposFacultad(Long facultadId){
		List<Grupo> gruposC = grupoRepository.getGruposFacultadC(facultadId);
		List<Grupo> gruposP = grupoRepository.getGruposFacultadP(facultadId);
	
		for(Grupo grupo : gruposC){
			if(!gruposP.contains(grupo)){
				gruposP.add(grupo);
			}
		}
		return gruposP;
	}
}
