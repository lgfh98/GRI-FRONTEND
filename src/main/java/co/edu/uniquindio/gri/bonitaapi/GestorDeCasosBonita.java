package co.edu.uniquindio.gri.bonitaapi;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.LiderGrupo;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.utilities.Util;

@Component
public class GestorDeCasosBonita {

	@Value("${bonita.nombre.proceso}")
	private String nombreDelProcesoDeSubidaYRevisionDeProduccionesDeInvestigacion;
	@Value("${bonita.usuario}")
	private String usuario;
	@Value("${bonita.password}")
	private String password;
	@Value("${bonita.servidor.base}")
	private String servidor;
	
	@Autowired
	private InvestigadorDAO investigadorDAO;
	
	public GestorDeCasosBonita() {
		
	}
	

	private static final Logger log = LoggerFactory.getLogger(GestorDeCasosBonita.class);

	public boolean generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(BonitaConnectorAPI bonita,
			ProduccionBGrupo produccionBGrupo, ProduccionGrupo produccionGrupo, String idDelProcesoBonita,
			String nombreDelProcesoBonita) {

		// Extracción datos de la produccion en estruturas JSONObject y JSONArray

		JSONArray parametros = new JSONArray();
		Grupo grupoDeInvestigacion = null;

		if (produccionBGrupo != null) {

			parametros
					.put((new JSONObject()).put("name", "idDeProduccion").put("value", produccionBGrupo.getId() + ""));
			parametros.put((new JSONObject()).put("name", "nombreDeProduccion").put("value",
					produccionBGrupo.getReferencia()));
			parametros.put((new JSONObject()).put("name", "tipoDeProduccion").put("value", Util.PRODUCCION_BIBLIOGRAFICA));
			grupoDeInvestigacion = produccionBGrupo.getGrupo();

		} else if (produccionGrupo != null) {

			parametros.put((new JSONObject()).put("name", "idDeProduccion").put("value", produccionGrupo.getId() + ""));

			parametros.put(
					(new JSONObject()).put("name", "nombreDeProduccion").put("value", produccionGrupo.getReferencia()));

			parametros.put((new JSONObject()).put("name", "tipoDeProduccion").put("value", Util.PRODUCCION_GENERICA));

			grupoDeInvestigacion = produccionGrupo.getGrupo();

		} else {
			return false;
		}

		parametros.put((new JSONObject()).put("name", "nombreGrupo").put("value", grupoDeInvestigacion.getNombre()));
		parametros.put(
				(new JSONObject()).put("name", "nombreDeLiderDeGrupo").put("value", grupoDeInvestigacion.getLider()));
		
		LiderGrupo liderGrupo = investigadorDAO.getLiderDeUnGrupo(grupoDeInvestigacion.getId());

		if (liderGrupo == null) {
			log.warn("No se encontró un lider de grupo para el grupo " + grupoDeInvestigacion.getNombre()
			+ " verifique si este grupo cuenta con un lider registrado en la tabla lideresgrupos");
			return false;
		}

		parametros.put((new JSONObject()).put("name", "correoDelLiderDelGrupo").put("value", liderGrupo.getEmail()));
		parametros.put((new JSONObject()).put("name", "diDelLiderDelGrupo").put("value", liderGrupo.getDi()));

		try {
			// Creación del caso
			bonita.iniciarCasoConVariables(nombreDelProcesoBonita, idDelProcesoBonita, parametros);
			log.info("Generación de caso exitosa " + idDelProcesoBonita);
			return true;
		} catch (URISyntaxException | IOException e) {
			log.error(e.getMessage());
			return false;
		}

	}

	public boolean eliminarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(BonitaConnectorAPI bonita, long id)
			throws ClientProtocolException, URISyntaxException, IOException {
		return bonita.eliminarCaso(id);
	}

	public boolean generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(Long prodId,
			ProduccionBGrupo produccionBGrupo, ProduccionGrupo produccionGrupo)
			throws ClientProtocolException, URISyntaxException, IOException {
		log.info("Generando nuevo caso para la producción con id: " + prodId);
		BonitaConnectorAPI b = new BonitaConnectorAPI(servidor, usuario, password);
		boolean respuesta = generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(b, produccionBGrupo, produccionGrupo, b.obtenerIdDelProceso(nombreDelProcesoDeSubidaYRevisionDeProduccionesDeInvestigacion), nombreDelProcesoDeSubidaYRevisionDeProduccionesDeInvestigacion);
		b.cerrarClienteHttp();
		return respuesta;
	}

	public boolean eliminarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(long id) throws ClientProtocolException, URISyntaxException, IOException {
		log.info("Dando de baja el caso  " + id + " del proceso \""+ nombreDelProcesoDeSubidaYRevisionDeProduccionesDeInvestigacion + "\"");
		BonitaConnectorAPI b = new BonitaConnectorAPI(servidor, usuario, password);
		boolean respuesta = eliminarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(b, id);
		b.cerrarClienteHttp();
		return respuesta;
	}

}
