package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.LineasInvestigacion;
import co.edu.uniquindio.gri.model.Tipo;
import co.edu.uniquindio.gri.repository.LineasInvestigacionRepository;
import co.edu.uniquindio.gri.repository.TipoRepository;

/**
 * Class TipoDAO.
 */
@Service
public class LineasInvestigacionDAO {

	/** Repository para tipos. */
	@Autowired
	LineasInvestigacionRepository lineasInvestigacionRepository;

	/**
	 * Obtiene todas las lineas de investigación de una facultad
	 * 
	 * @param facultadId el id de la facultad
	 * @return las lineas de investigación de una facultad
	 */
	public List<LineasInvestigacion> getLineasFacultad(Long facultadId) {
		return lineasInvestigacionRepository.getLineasFacultad(facultadId);
	}

	/**
	 * Obtiene todas las lineas de investigación de una facultad
	 * 
	 * @param centroId el id del centro
	 * @return las lineas de investigación de una facultad
	 */
	public List<LineasInvestigacion> getLineasCentro(Long centroId) {
		return lineasInvestigacionRepository.getLineasCentro(centroId);
	}

	/**
	 * Obtiene todas las lineas de investigación de una facultad
	 * 
	 * @param programaId el id del programa
	 * @return las lineas de investigación de una facultad
	 */
	public List<LineasInvestigacion> getLineasPrograma(Long programaId) {
		return lineasInvestigacionRepository.getLineasPrograma(programaId);
	}

	/**
	 * Obtiene todas las lineas de investigación de una facultad
	 * 
	 * @param grupoId el id del grupo
	 * @return las lineas de investigación de una facultad
	 */
	public List<LineasInvestigacion> getLineasGrupo(Long grupoId) {
		return lineasInvestigacionRepository.getLineasGrupo(grupoId);
	}

	/**
	 * Obtiene todas las lineas de investigación de una facultad
	 * 
	 * @return las lineas de investigación de una facultad
	 */
	public List<LineasInvestigacion> findAll() {
		return lineasInvestigacionRepository.findAllLineas();
	}
}
