package co.edu.uniquindio.gri.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.model.Tipo;
import co.edu.uniquindio.gri.repository.ProduccionRepository;
import co.edu.uniquindio.gri.repository.TipoRepository;

/**
 * Class ProduccionDAO.
 */
@Service
public class ProduccionDAO {

	/** Repository para producciones. */
	@Autowired
	ProduccionRepository produccionRepository;

	/** Repository para tipos. */
	@Autowired
	TipoRepository tipoRepository;

	/**
	 * Obtiene las producciones de una entidad específica.
	 *
	 * @param type     el tipo de la entidad (f: Facultad, p: Programa, c: Centro,
	 *                 g: Grupo de Investigación i: Investigador)
	 * @param entityId el id de la entidad
	 * @param tipoId   el tipo de la producción a obtener
	 * @return lista de producciones
	 */
	@SuppressWarnings("rawtypes")
	public List getProducciones(String type, Long entityId, Long tipoId) {
		Tipo tipo = tipoRepository.findOne(tipoId);
		long idTipoProd = tipo.getTipoProduccion().getId();

		if (type.equals("i")) {
			if (idTipoProd == 3) {
				return produccionRepository.getProduccionB(entityId, tipoId);
			} else if (tipoId == 1) {
				return produccionRepository.getTrabajosDirigidos(entityId);
			} else {
				return produccionRepository.getProduccion(entityId, tipoId);
			}

		} else if (type.equals("g")) {
			if (idTipoProd == 3) {
				return produccionRepository.getProduccionBGrupo(entityId, tipoId);
			} else if (tipoId == 1) {
				return produccionRepository.getTrabajosDirigidosGrupo(entityId);
			} else {
				return produccionRepository.getProduccionGrupo(entityId, tipoId);
			}

		} else if (type.equals("p")) {
			if (idTipoProd == 3) {
				return produccionRepository.getProduccionBPrograma(entityId, tipoId);
			} else if (tipoId == 1) {
				return produccionRepository.getTrabajosDirigidosPrograma(entityId);
			} else {
				return produccionRepository.getProduccionPrograma(entityId, tipoId);
			}

		} else if (type.equals("c")) {
			if (idTipoProd == 3) {
				return produccionRepository.getProduccionBCentro(entityId, tipoId);
			} else if (tipoId == 1) {
				return produccionRepository.getTrabajosDirigidosCentro(entityId);
			} else {
				return produccionRepository.getProduccionCentro(entityId, tipoId);
			}

		} else if (type.equals("f")) {
			if (idTipoProd == 3) {
				List<ProduccionBGrupo> prod_programas = produccionRepository.getProduccionBFacultadPrograma(entityId,
						tipoId);
				List<ProduccionBGrupo> prod_centros = produccionRepository.getProduccionBFacultadCentro(entityId,
						tipoId);

				for (ProduccionBGrupo produccion : prod_centros) {
					if (!prod_programas.contains(produccion)) {
						prod_programas.add(produccion);
					}
				}

				return prod_programas;
			} else if (tipoId == 1) {
				List<ProduccionGrupo> prod_programas = produccionRepository
						.getTrabajosDirigidosFacultadPrograma(entityId);
				List<ProduccionGrupo> prod_centros = produccionRepository.getTrabajosDirigidosFacultadCentro(entityId);

				for (ProduccionGrupo produccion : prod_centros) {
					if (!prod_programas.contains(produccion)) {
						prod_programas.add(produccion);
					}
				}

				return prod_programas;
			} else {
				List<ProduccionGrupo> prod_programas = produccionRepository.getProduccionFacultadPrograma(entityId,
						tipoId);
				List<ProduccionGrupo> prod_centros = produccionRepository.getProduccionFacultadCentro(entityId, tipoId);

				for (ProduccionGrupo produccion : prod_centros) {
					if (!prod_programas.contains(produccion)) {
						prod_programas.add(produccion);
					}
				}

				return prod_programas;
			}

		} else {
			if (idTipoProd == 3) {
				return produccionRepository.getProduccionBGeneral(tipoId);
			} else if (tipoId == 1) {
				return produccionRepository.getTrabajosDirigidosGeneral();
			} else {
				return produccionRepository.getProduccionGeneral(tipoId);
			}

		}
	}

