package co.edu.uniquindio.gri.webcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.dao.TipoDAO;
import co.edu.uniquindio.gri.model.Tipo;

/**
 * Clase ReporteController.
 */
@Controller
public class ReporteController {
	
	/** Controller para las tipologias . */
	@Autowired
	TipologiasController tipologiasController;
	
	/** DAO para tipos. */
	@Autowired
	TipoDAO tipoDAO;
	
	/** DAO para producciones. */
	@Autowired
	ProduccionDAO produccionDAO;

	/**
	 * Mapea la vista destinada a la visualización de reportes. 
	 *
	 * @param tipoProduccion el tipo de la producción
	 * @param type el tipo de entidad que posee la producción
	 * @param id el id de la entidad
	 * @param model el modelo de visualización
	 * @return vista con el reporte. 
	 */
	@GetMapping("reporte")
	public String getReporte(@RequestParam(name = "prod", required = false, defaultValue = "0") String tipoProduccion, @RequestParam(name = "type", required = false, defaultValue = "u") String type,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id,  Model model) {

		String[] datos = tipologiasController.getDatos(id, type);
		
		Tipo tipo = tipoDAO.getTipoById(Long.parseLong(tipoProduccion));
		
		model.addAttribute("id", id);
		model.addAttribute("tipo", type);
		model.addAttribute("nombre", datos[0]);
		model.addAttribute("color", datos[1]);
		model.addAttribute("tipo", tipo.getNombre());
		model.addAttribute("producciones", produccionDAO.getProducciones(type, Long.parseLong(id), Long.parseLong(tipoProduccion)));
		
		if (tipo.getTipoProduccion().getId() == 3){
			model.addAttribute("esBibliografica", true);
		} else {
			model.addAttribute("esBibliografica", false);
		}		
		
		return "reporte";
	}
	
	
	/**
	 * Mapea la vista destinada a la visualización de resultados respecto a una cadena de búsqueda.
	 *
	 * @param type el tipo de entidad que posee la producción: g en caso de grupos, i en caso de investigadores. 
	 * @param cadena la cadena de búsqueda
	 * @param model el modelo de visualización
	 * @return vista con los resultados de la búsqueda cargados.
	 */
	@GetMapping("busqueda")
	public String getResultadosBusqueda(@RequestParam(name = "type", required = false, defaultValue = "g") String type,
			@RequestParam(name = "search", required = false, defaultValue = "") String cadena, Model model) {
	
		
		String busqueda = cadena.replaceAll("\\+", " ").toUpperCase();
		if(type.equals("g")){
			model.addAttribute("esGrupo", true);
		} else {
			model.addAttribute("esGrupo", false);
		}
		
		model.addAttribute("cadena", cadena);
		model.addAttribute("resultados", produccionDAO.getProduccionBusqueda(type, busqueda));
		
		return "busqueda";
	}
}
