package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.model.RecononocimientosInvestigador;
import co.edu.uniquindio.gri.repository.CasoRevisionProduccionRepository;
import co.edu.uniquindio.gri.utilities.Util;

@Service
public class CasoRevisionProduccionDAO {

	@Autowired
	CasoRevisionProduccionRepository casoRevisionProduccionRepository;
	
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
	public boolean archivarNuevoCaso(long id, long idProduccion, String tipoProduccion, String estado) {
		try {
			casoRevisionProduccionRepository.save(new CasoRevisionProduccion(id, idProduccion, tipoProduccion, estado));
			return true;
		} catch (Exception e) {
			return false;
		}
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
	public boolean archivarNuevoCaso(long id, long idProduccion, String tipoProduccion) {
		try {
			casoRevisionProduccionRepository
					.save(new CasoRevisionProduccion(id, idProduccion, tipoProduccion, "EN CURSO"));
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
	 * Método del repositorio que retorna los casos de recolecciones de las
	 * producciones de una entidad
	 * 
	 * @param type     el tipo de la entidad (f: Facultad, p: Programa, c: Centro,
	 *                 g: Grupo de Investigación i: Investigador)
	 * @param entityId el id de la entidad
	 * @return lista de producciones
	 */
	public List<CasoRevisionProduccion> getRecolecciones() {
            return casoRevisionProduccionRepository.getRecolecciones();
	}

}
