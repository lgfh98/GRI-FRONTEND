package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.LineasInvestigacion;

/**
 * Interface TipoRepository.
 */
@Repository
public interface LineasInvestigacionRepository extends JpaRepository<LineasInvestigacion, Long> {

	/**
	 * Obtiene todas las lineas de investigación de una facultad
	 * 
	 * @param facultadId el id de la facultad
	 * @return las lineas de investigación de una facultad
	 */
	@Query(value = "SELECT DISTINCT l.id, l.nombre FROM gri.lineasinvestigacion l JOIN (SELECT MAX (l.id) id FROM gri.lineasinvestigacion l JOIN gri.grupos_lineas gl ON l.id = gl.lineasinvestigacion_id JOIN gri.grupos g ON gl.grupos_id = g.id JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id =:id GROUP BY l.nombre)a ON l.id = a.id", nativeQuery = true)
	List<LineasInvestigacion> getLineasFacultad(@Param("id") Long facultadId);

	/**
	 * Obtiene todas las lineas de investigación de un centro
	 * 
	 * @param facultadId el id del centro
	 * @return las lineas de investigación de un centro
	 */
	@Query(value = "SELECT DISTINCT l.id, l.nombre FROM gri.lineasinvestigacion l JOIN (SELECT MAX (l.id) id FROM gri.lineasinvestigacion l JOIN gri.grupos_lineas gl ON l.id = gl.lineasinvestigacion_id JOIN gri.grupos g ON gl.grupos_id = g.id JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id =:id GROUP BY l.nombre)a ON l.id = a.id", nativeQuery = true)
	List<LineasInvestigacion> getLineasCentro(@Param("id") Long centroId);

	/**
	 * Obtiene todas las lineas de investigación de un programa
	 * 
	 * @param programaId el id del programa
	 * @return las lineas de investigación de un programa
	 */
	@Query(value = "SELECT DISTINCT l.id, l.nombre FROM gri.lineasinvestigacion l JOIN (SELECT MAX (l.id) id FROM gri.lineasinvestigacion l JOIN gri.grupos_lineas gl ON l.id = gl.lineasinvestigacion_id JOIN gri.grupos g ON gl.grupos_id = g.id JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id WHERE p.id =:id GROUP BY l.nombre)a ON l.id = a.id", nativeQuery = true)
	List<LineasInvestigacion> getLineasPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene todas las lineas de investigación de un grupo
	 * 
	 * @param grupoid el id del grupo
	 * @return las lineas de investigación de un grupo
	 */
	@Query(value = "SELECT DISTINCT l.id, l.nombre FROM gri.lineasinvestigacion l JOIN (SELECT MAX (l.id) id FROM gri.lineasinvestigacion l JOIN gri.grupos_lineas gl ON l.id = gl.lineasinvestigacion_id WHERE gl.grupos_id =:id GROUP BY l.nombre)a ON l.id = a.id", nativeQuery = true)
	List<LineasInvestigacion> getLineasGrupo(@Param("id") Long grupoid);

	/**
	 * Obtiene todas las lineas de investigación de la universidad
	 * 
	 * @return las lineas de investigación de la universidad
	 */
	@Query(value = "SELECT MIN (l.id) id, l.nombre nombre FROM gri.lineasinvestigacion l JOIN gri.grupos_lineas gl ON gl.lineasinvestigacion_id=l.id WHERE gl.grupos_id<>0 GROUP BY l.nombre", nativeQuery = true)
	List<LineasInvestigacion> findAllLineas();

}
