package co.edu.uniquindio.gri.dao;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.controller.ProduccionController;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.model.Tipo;
import co.edu.uniquindio.gri.repository.ProduccionRepository;
import co.edu.uniquindio.gri.repository.TipoRepository;
import co.edu.uniquindio.gri.utilities.Util;

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
	
	private static final Logger log = LoggerFactory.getLogger(ProduccionDAO.class);

	/**
	 * Método que obtiene las producciones que no están en custodia
	 * 
	 * @return
	 */
	public List<ProduccionGrupo> getProduccionesSinCustodia() {
		return produccionRepository.getProduccionesSinCustodia();
	}

	/**
	 * Método que obtiene las producciones bbilbiográficas que no están en custodia
	 * 
	 * @return
	 */
	public List<ProduccionBGrupo> getProduccionesBSinCustodia() {
		return produccionRepository.getProduccionesBSinCustodia();
	}

	/**
	 * Método que actualiza el estado de una producción al estado dado por
	 * parámetros (no alterna estados)
	 * 
	 * @param id     ID de la producción
	 * @param tipo   tipo de producción
	 * @param estado estado de la producción, 0 - no en custodia, 1 en custodia, 2-
	 *               en proceso de recolección
	 * @return
	 */
	public boolean actualizarEstadoDeProduccion(long id, String tipo, int estado) {
		
		log.info("Actualizando estado de la producción " + id + " de tipo " + tipo + " a " + estado);
		
		if (tipo.equals("bibliografica")) {
			produccionRepository.updateProduccionBGrupo(id, estado);
			return true;
		} else if (tipo.equals("generica")) {
			produccionRepository.updateProduccionGrupo(id, estado);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Obtiene una producción bibliográficas con un id dado por parámetro.
	 * 
	 * @param id	 
	 * @return la producción bibliográfica
	 */
	public ProduccionBGrupo getProduccionB(long id){
		return produccionRepository.getProduccionB(id);
	}
	
	/**
	 * Obtiene todas las producciones.
	 * 
	 * @param id		 
	 * @return la producción genérica
	 */
	public ProduccionGrupo getProduccion(long id){
		return produccionRepository.getProduccion(id);
	}

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
		Tipo tipo = tipoRepository.findById(tipoId).orElse(null);
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
	 * @param tipo,   identifica si la producción es o no bibliográfica,
	 *                bibligráfica es 3
	 * @param estado, el estado de la producción. 0 si no se encuentra en custodia.
	 *                1 en caso contrario.
	 * @param prodId, el identificador de la producción en base de datos.
	 * @return true, si la actualización se realizó satisfactoriamente.
	 * @deprecated use {@link #actualizarEstadoDeProduccion()} instead. 
	 */
	@Deprecated
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
	 * @return cantidad de actividades de formacion.
	 * @param idFacultad id de la facultad
	 * @param tipoId     tipo de producción
	 */
	public BigInteger getCantidadProduccionesFacultadPorTipo(String facultadId, String tipoId) {
		return produccionRepository.getCantidadProduccionesFacultadPorTipo(Long.parseLong(facultadId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones bibliográficas total de la facultad por
	 * un tipo especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param idFacultad id de la facultad
	 * @param tipoId     tipo de producción
	 */
	public BigInteger getCantidadProduccionesBFacultadPorTipo(String facultadId, String tipoId) {
		return produccionRepository.getCantidadProduccionesBFacultadPorTipo(Long.parseLong(facultadId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total de la facultad por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param idFacultad id de la facultad
	 * @param tipoId     tipo de producción
	 */
	public BigInteger getCantidadProduccionesFacultadPorSubTipo(String facultadId, String tipoId) {
		return produccionRepository.getCantidadProduccionesFacultadPorSubTipo(Long.parseLong(facultadId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del centro por un tipo especifico
	 * de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param centroId id del centro
	 * @param tipoId   tipo de producción
	 */
	public BigInteger getCantidadProduccionesCentroPorTipo(String centroId, String tipoId) {
		return produccionRepository.getCantidadProduccionesCentroPorTipo(Long.parseLong(centroId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones bibliográficas total del centro por un
	 * tipo especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param centroId id del centro
	 * @param tipoId   tipo de producción
	 */
	public BigInteger getCantidadProduccionesBCentroPorTipo(String centroId, String tipoId) {
		return produccionRepository.getCantidadProduccionesBCentroPorTipo(Long.parseLong(centroId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del centro por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param centroId id del centro
	 * @param tipoId   tipo de producción
	 */
	public BigInteger getCantidadProduccionesCentroPorSubTipo(String centroId, String tipoId) {
		return produccionRepository.getCantidadProduccionesCentroPorSubTipo(Long.parseLong(centroId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del programa por un tipo especifico
	 * de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param programaId id del programa
	 * @param tipoId     tipo de producción
	 */
	public BigInteger getCantidadProduccionesProgramaPorTipo(String centroId, String tipoId) {
		return produccionRepository.getCantidadProduccionesProgramaPorTipo(Long.parseLong(centroId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones bibliográficas total del centro por un
	 * tipo especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param programaId id del programa
	 * @param tipoId     tipo de producción
	 */
	public BigInteger getCantidadProduccionesBProgramaPorTipo(String centroId, String tipoId) {
		return produccionRepository.getCantidadProduccionesBProgramaPorTipo(Long.parseLong(centroId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del programa por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param programaId id del programa
	 * @param tipoId     tipo de producción
	 */
	public BigInteger getCantidadProduccionesProgramaPorSubTipo(String centroId, String tipoId) {
		return produccionRepository.getCantidadProduccionesProgramaPorSubTipo(Long.parseLong(centroId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del grupo por un tipo especifico de
	 * producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param grupoId id del grupo
	 * @param tipoId  tipo de producción
	 */
	public BigInteger getCantidadProduccionesGrupoPorTipo(String grupoId, String tipoId) {
		return produccionRepository.getCantidadProduccionesGrupoPorTipo(Long.parseLong(grupoId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones bibliográficas total del grupo por un
	 * tipo especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param grupoId id del grupo
	 * @param tipoId  tipo de producción
	 */
	public BigInteger getCantidadProduccionesBGrupoPorTipo(String grupoId, String tipoId) {
		return produccionRepository.getCantidadProduccionesBGrupoPorTipo(Long.parseLong(grupoId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del grupo por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param grupoId id del grupo
	 * @param tipoId  tipo de producción
	 */
	public BigInteger getCantidadProduccionesGrupoPorSubTipo(String grupoId, String tipoId) {
		return produccionRepository.getCantidadProduccionesGrupoPorSubTipo(Long.parseLong(grupoId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del investigador por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param investigadorId id del investigador
	 * @param tipoId         tipo de producción
	 */
	public BigInteger getCantidadProduccionesInvestigadorPorTipo(String investigadorId, String tipoId) {
		return produccionRepository.getCantidadProduccionesInvestigadorPorTipo(Long.parseLong(investigadorId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones bibliográficas total del investigador por
	 * un tipo especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param investigadorId id del investigador
	 * @param tipoId         tipo de producción
	 */
	public BigInteger getCantidadProduccionesBInvestigadorPorTipo(String investigadorId, String tipoId) {
		return produccionRepository.getCantidadProduccionesBInvestigadorPorTipo(Long.parseLong(investigadorId),
				Long.parseLong(tipoId));
	}

	/**
	 * Obtiene la cantidad de producciones total del investigador por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param investigadorId id del investigador
	 * @param tipoId         tipo de producción
	 */
	public BigInteger getCantidadProduccionesInvestigadorPorSubTipo(String investigadorId, String tipoId) {
		return produccionRepository.getCantidadProduccionesInvestigadorPorSubTipo(Long.parseLong(investigadorId),
				Long.parseLong(tipoId));
	}

}
