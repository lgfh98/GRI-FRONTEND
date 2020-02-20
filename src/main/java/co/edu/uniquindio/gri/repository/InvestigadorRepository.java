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
public interface InvestigadorRepository extends JpaRepository<Investigador, Long> {

	public static final  String MODELO_INVESTIGADOR="co.edu.uniquindio.gri.model.Investigador(i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia,i.sexo)";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("select distinct NEW " +MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos g where g.estado = 'ACTUAL'")
	List<Investigador> findAll();

	/**
	 * Obtiene los integrantes de un grupo específico.
	 *
	 * @param id el id del grupo
	 * @return la lista de integrantes del grupo
	 */
	@Query("select NEW "+MODELO_INVESTIGADOR+" from co.edu.uniquindio.gri.model.Grupo g join g.investigadores gi join gi.investigadores i where g.id =:id and gi.estado ='ACTUAL'")
	List<Investigador> integrantesGrupo(@Param("id") Long id);

	/**
	 * Obtiene los integrantes de un programa específico.
	 *
	 * @param id el id del programa
	 * @return la lista de integrantes del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" from co.edu.uniquindio.gri.model.Programa p join p.grupos g join g.investigadores gi join gi.investigadores i where p.id = :id and gi.estado ='ACTUAL'")
	List<Investigador> integrantesPrograma(@Param("id") Long id);

	/**
	 * Obtiene los integrantes de un centro de investigaciones específico.
	 *
	 * @param id el id del centro de investigaciones
	 * @return la lista de integrantes del centro de investigaciones
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" from co.edu.uniquindio.gri.model.Centro c join c.grupo g join g.investigadores gi join gi.investigadores i where c.id = :id and gi.estado ='ACTUAL'")
	List<Investigador> integrantesCentro(@Param("id") Long id);

	/**
	 * Obtiene los integrantes de un grupo específico.
	 *
	 * @param id el id del grupo
	 * @return la lista de integrantes del grupo
	 */
	@Query(value = "select distinct i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia from gri.investigadores i join gri.grupos_inves gi on i.id = gi.investigadores_id join gri.grupos g on g.id = gi.grupos_id join gri.programas_grupos pg on g.id = pg.grupos_id join gri.programas p on p.id = pg.programas_id join gri.facultades f on f.id = p.facultades_id where f.id =:id and gi.estado = 'ACTUAL' union select distinct i.id, i.nombre, i.categoria, i.nivelAcademico, i.pertenencia from gri.investigadores i join gri.grupos_inves gi on i.id = gi.investigadores_id join gri.grupos g on g.id = gi.grupos_id join gri.centros c on c.id = g.centros_id join gri.facultades f on f.id = c.facultades_id where f.id =:id and gi.estado = 'ACTUAL'", nativeQuery = true)
	List<Investigador> integrantesFacultad(@Param("id") Long id);

	/**
	 * Obtiene los integrantes de un grupo específico.
	 *
	 * @param id el id del grupo
	 * @return la lista de integrantes del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos g where g.estado = 'ACTUAL'")
	List<Investigador> integrantesGeneral();

	/**
	 * Obtiene los investigadores emeritos especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores emeritos de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id and i.categoria='INVESTIGADOR EMÉRITO'")
	List<Investigador> getInvestigadoresEmeritosFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores senior especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores senior de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id and i.categoria='INVESTIGADOR SENIOR'")
	List<Investigador> getInvestigadoresSeniorFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores asociados especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores asociados de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id and i.categoria='INVESTIGADOR ASOCIADO'")
	List<Investigador> getInvestigadoresAsociadosFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores junior especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores junior de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id and i.categoria='INVESTIGADOR JUNIOR'")
	List<Investigador> getInvestigadoresJuniorFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores sin categoria de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id and i.categoria='SIN CATEGORÍA'")
	List<Investigador> getInvestigadoresSinCategoriaFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores con formación de doctorado especificado por un id
	 * de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación de doctorado de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id  and i.nivelAcademico like '%DOCTORADO%'")
	List<Investigador> getInvestigadoresInternosDoctoresFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores con formación de magister especificado por un id
	 * de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación de magister de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id  and i.nivelAcademico like '%MAESTRÍA%'")
	List<Investigador> getInvestigadoresInternosMagisterFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores con formación de especialidad especificado por un
	 * id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación de especialidad de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id  and i.nivelAcademico in ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA')")
	List<Investigador> getInvestigadoresInternosEspecialistasFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores con formación de pregrado especificado por un id
	 * de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores con formación de pregrado de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id  and i.nivelAcademico like '%PREGRADO%'")
	List<Investigador> getInvestigadoresInternosPregradoFacultad(@Param("id") Long id);

	/**
	 * Obtiene los investigadores especificado por un id de facultad.
	 *
	 * @param facultadid el id de la facultad
	 * @return lista de investigadores de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id")
	List<Investigador> getInvestigadoresFacultad(@Param("id") Long facultadId);

	/**
	 * Obtiene los investigadores internos de una facultad
	 *
	 * @param facultadId el id de la facultad
	 * @return lista de investigadores internos de la facultad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and f.id=:id ")
	List<Investigador> getInvestigadoresInternosFacultad(@Param("id") Long facultadId);

	/**
	 * Obtiene los investigadores emeritos de la Universidad
	 *
	 * @return lista de investigadores emeritos de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and i.categoria='INVESTIGADOR EMÉRITO'")
	List<Investigador> getAllInvestigadoresEmeritos();

	/**
	 * Obtiene los investigadores senior de la Universidad
	 *
	 * @return lista de investigadores senior de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and i.categoria='INVESTIGADOR SENIOR'")
	List<Investigador> getAllInvestigadoresSenior();

	/**
	 * Obtiene los investigadores asociados de la Universidad
	 *
	 * @return lista de investigadores asociados de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and i.categoria='INVESTIGADOR ASOCIADO'")
	List<Investigador> getAllInvestigadoresAsociado();

	/**
	 * Obtiene los investigadores junior de la Universidad
	 *
	 * @return lista de investigadores junior de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and i.categoria='INVESTIGADOR JUNIOR'")
	List<Investigador> getAllInvestigadoresJunior();

	/**
	 * Obtiene los investigadores sin categoria de la Universidad
	 *
	 * @return lista de investigadores sin categoria de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' and i.categoria='SIN CATEGORÍA'")
	List<Investigador> getAllInvestigadoresSinCategoria();

	/**
	 * Obtiene los investigadores con formación de doctores de la Universidad
	 *
	 * @return lista de investigadores con formación de doctores de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL'  and i.nivelAcademico like '%DOCTORADO%'")
	List<Investigador> getAllInvestigadoresInternosDoctores();

	/**
	 * Obtiene los investigadores con formación de magister de la Universidad
	 *
	 * @return lista de investigadores con formación de magister de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL'  and i.nivelAcademico like '%MAGISTER%'")
	List<Investigador> getAllInvestigadoresInternosMagister();

	/**
	 * Obtiene los investigadores con formación de especialidad de la Universidad
	 *
	 * @return lista de investigadores con formación de especialidad de la
	 *         universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL'  and i.nivelAcademico in ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA')")
	List<Investigador> getAllInvestigadoresEspecialistas();

	/**
	 * Obtiene los investigadores internos con formación de pregrado de la
	 * Universidad
	 *
	 * @return lista de investigadores con formación de pregrado de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL'  and i.nivelAcademico like '%PREGRADO%'")
	List<Investigador> getAllInvestigadoresPregrado();

	/**
	 * Obtiene los investigadores internos de la universidad
	 *
	 * @return lista de investigadores internos de la universidad
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p join p.facultad f where gi.estado = 'ACTUAL' ")
	List<Investigador> getAllInvestigadoresInternos();

	/**
	 * Obtiene los investigadores especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id")
	List<Investigador> getInvestigadoresCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores emeritos especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores emeritos del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id and i.categoria='INVESTIGADOR EMÉRITO'")
	List<Investigador> getInvestigadoresEmeritosCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores senior especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores senior del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id and i.categoria='INVESTIGADOR SENIOR'")
	List<Investigador> getInvestigadoresSeniorCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores asociados especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores asociados del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id and i.categoria='INVESTIGADOR ASOCIADO'")
	List<Investigador> getInvestigadoresAsociadosCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores junior especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores junior del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id and i.categoria='INVESTIGADOR JUNIOR'")
	List<Investigador> getInvestigadoresJuniorCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores sin categoria del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id and i.categoria='SIN CATEGORÍA'")
	List<Investigador> getInvestigadoresSinCategoriaCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores con formación de doctorado especificado por un id
	 * de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores con formación de doctorado del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id  and i.nivelAcademico like '%DOCTORADO%'")
	List<Investigador> getInvestigadoresInternosDoctoresCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores con formación de magister especificado por un id
	 * de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores con formación de magister del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id  and i.nivelAcademico like '%MAESTRÍA%'")
	List<Investigador> getInvestigadoresInternosMagisterCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores con formación de especialidad especificado por un
	 * id de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores con formación de especialidad del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id  and i.nivelAcademico in ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA')")
	List<Investigador> getInvestigadoresInternosEspecialistasCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores con formación de pregrado especificado por un id
	 * de centro.
	 *
	 * @param centroId el id del centro
	 * @return lista de investigadores con formación de pregrado del centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id  and i.nivelAcademico like '%PREGRADO%'")
	List<Investigador> getInvestigadoresInternosPregradoCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id")
	List<Investigador> getInvestigadoresPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores emeritos especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores emeritos del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id and i.categoria='INVESTIGADOR EMÉRITO'")
	List<Investigador> getInvestigadoresEmeritosPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores senior especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores senior del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id and i.categoria='INVESTIGADOR SENIOR'")
	List<Investigador> getInvestigadoresSeniorPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores asociados especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores asociados del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id and i.categoria='INVESTIGADOR ASOCIADO'")
	List<Investigador> getInvestigadoresAsociadosPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores junior especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores junior del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id and i.categoria='INVESTIGADOR JUNIOR'")
	List<Investigador> getInvestigadoresJuniorPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores sin categoria del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id and i.categoria='SIN CATEGORÍA'")
	List<Investigador> getInvestigadoresSinCategoriaPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores con formación de doctorado especificado por un id
	 * de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores con formación de doctorado del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id  and i.nivelAcademico like '%DOCTORADO%'")
	List<Investigador> getInvestigadoresInternosDoctoresPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores con formación de magister especificado por un id
	 * de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores con formación de magister del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id  and i.nivelAcademico like '%MAESTRÍA%'")
	List<Investigador> getInvestigadoresInternosMagisterPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores con formación de especialidad especificado por un
	 * id de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores con formación de especialidad del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id  and i.nivelAcademico in ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA')")
	List<Investigador> getInvestigadoresInternosEspecialistasPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores con formación de pregrado especificado por un id
	 * de programa.
	 *
	 * @param programaId el id del programa
	 * @return lista de investigadores con formación de pregrado del programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id  and i.nivelAcademico like '%PREGRADO%'")
	List<Investigador> getInvestigadoresInternosPregradoPrograma(@Param("id") Long programaId);

	/**
	 * Obtiene los investigadores especificado por un id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id")
	List<Investigador> getInvestigadoresGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores emeritos especificado por un id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores emeritos del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id and i.categoria='INVESTIGADOR EMÉRITO'")
	List<Investigador> getInvestigadoresEmeritosGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores senior especificado por un id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores senior del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id and i.categoria='INVESTIGADOR SENIOR'")
	List<Investigador> getInvestigadoresSeniorGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores asociados especificado por un id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores asociados del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id and i.categoria='INVESTIGADOR ASOCIADO'")
	List<Investigador> getInvestigadoresAsociadosGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores junior especificado por un id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores junior del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id and i.categoria='INVESTIGADOR JUNIOR'")
	List<Investigador> getInvestigadoresJuniorGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores sin categoria especificado por un id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores sin categoria del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id and i.categoria='SIN CATEGORÍA'")
	List<Investigador> getInvestigadoresSinCategoriaGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores con formación de doctorado especificado por un id
	 * de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores con formación de doctorado del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id  and i.nivelAcademico like '%DOCTORADO%'")
	List<Investigador> getInvestigadoresInternosDoctoresGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores con formación de magister especificado por un id
	 * de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores con formación de magister del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id  and i.nivelAcademico like '%MAESTRÍA%'")
	List<Investigador> getInvestigadoresInternosMagisterGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores con formación de especialidad especificado por un
	 * id de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores con formación de especialidad del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id  and i.nivelAcademico in ('ESPECIALIZACIÓN', 'ESPECIALIDAD MÉDICA')")
	List<Investigador> getInvestigadoresInternosEspecialistasGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores con formación de pregrado especificado por un id
	 * de grupo.
	 *
	 * @param grupoId el id del grupo
	 * @return lista de investigadores con formación de pregrado del grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id  and i.nivelAcademico like '%PREGRADO%'")
	List<Investigador> getInvestigadoresInternosPregradoGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores internos de un grupo
	 *
	 * @param facultadId el id de un grupo
	 * @return lista de investigadores internos de un grupo
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g where gi.estado = 'ACTUAL' and g.id=:id ")
	List<Investigador> getInvestigadoresInternosGrupo(@Param("id") Long grupoId);

	/**
	 * Obtiene los investigadores internos de un centro
	 *
	 * @param facultadId el id de un centro
	 * @return lista de investigadores internos de un centro
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.centro c where gi.estado = 'ACTUAL' and c.id=:id ")
	List<Investigador> getInvestigadoresInternosCentro(@Param("id") Long centroId);

	/**
	 * Obtiene los investigadores internos de un programa
	 *
	 * @param facultadId el id de un programa
	 * @return lista de investigadores internos de un programa
	 */
	@Query("select distinct NEW "+MODELO_INVESTIGADOR+" FROM co.edu.uniquindio.gri.model.Investigador i join i.grupos gi join gi.grupos g join g.programas p where gi.estado = 'ACTUAL' and p.id=:id ")
	List<Investigador> getInvestigadoresInternosPrograma(@Param("id") Long programaId);

}
