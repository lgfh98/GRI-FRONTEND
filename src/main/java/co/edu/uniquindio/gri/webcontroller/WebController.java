package co.edu.uniquindio.gri.webcontroller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uniquindio.gri.dao.CentroDAO;
import co.edu.uniquindio.gri.dao.FacultadDAO;
import co.edu.uniquindio.gri.dao.GrupoDAO;
import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.dao.ProgramaDAO;
import co.edu.uniquindio.gri.model.Centro;
import co.edu.uniquindio.gri.model.Facultad;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.Programa;

@Controller
public class WebController {

	@Autowired
	GrupoDAO grupoDAO;

	@Autowired
	InvestigadorDAO investigadorDAO;

	@Autowired
	CentroDAO centroDAO;

	@Autowired
	ProgramaDAO programaDAO;

	@Autowired
	FacultadDAO facultadDAO;

	@Autowired
	ProduccionDAO produccionDAO;

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
		return "login";
	}

	@GetMapping("/investigadores")
	public String getInvestigadores(Model model) {
		model.addAttribute("listaInvestigadores", investigadorDAO.findAll());
		return "investigadores";
	}

	@GetMapping("/programas")
	public String getProgramas(@RequestParam(name = "type", required = false, defaultValue = "pa") String type,
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
	public String getGrupos(@RequestParam(name = "type", required = false, defaultValue = "pa") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		if (Long.parseLong(id) != 0) {
			model.addAttribute("listaGrupos", grupoDAO.getAllGruposFacultad(Long.parseLong(id)));
		} else {
			model.addAttribute("listaGrupos", grupoDAO.findAll());
		}
		return "grupos";
	}

	@GetMapping("/general")
	public String getTipologias(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);

		if (type.equals("u")) {
			List<Facultad> facultades = facultadDAO.getAllFacultades();

			model.addAttribute("nombre", "TIPOLOGÍA DE PRODUCTOS PARA LA UNIVERSIDAD DEL QUINDÍO");
			model.addAttribute("lista", facultades);
			model.addAttribute("subtipo", "f");
			model.addAttribute("color", "card-0");
			model.addAttribute("tamanio", "ci-" + calcularTamanio(facultades.size()));

		} else if (type.equals("f")) {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));
			List<Programa> programas = programaDAO.getProgramasFacultad(Long.parseLong(id));

			model.addAttribute("nombre", f.getNombre());
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

			model.addAttribute("nombre", i.getNombre());
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
		} else {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));
			List<Grupo> listaGrupos = grupoDAO.getGruposPertenecientes(Long.parseLong(id), "f");
			model.addAttribute("nombre", f.getNombre());
			model.addAttribute("lista", listaGrupos);
			model.addAttribute("color", "card-" + f.getId());
			model.addAttribute("tamanio", "ci-" + calcularTamanio(listaGrupos.size()));
		}
		return "inventario/inventario";
	}

	@GetMapping("/reporteinventario")
	public String getReporteInventario(@RequestParam(name = "id", required = true) String id, Model model) {

		Grupo g = grupoDAO.findOne(Long.parseLong(id));

		model.addAttribute("nombre", g.getNombre());
		model.addAttribute("color", "card-" + g.getProgramas().get(0).getFacultad().getId());
		model.addAttribute("producciones", produccionDAO.getAllProducciones(Long.parseLong(id)));

		return "inventario/reporteinventario";
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

		if (type.equals("f")) {

			return getEstadisticasFacultad(id, model);

		} else if (type.equals("p")) {

			return getEstadisticasProgramas(id, model);

		} else if (type.equals("c")) {

			return getEstadisticasCentros(id, model);

		} else if (type.equals("g")) {

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

		String[] datos = new String[4];

		if (type.equals("f")) {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));

			datos[0] = "Estadísticas generales de la facultad de " + f.getNombre().toLowerCase();
			datos[1] = "card-" + f.getId();
			datos[2] = "btn-title-grid-" + f.getId();
			datos[3] = "btn-total-grid-" + f.getId();

		} else if (type.equals("p")) {
			Programa p = programaDAO.getProgramaById(Long.parseLong(id));

			datos[0] = p.getNombre();
			datos[1] = "card-" + p.getFacultad().getId();
			datos[2] = "btn-title-grid-" + p.getFacultad().getId();
			datos[3] = "btn-total-grid-" + p.getFacultad().getId();

		} else if (type.equals("c")) {
			Centro c = centroDAO.getCentroById(Long.parseLong(id));

			datos[0] = c.getNombre();
			datos[1] = "card-" + c.getFacultad().getId();
			datos[2] = "btn-title-grid-" + c.getFacultad().getId();
			datos[3] = "btn-total-grid-" + c.getFacultad().getId();

		} else if (type.equals("g")) {
			Grupo g = grupoDAO.findOne(Long.parseLong(id));

			datos[0] = g.getNombre();
			datos[1] = "card-" + g.getProgramas().get(0).getFacultad().getId();
			datos[2] = "btn-title-grid-" + g.getProgramas().get(0).getFacultad().getId();
			datos[3] = "btn-total-grid-" + g.getProgramas().get(0).getFacultad().getId();

			System.err.println("btn-title-grid-" + g.getProgramas().get(0).getFacultad().getId());

		} else if (type.equals("i")) {
			Investigador i = investigadorDAO.findOne(Long.parseLong(id));

			datos[0] = i.getNombre();
			datos[1] = "card-0";
			datos[2] = "btn-title-grid-0";
			datos[3] = "btn-total-grid-0";
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

		model.addAttribute("cantidadGruposTotal", resumen.get(8).add(
				resumen.get(9).add(resumen.get(10).add(resumen.get(11).add(resumen.get(12).add(resumen.get(13)))))));

		model.addAttribute("cantidadInvestigadoresTotal",
				resumen.get(14).add(resumen.get(15).add(resumen.get(16).add(resumen.get(17).add(resumen.get(18))))));

		model.addAttribute("cantidadDocentesTotal",
				resumen.get(19).add(resumen.get(20).add(resumen.get(21).add(resumen.get(22)))));

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

		return "estadisticas/centros";
	}

	public String getEstadisticasGrupo(String id, Model model) {
		return "estadisticas/grupos";
	}

	public String getEstadisticasInvestigador(String id, Model model) {
		return "estadisticas/investigadores";
	}

	public String getEstadisticasUniquindio(Model model) {

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

		model.addAttribute("cantidadInvestigadoresTotal", resumenIngenieria.get(7)
				.add(resumenCienciasBasicas.get(7)
						.add(resumenEducacion.get(7).add(resumenCienciasDeLaSalud.get(7).add(resumenCienciasHumanas
								.get(7).add(resumenAgroindustria.get(7).add(resumenCienciasEconomicas.get(7))))))));

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