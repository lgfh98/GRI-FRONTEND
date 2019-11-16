package co.edu.uniquindio.gri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.PertenenciaDAO;

/**
 * clase pertenencia controler
 */
@RestController
@RequestMapping("/rest/service")
public class PertenenciaController {

	@Autowired
	PertenenciaDAO pertenenciaDAO;

	/**
	 * actualiza o agrega la pertenencia de un investigador
	 * 
	 * @param id_invest   id de un investigador
	 * @param pertenencia pertenencia de un investigador
	 * @return confirmacion de investigador agregado
	 */
	@PostMapping("/pertenencia/{id_invest}/{pertenencia}")
	public boolean updatePertenencia(@PathVariable("id_invest") String id_invest,
			@PathVariable("pertenencia") String pertenencia) {

		// si ya existe un registro (ACTUALIZAR)
		if (pertenenciaDAO.getPertenenciaByIdInves(Long.parseLong(id_invest)) != null) {

			return pertenenciaDAO.actualizarPertenencia(Long.parseLong(id_invest), pertenencia);

		}
		// si no existe un registro con el id del investigador (AGREGAR)
		else {

			return pertenenciaDAO.agregarNuevaPertenencia(Long.parseLong(id_invest), pertenencia);

		}

	}

}
