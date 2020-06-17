package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.uniquindio.gri.model.LiderGrupo;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

public interface LiderGrupoRepository extends JpaRepository<LiderGrupo, Integer>{

	/**
	 * Consulta que genera 
	 * @param grupoId
	 * @return
	 */
	@Query(value = "FROM co.edu.uniquindio.gri.model.LiderGrupo l where l.grupo.id = :grupo_id")
	LiderGrupo getLiderDeUnGrupo(@Param("grupo_id") Long grupoId);
}
