package co.edu.uniquindio.gri.bonitaapi;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.LiderGrupo;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

public class GestorDeCasosBonita {
	
	@Autowired
	private InvestigadorDAO investigadorDAO;
	
	private static final Logger log = LoggerFactory.getLogger(GestorDeCasosBonita.class);

	public String generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(BonitaConnectorAPI bonita, ProduccionBGrupo produccionBGrupo,
			ProduccionGrupo produccionGrupo, String idDelProcesoBonita, String nombreDelProcesoBonita) {

		// Extracción datos de la produccion en estruturas JSONObject y JSONArray

		JSONArray parametros = new JSONArray();
		Grupo grupoDeInvestigacion = null;

		if (produccionBGrupo != null) {

			parametros
					.put((new JSONObject()).put("name", "idDeProduccion").put("value", produccionBGrupo.getId() + ""));
			parametros.put((new JSONObject()).put("name", "nombreDeProduccion").put("value",
					produccionBGrupo.getReferencia()));
			parametros.put((new JSONObject()).put("name", "tipoDeProduccion").put("value", "bibliografica"));
			grupoDeInvestigacion = produccionBGrupo.getGrupo();

		} else if (produccionGrupo != null) {

			parametros.put((new JSONObject()).put("name", "idDeProduccion").put("value", produccionGrupo.getId() + ""));

			parametros.put(
					(new JSONObject()).put("name", "nombreDeProduccion").put("value", produccionGrupo.getReferencia()));

			parametros.put((new JSONObject()).put("name", "tipoDeProduccion").put("value", "generica"));

			grupoDeInvestigacion = produccionGrupo.getGrupo();

		} else {
			return "Producción ingresada invalida";
		}

		parametros.put((new JSONObject()).put("name", "nombreGrupo").put("value", grupoDeInvestigacion.getNombre()));
		parametros.put(
				(new JSONObject()).put("name", "nombreDeLiderDeGrupo").put("value", grupoDeInvestigacion.getLider()));

		LiderGrupo liderGrupo = investigadorDAO.getLiderDeUnGrupo(grupoDeInvestigacion.getId());

		if (liderGrupo == null) {
			String ans = "No se encontró un lider de grupo para el grupo " + grupoDeInvestigacion.getNombre()
			+ " verifique si este grupo cuenta con un lider registrado en la tabla lideresgrupos";
			log.warn(ans);
			return ans;
		}

		parametros.put((new JSONObject()).put("name", "correoDelLiderDelGrupo").put("value", liderGrupo.getEmail()));
		parametros.put((new JSONObject()).put("name", "diDelLiderDelGrupo").put("value", liderGrupo.getDi()));

		try {
			// Creación del caso
			bonita.iniciarCasoConVariables(nombreDelProcesoBonita, idDelProcesoBonita, parametros);
			return "Generación de caso exitosa " + idDelProcesoBonita;
		} catch (URISyntaxException | IOException e) {
			String ans = e.getMessage();
			log.error(ans);
			return ans;
		}

	}
	
}
