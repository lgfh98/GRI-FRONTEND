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

	@GetMapping("/uniquindio")
	public String getEstadisticasUniquindio(Model model) {
		
//------Llamado a las consultas en la base de datos para producciones-----------------------------------------------------------------------
		model.addAttribute("cantidadActividadesDeFormacion", produccionDAO.getCantidadActividadesFormacion());
		model.addAttribute("cantidadActividadesEvaluador", produccionDAO.getCantidadActividadesEvaluador());
		model.addAttribute("cantidadApropiacionSocial", produccionDAO.getCantidadApropiacionSocial());
		model.addAttribute("cantidadProduccionesBibliograficas", produccionDAO.getCantidadProduccionesBibliograficas());
		model.addAttribute("cantidadTecnicasTecnologicas", produccionDAO.getCantidadTecnicasTecnologicas());
		model.addAttribute("cantidadProduccionesArte", String.valueOf(produccionDAO.getCantidadProduccionesArte()));

		model.addAttribute("cantidadProduccionesDemasTrabajos", produccionDAO.getCantidadProduccionesDemasTrabajos());
		model.addAttribute("cantidadProduccionesProyectos", produccionDAO.getCantidadProduccionesProyectos());

		//------Llamado a las consultas en la base de datos para las facultades-----------------------------------------------------------------------		
		List<BigInteger> resumenCienciasBasicas = facultadDAO.getResumenGeneral(new Long("1"));
		List<BigInteger> resumenEducacion = facultadDAO.getResumenGeneral(new Long("2"));
		List<BigInteger> resumenCienciasDeLaSalud = facultadDAO.getResumenGeneral(new Long("3"));
		List<BigInteger> resumenIngenieria = facultadDAO.getResumenGeneral(new Long("4"));
		List<BigInteger> resumenCienciasHumanas = facultadDAO.getResumenGeneral(new Long("5"));
		List<BigInteger> resumenAgroindustria = facultadDAO.getResumenGeneral(new Long("6"));
		List<BigInteger> resumenCienciasEconomicas = facultadDAO.getResumenGeneral(new Long("7"));
		
//------Adición de atributos al modelo con informacion de ingenieria-----------------------------------------------------------------------		

		model.addAttribute("cantidadProgramasAcademicosIngenieria", resumenIngenieria.get(0));
		model.addAttribute("cantidadProgramasDoctoradoIngenieria", resumenIngenieria.get(1));
		model.addAttribute("cantidadProgramasMaestriaIngenieria", resumenIngenieria.get(2));
		model.addAttribute("cantidadProgramasEspecializacionIngenieria", resumenIngenieria.get(3));
		model.addAttribute("cantidadCentrosInvestigacionIngenieria", resumenCienciasBasicas.get(4));
		model.addAttribute("cantidadGruposInvestigacionIngenieria", resumenIngenieria.get(5));
		model.addAttribute("cantidadLineasInvestigacionIngenieria", resumenIngenieria.get(6));
		model.addAttribute("cantidadInvestigadoresIngenieria", resumenIngenieria.get(7));
		
//------Adición de atributos al modelo con informacion de basicas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasBasicas", resumenCienciasBasicas.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasBasicas", resumenCienciasBasicas.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasBasicas", resumenCienciasBasicas.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasBasicas", resumenCienciasBasicas.get(3));
		model.addAttribute("cantidadCentrosInvestigacionCienciasBasicas", resumenCienciasBasicas.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasBasicas", resumenCienciasBasicas.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasBasicas", resumenCienciasBasicas.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasBasicas", resumenCienciasBasicas.get(7));
		
//------Adición de atributos al modelo con informacion de educacion-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosEducacion", resumenEducacion.get(0));
		model.addAttribute("cantidadProgramasDoctoradoEducacion", resumenEducacion.get(1));
		model.addAttribute("cantidadProgramasMaestriaEducacion", resumenEducacion.get(2));
		model.addAttribute("cantidadProgramasEspecializacionEducacion", resumenEducacion.get(3));
		model.addAttribute("cantidadCentrosInvestigacionEducacion", resumenEducacion.get(4));
		model.addAttribute("cantidadGruposInvestigacionEducacion", resumenEducacion.get(5));
		model.addAttribute("cantidadLineasInvestigacionEducacion", resumenEducacion.get(6));
		model.addAttribute("cantidadInvestigadoresEducacion", resumenEducacion.get(7));
		
//------Adición de atributos al modelo con informacion de salud-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasDeLaSalud", resumenCienciasDeLaSalud.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasDeLaSalud", resumenCienciasDeLaSalud.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasDeLaSalud", resumenCienciasDeLaSalud.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(3));
		model.addAttribute("cantidaCentrosInvestigacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasDeLaSalud", resumenCienciasDeLaSalud.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasDeLaSalud", resumenCienciasDeLaSalud.get(7));
		
//------Adición de atributos al modelo con informacion de humanas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasHumanas", resumenCienciasHumanas.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasHumanas", resumenCienciasHumanas.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasHumanas", resumenCienciasHumanas.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasHumanas", resumenCienciasHumanas.get(3));
		model.addAttribute("cantidadCentrosInvestigacionCienciasHumanas", resumenCienciasHumanas.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasHumanas", resumenCienciasHumanas.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasHumanas", resumenCienciasHumanas.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasHumanas", resumenCienciasHumanas.get(7));

//------Adición de atributos al modelo con informacion de agroindustria-----------------------------------------------------------------------		
		model.addAttribute("cantidadProgramasAcademicosAgroindustria", resumenAgroindustria.get(0));
		model.addAttribute("cantidadProgramasDoctoradoAgroindustria", resumenAgroindustria.get(1));
		model.addAttribute("cantidadProgramasMaestriaAgroindustria", resumenAgroindustria.get(2));
		model.addAttribute("cantidadProgramasEspecializacionAgroindustria", resumenAgroindustria.get(3));
		model.addAttribute("cantidadCentrosInvestigacionAgroindustria", resumenAgroindustria.get(4));
		model.addAttribute("cantidadGruposInvestigacionAgroindustria", resumenAgroindustria.get(5));
		model.addAttribute("cantidadLineasInvestigacionAgroindustria", resumenAgroindustria.get(6));
		model.addAttribute("cantidadInvestigadoresAgroindustria", resumenAgroindustria.get(7));

//------Adición de atributos al modelo con informacion de economicas-----------------------------------------------------------------------
		model.addAttribute("cantidadProgramasAcademicosCienciasEconomicas", resumenCienciasEconomicas.get(0));
		model.addAttribute("cantidadProgramasDoctoradoCienciasEconomicas", resumenCienciasEconomicas.get(1));
		model.addAttribute("cantidadProgramasMaestriaCienciasEconomicas", resumenCienciasEconomicas.get(2));
		model.addAttribute("cantidadProgramasEspecializacionCienciasEconomicas", resumenCienciasEconomicas.get(3));
		model.addAttribute("cantidadCentrosInvestigacionCienciasEconomicas", resumenCienciasEconomicas.get(4));
		model.addAttribute("cantidadGruposInvestigacionCienciasEconomicas", resumenCienciasEconomicas.get(5));
		model.addAttribute("cantidadLineasInvestigacionCienciasEconomicas", resumenCienciasEconomicas.get(6));
		model.addAttribute("cantidadInvestigadoresCienciasEconomicas", resumenCienciasEconomicas.get(7));

//------Adición de atributos al modelo con informacion de totales-----------------------------------------------------------------------		
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

//------Adición de atributos al modelo para referenciar a páginas especificas-----------------------------------------------------------------------		
		model.addAttribute("programaAcademico", "pa");
		model.addAttribute("programaDoctorado", "pd");
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
		
		model.addAttribute("idFacultadCienciasBasicas", "1");
		model.addAttribute("idFacultadEducacion", "2");
		model.addAttribute("idFacultadCienciasDeLaSalud", "3");
		model.addAttribute("idFacultadIngenieria", "4");
		model.addAttribute("idFacultadCienciasHumanas", "5");
		model.addAttribute("idFacultadAgroindustria", "6");
		model.addAttribute("idFacultadCienciasEconomicas", "7");
		
		

		return "estadisticas/uniquindio";
	}

	@GetMapping("/programas")
	public String getProgramas(@RequestParam(name = "type", required = false, defaultValue = "pa") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("id", id);
		if (Long.parseLong(id) != 0) {
			if (type.equals("pa")) {
				model.addAttribute("listaProgramas", programaDAO.getProgramasAcademicosFacultad(Long.parseLong(id)));
			}
		} else {
			model.addAttribute("listaProgramas", programaDAO.getAllProgramas());
		}
		return "programas";
	}

	@GetMapping("/facultades")
	public String getFacultades(Model model) {
		model.addAttribute("listaFacultades", facultadDAO.getAllFacultades());
		return "facultades";
	}

	@GetMapping("/centros")
	public String getCentros(Model model) {
		model.addAttribute("listaCentros", centroDAO.getAllCentros());
		return "centros";
	}

	@GetMapping("/grupos")
	public String getGrupos(Model model) {
		model.addAttribute("listaGrupos", grupoDAO.findAll());
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

	public int calcularTamanio(int tamanio) {
		for (int i = 5; i > 1; i--) {
			if ((tamanio % i) == 0) {
				return i;
			}
		}
		return calcularTamanio(tamanio + 1);
	}
}