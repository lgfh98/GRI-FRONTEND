package co.edu.uniquindio.gri.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.Pertenencia;
import co.edu.uniquindio.gri.repository.PertenenciaRepository;

@Service
public class PertenenciaDAO {

	@Autowired
	PertenenciaRepository pertenenciaRepository;

	/**
	 * metodo que permite obtener una pertenencia con el ID del investigador
	 * 
	 * @param investigador_id
	 * @return
	 */
	public Pertenencia getPertenenciaByIdInves(Long investigador_id) {

		return pertenenciaRepository.findOne(investigador_id);

	}

	public boolean actualizarPertenencia(long id_invest, String pertenencia) {
		pertenenciaRepository.actualizarPertenencia(id_invest, pertenencia);

		return true;
	}

	public boolean agregarNuevaPertenencia(long id_invest, String pertenencia) {

		pertenenciaRepository.agregarNuevaPertenencia(id_invest, pertenencia);
		return true;
	}

}
