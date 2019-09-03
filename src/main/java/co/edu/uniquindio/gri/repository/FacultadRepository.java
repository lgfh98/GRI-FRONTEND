package co.edu.uniquindio.gri.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	 * @return las estadísticas: Cantidad de grupos, investigadores y centros de investigación.
	 */
	@Query(value = "SELECT count(distinct(i.id)) FROM gri.investigadores i join gri.grupos_inves gi ON i.id = gi.investigadores_id join gri.grupos g ON g.id = gi.grupos_id WHERE gi.estado = 'ACTUAL' UNION ALL SELECT count(*) FROM gri.grupos UNION ALL SELECT count(*) FROM gri.centros WHERE id<>0", nativeQuery = true)
	List<BigInteger> getStats();
	
	/* (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Facultad where id<>0")
	List<Facultad> findAll();
}
