package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
