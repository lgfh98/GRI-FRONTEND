package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.repository.CasoRevisionProduccionRepository;
import co.edu.uniquindio.gri.utilities.Util;

@Service
public class CasoRevisionProduccionDAO {

	@Autowired
	CasoRevisionProduccionRepository casoRevisionProduccionRepository;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean eliminarCaso(Long id) {
		casoRevisionProduccionRepository.deleteById(id);
		return true;
	}
	
	public boolean eliminarCaso(Long idProduccion, String tipoProduccion) {
		for (CasoRevisionProduccion caso : casoRevisionProduccionRepository.findAll()) {
			if(caso.getIdProduccion() == idProduccion && tipoProduccion.equals(caso.getTipoProduccion())) {
				casoRevisionProduccionRepository.delete(caso);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Método del repositorio encargado de obtener todos los casos que estén en un estado específico
	 * @param estado el estado deseado a filtrar
	 * @return una lista de casos con un estado específico
	 */
	public List<CasoRevisionProduccion> getCasosPorEstado(String estado) {
		return casoRevisionProduccionRepository.getCasosPorEstado(estado);
	}

	/**
	 * Método encargado de guardar una nueva entidad CasoRevisionProduccion,
	 * creándola o actualizándola si ya existe
	 * 
	 * @param id             id del caso en bonita
	 * @param idProduccion   id de la produccion
	 * @param tipoProduccion tipo de la produccion
	 * @param estado         estado del caso ("EN CURSO" o "FINALIZADO")
	 */
	public boolean archivarCaso(long id, Long idProduccion, String tipoProduccion, String estado) {
		casoRevisionProduccionRepository.save(new CasoRevisionProduccion(id, idProduccion, tipoProduccion, estado));
		return true;
	}

	/**
	 * Método encargado de guardar una nueva entidad CasoRevisionProduccion,
	 * creándola o actualizándola si ya existe y guardando su estado por defecto que
	 * es el inicial ("EN CURSO")
	 * 
	 * @param id             id del caso en bonita
	 * @param idProduccion   id de la produccion
	 * @param tipoProduccion tipo de la produccion
	 */
	public boolean archivarCaso(long id, Long idProduccion, String tipoProduccion) {
		try {
			casoRevisionProduccionRepository
					.save(new CasoRevisionProduccion(id, idProduccion, tipoProduccion, Util.BONITA_CASO_EN_CURSO));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Método del repositorio que retorna los casos de revisión y subida de las
	 * producciones de un tipo dado por parámetro
	 * 
	 * @param tipoDeProduccion el tipo de la producción
	 * @return una lista de casos de revisión de producciones
	 */
	public List<CasoRevisionProduccion> getCasosPorTipoDeProduccion(String tipoDeProduccion) {
		return casoRevisionProduccionRepository.getCasosPorTipoDeProduccion(tipoDeProduccion);
	}
	
	/**
	 * Método del repositorio encargado de obtener un caso dado un id
	 * @param id
	 * @return
	 */
	public CasoRevisionProduccion getCaso(long id) {
		return casoRevisionProduccionRepository.getOne(id);
	}
	
	public CasoRevisionProduccion getCasoPorProduccion(long id, String tipoDeProduccion) {
		return casoRevisionProduccionRepository.getCasoPorProduccion(id,tipoDeProduccion);
	}

}
