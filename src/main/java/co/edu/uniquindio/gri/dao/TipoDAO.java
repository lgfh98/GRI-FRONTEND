package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Tipo;
import co.edu.uniquindio.gri.repository.TipoRepository;

/**
 * Class TipoDAO.
 */
@Service
public class TipoDAO {

	/** Repository para tipos. */
	@Autowired
	TipoRepository tipoRepository;
	
	/**
	 * Obtiene todos los tipos de producción.
	 *
	 * @return lista con todos los tipos de producción.
	 */
	public List<Tipo> getAllTipos(){
		return tipoRepository.findAll();
	}
	
	/**
	 * Obtiene un tipos de producción especificado por un id.
	 *
	 * @param programaId el id del tipo
	 * @return el tipos de producción especificado por el id
	 */
	public Tipo getTipoById(Long tipoId){
		return tipoRepository.findOne(tipoId);
	}
}
