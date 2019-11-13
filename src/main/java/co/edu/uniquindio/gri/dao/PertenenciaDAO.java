package co.edu.uniquindio.gri.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.repository.PertenenciaRepository;

@Service
public class PertenenciaDAO {
	
	
	@Autowired
	PertenenciaRepository pertenenciaRepository;

}
