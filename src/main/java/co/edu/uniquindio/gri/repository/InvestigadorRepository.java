package co.edu.uniquindio.gri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.edu.uniquindio.gri.model.Investigador;

/**
 * Interface InvestigadorRepository.
 */
@Repository
public interface InvestigadorRepository extends JpaRepository<Investigador, Long>{
	
	/* (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("select distinct NEW co.edu.uniquindio.gri.model.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos g where g.estado = 'ACTUAL'")
	List<Investigador> findAll();

	/**
	 * Obtiene los integrantes de un grupo específico.
	 *
	 * @param id el id del grupo
	 * @return la lista de integrantes del grupo
	 */
	@Query("select NEW co.edu.uniquindio.gri.model.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.model.Grupo g join g.investigadores gi join gi.investigadores i where g.id =:id and gi.estado ='ACTUAL'")
	List<Investigador> integrantesGrupo(@Param("id") Long id);
	
	/**
	 * Obtiene los integrantes de un programa específico.
	 *
	 * @param id el id del programa
	 * @return la lista de integrantes del programa
	 */
	@Query("select distinct NEW co.edu.uniquindio.gri.model.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.model.Programa p join p.grupos g join g.investigadores gi join gi.investigadores i where p.id = :id and gi.estado ='ACTUAL'")
	List<Investigador> integrantesPrograma(@Param("id") Long id);
	
	/**
	 * Obtiene los integrantes de un centro de investigaciones específico.
	 *
	 * @param id el id del centro de investigaciones
	 * @return la lista de integrantes del centro de investigaciones
	 */
	@Query("select distinct NEW co.edu.uniquindio.gri.model.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) from co.edu.uniquindio.gri.model.Centro c join c.grupo g join g.investigadores gi join gi.investigadores i where c.id = :id and gi.estado ='ACTUAL'")
	List<Investigador> integrantesCentro(@Param("id") Long id);
	
	/**
	 * Obtiene los integrantes de un grupo específico.
	 *
	 * @param id el id del grupo
	 * @return la lista de integrantes del grupo
	 */
	@Query(value="select distinct i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia from gri.investigadores i join gri.grupos_inves gi on i.id = gi.investigadores_id join gri.grupos g on g.id = gi.grupos_id join gri.programas_grupos pg on g.id = pg.grupos_id join gri.programas p on p.id = pg.programas_id join gri.facultades f on f.id = p.facultades_id where f.id =:id and gi.estado = 'ACTUAL' union select distinct i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia from gri.investigadores i join gri.grupos_inves gi on i.id = gi.investigadores_id join gri.grupos g on g.id = gi.grupos_id join gri.centros c on c.id = g.centros_id join gri.facultades f on f.id = c.facultades_id where f.id =:id and gi.estado = 'ACTUAL'", nativeQuery=true)
	List<Investigador> integrantesFacultad(@Param("id") Long id);
	
	/**
	 * Obtiene los integrantes de un grupo específico.
	 *
	 * @param id el id del grupo
	 * @return la lista de integrantes del grupo
	 */
	@Query("select distinct NEW co.edu.uniquindio.gri.model.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia) FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos g where g.estado = 'ACTUAL'")
	List<Investigador> integrantesGeneral();
	
}
