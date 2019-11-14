package co.edu.uniquindio.gri.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.gri.model.Pertenencia;

@Repository
public interface PertenenciaRepository extends JpaRepository<Pertenencia, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE co.edu.uniquindio.gri.model.Pertenencia p SET  p.pertenencia=:pertenencia WHERE p.investigador_id=:id_invest")
	public void actualizarPertenencia(@Param("id_invest") Long id_invest, @Param("pertenencia") String pertenencia);

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO gri.pertenencia_inves (investigadores_id,pertenencia) VALUES  (:id_invest,:pertenencia)", nativeQuery = true)
	public void agregarNuevaPertenencia(@Param("id_invest") Long id_invest, @Param("pertenencia") String pertenencia);

}
