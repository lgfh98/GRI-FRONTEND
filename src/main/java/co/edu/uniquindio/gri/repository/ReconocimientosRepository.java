package co.edu.uniquindio.gri.repository;

import co.edu.uniquindio.gri.dao.ReconocimientosDAO;
import co.edu.uniquindio.gri.model.LineasInvestigacion;
import co.edu.uniquindio.gri.model.RecononocimientosInvestigador;
import co.edu.uniquindio.gri.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface TipoRepository.
 */
@Repository
public interface ReconocimientosRepository extends JpaRepository<LineasInvestigacion, Long> {


	String UNIVERSITY_SEARCH = "SELECT DISTINCT ri.id, i, ri.anio, ri.reconocimiento, ri.entidad FROM gri.reconocimientos_inves ri INNER JOIN gri.investigadores i ON ri.investigadores_id = i.id";
	String FACULTY_SEARCH = "SELECT DISTINCT ri.id, i, ri.anio, ri.reconocimiento, ri.entidad FROM gri.reconocimientos_inves ri INNER JOIN gri.investigadores i ON ri.investigadores_id = i.id JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN gri.grupos g ON gi.grupos_id = g.id JOIN gri.programas_grupos gr ON gr.grupos_id = g.id JOIN gri. programas p ON p.id = gr.programas_id WHERE gi.estado = 'ACTUAL' and p.facultades_id";
	String CENTER_SEARCH = "SELECT DISTINCT ri.id, i, ri.anio, ri.reconocimiento, ri.entidad FROM gri.reconocimientos_inves ri INNER JOIN gri.investigadores i ON ri.investigadores_id = i.id JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN gri.grupos g ON g.id = gi.grupos_id WHERE gi.estado = 'ACTUAL' and g.centros_id";
	String PROGRAM_SEARCH = "SELECT DISTINCT ri.id, i, ri.anio, ri.reconocimiento, ri.entidad FROM gri.reconocimientos_inves ri INNER JOIN gri.investigadores i ON ri.investigadores_id = i.id JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id JOIN gri.grupos g ON gi.grupos_id = g.id JOIN gri.programas_grupos gr ON gr.grupos_id = g.id WHERE gi.estado = 'ACTUAL' and gr.programas_id.id";
	String GROUP_SEARCH = "SELECT DISTINCT ri.id, i, ri.anio, ri.reconocimiento, ri.entidad FROM gri.reconocimientos_inves ri INNER JOIN gri.investigadores i ON ri.investigadores_id = i.id JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' and gi.grupos_id";
	String RESEARCHER_SEARCH = "SELECT DISTINCT ri.id, i, ri.anio, ri.reconocimiento, ri.entidad FROM gri.reconocimientos_inves ri INNER JOIN gri.investigadores i ON ri.investigadores_id = i.id WHERE i.id";


	/**
	 * Obtiene todos los reconocimientos
	 *
	 * @return los reconocimientos de la universidad
	 */
	@Query(value = UNIVERSITY_SEARCH, nativeQuery = true)
	List<RecononocimientosInvestigador> getReconocimientos();

	/**
	 * Obtiene todos los reconocimientos de investigación de una facultad
	 *
	 * @param id el id de la facultad
	 * @return los reconocimientos de investigación de una facultad
	 */
	@Query(value = FACULTY_SEARCH +" = :id", nativeQuery = true)
	List<RecononocimientosInvestigador> getReconocimientosFacultad(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un centro
	 *
	 * @param id el id del centro
	 * @return los reconocimientos de investigación de un centro
	 */
	@Query(value = CENTER_SEARCH +" = :id", nativeQuery = true)
	List<RecononocimientosInvestigador> getReconocimientosCentros(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un programa
	 *
	 * @param id el id del programa
	 * @return los reconocimientos de investigación de un programa
	 */
	@Query(value = PROGRAM_SEARCH +" = :id", nativeQuery = true)
	List<RecononocimientosInvestigador> getReconocimientosProgramas(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un grupo
	 *
	 * @param id el id del grupo
	 * @return los reconocimientos de investigación de un grupo
	 */
	@Query(value = FACULTY_SEARCH +" = :id", nativeQuery = true)
	List<RecononocimientosInvestigador> getReconocimientosGrupos(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un investigador
	 *
	 * @param id el id del investigador
	 * @return los reconocimientos de investigación de un investigador
	 */
	@Query(value = RESEARCHER_SEARCH +" = :id", nativeQuery = true)
	List<RecononocimientosInvestigador> getReconocimientosInvestigadores(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos
	 *
	 * @return los reconocimientos de la universidad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador")
	List<RecononocimientosInvestigador> findAllReconocimientos();


}
