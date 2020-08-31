package co.edu.uniquindio.gri.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Centro;
import co.edu.uniquindio.gri.model.Programa;

/**
 * Interface ProgramaRepository.
 */
@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {

	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param idFacultad el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE id<>0 and facultad.id = :id")
	List<Programa> getProgramasFacultad(@Param("id") Long idFacultad);

	/**
	 * Obtiene los programas
	 *
	 * @return lista de los programas
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE id<>0")
	List<Programa> getProgramas();

	/**
	 * Obtiene la cantidad total de programas.
	 *
	 * @return cantidad total de programas
	 */
	@Query("SELECT COUNT(p) FROM co.edu.uniquindio.gri.model.Programa p where p.id <> 0")
	BigInteger getStats();

	/**
	 * Obtiene los programas academicos de una facultad específica.
	 *
	 * @param idFacultad el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE facultad.id = :id AND nombre NOT LIKE '%MAESTRÍA%' AND nombre NOT LIKE '%ESPECIALIZACIÓN%' AND nombre NOT LIKE '%DOCTORADO%'")
	List<Programa> getProgramasAcademicosFacultad(@Param("id") Long idFacultad);
	
	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param idFacultad el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE facultad.id = :id AND nombre LIKE '%DOCTORADO%'")
	List<Programa> getProgramasDoctoradoFacultad(@Param("id") Long idFacultad);
	
	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param idFacultad el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE facultad.id = :id AND nombre LIKE '%MAESTRÍA%'")
	List<Programa> getProgramasMaestriaFacultad(@Param("id") Long idFacultad);
	
	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param idFacultad el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE facultad.id = :id AND nombre LIKE '%ESPECIALIZACIÓN%'")
	List<Programa> getProgramasEspecializacionFacultad(@Param("id") Long idFacultad);
	
	/**
	 * Obtiene los programas academicos de la universidad.
	 *
	 * @return lista de los programas academicos
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE nombre NOT LIKE '%MAESTRÍA%' AND nombre NOT LIKE '%ESPECIALIZACIÓN%' AND nombre NOT LIKE '%DOCTORADO%'")
	List<Programa> getProgramasAcademicos();
	
	/**
	 * Obtiene los programas de doctorados de la universidad.
	 *
	 * @return lista de los programas de doctorado
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE nombre LIKE '%DOCTORADO%'")
	List<Programa> getProgramasDoctorado();
	
	/**
	 * Obtiene los programas de maestria de la universidad.
	 *
	 * @return lista de los programas de maestria
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE nombre LIKE '%MAESTRÍA%'")
	List<Programa> getProgramasMaestria();
	
	/**
	 * Obtiene los programas de especializacion.
	 *
	 * @return lista de los programas de especializacion
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE nombre LIKE '%ESPECIALIZACIÓN%'")
	List<Programa> getProgramasEspecializacion();

	/**
	 * Obtiene el resumen general del programa en números
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
	 * 
	 * @param Long programaId, id del programa.
	 *
	 * @return lista con los totales anteriores
	 */
	@Query(value = "SELECT Count (DISTINCT a.grupo) FROM  (SELECT g.id grupo, p.id programa  FROM  gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id  WHERE p.id =:programaId)a UNION ALL SELECT COUNT (DISTINCT l.nombre) FROM gri.lineasinvestigacion l JOIN (SELECT DISTINCT gl.lineasinvestigacion_id lineas FROM gri.grupos_lineas gl JOIN gri.grupos g ON gl.grupos_id = g.id JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id WHERE p.id =:programaId)a ON l.id = a.lineas  UNION ALL SELECT Count (DISTINCT gi.investigadores_id) FROM  gri.grupos_inves gi JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON g1.id = a.grupo WHERE g1.categoria = 'A1' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON g1.id = a.grupo WHERE g1.categoria = 'A' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON g1.id = a.grupo WHERE g1.categoria = 'B' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON g1.id = a.grupo WHERE g1.categoria = 'C' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON g1.id = a.grupo WHERE g1.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON g1.id = a.grupo WHERE g1.categoria LIKE '%N/D%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%EMÉRITO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%SENIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%ASOCIADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%JUNIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%DOCTORADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%MAGISTER%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico IN ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA') UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  p.id programa FROM  gri.grupos g  JOIN gri.programas_grupos pg  ON pg.grupos_id = g.id  JOIN gri.programas p  ON p.id = pg.programas_id WHERE p.id =:programaId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%PREGRADO%'", nativeQuery = true)
	List<BigInteger> getResumenGeneralPrograma(@Param("programaId") Long programaId);

	@Query(value = "select * from gri.programas us where us.id=(select max(id) from gri.programas)", nativeQuery = true)
	Programa findLastRegister();
	
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE id = :id")
	Programa findOne(@Param("id") long id);
	
}
