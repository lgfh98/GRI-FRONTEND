package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.gri.model.Produccion;
import co.edu.uniquindio.gri.model.ProduccionB;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

/**
 * Interface ProduccionRepository.
 */
@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long> {

	// Investigadores
	/**
	 * Obtiene la producción bibliográfica de un investigador.
	 *
	 * @param entityId
	 *            el id del investigador
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionB where investigador.id =:entityId and tipo.id =:tipoId ")
	public List<ProduccionB> getProduccionB(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	/**
	 * Obtiene la los trabajos dirigidos de un investigador.
	 *
	 * @param entityId
	 *            el id del investigador
	 * @return lista de trabajos dirigidos del investigador.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Produccion where investigador.id =:entityId and (tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43)")
	public List<Produccion> getTrabajosDirigidos(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción científica de un investigador.
	 *
	 * @param entityId
	 *            el id del investigador
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones científicas del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Produccion where investigador.id =:entityId and tipo.id =:tipoId")
	public List<Produccion> getProduccion(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Grupos
	/**
	 * Obtiene la producción bibliográfica de un grupo de investigación.
	 *
	 * @param entityId
	 *            el id del grupo de investigación
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionBGrupo where grupo.id =:entityId and tipo.id =:tipoId ")
	public List<ProduccionBGrupo> getProduccionBGrupo(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de un grupo de investigación.
	 * 
	 * @param entityId
	 *            el id del grupo de investigación
	 * @return lista de trabajos dirigidos.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionGrupo where grupo.id =:entityId and (tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43)")
	public List<ProduccionGrupo> getTrabajosDirigidosGrupo(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de un grupo de investigación.
	 *
	 * @param entityId
	 *            el id del grupo de investigación
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionGrupo where grupo.id =:entityId and tipo.id =:tipoId")
	public List<ProduccionGrupo> getProduccionGrupo(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Programas
	/**
	 * Obtiene la producción bibliográfica de un programa.
	 *
	 * @param entityId
	 *            el id del programa
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.programas p  where p.id =:entityId  and pb.tipo.id =:tipoId")
	public List<ProduccionBGrupo> getProduccionBPrograma(@Param("entityId") Long entityId,
			@Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de un programa.
	 *
	 * @param entityId
	 *            el id del programa
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p where p.id =:entityId and (pr.tipo.id = 1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
	public List<ProduccionGrupo> getTrabajosDirigidosPrograma(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de un programa.
	 *
	 * @param entityId
	 *            el id del programa
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p where p.id =:entityId and pr.tipo.id =:tipoId ")
	public List<ProduccionGrupo> getProduccionPrograma(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Centros
	/**
	 * Obtiene la producción bibliográfica de un centro.
	 *
	 * @param entityId
	 *            el id del centro
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.centro c where c.id =:entityId and pb.tipo.id =:tipoId")
	public List<ProduccionBGrupo> getProduccionBCentro(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de un centro.
	 *
	 * @param entityId
	 *            el id del centro
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.centro c where c.id =:entityId  and (pr.tipo.id = 1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
	public List<ProduccionGrupo> getTrabajosDirigidosCentro(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de un centro.
	 *
	 * @param entityId
	 *            el id del centro
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.centro c where c.id = :entityId and pr.tipo.id = :tipoId")
	public List<ProduccionGrupo> getProduccionCentro(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Facultades
	/**
	 * Obtiene la producción bibliográfica de una facultad con respecto a sus
	 * programas.
	 *
	 * @param entityId
	 *            el id de la facultad
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.programas p join p.facultad f where f.id =:entityId  and pb.tipo.id =:tipoId ")
	public List<ProduccionBGrupo> getProduccionBFacultadPrograma(@Param("entityId") Long entityId,
			@Param("tipoId") Long tipoId);

	/**
	 * Obtiene la producción bibliográfica de una facultad con respecto a sus
	 * centros.
	 *
	 * @param entityId
	 *            el id de la facultad
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.centro c join c.facultad f where f.id =:entityId  and pb.tipo.id =:tipoId ")
	public List<ProduccionBGrupo> getProduccionBFacultadCentro(@Param("entityId") Long entityId,
			@Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de una facultad respecto a sus programas.
	 *
	 * @param entityId
	 *            el id de la facultad
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p join p.facultad f where f.id =:entityId  and (pr.tipo.id =  1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
	public List<ProduccionGrupo> getTrabajosDirigidosFacultadPrograma(@Param("entityId") Long entityId);

	/**
	 * Obtiene los trabajos dirigidos de una facultad respecto a sus centros.
	 *
	 * @param entityId
	 *            el id de la facultad
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p join p.facultad f where f.id =:entityId  and (pr.tipo.id =  1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
	public List<ProduccionGrupo> getTrabajosDirigidosFacultadCentro(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de una facultad respecto a sus programas.
	 *
	 * @param entityId
	 *            el id de la facultad
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p join p.facultad f where f.id =:entityId  and pr.tipo.id =:tipoId ")
	public List<ProduccionGrupo> getProduccionFacultadPrograma(@Param("entityId") Long entityId,
			@Param("tipoId") Long tipoId);

	/**
	 * Obtiene la producción de una facultad respecto a sus centros.
	 *
	 * @param entityId
	 *            el id de la facultad
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.centro c join c.facultad f where f.id =:entityId  and pr.tipo.id =:tipoId")
	public List<ProduccionGrupo> getProduccionFacultadCentro(@Param("entityId") Long entityId,
			@Param("tipoId") Long tipoId);

	// General
	/**
	 * Obtiene la producción bibliográfica general de toda la universidad.
	 *
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(id, identificador, autores, anio, referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo where tipo.id =:tipoId")
	public List<ProduccionBGrupo> getProduccionBGeneral(@Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos general de toda la universidad.
	 * 
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(id, autores, anio, referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43")
	public List<ProduccionGrupo> getTrabajosDirigidosGeneral();

	/**
	 * Obtiene la producción general de toda la universidad.
	 *
	 * @param tipoId
	 *            el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(id, autores, anio, referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo where tipo.id = :tipoId")
	public List<ProduccionGrupo> getProduccionGeneral(@Param("tipoId") Long tipoId);

	// Búsquedas

	/**
	 * Obtiene las producciones bibliográficas contenidas en los GrupLAC de los
	 * grupos de acuerdo a una cadena de búsqueda.
	 *
	 * @param cadena
	 *            la cadena de búsqueda
	 * @return lista de producciones biblográficas correspondientes con la cadena de
	 *         búsqueda.
	 */
	@SuppressWarnings("rawtypes")
	// Grupos
	@Query(value = "SELECT bg.referencia, bg.autores, bg.anio, tp.nombre as tipo,  gr.nombre FROM gri.bibliograficasg bg  JOIN gri.tipos tp ON tp.id=bg.tipo_id JOIN gri.grupos gr ON bg.grupos_id= gr.id WHERE unaccent(bg.referencia) LIKE unaccent('%'||:cadena||'%') UNION SELECT pg.referencia, pg.autores, pg.anio, tp.nombre as tipo,  gr.nombre FROM gri.produccionesg pg  JOIN gri.tipos tp ON tp.id=pg.tipo_id JOIN gri.grupos gr ON pg.grupos_id= gr.id WHERE unaccent(pg.referencia) LIKE unaccent('%'||:cadena||'%')", nativeQuery = true)
	public List getProduccionGBusqueda(@Param("cadena") String cadena);

	// Investigadores
	/**
	 * Obtiene las producciones bibliográficas contenidas en los CvLAC de los
	 * investigadores de acuerdo a una cadena de búsqueda.
	 *
	 * @param cadena
	 *            la cadena de búsqueda
	 * @return lista de producciones biblográficas correspondientes con la cadena de
	 *         búsqueda.
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT bg.referencia, bg.autores, bg.anio, tp.nombre as tipo,  inv.nombre FROM gri.bibliograficas bg  JOIN gri.tipos tp ON tp.id=bg.tipo_id JOIN gri.investigadores inv ON bg.investigadores_id= inv.id WHERE unaccent(bg.referencia) LIKE unaccent('% '||:cadena||' %') UNION SELECT pg.referencia, pg.autores, pg.anio, tp.nombre as tipo,  inv.nombre FROM gri.producciones pg  JOIN gri.tipos tp ON tp.id=pg.tipo_id JOIN gri.investigadores inv ON pg.investigadores_id= inv.id WHERE unaccent(pg.referencia) LIKE unaccent('% '||:cadena||' %')", nativeQuery = true)
	public List getProduccionBusqueda(@Param("cadena") String cadena);
	
	@SuppressWarnings("rawtypes")
	@Query(value="SELECT b.id, b.referencia, b.autores, b.anio, t.nombre, t.tipoproduccion_id, b.inventario FROM gri.bibliograficasg b JOIN gri.tipos t ON t.id = b.tipo_id WHERE b.grupos_id =:id UNION ALL SELECT p.id, p.referencia, p.autores, p.anio, t.nombre, t.tipoproduccion_id, p.inventario FROM gri.produccionesg p JOIN gri.tipos t ON t.id = p.tipo_id WHERE p.grupos_id =:id" , nativeQuery = true)
	public List getAllProducciones(@Param("id") Long id);

	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param estado, el estado a actualizar de la producción. 0 si no se encuentra en custodia. 1 en caso contrario. 
	 * @param id, el identificador de la producción en base de datos.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE co.edu.uniquindio.gri.model.ProduccionGrupo p SET  p.estado=:estado WHERE p.id=:id")
	public void updateProduccionGrupo(@Param("id") Long id, @Param("estado") int estado);

	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param estado, el estado a actualizar de la producción. 0 si no se encuentra en custodia. 1 en caso contrario. 
	 * @param id, el identificador de la producción en base de datos.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE co.edu.uniquindio.gri.model.ProduccionBGrupo p SET  p.estado=:estado WHERE p.id=:id")
	public void updateProduccionBGrupo(@Param("id") Long id, @Param("estado") int estado);

}
