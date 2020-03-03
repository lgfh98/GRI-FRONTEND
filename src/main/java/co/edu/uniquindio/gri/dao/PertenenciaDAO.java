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

		return pertenenciaRepository.findById(investigador_id).orElse(null);

	}

	/**
	 * permite eliminar una pertenencia existente en la base de datos
	 * @param id_invest  id del investigador a editarle pertenencia
	 * @param pertenencia pertenencia nueva
	 * @return  true si la actualizo, false si no
	 */
	public boolean actualizarPertenencia(long id_invest, String pertenencia) {
		pertenenciaRepository.actualizarPertenencia(id_invest, pertenencia);

		return true;
	}

	/**
	 * metodo que permite agregar un nuevo registro a la pertenencia
	 * @param id_invest id del investigador al que se le agregara una pertenencia
	 * @param pertenencia nueva pertenencia del investigador
	 * @return true si la agrego, false si no
	 */
	public boolean agregarNuevaPertenencia(long id_invest, String pertenencia) {
		pertenenciaRepository.agregarNuevaPertenencia(id_invest, pertenencia);
		return true;
	}

}
