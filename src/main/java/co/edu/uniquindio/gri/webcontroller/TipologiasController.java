package co.edu.uniquindio.gri.webcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uniquindio.gri.dao.CentroDAO;
import co.edu.uniquindio.gri.dao.FacultadDAO;
import co.edu.uniquindio.gri.dao.GrupoDAO;
import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.dao.PertenenciaDAO;
import co.edu.uniquindio.gri.dao.ProgramaDAO;
import co.edu.uniquindio.gri.dao.TipoDAO;
import co.edu.uniquindio.gri.model.Centro;
import co.edu.uniquindio.gri.model.Facultad;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.GruposInves;
import co.edu.uniquindio.gri.model.Investigador;
import co.edu.uniquindio.gri.model.Pertenencia;
import co.edu.uniquindio.gri.model.Programa;

@Controller
public class TipologiasController {

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

	@GetMapping("apropiacion")
	public String getApropiacion(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/apropiacion";

		} else {
			return "grupos/apropiacion";
		}
	}

	@GetMapping("arte")
	public String getArte(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/arte";

		} else {
			return "grupos/arte";
		}
	}

	@GetMapping("bibliografica")
	public String getBibliografica(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/bibliografica";

		} else {
			return "grupos/bibliografica";
		}
	}

	@GetMapping("evaluador")
	public String getEvaluador(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/evaluador";

		} else {
			return "grupos/evaluador";
		}
	}

	@GetMapping("formacion")
	public String getFormacion(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/formacion";

		} else {
			return "grupos/formacion";
		}
	}

	@GetMapping("info")
	public String getInfo(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			Investigador inv = investigadorDAO.findOne(Long.parseLong(id));

			Pertenencia pertenecia_investigador = pertenenciaDAO.getPertenenciaByIdInves(inv.getId());
			if (pertenecia_investigador != null) {
				inv.setPertenencia(pertenecia_investigador.getPertenencia());
			} else {

				inv.setPertenencia("INDEFINIDO");

			}

			List<GruposInves> gruposInves = inv.getGrupos();
			List<Grupo> grupos = new ArrayList<Grupo>();
			for (GruposInves grupoInves : gruposInves) {
				grupos.add(grupoInves.getGrupo());
			}

			model.addAttribute("inv", inv);
			model.addAttribute("listaGrupos", grupos);
			model.addAttribute("listaIdiomas", inv.getIdiomas());

			return "investigadores/info";

		} else {
			List<Investigador> integrantes = investigadorDAO.getIntegrantes(type, Long.parseLong(id));
			Map<String, Integer> datosCategoria = new HashMap<String, Integer>();
			Map<String, Integer> datosFormacion = new HashMap<String, Integer>();

			for (Investigador investigador : integrantes) {

				// colocar categoria reaL

				Pertenencia pertenecia_investigador = pertenenciaDAO.getPertenenciaByIdInves(investigador.getId());
				if (pertenecia_investigador != null) {
					investigador.setPertenencia(pertenecia_investigador.getPertenencia());
				} else {

					investigador.setPertenencia("INDEFINIDO");

				}

				String categoria = investigador.getCategoria();
				if (datosCategoria.containsKey(categoria)) {
					int valor = datosCategoria.get(categoria);
					datosCategoria.put(categoria, valor + 1);
				} else {
					datosCategoria.put(categoria, 1);
				}
			}

			for (Investigador investigador : integrantes) {
				String formacion = investigador.getNivelAcademico();
				if (datosFormacion.containsKey(formacion)) {
					int valor = datosFormacion.get(formacion);
					datosFormacion.put(formacion, valor + 1);
				} else {
					datosFormacion.put(formacion, 1);
				}
			}

			model.addAttribute("integrantes", integrantes);

			model.addAttribute("clavesCategoria", datosCategoria.keySet());
			model.addAttribute("datosCategoria", datosCategoria.values());

			model.addAttribute("clavesFormacion", datosFormacion.keySet());
			model.addAttribute("datosFormacion", datosFormacion.values());

			if (!type.equals("g")) {
				model.addAttribute("esGrupo", false);
				model.addAttribute("gruposPertenecientes", grupoDAO.getGruposPertenecientes(Long.parseLong(id), type));
			} else {
				model.addAttribute("esGrupo", true);
			}

			return "grupos/info";
		}
	}

	@GetMapping("masinfo")
	public String getMasInfo(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/masinfo";

		} else {
			return "grupos/masinfo";
		}
	}

	@GetMapping("tecnica")
	public String getTecnica(@RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id, Model model) {

		String[] datos = getDatos(id, type);

		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("facultadId", datos[2]);

		if (type.equals("i")) {
			return "investigadores/tecnica";

		} else {
			return "grupos/tecnica";
		}
	}

	public String[] getDatos(String id, String type) {
		String[] datos = new String[3];

		if (type.equals("u")) {
			datos[0] = "TIPOLOGÍA DE PRODUCTOS PARA LA UNIVERSIDAD DEL QUINDÍO";
			datos[1] = "card-0";
			datos[2] = "0";

		} else if (type.equals("f")) {
			Facultad f = facultadDAO.getFacultadById(Long.parseLong(id));

			datos[0] = f.getNombre();
			datos[1] = "card-" + f.getId();
			datos[2] = "" + f.getId();

		} else if (type.equals("p")) {
			Programa p = programaDAO.getProgramaById(Long.parseLong(id));

			datos[0] = p.getNombre();
			datos[1] = "card-" + p.getFacultad().getId();
			datos[2] = "" + p.getFacultad().getId();

		} else if (type.equals("c")) {
			Centro c = centroDAO.getCentroById(Long.parseLong(id));

			datos[0] = c.getNombre();
			datos[1] = "card-" + c.getFacultad().getId();
			datos[2] = "" + c.getFacultad().getId();

		} else if (type.equals("g")) {
			Grupo g = grupoDAO.findOne(Long.parseLong(id));

			datos[0] = g.getNombre();
			datos[1] = "card-" + g.getProgramas().get(0).getFacultad().getId();
			datos[2] = "" + g.getProgramas().get(0).getFacultad().getId();

		} else if (type.equals("i")) {
			Investigador i = investigadorDAO.findOne(Long.parseLong(id));

			datos[0] = i.getNombre();
			datos[1] = "card-0";
			datos[2] = "0";
		}

		return datos;
	}
}
