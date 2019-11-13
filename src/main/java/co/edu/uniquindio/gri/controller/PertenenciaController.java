package co.edu.uniquindio.gri.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.PertenenciaDAO;

@RestController
@RequestMapping("/rest/service")
public class PertenenciaController {
	
	@Autowired
	PertenenciaDAO pertenenciaDAO;
	

}
