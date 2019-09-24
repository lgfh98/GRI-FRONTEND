package co.edu.uniquindio.gri.dao;

import java.math.BigInteger;
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
	
	/**
	 * Obtiene el resumen general de la facultad en números
	 * la lista en cada posición obtiene lo siguiente:
	 * 0 . Cantidad grupos de investigación 
	 * 1 - Cantidad de lineas investigación 
	 * 2 - Cantidad investigadores
	 * 3 - Grupos categoria A1
	 * 4 - Grupos categoria A
	 * 5 - Grupos categoria B
	 * 6 - Grupos categoria C
	 * 7 - Grupos reconocidos
	 * 8 - Grupos no reconocidos
	 * 9 - Investigadores emeritos
	 * 10 - Investigador senior
	 * 11 - Investigador asociados
	 * 12 - Investigador junior
	 * 13 - Investigador sin categoria
	 * 14 - Docentes con doctorado
	 * 15 - Docentes con magister
	 * 16 - Docentes especialistas
	 * 17 - Docentes pregrado
	 * @return lista con totales anteriores.
	 * @param centroId id del centro
	 */
	public List<BigInteger> getResumenGeneralCentros(Long centroId) {
		return centroRepository.getResumenGeneralCentros(centroId);
	}

	/**
	 * Obtiene un centro de investigación especificado por un id de facultad.
	 *
	 * @param facultadId el id de la facultad
	 * @return el centro especificado por el id de facultad
	 */
	public Object getAllCentrosFacultad(Long facultadId) {
		return centroRepository.getAllCentrosFacultad(facultadId);
	}
}
