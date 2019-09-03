package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.ProduccionDAO;

/**
 * Clase ProduccionController.
 */
@RestController
@RequestMapping("/rest/service")
public class ProduccionController { 

	/** DAO para producciones. */
	@Autowired
	ProduccionDAO produccionDAO;
	
	/**
	 * Obtiene las producciones de una entidad específica.
	 *
	 * @param type el tipo de la entidad (f: Facultad, p: Programa, c: Centro, g: Grupo de Investigación i: Investigador)
	 * @param entityId el id de la entidad
	 * @param tipoId el tipo de la producción a obtener
	 * @return lista de producciones
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/producciones/{type}/{id}/{tipo}")
	public List getProducciones(@PathVariable("type") String type, @PathVariable("id") Long entityId,@PathVariable("tipo") Long tipoId) {
		return produccionDAO.getProducciones(type, entityId, tipoId);
	}
	
	/**
	 * Obtiene las producciones de acuerdo a una cadena de búsqueda.
	 *
	 * @param tipo, el tipo de búsqueda a realizar (i: CvLAC, g: GrupLAC)
	 * @param cadena la cadena de búsqueda
	 * @return lista de producciones correspondientes con la cadena de búsqueda.
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/busqueda/{type}/{cadena}")
	public List getProduccionBBusqueda(@PathVariable("type") String tipo, @PathVariable("cadena") String cadena){
		String busqueda = cadena.replaceAll("\\+", " ").toUpperCase();
		return produccionDAO.getProduccionBusqueda(tipo, busqueda);
	}
	
	/**
	 * Actualiza el estado de una producción científica en función si se encuentra o no en custodia física de la Vicerrectoría de Investigaciones.
	 *
	 * @param tipo, identifica si la producción es o no bibliográfica 
	 * @param estado, el estado de la producción. 0 si no se encuentra en custodia. 1 en caso contrario. 
	 * @param prodId, el identificador de la producción en base de datos.
	 * @return true, si la actualización se realizó satisfactoriamente. 
	 */
	@PostMapping("/producciones/{tipo}/{estado}/{prodId}")
	public boolean updateInfoProduccion(@PathVariable("tipo") String tipo, @PathVariable("estado") int estado, @PathVariable("prodId") Long prodId){
		return produccionDAO.actualizarProducciones(tipo, estado, prodId);
	}
}
