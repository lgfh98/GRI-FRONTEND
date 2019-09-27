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
	
	/**
	 * Obtiene los grupos pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :id")
	List<Grupo> getAllGruposFacultad(@Param(value="id") Long facultadId);
	
	/**
	 * Obtiene los grupos A1 pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :facultadId and g.categoria like '%A1 CON VIGENCIA%'")
	List<Grupo> getGruposA1Facultad(@Param(value="facultadId") Long facultadId);
	
	/**
	 * Obtiene los grupos A pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :facultadId and g.categoria like '%A CON VIGENCIA%'")
	List<Grupo> getGruposAFacultad(@Param(value="facultadId") Long facultadId);
	
	/**
	 * Obtiene los grupos B pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :facultadId and g.categoria like '%B CON VIGENCIA%'")
	List<Grupo> getGruposBFacultad(@Param(value="facultadId") Long facultadId);
	
	/**
	 * Obtiene los grupos C pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :facultadId and g.categoria like '%C CON VIGENCIA%'")
	List<Grupo> getGruposCFacultad(@Param(value="facultadId") Long facultadId);
	
	/**
	 * Obtiene los grupos reconocidos pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :facultadId and g.categoria like '%SIN CATEGORÍA%'")
	List<Grupo> getGruposReconocidosFacultad(@Param(value="facultadId") Long facultadId);
	
	/**
	 * Obtiene los grupos no reconocidos pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where f.id = :facultadId and g.categoria like '%N/D%'")
	List<Grupo> getGruposNoReconocidosFacultad(@Param(value="facultadId") Long facultadId);
	
	/**
	 * Obtiene los grupos A1 pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where g.categoria like '%A1 CON VIGENCIA%'")
	List<Grupo> getAllGruposA1();
	
	/**
	 * Obtiene los grupos A pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where g.categoria like '%A CON VIGENCIA%'")
	List<Grupo> getAllGruposA();
	
	/**
	 * Obtiene los grupos B pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where g.categoria like '%B CON VIGENCIA%'")
	List<Grupo> getAllGruposB();
	
	/**
	 * Obtiene los grupos C pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where g.categoria like '%C CON VIGENCIA%'")
	List<Grupo> getAllGruposC();
	
	/**
	 * Obtiene los grupos reconocidos pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where g.categoria like '%SIN CATEGORÍA%'")
	List<Grupo> getAllGruposReconocidos();
	
	/**
	 * Obtiene los grupos no reconocidos pertenecientes a una facultad especifica
	 * 
	 * @param facultadId el id de la facultad
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join p.facultad f join g.centro c where g.categoria like '%N/D%'")
	List<Grupo> getAllGruposNoReconocidos();
}