	/**
	 * Obtiene las producciones de acuerdo a una cadena de búsqueda.
	 *
	 * @param tipo,  el tipo de búsqueda a realizar (i: CvLAC, g: GrupLAC)
	 * @param cadena la cadena de búsqueda
	 * @return lista de producciones correspondientes con la cadena de búsqueda.
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getProduccionBusqueda(String tipo, String cadena) {
		if (tipo.equals("g")) {
			return produccionRepository.getProduccionGBusqueda(cadena);
		} else {
			return produccionRepository.getProduccionBusqueda(cadena);
		}
	}

	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o
	 * no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param tipo,   identifica si la producción es o no bibliográfica
	 * @param estado, el estado de la producción. 0 si no se encuentra en custodia.
	 *                1 en caso contrario.
	 * @param prodId, el identificador de la producción en base de datos.
	 * @return true, si la actualización se realizó satisfactoriamente.
	 */
	public boolean actualizarProducciones(String tipo, int estado, Long prodId) {
		if (tipo.equals("3")) {
			if (estado == 0) {
				produccionRepository.updateProduccionBGrupo(prodId, 1);
				return true;
			} else {
				produccionRepository.updateProduccionBGrupo(prodId, 0);
				return true;
			}
		} else {
			if (estado == 0) {
				produccionRepository.updateProduccionGrupo(prodId, 1);
				return true;
			} else {
				produccionRepository.updateProduccionGrupo(prodId, 0);
				return true;
			}
		}

	}

	/**
	 * Obtiene todas las producciones de un grupo de investigación.
	 *
	 * @param id el identificador del grupo de investigación.
	 * @return Lista con todas las producciones del grupo de investigación.
	 */
	@SuppressWarnings("rawtypes")
	public List getAllProducciones(Long id) {
		return produccionRepository.getAllProducciones(id);
	}

	/**
	 * Obtiene la cantidad de actividades de formación total de la universidad.
	 * 
	 * @return cantidad de actividades de formacion.
	 */
	public BigInteger getCantidadActividadesFormacion() {
		return produccionRepository.getCantidadActividadesFormacion();
	}

	/**
	 * Obtiene la cantidad de actividades como evaluador total de la universidad.
	 * 
	 * @return cantidad de actividades como evaluador.
	 */
	public BigInteger getCantidadActividadesEvaluador() {
		return produccionRepository.getCantidadActividadesEvaluador();
	}

	/**
	 * Obtiene la cantidad de actividades de apropiacion social total de la
	 * universidad.
	 * 
	 * @return cantidad de actividades de apropiacion social.
	 */
	public BigInteger getCantidadApropiacionSocial() {
		return produccionRepository.getCantidadApropiacionSocial();
	}

	/**
	 * Obtiene la cantidad de producciones bibliograficas total de la universidad.
	 * 
	 * @return cantidad de producciones bibliograficas.
	 */
	public BigInteger getCantidadProduccionesBibliograficas() {
		return produccionRepository.getCantidadProduccionesBibliograficas();
	}

	/**
	 * Obtiene la cantidad de producciones tecnicas y teconologicas total de la
	 * universidad.
	 * 
	 * @return cantidad de producciones tecnicas y tecnologicas.
	 */
	public BigInteger getCantidadTecnicasTecnologicas() {
		return produccionRepository.getCantidadTecnicasTecnologicas();
	}

	/**
	 * Obtiene la cantidad de producciones en arte total de la universidad.
	 * 
	 * @return cantidad de producciones en arte.
	 */
	public BigInteger getCantidadProduccionesArte() {
		return produccionRepository.getCantidadProduccionesArte();
	}

	/**
	 * Obtiene la cantidad de producciones "Demas Trabajos" de la universidad.
	 * 
	 * @return cantidad de producciones en arte "Demas Trabajos" de la universidad.
	 */
	public BigInteger getCantidadProduccionesDemasTrabajos() {
		return produccionRepository.getCantidadProduccionesDemasTrabajos();
	}

	/**
	 * Obtiene la cantidad de producciones "Proyectos" de la universidad.
	 * 
	 * @return cantidad de producciones en arte "Proyectos" de la universidad.
	 */
	public BigInteger getCantidadProduccionesProyectos() {
		return produccionRepository.getCantidadProduccionesProyectos();
	}

	/**
	 * Obtiene la cantidad de producciones total de la facultad por un tipo
	 * especifico de producción.
	 * 
	 * @param id
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param idFacultad id de la facultad
	 * @param tipoId tipo de producción
	 */
	public BigInteger getCantidadProduccionesFacultadPorTipo(String facultadId, String tipoId) {
		return produccionRepository.getCantidadProduccionesFacultadPorTipo(Long.parseLong(facultadId), Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones bibliográficas total de la facultad por un tipo
	 * especifico de producción.
	 * 
	 * @param id
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param idFacultad id de la facultad
	 * @param tipoId tipo de producción
	 */
	public BigInteger getCantidadProduccionesBFacultadPorTipo(String facultadId, String tipoId) {
		return produccionRepository.getCantidadProduccionesBFacultadPorTipo(Long.parseLong(facultadId), Long.parseLong(tipoId));
	}
	
	/**
	 * Obtiene la cantidad de producciones total de la facultad por un sub tipo
	 * especifico de producción.
	 * 
	 * @param id
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param idFacultad id de la facultad
	 * @param tipoId tipo de producción
	 */
	public BigInteger getCantidadProduccionesFacultadPorSubTipo(String facultadId, String tipoId) {
		return produccionRepository.getCantidadProduccionesFacultadPorSubTipo(Long.parseLong(facultadId), Long.parseLong(tipoId));
	}
}
