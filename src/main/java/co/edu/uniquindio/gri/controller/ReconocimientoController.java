package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.ReconocimientosDAO;
import co.edu.uniquindio.gri.model.RecononocimientosInvestigador;

@RestController
@RequestMapping("/rest/service")
public class ReconocimientoController {

	@Autowired
	ReconocimientosDAO reconocimientosDAO;

	/**
	 * Obtiene los reconocimientos de una entidad específica.
	 *
	 * @param type     el tipo de la entidad (f: Facultad, p: Programa, c: Centro,
	 *                 g: Grupo de Investigación i: Investigador)
	 * @param entityId el id de la entidad
	 * @return lista de producciones
	 */

	@GetMapping("/reconocimientos/{type}/{id}")
	public List<RecononocimientosInvestigador> getProducciones(@PathVariable("type") String type,
			@PathVariable("id") Long entityId) {
		
		List<RecononocimientosInvestigador> result = reconocimientosDAO.getReconocimientos(entityId, type);

		return result;
	}

}