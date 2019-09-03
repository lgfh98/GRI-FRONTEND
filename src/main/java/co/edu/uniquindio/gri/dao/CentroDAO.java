package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Centro;
import co.edu.uniquindio.gri.repository.CentroRepository;

/**
 * Clase CentroDAO.
 */
@Service
public class CentroDAO {

	/** Repository para centros. */
	@Autowired
	CentroRepository centroRepository;
	
	/**
	 * Obtiene todos los centros de investigación.
	 *
	 * @return lista con todos los centros de investigación.
	 */
	public List<Centro> getAllCentros(){
		return centroRepository.findAll();
	}
	
	/**
	 * Obtiene un centro de investigación especificado por un id.
	 *
	 * @param centroId el id del centro de investigación
	 * @return el centro especificado por el id
	 */
	public Centro getCentroById(Long centroId){
		return centroRepository.findOne(centroId);
	}
}
