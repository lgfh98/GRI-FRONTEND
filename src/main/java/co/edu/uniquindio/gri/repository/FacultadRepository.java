package co.edu.uniquindio.gri.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Facultad;

/**
 * Interface FacultadRepository.
 */
@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {

	/**
	 * Obtiene las estadísticas de la actividad investigativa.
	 *
	 * @return las estadísticas: Cantidad de grupos, investigadores y centros de
	 *         investigación.
	 */
	@Query(value = "SELECT count(*) FROM gri.facultades WHERE id<>0 and id<>8 UNION ALL SELECT count(*) FROM gri.centros WHERE id<>0 UNION ALL SELECT count(*) FROM gri.programas where id<>0 UNION ALL SELECT count(*) FROM gri.grupos WHERE id<>0 UNION ALL SELECT count(distinct(i.id)) FROM gri.investigadores i join gri.grupos_inves gi ON i.id = gi.investigadores_id join gri.grupos g ON g.id = gi.grupos_id WHERE gi.estado = 'ACTUAL'", nativeQuery = true)
	List<BigInteger> getStats();

	/**
	 * Obtiene el resumen general de la facultad en números la lista en cada
	 * posición obtiene lo siguiente: 0 - Cantidad programas académicos 1 - Cantidad
	 * doctorados 2 - Cantidad maestrías 3 - Cantidad especializaciones 4 . Cantidad
	 * centros de investigación 5 . Cantidad grupos de investigación 6 - Cantidad de
	 * lineas investigación 7 - Cantidad investigadores 8 - Grupos categoria A1 9 -
	 * Grupos categoria A 10 - Grupos categoria B 11 - Grupos categoria C 12 -
	 * Grupos reconocidos 13 - Grupos no reconocidos 14 - Investigadores emeritos 15
	 * - Investigador senior 16 - Investigador asociados 17 - Investigador junior 18
	 * - Investigador sin categoria 19 - Docentes con doctorado 20 - Docentes con
	 * magister 21 - Docentes especialistas 22 - Docentes pregrado
	 * 
	 * @param Long facultadId, id de la facultad.
	 *
	 * @return lista con los totales anteriores
	 */
	@Query(value = "SELECT Count(*) FROM gri.facultades f JOIN gri.programas p ON f.id = p.facultades_id WHERE f.id = :facultadId AND p.nombre NOT LIKE '%MAESTRÍA%' AND p.nombre NOT LIKE '%ESPECIALIZACIÓN%' AND p.nombre NOT LIKE '%DOCTORADO%' UNION ALL SELECT Count(*) FROM gri.facultades f JOIN gri.programas p ON f.id = p.facultades_id WHERE f.id = :facultadId AND p.nombre LIKE '%DOCTORADO%' UNION ALL SELECT Count(*) FROM gri.facultades f JOIN gri.programas p ON f.id = p.facultades_id WHERE f.id = :facultadId AND p.nombre LIKE '%MAESTRÍA%' UNION ALL SELECT Count(*) FROM gri.facultades f JOIN gri.programas p ON f.id = p.facultades_id WHERE f.id = :facultadId AND p.nombre LIKE '%ESPECIALIZACIÓN%' UNION ALL SELECT Count(*) FROM gri.facultades f JOIN gri.centros c ON f.id = c.facultades_id WHERE f.id = :facultadId UNION ALL SELECT Count (DISTINCT a.grupo) FROM (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a UNION ALL SELECT COUNT (DISTINCT l.nombre) FROM gri.lineasinvestigacion l JOIN (SELECT DISTINCT gl.lineasinvestigacion_id lineas FROM gri.grupos_lineas gl JOIN gri.grupos g ON gl.grupos_id = g.id JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id =:facultadId)a ON l.id = a.lineas UNION ALL SELECT Count (DISTINCT gi.investigadores_id) FROM gri.grupos_inves gi JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' UNION ALL SELECT Count (DISTINCT g1.id) FROM gri.grupos g1 JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON g1.id = a.grupo WHERE g1.categoria = 'A1' UNION ALL SELECT Count (DISTINCT g1.id) FROM gri.grupos g1 JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON g1.id = a.grupo WHERE g1.categoria = 'A' UNION ALL SELECT Count (DISTINCT g1.id) FROM gri.grupos g1 JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON g1.id = a.grupo WHERE g1.categoria = 'B' UNION ALL SELECT Count (DISTINCT g1.id) FROM gri.grupos g1 JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON g1.id = a.grupo WHERE g1.categoria = 'C' UNION ALL SELECT Count (DISTINCT g1.id) FROM gri.grupos g1 JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON g1.id = a.grupo WHERE g1.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT g1.id) FROM gri.grupos g1 JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON g1.id = a.grupo WHERE g1.categoria LIKE '%N/D%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%EMÉRITO%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%SENIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%ASOCIADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%JUNIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND  i.nivelacademico LIKE '%DOCTORADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND  i.nivelacademico LIKE '%MAGISTER%' UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND  i.nivelacademico IN ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA') UNION ALL SELECT Count (DISTINCT i.id) FROM gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo, f.id facultad FROM gri.grupos g JOIN gri.programas_grupos pg ON pg.grupos_id = g.id JOIN gri.programas p ON p.id = pg.programas_id JOIN gri.facultades f ON f.id = p.facultades_id WHERE f.id = :facultadId)a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND  i.nivelacademico LIKE '%PREGRADO%'", nativeQuery = true)
	List<BigInteger> getResumenGeneral(@Param("facultadId") Long facultadId);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Facultad where id<>0")
	List<Facultad> findAll();

	@Query(value = "SELECT count(*) FROM gri.facultades f join gri.programas p on f.id=p.facultades_id WHERE f.id=:facultadId AND p.nombre LIKE '%AESTRÍA%' UNION ALL SELECT count(*) FROM gri.centros WHERE id<>0 UNION ALL SELECT count(*) FROM gri.programas where id<>0 UNION ALL SELECT count(*) FROM gri.grupos WHERE id<>0 UNION ALL SELECT count(distinct(i.id)) FROM gri.investigadores i join gri.grupos_inves gi ON i.id = gi.investigadores_id join gri.grupos g ON g.id = gi.grupos_id WHERE gi.estado = 'ACTUAL'", nativeQuery = true)
	List<BigInteger> getInfoGeneral(@Param("facultadId") Long idFacultad);

	@Query(value = "SELECT count(*) FROM gri.facultades WHERE id<>:facultadId", nativeQuery = true)
	List<BigInteger> getCantidadCategorias(@Param("facultadId") Long facultadId);
}
