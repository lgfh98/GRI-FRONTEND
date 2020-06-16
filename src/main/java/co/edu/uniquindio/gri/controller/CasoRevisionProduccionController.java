package co.edu.uniquindio.gri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.CasoRevisionProduccionDAO;

@RestController
@RequestMapping("/rest/service")
public class CasoRevisionProduccionController {

	@Autowired
	CasoRevisionProduccionDAO casoRevisionProduccionDAO;

	@PostMapping("/casos/revisonproduccion")
	public void crearCaso(@RequestParam("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion) {
		casoRevisionProduccionDAO.registrarNuevoCaso(id ,idProduccion, tipoProduccion);
	}

	@PutMapping("/casos/revisonproduccion/{id}")
	public void actualizarCaso(@PathVariable("id") long id, @RequestParam("idproduccion") long idProduccion,
			@RequestParam("tipo") String tipoProduccion, @RequestParam("estado") String estado) {
		casoRevisionProduccionDAO.registrarNuevoCaso(id, idProduccion, tipoProduccion, estado);
	}
}
