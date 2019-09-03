package co.edu.uniquindio.gri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Tipo;

/**
 * Interface TipoRepository.
 */
@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long>{

}
