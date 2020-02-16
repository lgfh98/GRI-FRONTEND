package co.edu.uniquindio.gri.webcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uniquindio.gri.dao.CentroDAO;
import co.edu.uniquindio.gri.dao.FacultadDAO;
import co.edu.uniquindio.gri.dao.GrupoDAO;
import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.dao.LineasInvestigacionDAO;
import co.edu.uniquindio.gri.dao.PertenenciaDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.dao.ProgramaDAO;
import co.edu.uniquindio.gri.model.Centro;
import co.edu.uniquindio.gri.model.Facultad;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.Pertenencia;
import co.edu.uniquindio.gri.model.Programa;
import co.edu.uniquindio.gri.utilities.Util;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Controller
public class WebController {

	@Autowired
	GrupoDAO grupoDAO;

	@Autowired
	InvestigadorDAO investigadorDAO;

	@Autowired
	PertenenciaDAO pertenenciaDAO;

	@Autowired
	CentroDAO centroDAO;

	@Autowired
	ProgramaDAO programaDAO;

	@Autowired
	FacultadDAO facultadDAO;

	@Autowired
	ProduccionDAO produccionDAO;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	LineasInvestigacionDAO lineasInvestigacionDAO;
	@Autowired
	Util utilidades = new Util();

	@GetMapping(value = { "/", "inicio" })
	public String main(Model model) {
		List<BigInteger> stats = facultadDAO.getStats();

		model.addAttribute("cantFacultades", stats.get(0));
		model.addAttribute("cantCentros", stats.get(1));
		model.addAttribute("cantProgramas", stats.get(2));
		model.addAttribute("cantGrupos", stats.get(3));
		model.addAttribute("cantInves", stats.get(4));
		model.addAttribute("estadisticas", "");

		return "index";
	}

	@GetMapping("/login")
	public String getLogin(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {

			return main(model);
		}

		return "login";

	}

