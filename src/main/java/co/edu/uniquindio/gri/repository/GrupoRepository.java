package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.Grupo;

/**
 * Interface GrupoRepository.
 */
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

	/**
	 * Obtiene los grupos pertenecientes a un programa específico.
	 *
	 * @param programaId el id del programa
	 * @return los grupos del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p where p.id = :id")
	List<Grupo> getGruposPrograma(@Param(value="id") Long programaId);
	
	/**
	 * Obtiene los grupos pertenecientes a un centro específico.
	 *
	 * @param centroId el id del centro
	 * @return los grupos del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider) FROM co.edu.uniquindio.gri.model.Grupo g where g.centro.id = :id")
	List<Grupo> getGruposCentro(@Param(value="id") Long centroId);
	
	
	
	/**
	 * Obtiene los grupos pertenecientes a una facultad especifica
	 * 
	 * 
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider) FROM co.edu.uniquindio.gri.model.Grupo g join g.centro c join c.facultad f where f.id = :id")
	List<Grupo> getGruposFacultadC(@Param(value="id") Long facultadId);
	
	/**
	 * Obtiene los grupos pertenecientes a una facultad especifica
	 * 
	 * 
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f where f.id = :id")
	List<Grupo> getGruposFacultadP(@Param(value="id") Long facultadId);
}
