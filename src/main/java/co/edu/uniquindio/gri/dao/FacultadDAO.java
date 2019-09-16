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
	public List<Facultad> getAllFacultades() {
		return facultadRepository.findAll();
	}

	/**
	 * Obtiene una facultad especificada por id
	 *
	 * @param idFacultad el id de la facultad
	 * @return la facultad especificada por el idFacultad
	 */
	public Facultad getFacultadById(Long facultadId) {
		return facultadRepository.findOne(facultadId);
	}

	/**
	 * Obtiene las estadísticas del sistema.
	 *
	 * @return lista con las estadísticas del sistema.
	 */
	public List<BigInteger> getStats() {
		return facultadRepository.getStats();
	}

	
	/**
	 * obtiene la cantidad por categoria de grupos, de la siguinte forma: 0-cantidad
	 * categoria A1 1-cantidad categoria A 2-cantidad categoria B 3-cantidad
	 * categoria C 4-cantidad categoria reconocidos 5-cantidad categoria no
	 * reconocidos
	 * 
	 * @param idFacultad facultad a la cual se va a llamar las cantidades por
	 *                   categoria
	 * @return una lista de la forma anteriormente mensionada
	 */
	public List<BigInteger> getCantidadCategorias(Long idFacultad) {
		return facultadRepository.getCantidadCategorias(idFacultad);
	}

}
