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


	String UNIVERSITY_SEARCH = "SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador";
	String FACULTY_SEARCH = "SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador";
	String CENTER_SEARCH = "SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador";
	String PROGRAM_SEARCH = "SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador";
	String GROUP_SEARCH = "SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador";
	String RESEARCHER_SEARCH = "SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador i WHERE i.id";


	/**
	 * Obtiene todos los reconocimientos
	 *
	 * @return los reconocimientos de la universidad
	 */
	@Query(UNIVERSITY_SEARCH)
	List<RecononocimientosInvestigador> getReconocimientos();

	/**
	 * Obtiene todos los reconocimientos de investigación de una facultad
	 *
	 * @param id el id de la facultad
	 * @return los reconocimientos de investigación de una facultad
	 */
	@Query(FACULTY_SEARCH)
	List<RecononocimientosInvestigador> getReconocimientosFacultad(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un centro
	 *
	 * @param id el id del centro
	 * @return los reconocimientos de investigación de un centro
	 */
	@Query(CENTER_SEARCH)
	List<RecononocimientosInvestigador> getReconocimientosCentros(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un programa
	 *
	 * @param id el id del programa
	 * @return los reconocimientos de investigación de un programa
	 */
	@Query(PROGRAM_SEARCH)
	List<RecononocimientosInvestigador> getReconocimientosProgramas(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un grupo
	 *
	 * @param id el id del grupo
	 * @return los reconocimientos de investigación de un grupo
	 */
	@Query(FACULTY_SEARCH)
	List<RecononocimientosInvestigador> getReconocimientosGrupos(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos de investigación de un investigador
	 *
	 * @param id el id del investigador
	 * @return los reconocimientos de investigación de un investigador
	 */
	@Query(RESEARCHER_SEARCH +" = :id")
	List<RecononocimientosInvestigador> getReconocimientosInvestigadores(@Param("id") Long id);

	/**
	 * Obtiene todos los reconocimientos
	 *
	 * @return los reconocimientos de la universidad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.RecononocimientosInvestigador(ri.id, ri.investigador, ri.anio, ri.reconocimiento, ri.entidad) FROM co.edu.uniquindio.gri.model.RecononocimientosInvestigador ri join ri.investigador")
	List<RecononocimientosInvestigador> findAllReconocimientos();


}
