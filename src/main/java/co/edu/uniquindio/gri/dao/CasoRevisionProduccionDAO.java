package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.CasoRevisionProduccion;
import co.edu.uniquindio.gri.repository.CasoRevisionProduccionRepository;

@Service
public class CasoRevisionProduccionDAO {

	@Autowired
	CasoRevisionProduccionRepository casoRevisionProduccionRepository;

	public List<CasoRevisionProduccion> getCasosPorEstado(String estado) {
		return casoRevisionProduccionRepository.getCasosPorEstado(estado);
	}

	public List<CasoRevisionProduccion> getCasosPorTipoDeProduccion(String tipoProduccion) {
		return casoRevisionProduccionRepository.getCasosPorTipoDeProduccion(tipoProduccion);
	}
	
	public void registrarNuevoCaso(long id, long idProduccion, String tipoProduccion, String estado) {
		casoRevisionProduccionRepository.save(new CasoRevisionProduccion(id,idProduccion, tipoProduccion, estado));
	}
	
	public void registrarNuevoCaso(long id, long idProduccion, String tipoProduccion) {
		casoRevisionProduccionRepository.save(new CasoRevisionProduccion(id, idProduccion, tipoProduccion, "EN CURSO"));
	}

}
