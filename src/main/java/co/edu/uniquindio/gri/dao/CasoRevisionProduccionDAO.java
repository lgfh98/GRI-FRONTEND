package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.bonitaapi.GestorDeCasosBonita;
import co.edu.uniquindio.gri.exception.CasoBonitaDuplicado;
import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.model.RecononocimientosInvestigador;
import co.edu.uniquindio.gri.repository.CasoRevisionProduccionRepository;
import co.edu.uniquindio.gri.utilities.Util;

@Service
public class CasoRevisionProduccionDAO {

	@Autowired
	CasoRevisionProduccionRepository casoRevisionProduccionRepository;

	@Autowired
	GestorDeCasosBonita gestorDeCasosBonita;

	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

	private static final Logger log = LoggerFactory.getLogger(CasoRevisionProduccionDAO.class);

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
			if (caso.getIdProduccion() == idProduccion && tipoProduccion.equals(caso.getTipoProduccion())) {
				casoRevisionProduccionRepository.delete(caso);
				return true;
			}
		}
		return false;
	}

	/**
	 * Método del repositorio encargado de obtener todos los casos que estén en un
	 * estado específico
	 * 
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
	 * es el inicial ("EN CURSO"), también sobreescribe esa entidad reiniciando el
	 * proceso en bonita en caso de que ya exista
	 * 
	 * @param id             id del caso en bonita
	 * @param idProduccion   id de la produccion
	 * @param tipoProduccion tipo de la produccion
	 */
	public boolean archivarCaso(long id, Long idProduccion, String tipoProduccion) {
		try {
			if (casoRevisionProduccionRepository.getCasoPorProduccion(idProduccion, tipoProduccion) != null) {
				log.warn("Ya existe un caso para la producción " + idProduccion + " de tipo " + tipoProduccion
						+ " se eliminará y se creará uno nuevo");
				//Reescritura del caso a nivel de BD y a nivel bonita en caso de que ya exista
				CasoRevisionProduccion c = casoRevisionProduccionDAO.getCasoPorProduccion(idProduccion, tipoProduccion);
				gestorDeCasosBonita.eliminarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(c.getId());
				casoRevisionProduccionDAO.eliminarCaso(c.getId());
			}
			//Registro de nuevo caso
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
	 * 
	 * @param id
	 * @return
	 */
	public CasoRevisionProduccion getCaso(long id) {
		return casoRevisionProduccionRepository.getOne(id);
	}

	public CasoRevisionProduccion getCasoPorProduccion(long id, String tipoDeProduccion) {
		return casoRevisionProduccionRepository.getCasoPorProduccion(id, tipoDeProduccion);
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
