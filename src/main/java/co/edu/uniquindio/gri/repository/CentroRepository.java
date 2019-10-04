package co.edu.uniquindio.gri.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Centro;

/**
 * Interface CentroRepository.
 */
@Repository
public interface CentroRepository extends JpaRepository<Centro, Long>{

	/* (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Centro WHERE id<>0")
	List<Centro> findAll();
	
	/* (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Centro WHERE facultad.id=:facultadId")
	List<Centro> getAllCentrosFacultad(@Param("facultadId") Long facultadId);
	
	/**
	 * Obtiene el resumen general de la facultad en números
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
	 * @return lista con totales anteriores.
	 * @param centroId id del centro
	 */
	@Query(value = "SELECT Count (DISTINCT a.grupo) FROM  (SELECT g.id grupo, c.id centro  FROM  gri.grupos g JOIN gri.centros c ON g.centros_id = c.id  WHERE c.id =:centroId)a UNION ALL SELECT COUNT (DISTINCT l.nombre) FROM gri.lineasinvestigacion l JOIN (SELECT DISTINCT gl.lineasinvestigacion_id lineas FROM gri.grupos_lineas gl JOIN gri.grupos g ON gl.grupos_id = g.id JOIN gri.programas_grupos pg ON pg.grupos_id = g.id WHERE g.centros_id =:centroId)a ON l.id = a.lineas  UNION ALL SELECT Count (DISTINCT gi.investigadores_id) FROM  gri.grupos_inves gi JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON g1.id = a.grupo WHERE g1.categoria LIKE '%A1 CON%' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON g1.id = a.grupo WHERE g1.categoria LIKE '%A CON%' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON g1.id = a.grupo WHERE g1.categoria LIKE '%B CON%' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON g1.id = a.grupo WHERE g1.categoria LIKE '%C CON%' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON g1.id = a.grupo WHERE g1.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT g1.id) FROM  gri.grupos g1 JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON g1.id = a.grupo WHERE g1.categoria LIKE '%N/D%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%EMÉRITO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%SENIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%ASOCIADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%JUNIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%DOCTORADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%MAGISTER%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.nivelacademico IN ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA') UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN (SELECT g.id grupo,  c.id centro FROM  gri.grupos g  JOIN gri.centros c  ON g.centros_id = c.id WHERE c.id =:centroId  )a ON gi.grupos_id = a.grupo WHERE gi.estado = 'ACTUAL' AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%PREGRADO%'", nativeQuery = true)
	List<BigInteger> getResumenGeneralCentros(@Param("centroId") Long centroId);

}