	@GetMapping("/investigadoresP")
	public String getInvestigadoresPertenencia(
			@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "subType", required = false, defaultValue = "pa") String subType,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);

		List<Investigador> investigadores = new ArrayList<Investigador>();
		if (Long.parseLong(id) != 0) {
			if (type.equals("f") || type.equals("u")) {
				if (subType.equals("adm")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ADMINISTRATIVO);
				} else if (subType.equals("dp")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_PLANTA);
				} else if (subType.equals("dc")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
				} else if (subType.equals("do")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
				} else if (subType.equals("ie")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_EXTERNO);
				} else if (subType.equals("ei")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ESTUDIANTE);
				} else if (subType.equals("ind")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_INDEFINIDO);
				} else {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
				}

			} else if (type.equals("g")) {
				if (subType.equals("adm")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ADMINISTRATIVO);
				} else if (subType.equals("dp")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_PLANTA);
				} else if (subType.equals("dc")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
				} else if (subType.equals("do")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
				} else if (subType.equals("ie")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_EXTERNO);
				} else if (subType.equals("ei")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ESTUDIANTE);
				} else if (subType.equals("ind")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_INDEFINIDO);
				} else {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
				}
			} else if (type.equals("c")) {
				if (subType.equals("adm")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ADMINISTRATIVO);
				} else if (subType.equals("dp")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_PLANTA);
				} else if (subType.equals("dc")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
				} else if (subType.equals("do")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
				} else if (subType.equals("ie")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_EXTERNO);
				} else if (subType.equals("ei")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ESTUDIANTE);
				} else if (subType.equals("ind")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_INDEFINIDO);
				} else {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
				}
			} else if (type.equals("p")) {
				if (subType.equals("adm")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ADMINISTRATIVO);
				} else if (subType.equals("dp")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_PLANTA);
				} else if (subType.equals("dc")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
				} else if (subType.equals("do")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
				} else if (subType.equals("ie")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_EXTERNO);
				} else if (subType.equals("ei")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_ESTUDIANTE);
				} else if (subType.equals("ind")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
					investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
							utilidades.PERTENENCIA_INDEFINIDO);
				} else {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
					investigadores = utilidades.agregarPertenenciaInves(investigadores);
				}
			}
		} else {
			if (subType.equals("adm")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_ADMINISTRATIVO);
			} else if (subType.equals("dp")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_DOCENTE_PLANTA);
			} else if (subType.equals("dc")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
			} else if (subType.equals("do")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
			} else if (subType.equals("ie")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_EXTERNO);
			} else if (subType.equals("ei")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_ESTUDIANTE);
			} else if (subType.equals("ind")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
				investigadores = utilidades.seleccionarInvestigadoresPertenencia(investigadores,
						utilidades.PERTENENCIA_INDEFINIDO);
			} else {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
				investigadores = utilidades.agregarPertenenciaInves(investigadores);
			}
		}

		model.addAttribute("listaInvestigadores", investigadores);

		return "investigadores";
	}

	@GetMapping("/investigadores")
	public String getInvestigadores(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "subType", required = false, defaultValue = "pa") String subType,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);

		List<Investigador> investigadores = new ArrayList<Investigador>();
		if (Long.parseLong(id) != 0) {
			if (type.equals("f") || type.equals("u")) {
				if (subType.equals("ie")) {
					investigadores = investigadorDAO.getInvestigadoresEmeritosFacultad(Long.parseLong(id));
				} else if (subType.equals("is")) {
					investigadores = investigadorDAO.getInvestigadoresSeniorFacultad(Long.parseLong(id));
				} else if (subType.equals("ia")) {
					investigadores = investigadorDAO.getInvestigadoresAsociadosFacultad(Long.parseLong(id));
				} else if (subType.equals("ij")) {
					investigadores = investigadorDAO.getInvestigadoresJuniorFacultad(Long.parseLong(id));
				} else if (subType.equals("isc")) {
					investigadores = investigadorDAO.getInvestigadoresSinCategoriaFacultad(Long.parseLong(id));
				} else if (subType.equals("fd")) {
					investigadores = investigadorDAO.getInvestigadoresInternosDoctoresFacultad(Long.parseLong(id));
				} else if (subType.equals("fm")) {
					investigadores = investigadorDAO.getInvestigadoresInternosMagisterFacultad(Long.parseLong(id));
				} else if (subType.equals("fe")) {
					investigadores = investigadorDAO.getInvestigadoresInternosEspecialistasFacultad(Long.parseLong(id));
				} else if (subType.equals("fp")) {
					investigadores = investigadorDAO.getInvestigadoresInternosPregradoFacultad(Long.parseLong(id));
				} else if (subType.equals("d")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id));
				} else {
					investigadores = investigadorDAO.getInvestigadoresFacultad(Long.parseLong(id));
				}
			} else if (type.equals("g")) {
				if (subType.equals("ie")) {
					investigadores = investigadorDAO.getInvestigadoresEmeritosGrupo(Long.parseLong(id));
				} else if (subType.equals("is")) {
					investigadores = investigadorDAO.getInvestigadoresSeniorGrupo(Long.parseLong(id));
				} else if (subType.equals("ia")) {
					investigadores = investigadorDAO.getInvestigadoresAsociadosGrupo(Long.parseLong(id));
				} else if (subType.equals("ij")) {
					investigadores = investigadorDAO.getInvestigadoresJuniorGrupo(Long.parseLong(id));
				} else if (subType.equals("isc")) {
					investigadores = investigadorDAO.getInvestigadoresSinCategoriaGrupo(Long.parseLong(id));
				} else if (subType.equals("fd")) {
					investigadores = investigadorDAO.getInvestigadoresInternosDoctoresGrupo(Long.parseLong(id));
				} else if (subType.equals("fm")) {
					investigadores = investigadorDAO.getInvestigadoresInternosMagisterGrupo(Long.parseLong(id));
				} else if (subType.equals("fe")) {
					investigadores = investigadorDAO.getInvestigadoresInternosEspecialistasGrupo(Long.parseLong(id));
				} else if (subType.equals("fp")) {
					investigadores = investigadorDAO.getInvestigadoresInternosPregradoGrupo(Long.parseLong(id));
				} else if (subType.equals("d")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id));
				} else {
					investigadores = investigadorDAO.getInvestigadoresGrupo(Long.parseLong(id));
				}
			} else if (type.equals("c")) {
				if (subType.equals("ie")) {
					investigadores = investigadorDAO.getInvestigadoresEmeritosCentro(Long.parseLong(id));
				} else if (subType.equals("is")) {
					investigadores = investigadorDAO.getInvestigadoresSeniorCentro(Long.parseLong(id));
				} else if (subType.equals("ia")) {
					investigadores = investigadorDAO.getInvestigadoresAsociadosCentro(Long.parseLong(id));
				} else if (subType.equals("ij")) {
					investigadores = investigadorDAO.getInvestigadoresJuniorCentro(Long.parseLong(id));
				} else if (subType.equals("isc")) {
					investigadores = investigadorDAO.getInvestigadoresSinCategoriaCentro(Long.parseLong(id));
				} else if (subType.equals("fd")) {
					investigadores = investigadorDAO.getInvestigadoresInternosDoctoresCentro(Long.parseLong(id));
				} else if (subType.equals("fm")) {
					investigadores = investigadorDAO.getInvestigadoresInternosMagisterCentro(Long.parseLong(id));
				} else if (subType.equals("fe")) {
					investigadores = investigadorDAO.getInvestigadoresInternosEspecialistasCentro(Long.parseLong(id));
				} else if (subType.equals("fp")) {
					investigadores = investigadorDAO.getInvestigadoresInternosPregradoCentro(Long.parseLong(id));
				} else if (subType.equals("d")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id));
				} else {
					investigadores = investigadorDAO.getInvestigadoresCentro(Long.parseLong(id));
				}
			} else if (type.equals("p")) {
				if (subType.equals("ie")) {
					investigadores = investigadorDAO.getInvestigadoresEmeritosPrograma(Long.parseLong(id));
				} else if (subType.equals("is")) {
					investigadores = investigadorDAO.getInvestigadoresSeniorPrograma(Long.parseLong(id));
				} else if (subType.equals("ia")) {
					investigadores = investigadorDAO.getInvestigadoresAsociadosPrograma(Long.parseLong(id));
				} else if (subType.equals("ij")) {
					investigadores = investigadorDAO.getInvestigadoresJuniorPrograma(Long.parseLong(id));
				} else if (subType.equals("isc")) {
					investigadores = investigadorDAO.getInvestigadoresSinCategoriaPrograma(Long.parseLong(id));
				} else if (subType.equals("fd")) {
					investigadores = investigadorDAO.getInvestigadoresInternosDoctoresPrograma(Long.parseLong(id));
				} else if (subType.equals("fm")) {
					investigadores = investigadorDAO.getInvestigadoresInternosMagisterPrograma(Long.parseLong(id));
				} else if (subType.equals("fe")) {
					investigadores = investigadorDAO.getInvestigadoresInternosEspecialistasPrograma(Long.parseLong(id));
				} else if (subType.equals("fp")) {
					investigadores = investigadorDAO.getInvestigadoresInternosPregradoPrograma(Long.parseLong(id));
				} else if (subType.equals("d")) {
					investigadores = investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id));
				} else {
					investigadores = investigadorDAO.getInvestigadoresPrograma(Long.parseLong(id));
				}
			}
		} else {
			if (subType.equals("ie")) {
				investigadores = investigadorDAO.getAllInvestigadoresEmeritos();
			} else if (subType.equals("is")) {
				investigadores = investigadorDAO.getAllInvestigadoresSenior();
			} else if (subType.equals("ia")) {
				investigadores = investigadorDAO.getAllInvestigadoresAsociado();
			} else if (subType.equals("ij")) {
				investigadores = investigadorDAO.getAllInvestigadoresJunior();
			} else if (subType.equals("isc")) {
				investigadores = investigadorDAO.getAllInvestigadoresSinCategoria();
			} else if (subType.equals("fd")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternosDoctores();
			} else if (subType.equals("fm")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternosMagister();
			} else if (subType.equals("fe")) {
				investigadores = investigadorDAO.getAllInvestigadoresEspecialistas();
			} else if (subType.equals("fp")) {
				investigadores = investigadorDAO.getAllInvestigadoresPregrado();
			} else if (subType.equals("d")) {
				investigadores = investigadorDAO.getAllInvestigadoresInternos();
			} else {
				investigadores = investigadorDAO.findAll();
			}
		}

		List<Investigador> investigadores_pertenencia = utilidades.agregarPertenenciaInves(investigadores);

		model.addAttribute("listaInvestigadores", investigadores_pertenencia);
		return "investigadores";
	}

	@GetMapping("/programas")
	public String getProgramas(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);
		if (Long.parseLong(id) != 0) {
			if (type.equals("pa")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasAcademicosFacultad(Long.parseLong(id)));
			} else if (type.equals("pd")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasDoctoradoFacultad(Long.parseLong(id)));
			} else if (type.equals("pm")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasMaestríaFacultad(Long.parseLong(id)));
			} else if (type.equals("pe")) {
				model.addAttribute("listaProgramas",
						programaDAO.getProgramasEspecializacionFacultad(Long.parseLong(id)));
			} else {
				model.addAttribute("listaProgramas", programaDAO.getAllProgramas());
			}
		} else {
			if (type.equals("pa")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasAcademicos());
			} else if (type.equals("pd")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasDoctorado());
			} else if (type.equals("pm")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasMaestria());
			} else if (type.equals("pe")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasEspecializacion());
			} else {
				model.addAttribute("listaProgramas", programaDAO.getAllProgramas());
			}
		}
		return "programas";
	}

	@GetMapping("/facultades")
	public String getFacultades(Model model) {
		model.addAttribute("listaFacultades", facultadDAO.getAllFacultades());
		return "facultades";
	}

	@GetMapping("/centros")
	public String getCentros(@RequestParam(name = "type", required = false, defaultValue = "pa") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);
		if (Long.parseLong(id) != 0) {
			model.addAttribute("listaCentros", centroDAO.getAllCentrosFacultad(Long.parseLong(id)));
		} else {
			model.addAttribute("listaCentros", centroDAO.getAllCentros());
		}
		return "centros";
	}

	@GetMapping("/grupos")
	public String getGrupos(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "subType", required = false, defaultValue = "pa") String subType,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);
		if (Long.parseLong(id) != 0) {
			if (type.equals("f") || type.equals("u")) {
				if (subType.equals("ca1")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposA1Facultad(Long.parseLong(id)));
				} else if (subType.equals("ca")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposAFacultad(Long.parseLong(id)));
				} else if (subType.equals("cb")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposBFacultad(Long.parseLong(id)));
				} else if (subType.equals("cc")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposCFacultad(Long.parseLong(id)));
				} else if (subType.equals("cr")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposReconocidosFacultad(Long.parseLong(id)));
				} else if (subType.equals("cnr")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposNoReconocidosFacultad(Long.parseLong(id)));
				} else {
					model.addAttribute("listaGrupos", grupoDAO.getAllGruposFacultad(Long.parseLong(id)));
				}
			} else if (type.equals("c")) {
				if (subType.equals("ca1")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposA1Centro(Long.parseLong(id)));
				} else if (subType.equals("ca")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposACentro(Long.parseLong(id)));
				} else if (subType.equals("cb")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposBCentro(Long.parseLong(id)));
				} else if (subType.equals("cc")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposCCentro(Long.parseLong(id)));
				} else if (subType.equals("cr")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposReconocidosCentro(Long.parseLong(id)));
				} else if (subType.equals("cnr")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposNoReconocidosCentro(Long.parseLong(id)));
				} else {
					model.addAttribute("listaGrupos", grupoDAO.getAllGruposCentro_0(Long.parseLong(id)));
				}
			} else if (type.equals("p")) {
				if (subType.equals("ca1")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposA1Programa(Long.parseLong(id)));
				} else if (subType.equals("ca")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposAPrograma(Long.parseLong(id)));
				} else if (subType.equals("cb")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposBPrograma(Long.parseLong(id)));
				} else if (subType.equals("cc")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposCPrograma(Long.parseLong(id)));
				} else if (subType.equals("cr")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposReconocidosPrograma(Long.parseLong(id)));
				} else if (subType.equals("cnr")) {
					model.addAttribute("listaGrupos", grupoDAO.getGruposNoReconocidosPrograma(Long.parseLong(id)));
				} else {
					model.addAttribute("listaGrupos", grupoDAO.getAllGruposPrograma(Long.parseLong(id)));
				}
			}
		} else {
			if (subType.equals("ca1")) {
				model.addAttribute("listaGrupos", grupoDAO.getAllGruposA1());
			} else if (subType.equals("ca")) {
				model.addAttribute("listaGrupos", grupoDAO.getAllGruposA());
			} else if (subType.equals("cb")) {
				model.addAttribute("listaGrupos", grupoDAO.getAllGruposB());
			} else if (subType.equals("cc")) {
				model.addAttribute("listaGrupos", grupoDAO.getAllGruposC());
			} else if (subType.equals("cr")) {
				model.addAttribute("listaGrupos", grupoDAO.getAllGruposReconocidos());
			} else if (subType.equals("cnr")) {
				model.addAttribute("listaGrupos", grupoDAO.getAllGruposNoReconocidos());
			} else {
				model.addAttribute("listaGrupos", grupoDAO.findAll());
			}
		}
		return "grupos";
	}

	@GetMapping("/lineas")
	public String getLineasInvestigacion(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "subType", required = false, defaultValue = "u") String subType,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);
		if (Long.parseLong(id) != 0) {
			if (type.equals("f") || type.equals("u")) {
				model.addAttribute("listaLineas", lineasInvestigacionDAO.getLineasFacultad(Long.parseLong(id)));
			} else if (type.equals("c")) {
				model.addAttribute("listaLineas", lineasInvestigacionDAO.getLineasCentro(Long.parseLong(id)));
			} else if (type.equals("p")) {
				model.addAttribute("listaLineas", lineasInvestigacionDAO.getLineasPrograma(Long.parseLong(id)));
			} else if (type.equals("g")) {
				model.addAttribute("listaLineas", lineasInvestigacionDAO.getLineasGrupo(Long.parseLong(id)));
			} else if (type.equals("i")) {
				model.addAttribute("listaLineas", lineasInvestigacionDAO.getLineasGrupo(Long.parseLong(id)));
			} else {
				model.addAttribute("listaLineas", lineasInvestigacionDAO.findAll());
			}
		} else {
			model.addAttribute("listaLineas", lineasInvestigacionDAO.findAll());
		}
		return "lineas";
	}

	@GetMapping("/general")
	public String getTipologias(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);

		if (type.equals("u")) {
			List<Facultad> facultades = facultadDAO.getAllFacultades();

			model.addAttribute("nombre", "Tipología De Productos Para La Universidad Del Quindío");
			model.addAttribute("lista", facultades);
			model.addAttribute("subtipo", "f");
			model.addAttribute("color", "card-0");
			model.addAttribute("tamanio", "ci-" + calcularTamanio(facultades.size()));

		} else if (type.equals("f")) {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));
			List<Programa> programas = programaDAO.getProgramasFacultad(Long.parseLong(id));

			model.addAttribute("nombre", "Tipología de Productos Para la Facultad de " + f.getNombre());
			model.addAttribute("lista", programas);
			model.addAttribute("subtipo", "p");
			model.addAttribute("color", "card-" + f.getId());
			model.addAttribute("tamanio", "ci-" + calcularTamanio(programas.size()));

		} else if (type.equals("p")) {
			Programa p = programaDAO.getProgramaById(Long.parseLong(id));
			List<Grupo> grupos = grupoDAO.getGruposPrograma(Long.parseLong(id));

			model.addAttribute("nombre", p.getNombre());
			model.addAttribute("lista", grupos);
			model.addAttribute("subtipo", "g");
			model.addAttribute("color", "card-" + p.getFacultad().getId());
			model.addAttribute("tamanio", "ci-" + calcularTamanio(grupos.size()));

		} else if (type.equals("c")) {
			Centro c = centroDAO.getCentroById(Long.parseLong(id));
			List<Grupo> grupos = grupoDAO.getGruposCentro(Long.parseLong(id));

			model.addAttribute("nombre", c.getNombre());
			model.addAttribute("lista", grupos);
			model.addAttribute("subtipo", "g");
			model.addAttribute("color", "card-" + c.getFacultad().getId());
			model.addAttribute("tamanio", "ci-" + calcularTamanio(grupos.size()));

		} else if (type.equals("g")) {
			Grupo g = grupoDAO.findOne(Long.parseLong(id));

			model.addAttribute("nombre", g.getNombre());
			model.addAttribute("color", "card-" + g.getProgramas().get(0).getFacultad().getId());

		} else if (type.equals("i")) {
			Investigador i = investigadorDAO.findOne(Long.parseLong(id));

			model.addAttribute("nombre",
					"Tipología de Productos de " + utilidades.convertToTitleCaseIteratingChars(i.getNombre()));
			model.addAttribute("color", "card-0");
		}

		return "general";
	}

	@GetMapping("/inventario")
	public String getInventario(@RequestParam(name = "id", required = false, defaultValue = "u") String id,
			Model model) {

		if (id.equals("u")) {
			model.addAttribute("nombre", "Producciones en Custodia");
			model.addAttribute("lista", facultadDAO.getAllFacultades());
			model.addAttribute("tamanio", "ci-4");
			model.addAttribute("color", "card-0");
			model.addAttribute("subtipo", "f");
		} else {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));
			List<Grupo> listaGrupos = grupoDAO.getGruposPertenecientes(Long.parseLong(id), "f");
			model.addAttribute("nombre", f.getNombre());
			model.addAttribute("lista", listaGrupos);
			model.addAttribute("color", "card-" + f.getId());
			model.addAttribute("id", "" + f.getId());
			model.addAttribute("tamanio", "ci-" + calcularTamanio(listaGrupos.size()));
		}
		return "inventario/inventario";
	}

	@GetMapping("/reporteinventario")
	public String getReporteInventario(@RequestParam(name = "id", required = true) String id, Model model) {

		Grupo g = grupoDAO.findOne(Long.parseLong(id));

		model.addAttribute("nombre", g.getNombre());
		model.addAttribute("color", "card-" + g.getProgramas().get(0).getFacultad().getId());
		model.addAttribute("id", "" + g.getProgramas().get(0).getFacultad().getId());
		model.addAttribute("producciones", produccionDAO.getAllProducciones(Long.parseLong(id)));

		return "inventario/reporteinventario";
	}

	@GetMapping("/reportepertenencia")
	public String getReportePertenencia(@RequestParam(name = "id", required = true) String id, Model model) {

		Grupo g = grupoDAO.findOne(Long.parseLong(id));

		List<Investigador> integrantes = new ArrayList<Investigador>();

		integrantes = investigadorDAO.getInvestigadoresGrupo(Long.parseLong(id));

		List<String> pertenencias = new ArrayList<String>();

		utilidades.agregarPertenenciaInves(integrantes);

		pertenencias.add(Util.PERTENENCIA_INDEFINIDO);
		pertenencias.add(Util.PERTENENCIA_ADMINISTRATIVO);
		pertenencias.add(Util.PERTENENCIA_DOCENTE_PLANTA);
		pertenencias.add(Util.PERTENENCIA_DOCENTE_CATEDRATICO);
		pertenencias.add(Util.PERTENENCIA_DOCENTE_OCASIONAL);
		pertenencias.add(Util.PERTENENCIA_EXTERNO);
		pertenencias.add(Util.PERTENENCIA_ESTUDIANTE);

		model.addAttribute("pertenencias", pertenencias);
		model.addAttribute("nombre", g.getNombre());
		model.addAttribute("color", "card-" + g.getProgramas().get(0).getFacultad().getId());
		model.addAttribute("integrantes", integrantes);
		model.addAttribute("id", "" + g.getProgramas().get(0).getFacultad().getId());

		return "pertenencia_investigadores/reportepertenencia";

	}

	@GetMapping("/pertenencia")
	public String getPertenencia(@RequestParam(name = "id", required = false, defaultValue = "u") String id,
			Model model) {

		if (id.equals("u")) {

			model.addAttribute("nombre", "Pertenencia de Investigadores");
			model.addAttribute("lista", facultadDAO.getAllFacultades());
			model.addAttribute("tamanio", "ci-4");
			model.addAttribute("color", "card-0");
			model.addAttribute("subtipo", "f");
		}

		else {

			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));
			List<Grupo> listaGrupos = grupoDAO.getGruposPertenecientes(Long.parseLong(id), "f");
			model.addAttribute("nombre", f.getNombre());
			model.addAttribute("lista", listaGrupos);
			model.addAttribute("color", "card-" + f.getId());
			model.addAttribute("id", "" + f.getId());
			model.addAttribute("tamanio", "ci-" + calcularTamanio(listaGrupos.size()));

		}

		return "pertenencia_investigadores/pertenencia";

	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {

		model.addAttribute("tamanio", "2");

		return "admin";

	}

	/**
	 * permite obtener el reporte estadistico solicitado en formato pdf
	 * 
	 * @param type
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/imprimir-reporte-estadistico")
	public void imprimirReporteEstadistico(
			@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model,
			HttpServletResponse response) throws SQLException, IOException, JRException {

		Connection conexion = jdbcTemplate.getDataSource().getConnection();

		List<JasperPrint> jasperPrintList = new ArrayList<>();

		configurarReportes(jasperPrintList, type, id, conexion);

		imprimirReporte(response, jasperPrintList);

		conexion.close();

	}

	/**
	 * permite obtener el reporte estadistico solicitado en formato pdf
	 * 
	 * @param type
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/descargar-reporte-estadistico")
	public void descargarReporteEstadistico(
			@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model,
			HttpServletResponse response) throws SQLException, IOException, JRException {

		Connection conexion = jdbcTemplate.getDataSource().getConnection();

		List<JasperPrint> jasperPrintList = new ArrayList<>();

		configurarReportes(jasperPrintList, type, id, conexion);

		descargarReportePDF(response, jasperPrintList);

		conexion.close();

	}

	/**
	 * 
	 * @param response
	 * @param jasperPrintList
	 * @throws IOException
	 * @throws JRException
	 */
	private void imprimirReporte(HttpServletResponse response, List<JasperPrint> jasperPrintList)
			throws IOException, JRException {

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=reporte de investigación.pdf");

		final OutputStream outStream = response.getOutputStream();

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

		SimpleOutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(outStream);

		exporter.setExporterOutput(exporterOutput);

		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();

		exporter.setConfiguration(configuration);

		exporter.exportReport();

	}

	/**
	 * 
	 * @param response
	 * @param jasperPrintList
	 * @throws IOException
	 * @throws JRException
	 */
	private void descargarReportePDF(HttpServletResponse response, List<JasperPrint> jasperPrintList)
			throws IOException, JRException {
		response.setContentType("application/download");

		response.setHeader("Content-disposition", "inline; filename=reporte de investigación.pdf");

		final OutputStream outStream = response.getOutputStream();

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

		SimpleOutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(outStream);

		exporter.setExporterOutput(exporterOutput);

		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();

		exporter.setConfiguration(configuration);

		exporter.exportReport();

	}

	/**
	 * Permite generar un listado de archivos .jasper, que seran exportados en un
	 * formato posteriormente, como pdf.
	 * 
	 * @param jasperPrintList lista de archivos .jasper que será generada
	 * @param type
	 * @param id
	 * @param conexion
	 */
	private void configurarReportes(List<JasperPrint> jasperPrintList, String type, String id, Connection conexion)
			throws JRException {

		BigInteger cantidad_grupos = new BigInteger("0");
		BigInteger cantidad_investigadores = new BigInteger("0");
		BigInteger cantidad_producciones = new BigInteger("0");

		// PARAMETROS FACULTAD//
		String title_facultad = "";
		String mision_facultad = "";
		String vision_facultad = "";
		String contacto_facultad = "";
		Long id_facultad = null;
		////////////

		// PARAMETROS PROGRAMA//
		String title_programa = "";
		String mision_programa = "";
		String vision_programa = "";
		String contacto_programa = "";
		Long id_programa = null;
		////////////

		// PARAMETROS CENTRO//
		String title_centro = "";
		String info_general = "";
		String contacto_centro = "";
		Long id_centro = null;
		////////////

		// PARAMETROS GRUPO//
		String title_grupo = "";
		String contacto_grupo = "";
		Long id_grupo = null;
		////////////

		// PARAMETROS INVESTIGADOR//
		String nombre_investigador = "";
		Long id_investigador = null;
		////////////

		boolean facultad = false;
		boolean programa = false;
		boolean centro = false;
		boolean grupo = false;
		boolean universidad = false;
		boolean investigador = false;

		if (type.equals("f")) {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));
			facultad = true;
			title_facultad = f.getNombre();

			if (f.getMision() != null) {

				mision_facultad = f.getMision().replaceAll("\n", " ");

			}
			if (f.getVision() != null) {

				vision_facultad = f.getVision().replaceAll("\n", " ");

			}

			contacto_facultad = f.getContacto();
			id_facultad = new Long(f.getId());

			cantidad_grupos = BigInteger.valueOf(grupoDAO.getAllGruposFacultad(id_facultad).size());
			cantidad_investigadores = BigInteger.valueOf(investigadorDAO.getInvestigadoresFacultad(id_facultad).size());

			int suma = 0;

			for (int j = 0; j < 7; j++) {
				if (j != 5) {
					suma += produccionDAO.getCantidadProduccionesFacultadPorTipo(String.valueOf(id_facultad), j + "")
							.intValue();
				} else {
					suma += produccionDAO.getCantidadProduccionesFacultadPorTipo(String.valueOf(id_facultad), j + "")
							.intValue();
				}

			}

			suma += produccionDAO.getCantidadProduccionesFacultadPorSubTipo(String.valueOf(id_facultad), "32")
					.intValue();
			suma += produccionDAO.getCantidadProduccionesFacultadPorSubTipo(String.valueOf(id_facultad), "33")
					.intValue();

			cantidad_producciones = new BigInteger(String.valueOf(suma));

		} else if (type.equals("p")) {
			Programa p = programaDAO.getProgramaById(Long.parseLong(id));
			programa = true;
			title_programa = p.getNombre();

			if (p.getMision() != null) {

				mision_programa = p.getMision().replaceAll("\n", " ");

			}
			if (p.getVision() != null) {

				vision_programa = p.getVision().replaceAll("\n", " ");

			}

			contacto_programa = p.getContacto();
			id_programa = p.getId();
			id_facultad = p.getFacultad().getId();

			cantidad_grupos = BigInteger.valueOf(grupoDAO.getAllGruposPrograma(id_programa).size());
			cantidad_investigadores = BigInteger.valueOf(investigadorDAO.getInvestigadoresPrograma(id_programa).size());

			int suma = 0;

			for (int j = 0; j < 7; j++) {
				if (j != 5) {
					suma += produccionDAO.getCantidadProduccionesProgramaPorTipo(String.valueOf(id_programa), j + "")
							.intValue();
				} else {
					suma += produccionDAO.getCantidadProduccionesProgramaPorTipo(String.valueOf(id_programa), j + "")
							.intValue();
				}

			}

			suma += produccionDAO.getCantidadProduccionesProgramaPorSubTipo(String.valueOf(id_programa), "32")
					.intValue();
			suma += produccionDAO.getCantidadProduccionesProgramaPorSubTipo(String.valueOf(id_programa), "33")
					.intValue();

			cantidad_producciones = new BigInteger(String.valueOf(suma));

		} else if (type.equals("c")) {
			Centro c = centroDAO.getCentroById(Long.parseLong(id));
			centro = true;
			title_centro = c.getNombre();

			info_general = c.getInformaciongeneral().replaceAll("\n", " ");

			contacto_centro = c.getContacto();
			id_centro = c.getId();
			id_facultad = c.getFacultad().getId();
			cantidad_grupos = BigInteger.valueOf(grupoDAO.getAllGruposCentro(id_centro).size());
			cantidad_investigadores = BigInteger.valueOf(investigadorDAO.getInvestigadoresCentro(id_centro).size());

			int suma = 0;

			for (int j = 0; j < 7; j++) {
				if (j != 5) {
					suma += produccionDAO.getCantidadProduccionesCentroPorTipo(String.valueOf(id_centro), j + "")
							.intValue();
				} else {
					suma += produccionDAO.getCantidadProduccionesCentroPorTipo(String.valueOf(id_centro), j + "")
							.intValue();
				}

			}

			suma += produccionDAO.getCantidadProduccionesCentroPorSubTipo(String.valueOf(id_centro), "32").intValue();
			suma += produccionDAO.getCantidadProduccionesCentroPorSubTipo(String.valueOf(id_centro), "33").intValue();

			cantidad_producciones = new BigInteger(String.valueOf(suma));

		} else if (type.equals("g")) {
			Grupo g = grupoDAO.findOne(Long.parseLong(id));
			grupo = true;
			title_grupo = g.getNombre();

			if (g.getInformaciongeneral() != null) {

				info_general = g.getInformaciongeneral().replaceAll("\n", " ");

			}

			contacto_grupo = g.getContacto();
			id_grupo = g.getId();
			id_facultad = g.getProgramas().get(0).getFacultad().getId();

			cantidad_investigadores = BigInteger.valueOf(investigadorDAO.getInvestigadoresGrupo(id_grupo).size());
			int suma = 0;

			for (int j = 0; j < 7; j++) {
				if (j != 5) {
					suma += produccionDAO.getCantidadProduccionesGrupoPorTipo(String.valueOf(id_grupo), j + "")
							.intValue();
				} else {
					suma += produccionDAO.getCantidadProduccionesGrupoPorTipo(String.valueOf(id_grupo), j + "")
							.intValue();
				}

			}

			suma += produccionDAO.getCantidadProduccionesGrupoPorSubTipo(String.valueOf(id_grupo), "32").intValue();
			suma += produccionDAO.getCantidadProduccionesGrupoPorSubTipo(String.valueOf(id_grupo), "33").intValue();

			cantidad_producciones = new BigInteger(String.valueOf(suma));

		} else if (type.equals("i")) {
			Investigador i = investigadorDAO.findOne(Long.parseLong(id));
			investigador = true;
			nombre_investigador = utilidades.convertToTitleCaseIteratingChars(i.getNombre());
			id_investigador = i.getId();

			int suma = 0;

			for (int j = 0; j < 7; j++) {
				if (j != 5) {
					suma += produccionDAO
							.getCantidadProduccionesInvestigadorPorTipo(String.valueOf(id_investigador), j + "")
							.intValue();
				} else {
					suma += produccionDAO
							.getCantidadProduccionesInvestigadorPorTipo(String.valueOf(id_investigador), j + "")
							.intValue();
				}

			}

			suma += produccionDAO.getCantidadProduccionesInvestigadorPorSubTipo(String.valueOf(id_investigador), "32")
					.intValue();
			suma += produccionDAO.getCantidadProduccionesInvestigadorPorSubTipo(String.valueOf(id_investigador), "33")
					.intValue();

			cantidad_producciones = new BigInteger(String.valueOf(suma));

		} else {
			universidad = true;
//			cantidad_grupos = facultadDAO.getStats().get(3);
//
//			for (int i = 1; i < 8; i++) {
//				for (int j = 1; j < 9; j++) {
//					if (j != 3) {
//						cantidad_producciones.add(produccionDAO.getCantidadProduccionesFacultadPorTipo(i + "", j + ""));
//					} else {
//						cantidad_producciones
//								.add(produccionDAO.getCantidadProduccionesBFacultadPorTipo(i + "", j + ""));
//					}
//
//				}
//			}

		}

		int aux = 1;
		InputStream input = null;

		while (true) {
			Map<String, Object> parametros = new HashMap<>();

			if (universidad) {

				if (type.equals("u")) {

					input = this.getClass().getResourceAsStream("/reportes/" + type + "_" + id + "_" + aux + ".jasper");
				} else if (type.equals("i")) {

				}
			} else {
				if (facultad) {
					if (aux == 4 && cantidad_grupos.intValue() == 0) {
						aux++;
						continue;
					}
					if ((aux == 6 || aux == 7) && cantidad_investigadores.intValue() == 0) {
						aux++;
						continue;
					}
					if (aux == 9 && cantidad_producciones.intValue() == 0) {
						break;
					}
					parametros.put("title_facultad", title_facultad);
					parametros.put("mision_facultad", mision_facultad);
					parametros.put("vision_facultad", vision_facultad);
					parametros.put("contacto_facultad", contacto_facultad);
					parametros.put("id_facultad", id_facultad);

					input = this.getClass().getResourceAsStream("/reportes/" + type + "_" + aux + ".jasper");

				} else if (programa) {

					if (aux == 4 && cantidad_grupos.intValue() == 0) {
						aux++;
						continue;
					}
					if ((aux == 5 || aux == 6) && cantidad_investigadores.intValue() == 0) {
						aux++;
						continue;
					}
					if (aux == 8 && cantidad_producciones.intValue() == 0) {
						break;
					}
					parametros.put("title_programa", title_programa);
					parametros.put("mision_programa", mision_programa);
					parametros.put("vision_programa", vision_programa);
					parametros.put("contacto_programa", contacto_programa);
					parametros.put("id_facultad", id_facultad);
					parametros.put("id_programa", id_programa);

					input = this.getClass().getResourceAsStream("/reportes/" + type + "_" + aux + ".jasper");

				} else if (centro) {
					if (aux == 4 && cantidad_grupos.intValue() == 0) {
						aux++;
						continue;
					}
					if ((aux == 5 || aux == 6) && cantidad_investigadores.intValue() == 0) {
						aux++;
						continue;
					}
					if (aux == 8 && cantidad_producciones.intValue() == 0) {
						break;
					}
					parametros.put("title_centro", title_centro);
					parametros.put("info_general", info_general);
					parametros.put("contacto_centro", contacto_centro);
					parametros.put("id_facultad", id_facultad);
					parametros.put("id_centro", id_centro);

					input = this.getClass().getResourceAsStream("/reportes/" + type + "_" + aux + ".jasper");

				} else if (grupo) {

					if ((aux == 4 || aux == 5) && cantidad_investigadores.intValue() == 0) {
						aux++;
						continue;
					}
					if (aux == 7 && cantidad_producciones.intValue() == 0) {
						break;
					}
					parametros.put("title_grupo", title_grupo);
					parametros.put("info_general", info_general);
					parametros.put("contacto_grupo", contacto_grupo);
					parametros.put("id_facultad", id_facultad);
					parametros.put("id_grupo", id_grupo);

					input = this.getClass().getResourceAsStream("/reportes/" + type + "_" + aux + ".jasper");

				} else if (investigador) {
					if (aux == 3 && cantidad_producciones.intValue() == 0) {
						break;
					}
					parametros.put("nombre_investigador", nombre_investigador);
					parametros.put("id_investigador", id_investigador);

					input = this.getClass().getResourceAsStream("/reportes/" + type + "_" + aux + ".jasper");

				}
			}

			if (input == null) {
				break;
			} else {
				aux++;
			}

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(input);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);
			jasperPrintList.add(jasperPrint);

		}

	}

	@GetMapping("/estadisticas")
	public String getEstadisticas(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatosEstadisticas(id, type);
		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("colorTituloBoton", datos[2]);
		model.addAttribute("colorTotalBoton", datos[3]);

		List<String> stringinfo = formatoCadena(datos[4]);
		List<String> stringcontacto = formatoCadena(datos[5]);
		model.addAttribute("infogeneral", stringinfo);
		model.addAttribute("contacto", stringcontacto);

		if (type.equals("f")) {

			model.addAttribute("mision", datos[4]);
			model.addAttribute("vision", datos[6]);
			return getEstadisticasFacultad(id, model);

		} else if (type.equals("p")) {
			List<String> mision = formatoCadena(datos[6]);
			List<String> vision = formatoCadena(datos[7]);
			model.addAttribute("mision", mision);
			model.addAttribute("vision", vision);

			return getEstadisticasProgramas(id, model);

		} else if (type.equals("c")) {

			return getEstadisticasCentros(id, model);

		} else if (type.equals("g")) {
			model.addAttribute("infogeneral", datos[4]);
			return getEstadisticasGrupo(id, model);

		} else if (type.equals("i")) {
			return getEstadisticasInvestigador(id, model);

		} else {
			return getEstadisticasUniquindio(model);
		}

	}

	/**
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public String[] getDatosEstadisticas(String id, String type) {

		String[] datos = new String[8];

		if (type.equals("f")) {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));

			datos[0] = "Estadísticas Generales de la Facultad de " + f.getNombre();
			datos[1] = "card-" + f.getId();
			datos[2] = "btn-title-grid-" + f.getId();
			datos[3] = "btn-total-grid-" + f.getId();
			datos[4] = f.getMision();
			datos[5] = f.getContacto();
			datos[6] = f.getVision();
		} else if (type.equals("p")) {
			Programa p = programaDAO.getProgramaById(Long.parseLong(id));

			datos[0] = p.getNombre();
			datos[1] = "card-" + p.getFacultad().getId();
			datos[2] = "btn-title-grid-" + p.getFacultad().getId();
			datos[3] = "btn-total-grid-" + p.getFacultad().getId();
			datos[4] = p.getInformaciongeneral();
			datos[5] = p.getContacto();
			datos[6] = p.getMision();
			datos[7] = p.getVision();
		} else if (type.equals("c")) {
			Centro c = centroDAO.getCentroById(Long.parseLong(id));

			datos[0] = c.getNombre();
			datos[1] = "card-" + c.getFacultad().getId();
			datos[2] = "btn-title-grid-" + c.getFacultad().getId();
			datos[3] = "btn-total-grid-" + c.getFacultad().getId();
			datos[4] = c.getInformaciongeneral();
			datos[5] = c.getContacto();

		} else if (type.equals("g")) {
			Grupo g = grupoDAO.findOne(Long.parseLong(id));

			datos[0] = g.getNombre();
			datos[1] = "card-" + g.getProgramas().get(0).getFacultad().getId();
			datos[2] = "btn-title-grid-" + g.getProgramas().get(0).getFacultad().getId();
			datos[3] = "btn-total-grid-" + g.getProgramas().get(0).getFacultad().getId();
			datos[4] = g.getInformaciongeneral();
			datos[5] = g.getContacto();

		} else if (type.equals("i")) {
			Investigador i = investigadorDAO.findOne(Long.parseLong(id));

			datos[0] = i.getNombre();
			datos[1] = "card-0";
			datos[2] = "btn-title-grid-0";
			datos[3] = "btn-total-grid-0";
			datos[4] = "";
			datos[5] = "";
		} else {
			datos[4] = "";
			datos[5] = "";

		}

		return datos;

	}

	public int calcularTamanio(int tamanio) {
		for (int i = 5; i > 1; i--) {
			if ((tamanio % i) == 0) {
				return i;
			}
		}
		return calcularTamanio(tamanio + 1);
	}

	/**
	 * metodo que permite dar formato a una cadena quitando - y separandola por \n
	 * 
	 * @param cadena cadena a formatear
	 * @return lista con posiciones por \n
	 */
	public List<String> formatoCadena(String cadena) {

		List<String> resultado = new ArrayList<String>();

		if (cadena != null && !cadena.equals("")) {
			String[] splitcadena = cadena.split("\n");

			for (String string : splitcadena) {

				if (!string.equals("") && !string.contains("N/A")) {

					resultado.add(string);

				}
			}
		}
		return resultado;

	}

	public String getEstadisticasFacultad(String id, Model model) {
		// ------Llamado a las consultas en la base de datos para las
		// ------facultades-----------------------------------------------------------------------
		List<BigInteger> resumen = facultadDAO.getResumenGeneral(new Long(id));

		// ------Llamado a las consultas en la base de datos para
		// producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion",
				produccionDAO.getCantidadProduccionesFacultadPorTipo(id, "0"));
		model.addAttribute("cantidadActividadesEvaluador",
				produccionDAO.getCantidadProduccionesFacultadPorTipo(id, "1"));
		model.addAttribute("cantidadApropiacionSocial", produccionDAO.getCantidadProduccionesFacultadPorTipo(id, "2"));
		model.addAttribute("cantidadProduccionesBibliograficas",
				produccionDAO.getCantidadProduccionesBFacultadPorTipo(id, "3"));
		model.addAttribute("cantidadTecnicasTecnologicas",
				produccionDAO.getCantidadProduccionesFacultadPorTipo(id, "4"));
		model.addAttribute("cantidadProduccionesArte",
				String.valueOf(produccionDAO.getCantidadProduccionesFacultadPorTipo(id, "6")));
		model.addAttribute("cantidadProduccionesDemasTrabajos",
				produccionDAO.getCantidadProduccionesFacultadPorSubTipo(id, "32"));
		model.addAttribute("cantidadProduccionesProyectos",
				produccionDAO.getCantidadProduccionesFacultadPorSubTipo(id, "33"));

		// ------Adición de atributos al modelo con informacion de
		// basicas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicos", resumen.get(0));
		model.addAttribute("cantidadProgramasDoctorado", resumen.get(1));
		model.addAttribute("cantidadProgramasMaestria", resumen.get(2));
		model.addAttribute("cantidadProgramasEspecializacion", resumen.get(3));
		model.addAttribute("cantidadCentrosInvestigacion", resumen.get(4));
		model.addAttribute("cantidadGruposInvestigacion", resumen.get(5));
		model.addAttribute("cantidadLineasInvestigacion", resumen.get(6));
		model.addAttribute("cantidadInvestigadores", resumen.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1", resumen.get(8));
		model.addAttribute("cantidadGruposInvestigacionA", resumen.get(9));
		model.addAttribute("cantidadGruposInvestigacionB", resumen.get(10));
		model.addAttribute("cantidadGruposInvestigacionC", resumen.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidos", resumen.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocido", resumen.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritos", resumen.get(14));
		model.addAttribute("cantidadInvestigadoresSenior", resumen.get(15));
		model.addAttribute("cantidadInvestigadoresAsociados", resumen.get(16));
		model.addAttribute("cantidadInvestigadoresJunior", resumen.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoria", resumen.get(18));

		model.addAttribute("cantidadDocentesDoctores", resumen.get(19));
		model.addAttribute("cantidadDocentesMagister", resumen.get(20));
		model.addAttribute("cantidadDocentesEspecialistas", resumen.get(21));
		model.addAttribute("cantidadDocentesPregrado", resumen.get(22));

		model.addAttribute("cantidadProgramasTotal",
				resumen.get(0).add(resumen.get(1).add(resumen.get(2).add(resumen.get(3)))));

		model.addAttribute("cantidadGruposTotal", resumen.get(8).add(
				resumen.get(9).add(resumen.get(10).add(resumen.get(11).add(resumen.get(12)).add(resumen.get(13))))));

		model.addAttribute("cantidadInvestigadoresTotal",
				resumen.get(14).add(resumen.get(15).add(resumen.get(16).add(resumen.get(17).add(resumen.get(18))))));

		model.addAttribute("cantidadDocentesTotal",
				resumen.get(19).add(resumen.get(20).add(resumen.get(21).add(resumen.get(22)))));

		List<Investigador> investigadores_facultad = utilidades
				.agregarPertenenciaInves(investigadorDAO.getAllInvestigadoresInternosFacultad(Long.parseLong(id)));

		List<Investigador> investigadores_facultad_Adm = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_facultad, utilidades.PERTENENCIA_ADMINISTRATIVO);
		List<Investigador> investigadores_facultad_DP = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_facultad, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		List<Investigador> investigadores_facultad_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_facultad, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		List<Investigador> investigadores_facultad_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_facultad, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		List<Investigador> investigadores_facultad_IE = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_facultad, utilidades.PERTENENCIA_EXTERNO);
		List<Investigador> investigadores_facultad_EI = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_facultad, utilidades.PERTENENCIA_ESTUDIANTE);
		List<Investigador> investigadores_facultad_IND = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_facultad, utilidades.PERTENENCIA_INDEFINIDO);
		
		model.addAttribute("num_inves_Adm", investigadores_facultad_Adm.size());
		model.addAttribute("num_inves_DP", investigadores_facultad_DP.size());
		model.addAttribute("num_inves_DC", investigadores_facultad_DC.size());
		model.addAttribute("num_inves_DO", investigadores_facultad_DO.size());
		model.addAttribute("num_inves_IE", investigadores_facultad_IE.size());
		model.addAttribute("num_inves_EI", investigadores_facultad_EI.size());
		model.addAttribute("num_inves_IND", investigadores_facultad_IND.size());

		model.addAttribute("peAdm", "adm");
		model.addAttribute("peDp", "dp");
		model.addAttribute("peDc", "dc");
		model.addAttribute("peDo", "do");
		model.addAttribute("peIe", "ie");
		model.addAttribute("peEi", "ei");
		model.addAttribute("peInd", "ind");

		model.addAttribute("programaAcademico", "pa");
		model.addAttribute("programaDoctorado", "pd");
		model.addAttribute("programaMagister", "pm");
		model.addAttribute("programaEspecializacion", "pe");
		model.addAttribute("centrosInvestigacion", "c");
		model.addAttribute("gruposInvestigacion", "g");
		model.addAttribute("lineasInvestigacion", "l");
		model.addAttribute("investigadores", "i");

		model.addAttribute("categoriaA1", "ca1");
		model.addAttribute("categoriaA", "ca");
		model.addAttribute("categoriaB", "cb");
		model.addAttribute("categoriaC", "cc");
		model.addAttribute("categoriaReconocido", "cr");
		model.addAttribute("categoriaNoReconocido", "cnr");

		model.addAttribute("investigadorEmerito", "ie");
		model.addAttribute("investigadorSenior", "is");
		model.addAttribute("investigadorAsociado", "ia");
		model.addAttribute("investigadorJunior", "ij");
		model.addAttribute("investigadorSinCategoria", "isc");

		model.addAttribute("formacionDoctor", "fd");
		model.addAttribute("formacionMagister", "fm");
		model.addAttribute("formacionEspecialista", "fe");
		model.addAttribute("formacionPregrado", "fp");
		model.addAttribute("docentes", "d");

		model.addAttribute("idUniquindio", "0");
		model.addAttribute("idFacultad", id);

		return "estadisticas/facultades";
	}

	public String getEstadisticasProgramas(String id, Model model) {
		// ------Llamado a las consultas en la base de datos para las
		// ------facultades-----------------------------------------------------------------------
		List<BigInteger> resumen = programaDAO.getResumenGeneralPrograma(new Long(id));

		// ------Llamado a las consultas en la base de datos para
		// producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion",
				produccionDAO.getCantidadProduccionesProgramaPorTipo(id, "0"));
		model.addAttribute("cantidadActividadesEvaluador",
				produccionDAO.getCantidadProduccionesProgramaPorTipo(id, "1"));
		model.addAttribute("cantidadApropiacionSocial", produccionDAO.getCantidadProduccionesProgramaPorTipo(id, "2"));
		model.addAttribute("cantidadProduccionesBibliograficas",
				produccionDAO.getCantidadProduccionesBProgramaPorTipo(id, "3"));
		model.addAttribute("cantidadTecnicasTecnologicas",
				produccionDAO.getCantidadProduccionesProgramaPorTipo(id, "4"));
		model.addAttribute("cantidadProduccionesArte",
				String.valueOf(produccionDAO.getCantidadProduccionesProgramaPorTipo(id, "6")));
		model.addAttribute("cantidadProduccionesDemasTrabajos",
				produccionDAO.getCantidadProduccionesProgramaPorSubTipo(id, "32"));
		model.addAttribute("cantidadProduccionesProyectos",
				produccionDAO.getCantidadProduccionesProgramaPorSubTipo(id, "33"));

		// ------Adición de atributos al modelo con informacion de
		// basicas-----------------------------------------------------------------------
		model.addAttribute("cantidadGruposInvestigacion", resumen.get(0));
		model.addAttribute("cantidadLineasInvestigacion", resumen.get(1));
		model.addAttribute("cantidadInvestigadores", resumen.get(2));
		model.addAttribute("cantidadGruposInvestigacionA1", resumen.get(3));
		model.addAttribute("cantidadGruposInvestigacionA", resumen.get(4));
		model.addAttribute("cantidadGruposInvestigacionB", resumen.get(5));
		model.addAttribute("cantidadGruposInvestigacionC", resumen.get(6));
		model.addAttribute("cantidadGruposInvestigacionReconocidos", resumen.get(7));
		model.addAttribute("cantidadGruposInvestigacionNoReconocido", resumen.get(8));
		model.addAttribute("cantidadInvestigadoresEmeritos", resumen.get(9));
		model.addAttribute("cantidadInvestigadoresSenior", resumen.get(10));
		model.addAttribute("cantidadInvestigadoresAsociados", resumen.get(11));
		model.addAttribute("cantidadInvestigadoresJunior", resumen.get(12));
		model.addAttribute("cantidadInvestigadoresSinCategoria", resumen.get(13));
		model.addAttribute("cantidadDocentesDoctores", resumen.get(14));
		model.addAttribute("cantidadDocentesMagister", resumen.get(15));
		model.addAttribute("cantidadDocentesEspecialistas", resumen.get(16));
		model.addAttribute("cantidadDocentesPregrado", resumen.get(17));

		model.addAttribute("cantidadGruposTotal", resumen.get(3)
				.add(resumen.get(4).add(resumen.get(5).add(resumen.get(6).add(resumen.get(7)).add(resumen.get(8))))));

		model.addAttribute("cantidadInvestigadoresTotal",
				resumen.get(9).add(resumen.get(10).add(resumen.get(11).add(resumen.get(12).add(resumen.get(13))))));

		model.addAttribute("cantidadDocentesTotal",
				resumen.get(14).add(resumen.get(15).add(resumen.get(16).add(resumen.get(17)))));

		List<Investigador> investigadores_programa = utilidades
				.agregarPertenenciaInves(investigadorDAO.getAllInvestigadoresInternosPrograma(Long.parseLong(id)));

		List<Investigador> investigadores_programa_Adm = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_programa, utilidades.PERTENENCIA_ADMINISTRATIVO);
		List<Investigador> investigadores_programa_DP = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_programa, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		List<Investigador> investigadores_programa_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_programa, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		List<Investigador> investigadores_programa_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_programa, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		List<Investigador> investigadores_programa_IE = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_programa, utilidades.PERTENENCIA_EXTERNO);
		List<Investigador> investigadores_programa_EI = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_programa, utilidades.PERTENENCIA_ESTUDIANTE);
		List<Investigador> investigadores_programa_IND = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_programa, utilidades.PERTENENCIA_INDEFINIDO);

		model.addAttribute("num_inves_Adm", investigadores_programa_Adm.size());
		model.addAttribute("num_inves_DP", investigadores_programa_DP.size());
		model.addAttribute("num_inves_DC", investigadores_programa_DC.size());
		model.addAttribute("num_inves_DO", investigadores_programa_DO.size());
		model.addAttribute("num_inves_IE", investigadores_programa_IE.size());
		model.addAttribute("num_inves_EI", investigadores_programa_EI.size());
		model.addAttribute("num_inves_IND", investigadores_programa_IND.size());

		model.addAttribute("peAdm", "adm");
		model.addAttribute("peDp", "dp");
		model.addAttribute("peDc", "dc");
		model.addAttribute("peDo", "do");
		model.addAttribute("peIe", "ie");
		model.addAttribute("peEi", "ei");
		model.addAttribute("peInd", "ind");

		model.addAttribute("gruposInvestigacion", "g");
		model.addAttribute("lineasInvestigacion", "l");
		model.addAttribute("investigadores", "i");

		model.addAttribute("categoriaA1", "ca1");
		model.addAttribute("categoriaA", "ca");
		model.addAttribute("categoriaB", "cb");
		model.addAttribute("categoriaC", "cc");
		model.addAttribute("categoriaReconocido", "cr");
		model.addAttribute("categoriaNoReconocido", "cnr");

		model.addAttribute("investigadorEmerito", "ie");
		model.addAttribute("investigadorSenior", "is");
		model.addAttribute("investigadorAsociado", "ia");
		model.addAttribute("investigadorJunior", "ij");
		model.addAttribute("investigadorSinCategoria", "isc");

		model.addAttribute("formacionDoctor", "fd");
		model.addAttribute("formacionMagister", "fm");
		model.addAttribute("formacionEspecialista", "fe");
		model.addAttribute("formacionPregrado", "fp");
		model.addAttribute("docentes", "d");

		model.addAttribute("idUniquindio", "0");
		model.addAttribute("idPrograma", id);

		return "estadisticas/programas";
	}

	public String getEstadisticasCentros(String id, Model model) {
		// ------Llamado a las consultas en la base de datos para las
		// ------facultades-----------------------------------------------------------------------
		List<BigInteger> resumen = centroDAO.getResumenGeneralCentros(new Long(id));

		// ------Llamado a las consultas en la base de datos para
		// producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion",
				produccionDAO.getCantidadProduccionesCentroPorTipo(id, "0"));
		model.addAttribute("cantidadActividadesEvaluador", produccionDAO.getCantidadProduccionesCentroPorTipo(id, "1"));
		model.addAttribute("cantidadApropiacionSocial", produccionDAO.getCantidadProduccionesCentroPorTipo(id, "2"));
		model.addAttribute("cantidadProduccionesBibliograficas",
				produccionDAO.getCantidadProduccionesBCentroPorTipo(id, "3"));
		model.addAttribute("cantidadTecnicasTecnologicas", produccionDAO.getCantidadProduccionesCentroPorTipo(id, "4"));
		model.addAttribute("cantidadProduccionesArte",
				String.valueOf(produccionDAO.getCantidadProduccionesCentroPorTipo(id, "6")));
		model.addAttribute("cantidadProduccionesDemasTrabajos",
				produccionDAO.getCantidadProduccionesCentroPorSubTipo(id, "32"));
		model.addAttribute("cantidadProduccionesProyectos",
				produccionDAO.getCantidadProduccionesCentroPorSubTipo(id, "33"));

		// ------Adición de atributos al modelo con informacion de
		// basicas-----------------------------------------------------------------------
		model.addAttribute("cantidadGruposInvestigacion", resumen.get(0));
		model.addAttribute("cantidadLineasInvestigacion", resumen.get(1));
		model.addAttribute("cantidadInvestigadores", resumen.get(2));
		model.addAttribute("cantidadGruposInvestigacionA1", resumen.get(3));
		model.addAttribute("cantidadGruposInvestigacionA", resumen.get(4));
		model.addAttribute("cantidadGruposInvestigacionB", resumen.get(5));
		model.addAttribute("cantidadGruposInvestigacionC", resumen.get(6));
		model.addAttribute("cantidadGruposInvestigacionReconocidos", resumen.get(7));
		model.addAttribute("cantidadGruposInvestigacionNoReconocido", resumen.get(8));
		model.addAttribute("cantidadInvestigadoresEmeritos", resumen.get(9));
		model.addAttribute("cantidadInvestigadoresSenior", resumen.get(10));
		model.addAttribute("cantidadInvestigadoresAsociados", resumen.get(11));
		model.addAttribute("cantidadInvestigadoresJunior", resumen.get(12));
		model.addAttribute("cantidadInvestigadoresSinCategoria", resumen.get(13));
		model.addAttribute("cantidadDocentesDoctores", resumen.get(14));
		model.addAttribute("cantidadDocentesMagister", resumen.get(15));
		model.addAttribute("cantidadDocentesEspecialistas", resumen.get(16));
		model.addAttribute("cantidadDocentesPregrado", resumen.get(17));

		model.addAttribute("cantidadGruposTotal", resumen.get(3)
				.add(resumen.get(4).add(resumen.get(5).add(resumen.get(6).add(resumen.get(7)).add(resumen.get(8))))));

		model.addAttribute("cantidadInvestigadoresTotal",
				resumen.get(9).add(resumen.get(10).add(resumen.get(11).add(resumen.get(12).add(resumen.get(13))))));

		model.addAttribute("cantidadDocentesTotal",
				resumen.get(14).add(resumen.get(15).add(resumen.get(16).add(resumen.get(17)))));

		model.addAttribute("gruposInvestigacion", "g");
		model.addAttribute("lineasInvestigacion", "l");
		model.addAttribute("investigadores", "i");

		model.addAttribute("categoriaA1", "ca1");
		model.addAttribute("categoriaA", "ca");
		model.addAttribute("categoriaB", "cb");
		model.addAttribute("categoriaC", "cc");
		model.addAttribute("categoriaReconocido", "cr");
		model.addAttribute("categoriaNoReconocido", "cnr");

		model.addAttribute("investigadorEmerito", "ie");
		model.addAttribute("investigadorSenior", "is");
		model.addAttribute("investigadorAsociado", "ia");
		model.addAttribute("investigadorJunior", "ij");
		model.addAttribute("investigadorSinCategoria", "isc");

		model.addAttribute("formacionDoctor", "fd");
		model.addAttribute("formacionMagister", "fm");
		model.addAttribute("formacionEspecialista", "fe");
		model.addAttribute("formacionPregrado", "fp");
		model.addAttribute("docentes", "d");

		model.addAttribute("idUniquindio", "0");
		model.addAttribute("idCentro", id);

		List<Investigador> investigadores_centro = utilidades
				.agregarPertenenciaInves(investigadorDAO.getAllInvestigadoresInternosCentro(Long.parseLong(id)));

		List<Investigador> investigadores_centro_Adm = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_centro, utilidades.PERTENENCIA_ADMINISTRATIVO);
		List<Investigador> investigadores_centro_DP = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_centro, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		List<Investigador> investigadores_centro_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_centro, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		List<Investigador> investigadores_centro_DO = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_centro, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		List<Investigador> investigadores_centro_IE = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_centro, utilidades.PERTENENCIA_EXTERNO);
		List<Investigador> investigadores_centro_EI = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_centro, utilidades.PERTENENCIA_ESTUDIANTE);
		List<Investigador> investigadores_centro_IND = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_centro, utilidades.PERTENENCIA_INDEFINIDO);

		
		model.addAttribute("num_inves_Adm", investigadores_centro_Adm.size());
		model.addAttribute("num_inves_DP", investigadores_centro_DP.size());
		model.addAttribute("num_inves_DC", investigadores_centro_DC.size());
		model.addAttribute("num_inves_DO", investigadores_centro_DO.size());
		model.addAttribute("num_inves_IE", investigadores_centro_IE.size());
		model.addAttribute("num_inves_EI", investigadores_centro_EI.size());
		model.addAttribute("num_inves_IND", investigadores_centro_IND.size());

		model.addAttribute("peAdm", "adm");
		model.addAttribute("peDp", "dp");
		model.addAttribute("peDc", "dc");
		model.addAttribute("peDo", "do");
		model.addAttribute("peIe", "ie");
		model.addAttribute("peEi", "ei");
		model.addAttribute("peInd", "ind");

		return "estadisticas/centros";
	}

	public String getEstadisticasGrupo(String id, Model model) {
		// ------Llamado a las consultas en la base de datos para las
		// ------facultades-----------------------------------------------------------------------
		List<BigInteger> resumen = grupoDAO.getResumenGeneralGrupo(new Long(id));

		// ------Llamado a las consultas en la base de datos para
		// producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion",
				produccionDAO.getCantidadProduccionesGrupoPorTipo(id, "0"));
		model.addAttribute("cantidadActividadesEvaluador", produccionDAO.getCantidadProduccionesGrupoPorTipo(id, "1"));
		model.addAttribute("cantidadApropiacionSocial", produccionDAO.getCantidadProduccionesGrupoPorTipo(id, "2"));
		model.addAttribute("cantidadProduccionesBibliograficas",
				produccionDAO.getCantidadProduccionesBGrupoPorTipo(id, "3"));
		model.addAttribute("cantidadTecnicasTecnologicas", produccionDAO.getCantidadProduccionesGrupoPorTipo(id, "4"));
		model.addAttribute("cantidadProduccionesArte",
				String.valueOf(produccionDAO.getCantidadProduccionesGrupoPorTipo(id, "6")));
		model.addAttribute("cantidadProduccionesDemasTrabajos",
				produccionDAO.getCantidadProduccionesGrupoPorSubTipo(id, "32"));
		model.addAttribute("cantidadProduccionesProyectos",
				produccionDAO.getCantidadProduccionesGrupoPorSubTipo(id, "33"));

		// ------Adición de atributos al modelo con informacion de
		// basicas-----------------------------------------------------------------------
		model.addAttribute("cantidadLineasInvestigacion", resumen.get(0));
		model.addAttribute("cantidadInvestigadores", resumen.get(1));
		model.addAttribute("cantidadInvestigadoresEmeritos", resumen.get(2));
		model.addAttribute("cantidadInvestigadoresSenior", resumen.get(3));
		model.addAttribute("cantidadInvestigadoresAsociados", resumen.get(4));
		model.addAttribute("cantidadInvestigadoresJunior", resumen.get(5));
		model.addAttribute("cantidadInvestigadoresSinCategoria", resumen.get(6));
		model.addAttribute("cantidadDocentesDoctores", resumen.get(7));
		model.addAttribute("cantidadDocentesMagister", resumen.get(8));
		model.addAttribute("cantidadDocentesEspecialistas", resumen.get(9));
		model.addAttribute("cantidadDocentesPregrado", resumen.get(10));

		model.addAttribute("cantidadInvestigadoresTotal",
				resumen.get(2).add(resumen.get(3).add(resumen.get(4).add(resumen.get(5).add(resumen.get(6))))));

		model.addAttribute("cantidadDocentesTotal",
				resumen.get(7).add(resumen.get(8).add(resumen.get(9).add(resumen.get(10)))));

		model.addAttribute("gruposInvestigacion", "g");
		model.addAttribute("lineasInvestigacion", "l");
		model.addAttribute("investigadores", "i");

		model.addAttribute("investigadorEmerito", "ie");
		model.addAttribute("investigadorSenior", "is");
		model.addAttribute("investigadorAsociado", "ia");
		model.addAttribute("investigadorJunior", "ij");
		model.addAttribute("investigadorSinCategoria", "isc");

		model.addAttribute("formacionDoctor", "fd");
		model.addAttribute("formacionMagister", "fm");
		model.addAttribute("formacionEspecialista", "fe");
		model.addAttribute("formacionPregrado", "fp");
		model.addAttribute("docentes", "d");

		model.addAttribute("idUniquindio", "0");
		model.addAttribute("idGrupo", id);

		List<Investigador> investigadores_grupo = utilidades
				.agregarPertenenciaInves(investigadorDAO.getAllInvestigadoresInternosGrupo(Long.parseLong(id)));

		List<Investigador> investigadores_grupo_Adm = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_ADMINISTRATIVO);
		List<Investigador> investigadores_grupo_DP = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		List<Investigador> investigadores_grupo_DC = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		List<Investigador> investigadores_grupo_DO = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		List<Investigador> investigadores_grupo_IE = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_EXTERNO);
		List<Investigador> investigadores_grupo_EI = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_ESTUDIANTE);
		List<Investigador> investigadores_grupo_IND = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_grupo, utilidades.PERTENENCIA_INDEFINIDO);

		
		
		model.addAttribute("num_inves_Adm", investigadores_grupo_Adm.size());
		model.addAttribute("num_inves_DP", investigadores_grupo_DP.size());
		model.addAttribute("num_inves_DC", investigadores_grupo_DC.size());
		model.addAttribute("num_inves_DO", investigadores_grupo_DO.size());
		model.addAttribute("num_inves_IE", investigadores_grupo_IE.size());
		model.addAttribute("num_inves_EI", investigadores_grupo_EI.size());
		model.addAttribute("num_inves_IND", investigadores_grupo_IND.size());

		model.addAttribute("peAdm", "adm");
		model.addAttribute("peDp", "dp");
		model.addAttribute("peDc", "dc");
		model.addAttribute("peDo", "do");
		model.addAttribute("peIe", "ie");
		model.addAttribute("peEi", "ei");
		model.addAttribute("peInd", "ind");

		return "estadisticas/grupos";
	}

	public String getEstadisticasInvestigador(String id, Model model) {
		// ------Llamado a las consultas en la base de datos para
		// producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion",
				produccionDAO.getCantidadProduccionesInvestigadorPorTipo(id, "0"));
		model.addAttribute("cantidadActividadesEvaluador",
				produccionDAO.getCantidadProduccionesInvestigadorPorTipo(id, "1"));
		model.addAttribute("cantidadApropiacionSocial",
				produccionDAO.getCantidadProduccionesInvestigadorPorTipo(id, "2"));
		model.addAttribute("cantidadProduccionesBibliograficas",
				produccionDAO.getCantidadProduccionesBInvestigadorPorTipo(id, "3"));
		model.addAttribute("cantidadTecnicasTecnologicas",
				produccionDAO.getCantidadProduccionesInvestigadorPorTipo(id, "4"));
		model.addAttribute("cantidadProduccionesArte",
				String.valueOf(produccionDAO.getCantidadProduccionesInvestigadorPorTipo(id, "6")));
		model.addAttribute("cantidadProduccionesDemasTrabajos",
				produccionDAO.getCantidadProduccionesInvestigadorPorSubTipo(id, "32"));
		model.addAttribute("cantidadProduccionesProyectos",
				produccionDAO.getCantidadProduccionesInvestigadorPorSubTipo(id, "33"));

		return "estadisticas/investigadores";
	}

	public String getEstadisticasUniquindio(Model model) {

		model.addAttribute("color", "card-0");

		// ------Llamado a las consultas en la base de datos para
		// producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion", produccionDAO.getCantidadActividadesFormacion());
		model.addAttribute("cantidadActividadesEvaluador", produccionDAO.getCantidadActividadesEvaluador());
		model.addAttribute("cantidadApropiacionSocial", produccionDAO.getCantidadApropiacionSocial());
		model.addAttribute("cantidadProduccionesBibliograficas", produccionDAO.getCantidadProduccionesBibliograficas());
		model.addAttribute("cantidadTecnicasTecnologicas", produccionDAO.getCantidadTecnicasTecnologicas());
		model.addAttribute("cantidadProduccionesArte", String.valueOf(produccionDAO.getCantidadProduccionesArte()));
		model.addAttribute("cantidadProduccionesDemasTrabajos", produccionDAO.getCantidadProduccionesDemasTrabajos());
		model.addAttribute("cantidadProduccionesProyectos", produccionDAO.getCantidadProduccionesProyectos());

		// ------Llamado a las consultas en la base de datos para las
		// ------facultades-----------------------------------------------------------------------
		List<BigInteger> resumenCienciasBasicas = facultadDAO.getResumenGeneral(new Long("1"));
		List<BigInteger> resumenEducacion = facultadDAO.getResumenGeneral(new Long("2"));
		List<BigInteger> resumenCienciasDeLaSalud = facultadDAO.getResumenGeneral(new Long("3"));
		List<BigInteger> resumenIngenieria = facultadDAO.getResumenGeneral(new Long("4"));
		List<BigInteger> resumenCienciasHumanas = facultadDAO.getResumenGeneral(new Long("5"));
		List<BigInteger> resumenAgroindustria = facultadDAO.getResumenGeneral(new Long("6"));
		List<BigInteger> resumenCienciasEconomicas = facultadDAO.getResumenGeneral(new Long("7"));

		// Este número es usado para indicar la cantidad de investigadores total debido
		// a que con una suma aritmetica normal repetiría los investigadores
		BigInteger cantidadTotalInvestigadores = facultadDAO.getStats().get(4);

		// ------Adición de atributos al modelo con informacion de
		// ingenieria-----------------------------------------------------------------------

		model.addAttribute("cantidadProgramasAcademicosIngenieria", resumenIngenieria.get(0));
		model.addAttribute("cantidadProgramasDoctoradoIngenieria", resumenIngenieria.get(1));
		model.addAttribute("cantidadProgramasMaestriaIngenieria", resumenIngenieria.get(2));
		model.addAttribute("cantidadProgramasEspecializacionIngenieria", resumenIngenieria.get(3));
		model.addAttribute("cantidadCentrosInvestigacionIngenieria", resumenIngenieria.get(4));
		model.addAttribute("cantidadGruposInvestigacionIngenieria", resumenIngenieria.get(5));
		model.addAttribute("cantidadLineasInvestigacionIngenieria", resumenIngenieria.get(6));
		model.addAttribute("cantidadInvestigadoresIngenieria", resumenIngenieria.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1Ingenieria", resumenIngenieria.get(8));
		model.addAttribute("cantidadGruposInvestigacionAIngenieria", resumenIngenieria.get(9));
		model.addAttribute("cantidadGruposInvestigacionBIngenieria", resumenIngenieria.get(10));
		model.addAttribute("cantidadGruposInvestigacionCIngenieria", resumenIngenieria.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosIngenieria", resumenIngenieria.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoIngenieria", resumenIngenieria.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosIngenieria", resumenIngenieria.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorIngenieria", resumenIngenieria.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosIngenieria", resumenIngenieria.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorIngenieria", resumenIngenieria.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaIngenieria", resumenIngenieria.get(18));
		model.addAttribute("cantidadDocentesDoctoresIngenieria", resumenIngenieria.get(19));
		model.addAttribute("cantidadDocentesMagisterIngenieria", resumenIngenieria.get(20));
		model.addAttribute("cantidadDocentesEspecialistasIngenieria", resumenIngenieria.get(21));
		model.addAttribute("cantidadDocentesPregradoIngenieria", resumenIngenieria.get(22));

		// ------Adición de atributos al modelo con informacion de
		// basicas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasBasicas", resumenCienciasBasicas.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasBasicas", resumenCienciasBasicas.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasBasicas", resumenCienciasBasicas.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasBasicas", resumenCienciasBasicas.get(3));
		model.addAttribute("cantidadCentrosInvestigacionCienciasBasicas", resumenCienciasBasicas.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasBasicas", resumenCienciasBasicas.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasBasicas", resumenCienciasBasicas.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasBasicas", resumenCienciasBasicas.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1CienciasBasicas", resumenCienciasBasicas.get(8));
		model.addAttribute("cantidadGruposInvestigacionACienciasBasicas", resumenCienciasBasicas.get(9));
		model.addAttribute("cantidadGruposInvestigacionBCienciasBasicas", resumenCienciasBasicas.get(10));
		model.addAttribute("cantidadGruposInvestigacionCCienciasBasicas", resumenCienciasBasicas.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosCienciasBasicas", resumenCienciasBasicas.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoCienciasBasicas", resumenCienciasBasicas.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosCienciasBasicas", resumenCienciasBasicas.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorCienciasBasicas", resumenCienciasBasicas.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosCienciasBasicas", resumenCienciasBasicas.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorCienciasBasicas", resumenCienciasBasicas.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaCienciasBasicas", resumenCienciasBasicas.get(18));
		model.addAttribute("cantidadDocentesDoctoresCienciasBasicas", resumenCienciasBasicas.get(19));
		model.addAttribute("cantidadDocentesMagisterCienciasBasicas", resumenCienciasBasicas.get(20));
		model.addAttribute("cantidadDocentesEspecialistasCienciasBasicas", resumenCienciasBasicas.get(21));
		model.addAttribute("cantidadDocentesPregradoCienciasBasicas", resumenCienciasBasicas.get(22));

		// ------Adición de atributos al modelo con informacion de
		// educacion-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosEducacion", resumenEducacion.get(0));
		model.addAttribute("cantidadProgramasDoctoradoEducacion", resumenEducacion.get(1));
		model.addAttribute("cantidadProgramasMaestriaEducacion", resumenEducacion.get(2));
		model.addAttribute("cantidadProgramasEspecializacionEducacion", resumenEducacion.get(3));
		model.addAttribute("cantidadCentrosInvestigacionEducacion", resumenEducacion.get(4));
		model.addAttribute("cantidadGruposInvestigacionEducacion", resumenEducacion.get(5));
		model.addAttribute("cantidadLineasInvestigacionEducacion", resumenEducacion.get(6));
		model.addAttribute("cantidadInvestigadoresEducacion", resumenEducacion.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1Educacion", resumenEducacion.get(8));
		model.addAttribute("cantidadGruposInvestigacionAEducacion", resumenEducacion.get(9));
		model.addAttribute("cantidadGruposInvestigacionBEducacion", resumenEducacion.get(10));
		model.addAttribute("cantidadGruposInvestigacionCEducacion", resumenEducacion.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosEducacion", resumenEducacion.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoEducacion", resumenEducacion.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosEducacion", resumenEducacion.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorEducacion", resumenEducacion.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosEducacion", resumenEducacion.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorEducacion", resumenEducacion.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaEducacion", resumenEducacion.get(18));
		model.addAttribute("cantidadDocentesDoctoresEducacion", resumenEducacion.get(19));
		model.addAttribute("cantidadDocentesMagisterEducacion", resumenEducacion.get(20));
		model.addAttribute("cantidadDocentesEspecialistasEducacion", resumenEducacion.get(21));
		model.addAttribute("cantidadDocentesPregradoEducacion", resumenEducacion.get(22));

		// ------Adición de atributos al modelo con informacion de
		// salud-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasDeLaSalud", resumenCienciasDeLaSalud.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasDeLaSalud", resumenCienciasDeLaSalud.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasDeLaSalud", resumenCienciasDeLaSalud.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(3));
		model.addAttribute("cantidaCentrosInvestigacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasDeLaSalud", resumenCienciasDeLaSalud.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1CienciasDeLaSalud", resumenCienciasDeLaSalud.get(8));
		model.addAttribute("cantidadGruposInvestigacionACienciasDeLaSalud", resumenCienciasDeLaSalud.get(9));
		model.addAttribute("cantidadGruposInvestigacionBCienciasDeLaSalud", resumenCienciasDeLaSalud.get(10));
		model.addAttribute("cantidadGruposInvestigacionCCienciasDeLaSalud", resumenCienciasDeLaSalud.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosCienciasDeLaSalud", resumenCienciasDeLaSalud.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoCienciasDeLaSalud",
				resumenCienciasDeLaSalud.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosCienciasDeLaSalud", resumenCienciasDeLaSalud.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorCienciasDeLaSalud", resumenCienciasDeLaSalud.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosCienciasDeLaSalud", resumenCienciasDeLaSalud.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorCienciasDeLaSalud", resumenCienciasDeLaSalud.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaCienciasDeLaSalud", resumenCienciasDeLaSalud.get(18));
		model.addAttribute("cantidadDocentesDoctoresCienciasDeLaSalud", resumenCienciasDeLaSalud.get(19));
		model.addAttribute("cantidadDocentesMagisterCienciasDeLaSalud", resumenCienciasDeLaSalud.get(20));
		model.addAttribute("cantidadDocentesEspecialistasCienciasDeLaSalud", resumenCienciasDeLaSalud.get(21));
		model.addAttribute("cantidadDocentesPregradoCienciasDeLaSalud", resumenCienciasDeLaSalud.get(22));

		// ------Adición de atributos al modelo con informacion de
		// humanas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasHumanas", resumenCienciasHumanas.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasHumanas", resumenCienciasHumanas.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasHumanas", resumenCienciasHumanas.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasHumanas", resumenCienciasHumanas.get(3));
		model.addAttribute("cantidadCentrosInvestigacionCienciasHumanas", resumenCienciasHumanas.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasHumanas", resumenCienciasHumanas.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasHumanas", resumenCienciasHumanas.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasHumanas", resumenCienciasHumanas.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1CienciasHumanas", resumenCienciasHumanas.get(8));
		model.addAttribute("cantidadGruposInvestigacionACienciasHumanas", resumenCienciasHumanas.get(9));
		model.addAttribute("cantidadGruposInvestigacionBCienciasHumanas", resumenCienciasHumanas.get(10));
		model.addAttribute("cantidadGruposInvestigacionCCienciasHumanas", resumenCienciasHumanas.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosCienciasHumanas", resumenCienciasHumanas.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoCienciasHumanas", resumenCienciasHumanas.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosCienciasHumanas", resumenCienciasHumanas.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorCienciasHumanas", resumenCienciasHumanas.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosCienciasHumanas", resumenCienciasHumanas.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorCienciasHumanas", resumenCienciasHumanas.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaCienciasHumanas", resumenCienciasHumanas.get(18));
		model.addAttribute("cantidadDocentesDoctoresCienciasHumanas", resumenCienciasHumanas.get(19));
		model.addAttribute("cantidadDocentesMagisterCienciasHumanas", resumenCienciasHumanas.get(20));
		model.addAttribute("cantidadDocentesEspecialistasCienciasHumanas", resumenCienciasHumanas.get(21));
		model.addAttribute("cantidadDocentesPregradoCienciasHumanas", resumenCienciasHumanas.get(22));

		// ------Adición de atributos al modelo con informacion de
		// agroindustria-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosAgroindustria", resumenAgroindustria.get(0));
		model.addAttribute("cantidadProgramasDoctoradoAgroindustria", resumenAgroindustria.get(1));
		model.addAttribute("cantidadProgramasMaestriaAgroindustria", resumenAgroindustria.get(2));
		model.addAttribute("cantidadProgramasEspecializacionAgroindustria", resumenAgroindustria.get(3));
		model.addAttribute("cantidadCentrosInvestigacionAgroindustria", resumenAgroindustria.get(4));
		model.addAttribute("cantidadGruposInvestigacionAgroindustria", resumenAgroindustria.get(5));
		model.addAttribute("cantidadLineasInvestigacionAgroindustria", resumenAgroindustria.get(6));
		model.addAttribute("cantidadInvestigadoresAgroindustria", resumenAgroindustria.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1Agroindustria", resumenAgroindustria.get(8));
		model.addAttribute("cantidadGruposInvestigacionAAgroindustria", resumenAgroindustria.get(9));
		model.addAttribute("cantidadGruposInvestigacionBAgroindustria", resumenAgroindustria.get(10));
		model.addAttribute("cantidadGruposInvestigacionCAgroindustria", resumenAgroindustria.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosAgroindustria", resumenAgroindustria.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoAgroindustria", resumenAgroindustria.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosAgroindustria", resumenAgroindustria.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorAgroindustria", resumenAgroindustria.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosAgroindustria", resumenAgroindustria.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorAgroindustria", resumenAgroindustria.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaAgroindustria", resumenAgroindustria.get(18));
		model.addAttribute("cantidadDocentesDoctoresAgroindustria", resumenAgroindustria.get(19));
		model.addAttribute("cantidadDocentesMagisterAgroindustria", resumenAgroindustria.get(20));
		model.addAttribute("cantidadDocentesEspecialistasAgroindustria", resumenAgroindustria.get(21));
		model.addAttribute("cantidadDocentesPregradoAgroindustria", resumenAgroindustria.get(22));

		// ------Adición de atributos al modelo con informacion de
		// economicas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasEconomicas", resumenCienciasEconomicas.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasEconomicas", resumenCienciasEconomicas.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasEconomicas", resumenCienciasEconomicas.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasEconomicas", resumenCienciasEconomicas.get(3));
		model.addAttribute("cantidadCentrosInvestigacionCienciasEconomicas", resumenCienciasEconomicas.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasEconomicas", resumenCienciasEconomicas.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasEconomicas", resumenCienciasEconomicas.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasEconomicas", resumenCienciasEconomicas.get(7));
		model.addAttribute("cantidadGruposInvestigacionA1CienciasEconomicas", resumenCienciasEconomicas.get(8));
		model.addAttribute("cantidadGruposInvestigacionACienciasEconomicas", resumenCienciasEconomicas.get(9));
		model.addAttribute("cantidadGruposInvestigacionBCienciasEconomicas", resumenCienciasEconomicas.get(10));
		model.addAttribute("cantidadGruposInvestigacionCCienciasEconomicas", resumenCienciasEconomicas.get(11));
		model.addAttribute("cantidadGruposInvestigacionReconocidosCienciasEconomicas",
				resumenCienciasEconomicas.get(12));
		model.addAttribute("cantidadGruposInvestigacionNoReconocidoCienciasEconomicas",
				resumenCienciasEconomicas.get(13));
		model.addAttribute("cantidadInvestigadoresEmeritosCienciasEconomicas", resumenCienciasEconomicas.get(14));
		model.addAttribute("cantidadInvestigadoresSeniorCienciasEconomicas", resumenCienciasEconomicas.get(15));
		model.addAttribute("cantidadInvestigadoresAsociadosCienciasEconomicas", resumenCienciasEconomicas.get(16));
		model.addAttribute("cantidadInvestigadoresJuniorCienciasEconomicas", resumenCienciasEconomicas.get(17));
		model.addAttribute("cantidadInvestigadoresSinCategoriaCienciasEconomicas", resumenCienciasEconomicas.get(18));
		model.addAttribute("cantidadDocentesDoctoresCienciasEconomicas", resumenCienciasEconomicas.get(19));
		model.addAttribute("cantidadDocentesMagisterCienciasEconomicas", resumenCienciasEconomicas.get(20));
		model.addAttribute("cantidadDocentesEspecialistasCienciasEconomicas", resumenCienciasEconomicas.get(21));
		model.addAttribute("cantidadDocentesPregradoCienciasEconomicas", resumenCienciasEconomicas.get(22));

		// ------Adición de atributos al modelo con informacion de
		// totales-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosTotal", resumenIngenieria.get(0)
				.add(resumenCienciasBasicas.get(0)
						.add(resumenEducacion.get(0).add(resumenCienciasDeLaSalud.get(0).add(resumenCienciasHumanas
								.get(0).add(resumenAgroindustria.get(0).add(resumenCienciasEconomicas.get(0))))))));

		model.addAttribute("cantidadProgramasDoctoradoTotal", resumenIngenieria.get(1)
				.add(resumenCienciasBasicas.get(1)
						.add(resumenEducacion.get(1).add(resumenCienciasDeLaSalud.get(1).add(resumenCienciasHumanas
								.get(1).add(resumenAgroindustria.get(1).add(resumenCienciasEconomicas.get(1))))))));

		model.addAttribute("cantidadProgramasMaestriaTotal", resumenIngenieria.get(2)
				.add(resumenCienciasBasicas.get(2)
						.add(resumenEducacion.get(2).add(resumenCienciasDeLaSalud.get(2).add(resumenCienciasHumanas
								.get(2).add(resumenAgroindustria.get(2).add(resumenCienciasEconomicas.get(2))))))));

		model.addAttribute("cantidadProgramasEspecializacionTotal", resumenIngenieria.get(3)
				.add(resumenCienciasBasicas.get(3)
						.add(resumenEducacion.get(3).add(resumenCienciasDeLaSalud.get(3).add(resumenCienciasHumanas
								.get(3).add(resumenAgroindustria.get(3).add(resumenCienciasEconomicas.get(3))))))));

		model.addAttribute("cantidadCentrosInvestigacionTotal", resumenIngenieria.get(4)
				.add(resumenCienciasBasicas.get(4)
						.add(resumenEducacion.get(4).add(resumenCienciasDeLaSalud.get(4).add(resumenCienciasHumanas
								.get(4).add(resumenAgroindustria.get(4).add(resumenCienciasEconomicas.get(4))))))));

		model.addAttribute("cantidadGruposInvestigacionTotal", resumenIngenieria.get(5)
				.add(resumenCienciasBasicas.get(5)
						.add(resumenEducacion.get(5).add(resumenCienciasDeLaSalud.get(5).add(resumenCienciasHumanas
								.get(5).add(resumenAgroindustria.get(5).add(resumenCienciasEconomicas.get(5))))))));

		model.addAttribute("cantidadLineasInvestigacionTotal", resumenIngenieria.get(6)
				.add(resumenCienciasBasicas.get(6)
						.add(resumenEducacion.get(6).add(resumenCienciasDeLaSalud.get(6).add(resumenCienciasHumanas
								.get(6).add(resumenAgroindustria.get(6).add(resumenCienciasEconomicas.get(6))))))));

		model.addAttribute("cantidadInvestigadoresTotal", cantidadTotalInvestigadores);

		// ------Segunda
		// tabla----------------------------------------------------------------------------------------------------
		model.addAttribute("cantidadGruposA1Total", resumenIngenieria.get(8)
				.add(resumenCienciasBasicas.get(8)
						.add(resumenEducacion.get(8).add(resumenCienciasDeLaSalud.get(8).add(resumenCienciasHumanas
								.get(8).add(resumenAgroindustria.get(8).add(resumenCienciasEconomicas.get(8))))))));

		model.addAttribute("cantidadGruposATotal", resumenIngenieria.get(9)
				.add(resumenCienciasBasicas.get(9)
						.add(resumenEducacion.get(9).add(resumenCienciasDeLaSalud.get(9).add(resumenCienciasHumanas
								.get(9).add(resumenAgroindustria.get(9).add(resumenCienciasEconomicas.get(9))))))));

		model.addAttribute("cantidadGruposBTotal", resumenIngenieria.get(10)
				.add(resumenCienciasBasicas.get(10)
						.add(resumenEducacion.get(10).add(resumenCienciasDeLaSalud.get(10).add(resumenCienciasHumanas
								.get(10).add(resumenAgroindustria.get(10).add(resumenCienciasEconomicas.get(10))))))));

		model.addAttribute("cantidadGruposCTotal", resumenIngenieria.get(11)
				.add(resumenCienciasBasicas.get(11)
						.add(resumenEducacion.get(11).add(resumenCienciasDeLaSalud.get(11).add(resumenCienciasHumanas
								.get(11).add(resumenAgroindustria.get(11).add(resumenCienciasEconomicas.get(11))))))));

		model.addAttribute("cantidadGruposReconocidosTotal", resumenIngenieria.get(12)
				.add(resumenCienciasBasicas.get(12)
						.add(resumenEducacion.get(12).add(resumenCienciasDeLaSalud.get(12).add(resumenCienciasHumanas
								.get(12).add(resumenAgroindustria.get(12).add(resumenCienciasEconomicas.get(12))))))));

		model.addAttribute("cantidadGruposNoReconocidosTotal", resumenIngenieria.get(13)
				.add(resumenCienciasBasicas.get(13)
						.add(resumenEducacion.get(13).add(resumenCienciasDeLaSalud.get(13).add(resumenCienciasHumanas
								.get(13).add(resumenAgroindustria.get(13).add(resumenCienciasEconomicas.get(13))))))));

		// ------Totales tabla 2
		// --------------------------------------------------------------------------------------------------
		model.addAttribute("cantidadGruposIngenieriaTotal",
				resumenIngenieria.get(8).add(resumenIngenieria.get(9).add(resumenIngenieria.get(10).add(
						resumenIngenieria.get(11).add(resumenIngenieria.get(12).add(resumenIngenieria.get(13)))))));

		model.addAttribute("cantidadGruposCienciasBasicasTotal",
				resumenCienciasBasicas.get(8)
						.add(resumenCienciasBasicas.get(9).add(resumenCienciasBasicas.get(10).add(resumenCienciasBasicas
								.get(11).add(resumenCienciasBasicas.get(12).add(resumenCienciasBasicas.get(13)))))));

		model.addAttribute("cantidadGruposEducacionTotal",
				resumenEducacion.get(8).add(resumenEducacion.get(9).add(resumenEducacion.get(10)
						.add(resumenEducacion.get(11).add(resumenEducacion.get(12).add(resumenEducacion.get(13)))))));

		model.addAttribute("cantidadGruposCienciasDeLaSaludTotal",
				resumenCienciasDeLaSalud.get(8).add(resumenCienciasDeLaSalud.get(9)
						.add(resumenCienciasDeLaSalud.get(10).add(resumenCienciasDeLaSalud.get(11)
								.add(resumenCienciasDeLaSalud.get(12).add(resumenCienciasDeLaSalud.get(13)))))));

		model.addAttribute("cantidadGruposCienciasHumanasTotal",
				resumenCienciasHumanas.get(8)
						.add(resumenCienciasHumanas.get(9).add(resumenCienciasHumanas.get(10).add(resumenCienciasHumanas
								.get(11).add(resumenCienciasHumanas.get(12).add(resumenCienciasHumanas.get(13)))))));

		model.addAttribute("cantidadGruposAgroindustriaTotal",
				resumenAgroindustria.get(8)
						.add(resumenAgroindustria.get(9).add(resumenAgroindustria.get(10).add(resumenAgroindustria
								.get(11).add(resumenAgroindustria.get(12).add(resumenAgroindustria.get(13)))))));

		model.addAttribute("cantidadGruposCienciasEconomicasTotal",
				resumenCienciasEconomicas.get(8).add(resumenCienciasEconomicas.get(9)
						.add(resumenCienciasEconomicas.get(10).add(resumenCienciasEconomicas.get(11)
								.add(resumenCienciasEconomicas.get(12).add(resumenCienciasEconomicas.get(13)))))));

		// ------Tercera
		// tabla----------------------------------------------------------------------------------------------------
		model.addAttribute("cantidadInvestigadoresEmeritosTotal", resumenIngenieria.get(14)
				.add(resumenCienciasBasicas.get(14)
						.add(resumenEducacion.get(14).add(resumenCienciasDeLaSalud.get(14).add(resumenCienciasHumanas
								.get(14).add(resumenAgroindustria.get(14).add(resumenCienciasEconomicas.get(14))))))));

		model.addAttribute("cantidadInvestigadoresSeniorTotal", resumenIngenieria.get(15)
				.add(resumenCienciasBasicas.get(15)
						.add(resumenEducacion.get(15).add(resumenCienciasDeLaSalud.get(15).add(resumenCienciasHumanas
								.get(15).add(resumenAgroindustria.get(15).add(resumenCienciasEconomicas.get(15))))))));

		model.addAttribute("cantidadInvestigadoresAsociadosTotal", resumenIngenieria.get(16)
				.add(resumenCienciasBasicas.get(16)
						.add(resumenEducacion.get(16).add(resumenCienciasDeLaSalud.get(16).add(resumenCienciasHumanas
								.get(16).add(resumenAgroindustria.get(16).add(resumenCienciasEconomicas.get(16))))))));

		model.addAttribute("cantidadInvestigadoresJuniorTotal", resumenIngenieria.get(17)
				.add(resumenCienciasBasicas.get(17)
						.add(resumenEducacion.get(17).add(resumenCienciasDeLaSalud.get(17).add(resumenCienciasHumanas
								.get(17).add(resumenAgroindustria.get(17).add(resumenCienciasEconomicas.get(17))))))));

		model.addAttribute("cantidadInvestigadoresSinCategoriaTotal", resumenIngenieria.get(18)
				.add(resumenCienciasBasicas.get(18)
						.add(resumenEducacion.get(18).add(resumenCienciasDeLaSalud.get(18).add(resumenCienciasHumanas
								.get(18).add(resumenAgroindustria.get(18).add(resumenCienciasEconomicas.get(18))))))));

		// ------Totales tabla 3
		// --------------------------------------------------------------------------------------------------
		model.addAttribute("cantidadInvestigadoresIngenieriaTotal", resumenIngenieria.get(14).add(resumenIngenieria
				.get(15).add(resumenIngenieria.get(16).add(resumenIngenieria.get(17).add(resumenIngenieria.get(18))))));

		model.addAttribute("cantidadInvestigadoresCienciasBasicasTotal",
				resumenCienciasBasicas.get(14).add(resumenCienciasBasicas.get(15).add(resumenCienciasBasicas.get(16)
						.add(resumenCienciasBasicas.get(17).add(resumenCienciasBasicas.get(18))))));

		model.addAttribute("cantidadInvestigadoresEducacionTotal", resumenEducacion.get(14).add(resumenEducacion.get(15)
				.add(resumenEducacion.get(16).add(resumenEducacion.get(17).add(resumenEducacion.get(18))))));

		model.addAttribute("cantidadInvestigadoresCienciasDeLaSaludTotal",
				resumenCienciasDeLaSalud.get(14).add(resumenCienciasDeLaSalud.get(15).add(resumenCienciasDeLaSalud
						.get(16).add(resumenCienciasDeLaSalud.get(17).add(resumenCienciasDeLaSalud.get(18))))));

		model.addAttribute("cantidadInvestigadoresCienciasHumanasTotal",
				resumenCienciasHumanas.get(14).add(resumenCienciasHumanas.get(15).add(resumenCienciasHumanas.get(16)
						.add(resumenCienciasHumanas.get(17).add(resumenCienciasHumanas.get(18))))));

		model.addAttribute("cantidadInvestigadoresAgroindustriaTotal",
				resumenAgroindustria.get(14).add(resumenAgroindustria.get(15).add(resumenAgroindustria.get(16)
						.add(resumenAgroindustria.get(17).add(resumenAgroindustria.get(18))))));

		model.addAttribute("cantidadInvestigadoresCienciasEconomicasTotal",
				resumenCienciasEconomicas.get(14).add(resumenCienciasEconomicas.get(15).add(resumenCienciasEconomicas
						.get(16).add(resumenCienciasEconomicas.get(17).add(resumenCienciasEconomicas.get(18))))));

		// ------Cuarta
		// tabla----------------------------------------------------------------------------------------------------
		model.addAttribute("cantidadDocentesDoctoresTotal", resumenIngenieria.get(19)
				.add(resumenCienciasBasicas.get(19)
						.add(resumenEducacion.get(19).add(resumenCienciasDeLaSalud.get(19).add(resumenCienciasHumanas
								.get(19).add(resumenAgroindustria.get(19).add(resumenCienciasEconomicas.get(19))))))));

		model.addAttribute("cantidadDocentesMagisterTotal", resumenIngenieria.get(20)
				.add(resumenCienciasBasicas.get(20)
						.add(resumenEducacion.get(20).add(resumenCienciasDeLaSalud.get(20).add(resumenCienciasHumanas
								.get(20).add(resumenAgroindustria.get(20).add(resumenCienciasEconomicas.get(20))))))));

		model.addAttribute("cantidadDocentesEspecialistasTotal", resumenIngenieria.get(21)
				.add(resumenCienciasBasicas.get(21)
						.add(resumenEducacion.get(21).add(resumenCienciasDeLaSalud.get(21).add(resumenCienciasHumanas
								.get(21).add(resumenAgroindustria.get(21).add(resumenCienciasEconomicas.get(21))))))));

		model.addAttribute("cantidadDocentesPregradoTotal", resumenIngenieria.get(22)
				.add(resumenCienciasBasicas.get(22)
						.add(resumenEducacion.get(22).add(resumenCienciasDeLaSalud.get(22).add(resumenCienciasHumanas
								.get(22).add(resumenAgroindustria.get(22).add(resumenCienciasEconomicas.get(22))))))));

		// ------Totales tabla 4
		// --------------------------------------------------------------------------------------------------
		model.addAttribute("cantidadDocentesIngenieriaTotal", resumenIngenieria.get(19)
				.add(resumenIngenieria.get(20).add(resumenIngenieria.get(21).add(resumenIngenieria.get(22)))));

		model.addAttribute("cantidadDocentesCienciasBasicasTotal",
				resumenCienciasBasicas.get(19).add(resumenCienciasBasicas.get(20)
						.add(resumenCienciasBasicas.get(21).add(resumenCienciasBasicas.get(22)))));

		model.addAttribute("cantidadDocentesEducacionTotal", resumenEducacion.get(19)
				.add(resumenEducacion.get(20).add(resumenEducacion.get(21).add(resumenEducacion.get(22)))));

		model.addAttribute("cantidadDocentesCienciasDeLaSaludTotal",
				resumenCienciasDeLaSalud.get(19).add(resumenCienciasDeLaSalud.get(20)
						.add(resumenCienciasDeLaSalud.get(21).add(resumenCienciasDeLaSalud.get(22)))));

		model.addAttribute("cantidadDocentesCienciasHumanasTotal",
				resumenCienciasHumanas.get(19).add(resumenCienciasHumanas.get(20)
						.add(resumenCienciasHumanas.get(21).add(resumenCienciasHumanas.get(22)))));

		model.addAttribute("cantidadDocentesAgroindustriaTotal", resumenAgroindustria.get(19)
				.add(resumenAgroindustria.get(20).add(resumenAgroindustria.get(21).add(resumenAgroindustria.get(22)))));

		model.addAttribute("cantidadDocentesCienciasEconomicasTotal",
				resumenCienciasEconomicas.get(19).add(resumenCienciasEconomicas.get(20)
						.add(resumenCienciasEconomicas.get(21).add(resumenCienciasEconomicas.get(22)))));

		model.addAttribute("cantidadDocentesTotal", resumenIngenieria.get(19).add(resumenIngenieria.get(20)
				.add(resumenIngenieria.get(21).add(resumenIngenieria.get(22).add(resumenCienciasBasicas.get(19).add(
						resumenCienciasBasicas.get(20).add(resumenCienciasBasicas.get(21).add(resumenCienciasBasicas
								.get(22)
								.add(resumenEducacion.get(19).add(resumenEducacion.get(20).add(resumenEducacion.get(21)
										.add(resumenEducacion.get(22).add(resumenCienciasDeLaSalud.get(19)
												.add(resumenCienciasDeLaSalud.get(20).add(resumenCienciasDeLaSalud
														.get(21)
														.add(resumenCienciasDeLaSalud.get(22).add(resumenCienciasHumanas
																.get(19)
																.add(resumenCienciasHumanas.get(20)
																		.add(resumenCienciasHumanas.get(21)
																				.add(resumenCienciasHumanas.get(22)
																						.add(resumenAgroindustria
																								.get(19)
																								.add(resumenAgroindustria
																										.get(20)
																										.add(resumenAgroindustria
																												.get(21)
																												.add(resumenAgroindustria
																														.get(22)
																														.add(resumenCienciasEconomicas
																																.get(19)
																																.add(resumenCienciasEconomicas
																																		.get(20)
																																		.add(resumenCienciasEconomicas
																																				.get(21)
																																				.add(resumenCienciasEconomicas
																																						.get(22)))))))))))))))))))))))))))));
		/////////////////// Tabla Pertenencia////////////////////////////////////

		List<Investigador> investigadores_Basicas = investigadorDAO.getAllInvestigadoresInternosFacultad(1l);
		List<Investigador> investigadores_pertenencia_Basicas = utilidades
				.agregarPertenenciaInves(investigadores_Basicas);

		List<Investigador> investigadores_Educacion = investigadorDAO.getAllInvestigadoresInternosFacultad(2l);
		List<Investigador> investigadores_pertenencia_Educacion = utilidades
				.agregarPertenenciaInves(investigadores_Educacion);

		List<Investigador> investigadores_Salud = investigadorDAO.getAllInvestigadoresInternosFacultad(3l);
		List<Investigador> investigadores_pertenencia_Salud = utilidades.agregarPertenenciaInves(investigadores_Salud);

		List<Investigador> investigadores_Ingenieria = investigadorDAO.getAllInvestigadoresInternosFacultad(4l);
		List<Investigador> investigadores_pertenencia_Ingenieria = utilidades
				.agregarPertenenciaInves(investigadores_Ingenieria);

		List<Investigador> investigadores_Humanas = investigadorDAO.getAllInvestigadoresInternosFacultad(5l);
		List<Investigador> investigadores_pertenencia_Humanas = utilidades
				.agregarPertenenciaInves(investigadores_Humanas);

		List<Investigador> investigadores_Agro = investigadorDAO.getAllInvestigadoresInternosFacultad(6l);
		List<Investigador> investigadores_pertenencia_Agro = utilidades.agregarPertenenciaInves(investigadores_Agro);

		List<Investigador> investigadores_Economicas = investigadorDAO.getAllInvestigadoresInternosFacultad(7l);
		List<Investigador> investigadores_pertenencia_Economicas = utilidades
				.agregarPertenenciaInves(investigadores_Economicas);

		// ---------------BASICAS-----------------------------

		List<Investigador> lista_investigadores_Basicas_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_basicas_Admin", lista_investigadores_Basicas_Adm.size());

		List<Investigador> lista_investigadores_Basicas_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_basicas_dp", lista_investigadores_Basicas_DP.size());

		List<Investigador> lista_investigadores_Basicas_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_basicas_dc", lista_investigadores_Basicas_DC.size());

		List<Investigador> lista_investigadores_Basicas_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_basicas_do", lista_investigadores_Basicas_DO.size());

		List<Investigador> lista_investigadores_Basicas_IE = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_basicas_ie", lista_investigadores_Basicas_IE.size());

		List<Investigador> lista_investigadores_Basicas_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_basicas_ei", lista_investigadores_Basicas_EI.size());

		List<Investigador> lista_investigadores_Basicas_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Basicas, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_basicas_ind", lista_investigadores_Basicas_IND.size());

		// --------------------EDUCACION----------------------

		List<Investigador> lista_investigadores_Educacion_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_educacion_Admin", lista_investigadores_Educacion_Adm.size());

		List<Investigador> lista_investigadores_Educacion_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_educacion_dp", lista_investigadores_Educacion_DP.size());

		List<Investigador> lista_investigadores_Educacion_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_educacion_dc", lista_investigadores_Educacion_DC.size());

		List<Investigador> lista_investigadores_Educacion_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_educacion_do", lista_investigadores_Educacion_DO.size());
		List<Investigador> lista_investigadores_Educacion_IE = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_educacion_ie", lista_investigadores_Educacion_IE.size());

		List<Investigador> lista_investigadores_Educacion_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_educacion_ei", lista_investigadores_Educacion_EI.size());

		List<Investigador> lista_investigadores_Educacion_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Educacion, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_educacion_ind", lista_investigadores_Educacion_IND.size());

		// -----------------------SALUD----------------------------

		List<Investigador> lista_investigadores_Salud_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Salud, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_salud_Admin", lista_investigadores_Salud_Adm.size());

		List<Investigador> lista_investigadores_Salud_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Salud, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_salud_dp", lista_investigadores_Salud_DP.size());

		List<Investigador> lista_investigadores_Salud_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Salud, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_salud_dc", lista_investigadores_Salud_DC.size());

		List<Investigador> lista_investigadores_Salud_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Salud, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_salud_do", lista_investigadores_Salud_DO.size());
		List<Investigador> lista_investigadores_Salud_IE = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_pertenencia_Salud, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_salud_ie", lista_investigadores_Salud_IE.size());

		List<Investigador> lista_investigadores_Salud_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Salud, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_salud_ei", lista_investigadores_Salud_EI.size());

		List<Investigador> lista_investigadores_Salud_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Salud, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_salud_ind", lista_investigadores_Salud_IND.size());

		// -----------------INGENIERIA-------------------------

		List<Investigador> lista_investigadores_Ingenieria_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_ingenieria_Admin", lista_investigadores_Ingenieria_Adm.size());

		List<Investigador> lista_investigadores_Ingenieria_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_ingenieria_dp", lista_investigadores_Ingenieria_DP.size());

		List<Investigador> lista_investigadores_Ingenieria_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_ingenieria_dc", lista_investigadores_Ingenieria_DC.size());

		List<Investigador> lista_investigadores_Ingenieria_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_ingenieria_do", lista_investigadores_Ingenieria_DO.size());
		List<Investigador> lista_investigadores_Ingenieria_IE = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_ingenieria_ie", lista_investigadores_Ingenieria_IE.size());

		List<Investigador> lista_investigadores_Ingenieria_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_ingenieria_ei", lista_investigadores_Ingenieria_EI.size());

		List<Investigador> lista_investigadores_Ingenieria_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Ingenieria, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_ingenieria_ind", lista_investigadores_Ingenieria_IND.size());

		// -----------------------------HUMANAS---------------------------

		List<Investigador> lista_investigadores_Humanas_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_humanas_Admin", lista_investigadores_Humanas_Adm.size());

		List<Investigador> lista_investigadores_Humanas_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_humanas_dp", lista_investigadores_Humanas_DP.size());

		List<Investigador> lista_investigadores_Humanas_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_humanas_dc", lista_investigadores_Humanas_DC.size());

		List<Investigador> lista_investigadores_Humanas_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_humanas_do", lista_investigadores_Humanas_DO.size());
		List<Investigador> lista_investigadores_Humanas_IE = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_humanas_ie", lista_investigadores_Humanas_IE.size());

		List<Investigador> lista_investigadores_Humanas_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_humanas_ei", lista_investigadores_Humanas_EI.size());

		List<Investigador> lista_investigadores_Humanas_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Humanas, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_humanas_ind", lista_investigadores_Humanas_IND.size());

		// --------------------AGRO-------------------------------

		List<Investigador> lista_investigadores_Agro_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Agro, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_agro_Admin", lista_investigadores_Agro_Adm.size());

		List<Investigador> lista_investigadores_Agro_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Agro, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_agro_dp", lista_investigadores_Agro_DP.size());

		List<Investigador> lista_investigadores_Agro_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Agro, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_agro_dc", lista_investigadores_Agro_DC.size());

		List<Investigador> lista_investigadores_Agro_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Agro, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_agro_do", lista_investigadores_Agro_DO.size());
		List<Investigador> lista_investigadores_Agro_IE = utilidades
				.seleccionarInvestigadoresPertenencia(investigadores_pertenencia_Agro, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_agro_ie", lista_investigadores_Agro_IE.size());

		List<Investigador> lista_investigadores_Agro_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Agro, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_agro_ei", lista_investigadores_Agro_EI.size());

		List<Investigador> lista_investigadores_Agro_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Agro, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_agro_ind", lista_investigadores_Agro_IND.size());

		// -------------------Economicas-------------------------------
		List<Investigador> lista_investigadores_Economicas_Adm = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_ADMINISTRATIVO);
		model.addAttribute("Num_inves_economicas_Admin", lista_investigadores_Economicas_Adm.size());

		List<Investigador> lista_investigadores_Economicas_DP = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_DOCENTE_PLANTA);
		model.addAttribute("Num_inves_economicas_dp", lista_investigadores_Economicas_DP.size());

		List<Investigador> lista_investigadores_Economicas_DC = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_DOCENTE_CATEDRATICO);
		model.addAttribute("Num_inves_economicas_dc", lista_investigadores_Economicas_DC.size());

		List<Investigador> lista_investigadores_Economicas_DO = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_DOCENTE_OCASIONAL);
		model.addAttribute("Num_inves_economicas_do", lista_investigadores_Economicas_DO.size());
		List<Investigador> lista_investigadores_Economicas_IE = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_EXTERNO);
		model.addAttribute("Num_inves_economicas_ie", lista_investigadores_Economicas_IE.size());

		List<Investigador> lista_investigadores_Economicas_EI = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_ESTUDIANTE);
		model.addAttribute("Num_inves_economicas_ei", lista_investigadores_Economicas_EI.size());

		List<Investigador> lista_investigadores_Economicas_IND = utilidades.seleccionarInvestigadoresPertenencia(
				investigadores_pertenencia_Economicas, utilidades.PERTENENCIA_INDEFINIDO);
		model.addAttribute("Num_inves_economicas_ind", lista_investigadores_Economicas_IND.size());

		// -------------------------------TOTAL-------------------------------------------

		int total_investigadores_Adm = lista_investigadores_Basicas_Adm.size()
				+ lista_investigadores_Educacion_Adm.size() + lista_investigadores_Salud_Adm.size()
				+ lista_investigadores_Ingenieria_Adm.size() + lista_investigadores_Humanas_Adm.size()
				+ lista_investigadores_Agro_Adm.size() + lista_investigadores_Economicas_Adm.size();
		int total_investigadores_DP = lista_investigadores_Basicas_DP.size() + lista_investigadores_Educacion_DP.size()
				+ lista_investigadores_Salud_DP.size() + lista_investigadores_Ingenieria_DP.size()
				+ lista_investigadores_Humanas_DP.size() + lista_investigadores_Agro_DP.size()
				+ lista_investigadores_Economicas_DP.size();
		int total_investigadores_DC = lista_investigadores_Basicas_DC.size() + lista_investigadores_Educacion_DC.size()
				+ lista_investigadores_Salud_DC.size() + lista_investigadores_Ingenieria_DC.size()
				+ lista_investigadores_Humanas_DC.size() + lista_investigadores_Agro_DC.size()
				+ lista_investigadores_Economicas_DC.size();
		int total_investigadores_DO = lista_investigadores_Basicas_DO.size() + lista_investigadores_Educacion_DO.size()
				+ lista_investigadores_Salud_DO.size() + lista_investigadores_Ingenieria_DO.size()
				+ lista_investigadores_Humanas_DO.size() + lista_investigadores_Agro_DO.size()
				+ lista_investigadores_Economicas_DO.size();
		int total_investigadores_IE = lista_investigadores_Basicas_IE.size() + lista_investigadores_Educacion_IE.size()
				+ lista_investigadores_Salud_IE.size() + lista_investigadores_Ingenieria_IE.size()
				+ lista_investigadores_Humanas_IE.size() + lista_investigadores_Agro_IE.size()
				+ lista_investigadores_Economicas_IE.size();
		int total_investigadores_EI = lista_investigadores_Basicas_EI.size() + lista_investigadores_Educacion_EI.size()
				+ lista_investigadores_Salud_EI.size() + lista_investigadores_Ingenieria_EI.size()
				+ lista_investigadores_Humanas_EI.size() + lista_investigadores_Agro_EI.size()
				+ lista_investigadores_Economicas_EI.size();

		int total_investigadores_IND = lista_investigadores_Basicas_IND.size()
				+ lista_investigadores_Educacion_IND.size() + lista_investigadores_Salud_IND.size()
				+ lista_investigadores_Ingenieria_IND.size() + lista_investigadores_Humanas_IND.size()
				+ lista_investigadores_Agro_IND.size() + lista_investigadores_Economicas_IND.size();

		model.addAttribute("total_adm", total_investigadores_Adm);
		model.addAttribute("total_DP", total_investigadores_DP);
		model.addAttribute("total_DC", total_investigadores_DC);
		model.addAttribute("total_DO", total_investigadores_DO);
		model.addAttribute("total_IE", total_investigadores_IE);
		model.addAttribute("total_EI", total_investigadores_EI);
		model.addAttribute("total_IND", total_investigadores_IND);

		model.addAttribute("peAdm", "adm");
		model.addAttribute("peDp", "dp");
		model.addAttribute("peDc", "dc");
		model.addAttribute("peDo", "do");
		model.addAttribute("peIe", "ie");
		model.addAttribute("peEi", "ei");
		model.addAttribute("peInd", "ind");

		// ------Adición de atributos al modelo para referenciar a páginas
		// especificas-----------------------------------------------------------------------

		model.addAttribute("programaAcademico", "pa");
		model.addAttribute("programaDoctorado", "pd");
		model.addAttribute("programaMagister", "pm");
		model.addAttribute("programaEspecializacion", "pe");
		model.addAttribute("centrosInvestigacion", "c");
		model.addAttribute("gruposInvestigacion", "g");
		model.addAttribute("lineasInvestigacion", "l");
		model.addAttribute("investigadores", "i");

		model.addAttribute("categoriaA1", "ca1");
		model.addAttribute("categoriaA", "ca");
		model.addAttribute("categoriaB", "cb");
		model.addAttribute("categoriaC", "cc");
		model.addAttribute("categoriaReconocido", "cr");
		model.addAttribute("categoriaNoReconocido", "cnr");

		model.addAttribute("investigadorEmerito", "ie");
		model.addAttribute("investigadorSenior", "is");
		model.addAttribute("investigadorAsociado", "ia");
		model.addAttribute("investigadorJunior", "ij");
		model.addAttribute("investigadorSinCategoria", "isc");

		model.addAttribute("formacionDoctor", "fd");
		model.addAttribute("formacionMagister", "fm");
		model.addAttribute("formacionEspecialista", "fe");
		model.addAttribute("formacionPregrado", "fp");
		model.addAttribute("docentes", "d");

		model.addAttribute("idUniquindio", "0");
		model.addAttribute("idFacultadCienciasBasicas", "1");
		model.addAttribute("idFacultadEducacion", "2");
		model.addAttribute("idFacultadCienciasDeLaSalud", "3");
		model.addAttribute("idFacultadIngenieria", "4");
		model.addAttribute("idFacultadCienciasHumanas", "5");
		model.addAttribute("idFacultadAgroindustria", "6");
		model.addAttribute("idFacultadCienciasEconomicas", "7");

		return "estadisticas/uniquindio";

	}

}