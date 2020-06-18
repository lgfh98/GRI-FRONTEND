package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.uniquindio.gri.model.CasoRevisionProduccion;

/**
 * 
 * Repositorio que maneja las consultas y accesos a los casos de revisión de producciones en bonita
 * @author Jhon Sebatian Montes R
 *
 */

public interface CasoRevisionProduccionRepository extends JpaRepository<CasoRevisionProduccion, Long> {
	/**
	 * Consulta que devuelve los todoas los casos con un estado dado por parámetro
	 * 
	 * @param estado
	 * @return
	 */
	@Query(value = "FROM co.edu.uniquindio.gri.model.CasoRevisionProduccion c where c.estado = :estado")
	List<CasoRevisionProduccion> getCasosPorEstado(@Param("estado") String estado);
	
	/**
	 * Consulta que devuelve los todoas los casos de producciones de un tipo dado por parámetro
	 * 
	 * @param tipoDeProduccion
	 * @return
	 */
	@Query(value = "FROM co.edu.uniquindio.gri.model.CasoRevisionProduccion c where c.tipoProduccion = :tipoProduccion")
	List<CasoRevisionProduccion> getCasosPorTipoDeProduccion(@Param("tipoProduccion") String tipoProduccion);
	
	/**
	 * Consulta que retorna el Caso de revisión asociado a un id de producción específica
	 * @param idProduccion
	 * @return
	 */
	@Query(value = "FROM co.edu.uniquindio.gri.model.CasoRevisionProduccion c where c.idProduccion = :idProduccion and c.tipoProduccion = :tipoProduccion ")
	CasoRevisionProduccion getCasoPorProduccion(@Param("idProduccion") Long idProduccion, @Param("tipoProduccion") String tipoProduccion);
}
