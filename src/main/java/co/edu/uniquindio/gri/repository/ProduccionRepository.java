package co.edu.uniquindio.gri.repository;

import java.math.BigInteger;
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

	//Para Bonita
	
	
	/**
	 * Obtiene una producción bibliográficas con un id dado por parámetro.
	 * 
	 * @param id	 
	 * @return la producción bibliográfica
	 */

	@Query("FROM co.edu.uniquindio.gri.model.ProduccionBGrupo p where p.id = :id")
    ProduccionBGrupo getProduccionB(@Param("id") Long id);
	
	/**
	 * Obtiene todas las producciones.
	 * 
	 * @param id		 
	 * @return la producción genérica
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionGrupo p where p.id = :id")
    ProduccionGrupo getProduccion(@Param("id") Long id);
	
	/**
	 * Obtiene todas las producciones que no están en custodia.
	 *
	 * @return lista de producciones que no están en custodia
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionBGrupo p where repetido <> 'ES REPETIDO' and p.estado = 0")
    List<ProduccionBGrupo> getProduccionesBSinCustodia();
	
	/**
	 * Obtiene todas las producciones bibliográficas que no están en custodia.
	 *
	 * @return lista de producciones que no están en custodia
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionGrupo p where repetido <> 'ES REPETIDO' and p.estado = 0")
    List<ProduccionGrupo> getProduccionesSinCustodia();
	
	// Investigadores
	/**
	 * Obtiene la producción bibliográfica de un investigador.
	 *
	 * @param entityId el id del investigador
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionB where investigador.id =:entityId and tipo.id =:tipoId ")
    List<ProduccionB> getProduccionB(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	/**
	 * Obtiene la los trabajos dirigidos de un investigador.
	 *
	 * @param entityId el id del investigador
	 * @return lista de trabajos dirigidos del investigador.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Produccion where investigador.id =:entityId and (tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43)")
    List<Produccion> getTrabajosDirigidos(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción científica de un investigador.
	 *
	 * @param entityId el id del investigador
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones científicas del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Produccion where investigador.id =:entityId and tipo.id =:tipoId")
    List<Produccion> getProduccion(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Grupos
	/**
	 * Obtiene la producción bibliográfica de un grupo de investigación.
	 *
	 * @param entityId el id del grupo de investigación
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionBGrupo where grupo.id =:entityId and tipo.id =:tipoId ")
    List<ProduccionBGrupo> getProduccionBGrupo(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de un grupo de investigación.
	 * 
	 * @param entityId el id del grupo de investigación
	 * @return lista de trabajos dirigidos.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionGrupo where grupo.id =:entityId and (tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43)")
    List<ProduccionGrupo> getTrabajosDirigidosGrupo(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de un grupo de investigación.
	 *
	 * @param entityId el id del grupo de investigación
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("FROM co.edu.uniquindio.gri.model.ProduccionGrupo where grupo.id =:entityId and tipo.id =:tipoId")
    List<ProduccionGrupo> getProduccionGrupo(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Programas
	/**
	 * Obtiene la producción bibliográfica de un programa.
	 *
	 * @param entityId el id del programa
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.programas p  where p.id =:entityId  and pb.tipo.id =:tipoId")
    List<ProduccionBGrupo> getProduccionBPrograma(@Param("entityId") Long entityId,
                                                  @Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de un programa.
	 *
	 * @param entityId el id del programa
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p where p.id =:entityId and (pr.tipo.id = 1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
    List<ProduccionGrupo> getTrabajosDirigidosPrograma(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de un programa.
	 *
	 * @param entityId el id del programa
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p where p.id =:entityId and pr.tipo.id =:tipoId ")
    List<ProduccionGrupo> getProduccionPrograma(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Centros
	/**
	 * Obtiene la producción bibliográfica de un centro.
	 *
	 * @param entityId el id del centro
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.centro c where c.id =:entityId and pb.tipo.id =:tipoId")
    List<ProduccionBGrupo> getProduccionBCentro(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de un centro.
	 *
	 * @param entityId el id del centro
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.centro c where c.id =:entityId  and (pr.tipo.id = 1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
    List<ProduccionGrupo> getTrabajosDirigidosCentro(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de un centro.
	 *
	 * @param entityId el id del centro
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.centro c where c.id = :entityId and pr.tipo.id = :tipoId")
    List<ProduccionGrupo> getProduccionCentro(@Param("entityId") Long entityId, @Param("tipoId") Long tipoId);

	// Facultades
	/**
	 * Obtiene la producción bibliográfica de una facultad con respecto a sus
	 * programas.
	 *
	 * @param entityId el id de la facultad
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.programas p join p.facultad f where f.id =:entityId  and pb.tipo.id =:tipoId ")
    List<ProduccionBGrupo> getProduccionBFacultadPrograma(@Param("entityId") Long entityId,
                                                          @Param("tipoId") Long tipoId);

	/**
	 * Obtiene la producción bibliográfica de una facultad con respecto a sus
	 * centros.
	 *
	 * @param entityId el id de la facultad
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(pb.id, pb.identificador, pb.autores, pb.anio, pb.referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pb join pb.grupo g join g.centro c join c.facultad f where f.id =:entityId  and pb.tipo.id =:tipoId ")
    List<ProduccionBGrupo> getProduccionBFacultadCentro(@Param("entityId") Long entityId,
                                                        @Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos de una facultad respecto a sus programas.
	 *
	 * @param entityId el id de la facultad
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p join p.facultad f where f.id =:entityId  and (pr.tipo.id =  1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
    List<ProduccionGrupo> getTrabajosDirigidosFacultadPrograma(@Param("entityId") Long entityId);

	/**
	 * Obtiene los trabajos dirigidos de una facultad respecto a sus centros.
	 *
	 * @param entityId el id de la facultad
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p join p.facultad f where f.id =:entityId  and (pr.tipo.id =  1 or pr.tipo.id = 41 or pr.tipo.id = 42 or pr.tipo.id = 43)")
    List<ProduccionGrupo> getTrabajosDirigidosFacultadCentro(@Param("entityId") Long entityId);

	/**
	 * Obtiene la producción de una facultad respecto a sus programas.
	 *
	 * @param entityId el id de la facultad
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.programas p join p.facultad f where f.id =:entityId  and pr.tipo.id =:tipoId ")
    List<ProduccionGrupo> getProduccionFacultadPrograma(@Param("entityId") Long entityId,
                                                        @Param("tipoId") Long tipoId);

	/**
	 * Obtiene la producción de una facultad respecto a sus centros.
	 *
	 * @param entityId el id de la facultad
	 * @param tipoId   el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(pr.id, pr.autores, pr.anio, pr.referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr join pr.grupo g join g.centro c join c.facultad f where f.id =:entityId  and pr.tipo.id =:tipoId")
    List<ProduccionGrupo> getProduccionFacultadCentro(@Param("entityId") Long entityId,
                                                      @Param("tipoId") Long tipoId);

	// General
	/**
	 * Obtiene la producción bibliográfica general de toda la universidad.
	 *
	 * @param tipoId el tipo de la producción
	 * @return lista de producciones bibliográficas del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionBGrupo(id, identificador, autores, anio, referencia)  from co.edu.uniquindio.gri.model.ProduccionBGrupo where tipo.id =:tipoId")
    List<ProduccionBGrupo> getProduccionBGeneral(@Param("tipoId") Long tipoId);

	/**
	 * Obtiene los trabajos dirigidos general de toda la universidad.
	 * 
	 * @return lista de trabajos dirigidos.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(id, autores, anio, referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43")
    List<ProduccionGrupo> getTrabajosDirigidosGeneral();

	/**
	 * Obtiene la producción general de toda la universidad.
	 *
	 * @param tipoId el tipo de la producción
	 * @return lista de producciones del tipo tipoId.
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.ProduccionGrupo(id, autores, anio, referencia)  from co.edu.uniquindio.gri.model.ProduccionGrupo where tipo.id = :tipoId")
    List<ProduccionGrupo> getProduccionGeneral(@Param("tipoId") Long tipoId);

	// Búsquedas

	/**
	 * Obtiene las producciones bibliográficas contenidas en los GrupLAC de los
	 * grupos de acuerdo a una cadena de búsqueda.
	 *
	 * @param cadena la cadena de búsqueda
	 * @return lista de producciones biblográficas correspondientes con la cadena de
	 *         búsqueda.
	 */
	@SuppressWarnings("rawtypes")
	// Grupos
	@Query(value = "SELECT bg.referencia, bg.autores, bg.anio, tp.nombre as tipo,  gr.nombre FROM gri.bibliograficasg bg  JOIN gri.tipos tp ON tp.id=bg.tipo_id JOIN gri.grupos gr ON bg.grupos_id= gr.id WHERE unaccent(bg.referencia) LIKE unaccent('%'||:cadena||'%') UNION SELECT pg.referencia, pg.autores, pg.anio, tp.nombre as tipo,  gr.nombre FROM gri.produccionesg pg  JOIN gri.tipos tp ON tp.id=pg.tipo_id JOIN gri.grupos gr ON pg.grupos_id= gr.id WHERE unaccent(pg.referencia) LIKE unaccent('%'||:cadena||'%')", nativeQuery = true)
    List getProduccionGBusqueda(@Param("cadena") String cadena);

	// Investigadores
	/**
	 * Obtiene las producciones bibliográficas contenidas en los CvLAC de los
	 * investigadores de acuerdo a una cadena de búsqueda.
	 *
	 * @param cadena la cadena de búsqueda
	 * @return lista de producciones biblográficas correspondientes con la cadena de
	 *         búsqueda.
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT bg.referencia, bg.autores, bg.anio, tp.nombre as tipo,  inv.nombre FROM gri.bibliograficas bg  JOIN gri.tipos tp ON tp.id=bg.tipo_id JOIN gri.investigadores inv ON bg.investigadores_id= inv.id WHERE unaccent(bg.referencia) LIKE unaccent('% '||:cadena||' %') UNION SELECT pg.referencia, pg.autores, pg.anio, tp.nombre as tipo,  inv.nombre FROM gri.producciones pg  JOIN gri.tipos tp ON tp.id=pg.tipo_id JOIN gri.investigadores inv ON pg.investigadores_id= inv.id WHERE unaccent(pg.referencia) LIKE unaccent('% '||:cadena||' %')", nativeQuery = true)
    List getProduccionBusqueda(@Param("cadena") String cadena);

	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT b.id, b.referencia, b.autores, b.anio, t.nombre, t.tipoproduccion_id, b.inventario FROM gri.bibliograficasg b JOIN gri.tipos t ON t.id = b.tipo_id WHERE b.grupos_id =:id UNION ALL SELECT p.id, p.referencia, p.autores, p.anio, t.nombre, t.tipoproduccion_id, p.inventario FROM gri.produccionesg p JOIN gri.tipos t ON t.id = p.tipo_id WHERE p.grupos_id =:id", nativeQuery = true)
    List getAllProducciones(@Param("id") Long id);

	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o
	 * no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param estado, el estado a actualizar de la producción. 0 si no se encuentra
	 *                en custodia. 1 en caso contrario.
	 * @param id,     el identificador de la producción en base de datos.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE co.edu.uniquindio.gri.model.ProduccionGrupo p SET  p.estado=:estado WHERE p.id=:id")
    void updateProduccionGrupo(@Param("id") Long id, @Param("estado") int estado);

	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o
	 * no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param estado, el estado a actualizar de la producción. 0 si no se encuentra
	 *                en custodia. 1 en caso contrario.
	 * @param id,     el identificador de la producción en base de datos.
	 */
	@Transactional
	@Modifying
	@Query("UPDATE co.edu.uniquindio.gri.model.ProduccionBGrupo p SET  p.estado=:estado WHERE p.id=:id")
    void updateProduccionBGrupo(@Param("id") Long id, @Param("estado") int estado);

	/**
	 * Obtiene la cantidad de dirigidos de la universidad.
	 * 
	 * @return cantidad de trabajos dirigidos.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.id = 1 or tipo.id = 41 or tipo.id = 42 or tipo.id = 43")
    BigInteger getCantidadTrabajosDirigidos();

	/**
	 * Obtiene la cantidad de cursos cortos de la universidad.
	 * 
	 * @return cantidad de cursos cortos.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.id = 0")
    BigInteger getCantidadCursosCortaDuracion();

	/**
	 * Obtiene la cantidad de actividades de formación total de la universidad.
	 * 
	 * @return cantidad de actividades de formacion.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 0")
    BigInteger getCantidadActividadesFormacion();

	/**
	 * Obtiene la cantidad de actividades como evaluador total de la universidad.
	 * 
	 * @return cantidad de actividades como evaluador.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 1")
    BigInteger getCantidadActividadesEvaluador();

	/**
	 * Obtiene la cantidad de actividades de apropiacion social total de la
	 * universidad.
	 * 
	 * @return cantidad de actividades de apropiacion social.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 2")
    BigInteger getCantidadApropiacionSocial();

	/**
	 * Obtiene la cantidad de producciones bibliograficas total de la universidad.
	 * 
	 * @return cantidad de producciones bibliograficas.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionBGrupo pr where tipo.tipoProduccion.id = 3")
    BigInteger getCantidadProduccionesBibliograficas();

	/**
	 * Obtiene la cantidad de producciones tecnicas y teconologicas total de la
	 * universidad.
	 * 
	 * @return cantidad de producciones tecnicas y tecnologicas.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 4")
    BigInteger getCantidadTecnicasTecnologicas();

	/**
	 * Obtiene la cantidad de producciones en arte total de la universidad.
	 * 
	 * @return cantidad de producciones en arte.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 6")
    BigInteger getCantidadProduccionesArte();

	/**
	 * Obtiene la cantidad de producciones "Demas Trabajos" de la universidad.
	 * 
	 * @return cantidad de producciones "Demas Trabajos" de la universidad.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 5 and tipo.id = 32")
    BigInteger getCantidadProduccionesDemasTrabajos();

	/**
	 * Obtiene la cantidad de producciones "Proyectos" de la universidad.
	 * 
	 * @return cantidad de producciones "Proyectos" de la universidad.
	 */
	@Query("SELECT COUNT (pr)  from co.edu.uniquindio.gri.model.ProduccionGrupo pr where tipo.tipoProduccion.id = 5 and tipo.id = 33")
    BigInteger getCantidadProduccionesProyectos();

	/**
	 * Obtiene la cantidad de producciones total de la facultad por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param facultadId el id de la facultad
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.programas_grupos pg1 ON g.id=pg1.grupos_id JOIN gri.programas p ON pg1.programas_id = p.id JOIN gri.facultades f ON p.facultades_id = f.id AND f.id=:facultadId", nativeQuery = true)
    BigInteger getCantidadProduccionesFacultadPorTipo(@Param("facultadId") Long facultadId,
                                                      @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones bibliográficas total de la facultad por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param facultadId el id de la facultad
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.bibliograficasg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.programas_grupos pg1 ON g.id=pg1.grupos_id JOIN gri.programas p ON pg1.programas_id = p.id JOIN gri.facultades f ON p.facultades_id = f.id AND f.id=:facultadId", nativeQuery = true)
    BigInteger getCantidadProduccionesBFacultadPorTipo(@Param("facultadId") Long facultadId,
                                                       @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total de la facultad por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param facultadId el id de la facultad
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id AND t.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.programas_grupos pg1 ON g.id=pg1.grupos_id JOIN gri.programas p ON pg1.programas_id = p.id JOIN gri.facultades f ON p.facultades_id = f.id AND f.id=:facultadId", nativeQuery = true)
    BigInteger getCantidadProduccionesFacultadPorSubTipo(@Param("facultadId") Long facultadId,
                                                         @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del centro por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param centroId el id del centro
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.centros c ON c.id=g.centros_id WHERE c.id=:centroId", nativeQuery = true)
    BigInteger getCantidadProduccionesCentroPorTipo(@Param("centroId") Long centroId,
                                                    @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones bibliográficas total del centro por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param centroId el id del centro
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.bibliograficasg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.centros c ON c.id=g.centros_id WHERE c.id=:centroId", nativeQuery = true)
    BigInteger getCantidadProduccionesBCentroPorTipo(@Param("centroId") Long centroId,
                                                     @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del centro por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param centroId el id del centro
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id AND t.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.centros c ON c.id=g.centros_id WHERE c.id=:centroId", nativeQuery = true)
    BigInteger getCantidadProduccionesCentroPorSubTipo(@Param("centroId") Long centroId,
                                                       @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del programa por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param programaId el id del programa
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.programas_grupos pgr ON g.id=pgr.grupos_id WHERE pgr.programas_id =:programaId", nativeQuery = true)
    BigInteger getCantidadProduccionesProgramaPorTipo(@Param("programaId") Long programaId,
                                                      @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones bibliográficas total del programa por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param programaId el id del programa
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.bibliograficasg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.programas_grupos pgr ON pgr.grupos_id = g.id WHERE pgr.programas_id =:programaId", nativeQuery = true)
    BigInteger getCantidadProduccionesBProgramaPorTipo(@Param("programaId") Long programaId,
                                                       @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del programa por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param programaId el id del programa
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id AND t.id=:prodId JOIN gri.grupos g ON pg.grupos_id=g.id JOIN gri.programas_grupos pgr ON pgr.grupos_id = g.id WHERE pgr.programas_id =:programaId", nativeQuery = true)
    BigInteger getCantidadProduccionesProgramaPorSubTipo(@Param("programaId") Long programaId,
                                                         @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del grupo por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param grupoId el id del grupo
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId WHERE pg.grupos_id=:grupoId", nativeQuery = true)
    BigInteger getCantidadProduccionesGrupoPorTipo(@Param("grupoId") Long grupoId,
                                                   @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones bibliográficas total del grupo por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param grupoId el id del grupo
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.bibliograficasg pg JOIN gri.tipos t on pg.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId WHERE pg.grupos_id=:grupoId", nativeQuery = true)
    BigInteger getCantidadProduccionesBGrupoPorTipo(@Param("grupoId") Long grupoId,
                                                    @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del grupo por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param grupoId el id del grupo
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT pg.id) FROM gri.produccionesg pg JOIN gri.tipos t on pg.tipo_id = t.id AND t.id=:prodId WHERE pg.grupos_id =:grupoId", nativeQuery = true)
    BigInteger getCantidadProduccionesGrupoPorSubTipo(@Param("grupoId") Long grupoId,
                                                      @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del investigador por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param investigadorId el id del investigador
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT p.id) FROM gri.producciones p JOIN gri.tipos t on p.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId WHERE p.investigadores_id=:investigadorId", nativeQuery = true)
    BigInteger getCantidadProduccionesInvestigadorPorTipo(@Param("investigadorId") Long investigadorId,
                                                          @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones bibliográficas total del investigador por un tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param investigadorId el id del investigador
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT b.id) FROM gri.bibliograficas b JOIN gri.tipos t on b.tipo_id = t.id JOIN gri.tipoproduccion tp ON t.tipoproduccion_id = tp.id AND tp.id=:prodId WHERE b.investigadores_id=:investigadorId", nativeQuery = true)
    BigInteger getCantidadProduccionesBInvestigadorPorTipo(@Param("investigadorId") Long investigadorId,
                                                           @Param("prodId") Long prodId);
	
	/**
	 * Obtiene la cantidad de producciones total del investigador por un sub tipo
	 * especifico de producción.
	 * 
	 * @return cantidad de actividades de formacion.
	 * @param investigadorId el id del investigador
	 * @param prodId     el id del tipo de produccion
	 */
	@Query(value = "SELECT COUNT (DISTINCT p.id) FROM gri.producciones p JOIN gri.tipos t on p.tipo_id = t.id AND t.id=:prodId WHERE p.investigadores_id =:investigadorId", nativeQuery = true)
    BigInteger getCantidadProduccionesInvestigadorPorSubTipo(@Param("investigadorId") Long investigadorId,
                                                             @Param("prodId") Long prodId);

}
