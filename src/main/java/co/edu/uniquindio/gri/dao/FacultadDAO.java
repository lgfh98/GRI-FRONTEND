package co.edu.uniquindio.gri.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Facultad;
import co.edu.uniquindio.gri.repository.FacultadRepository;

/**
 * Clase FacultadDAO.
 */
@Service
public class FacultadDAO {

	/** Repository para facultades. */
	@Autowired
	FacultadRepository facultadRepository;
	
	/**
	 * Obtiene todas las facultades.
	 *
	 * @return lista con todas las facultades.
	 */
	public List<Facultad> getAllFacultades(){
		return facultadRepository.findAll();
	}
	
	/**
	 * Obtiene una facultad especificada por id
	 *
	 * @param idFacultad el id de la facultad
	 * @return la facultad especificada por el idFacultad
	 */
	public Facultad getFacultadById(Long facultadId){
		return facultadRepository.findOne(facultadId);
	}
	
	/**
	 * Obtiene las estadísticas del sistema.
	 *
	 * @return lista con las estadísticas del sistema. 
	 */
	public List<BigInteger> getStats(){
		return facultadRepository.getStats();
	}
	
}
