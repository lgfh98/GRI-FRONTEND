package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Programa;

/**
 * Interface ProgramaRepository.
 */
@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long>{

	/**
	 * Obtiene los programas de una facultad específica.
	 *
	 * @param idFacultad el id de la facultad
	 * @return lista de los programas pertenecientes a la facultad idFacultad
	 */
	@Query("FROM co.edu.uniquindio.gri.model.Programa WHERE id<>0 and facultad.id = :id")
	List<Programa> getProgramasFacultad(@Param("id") Long idFacultad);
}
