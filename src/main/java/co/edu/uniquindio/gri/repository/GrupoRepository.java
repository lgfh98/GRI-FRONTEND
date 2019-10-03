package co.edu.uniquindio.gri.repository;

import java.math.BigInteger;
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
	
	/**
	 * Obtiene los grupos pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId")
	List<Grupo> getAllGruposPrograma(@Param(value="programaId") Long programaId);
	
	/**
	 * Obtiene los grupos A1 pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId and g.categoria like '%A1 CON VIGENCIA%'")
	List<Grupo> getGruposA1Programa(@Param(value="programaId") Long programaId);
	
	/**
	 * Obtiene los grupos A pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId and g.categoria like '%A CON VIGENCIA%'")
	List<Grupo> getGruposAPrograma(@Param(value="programaId") Long programaId);
	
	/**
	 * Obtiene los grupos B pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId and g.categoria like '%B CON VIGENCIA%'")
	List<Grupo> getGruposBPrograma(@Param(value="programaId") Long programaId);
	
	/**momoomm
	 * Obtiene los grupos C pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId and g.categoria like '%C CON VIGENCIA%'")
	List<Grupo> getGruposCPrograma(@Param(value="programaId") Long programaId);
	
	/**
	 * Obtiene los grupos reconocidos pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId and g.categoria like '%SIN CATEGORÍA%'")
	List<Grupo> getGruposReconocidosPrograma(@Param(value="programaId") Long programaId);
	
	/**
	 * Obtiene los grupos no reconocidos pertenecientes a un programa especifico
	 * 
	 * @param programaId el id del programa
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where p.id = :programaId and g.categoria like '%N/D%'")
	List<Grupo> getGruposNoReconocidosPrograma(@Param(value="programaId") Long programaId);

	/**
	 * Obtiene el resumen general del grupo en números
	 * la lista en cada posición obtiene lo siguiente:
	 * 0 - Cantidad de lineas investigación 
	 * 1 - Cantidad investigadores
	 * 2 - Investigadores emeritos
	 * 3 - Investigador senior
	 * 4 - Investigador asociados
	 * 5 - Investigador junior
	 * 6 - Investigador sin categoria
	 * 7 - Docentes con doctorado
	 * 8 - Docentes con magister
	 * 9 - Docentes especialistas
	 * 10 - Docentes pregrado
	 * 
	 * @param Long programaId, id del grupo.
	 *
	 * @return lista con los totales anteriores
	 */
	@Query(value = "SELECT Count (DISTINCT l.nombre) FROM  gri.lineasinvestigacion l JOIN (SELECT DISTINCT gl.lineasinvestigacion_id lineas FROM  gri.grupos_lineas gl  WHERE gl.grupos_id =:grupoId)a ON l.id = a.lineas UNION ALL SELECT Count (DISTINCT gi.investigadores_id) FROM  gri.grupos_inves gi WHERE gi.estado = 'ACTUAL'  AND gi.grupos_id=:grupoId UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.categoria LIKE '%EMÉRITO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.categoria LIKE '%SENIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.categoria LIKE '%ASOCIADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.categoria LIKE '%JUNIOR%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.categoria LIKE '%SIN CAT%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%DOCTORADO%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%MAGISTER%' UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico IN ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA')UNION ALL SELECT Count (DISTINCT i.id) FROM  gri.investigadores i JOIN gri.grupos_inves gi ON i.id = gi.investigadores_id WHERE gi.estado = 'ACTUAL' AND gi.grupos_id =:grupoId  AND i.pertenencia LIKE '%INTERNO%' AND i.nivelacademico LIKE '%PREGRADO%'", nativeQuery = true)
	List<BigInteger> getResumenGeneralGrupo(@Param(value="grupoId") Long grupoId);

	/**
	 * Obtiene los grupos pertenecientes a un centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId")
	List<Grupo> getAllGruposCentro(@Param(value="centroId") Long centroId);
	
	/**
	 * Obtiene los grupos A1 pertenecientes a un centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId and g.categoria like '%A1 CON VIGENCIA%'")
	List<Grupo> getGruposA1Centro(@Param(value="centroId") Long centroId);
	
	/**
	 * Obtiene los grupos A pertenecientes a un centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId and g.categoria like '%A CON VIGENCIA%'")
	List<Grupo> getGruposACentro(@Param(value="centroId") Long centroId);
	
	/**
	 * Obtiene los grupos B pertenecientes a un centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId and g.categoria like '%B CON VIGENCIA%'")
	List<Grupo> getGruposBCentro(@Param(value="centroId") Long centroId);
	
	/**momoomm
	 * Obtiene los grupos C pertenecientes a un centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId and g.categoria like '%C CON VIGENCIA%'")
	List<Grupo> getGruposCCentro(@Param(value="centroId") Long centroId);
	
	/**
	 * Obtiene los grupos reconocidos pertenecientes a un centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId and g.categoria like '%SIN CATEGORÍA%'")
	List<Grupo> getGruposReconocidosCentro(@Param(value="centroId") Long centroId);
	
	/**
	 * Obtiene los grupos no reconocidos pertenecientes a una centro especifico
	 * 
	 * @param centroId el id del centro
	 */
	@Query("SELECT NEW co.edu.uniquindio.gri.model.Grupo(g.id, g.nombre, g.categoria, g.lider, p, c) FROM co.edu.uniquindio.gri.model.Grupo g join g.programas p join g.centro c where c.id = :centroId and g.categoria like '%N/D%'")
	List<Grupo> getGruposNoReconocidosCentro(@Param(value="centroId") Long centroId);

}
