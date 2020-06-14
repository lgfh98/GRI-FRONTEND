package co.edu.uniquindio.gri.schedulingtasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.gri.dao.InvestigadorDAO;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.LiderGrupo;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Clase encargada de gestionar la generación de casos BPM de subida y revisón
 * de producciones de investigación, esta clase gestiona la conexión a los
 * servidores bonita BPM de la Universidad del Quindío y genera un caso
 * respectivo cuando sea necesario según lo reportado por el GRI y sus bases de
 * datos
 * 
 * @author Sebastian Montes
 *
 */
@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	// Variables de entorno declaradas localmente (application.properties)

	@Value("${bonita.nombre.proceso}")
	private String nombreDelProcesoBonita;
	@Value("${bonita.servidor.login}")
	private String servidorBonitaLogin;
	@Value("${bonita.servidor.consulta.idproceso}")
	private String servidorBonitaConsultaIdProceso;
	@Value("${bonita.usuario}")
	private String usuario;
	@Value("${bonita.password}")
	private String password;
	@Value("${bonita.servidor.iniciocaso}")
	private String servidorBonitaInicioCaso;

	private CloseableHttpClient httpClient;
	private URIBuilder builder;

	/*
	 * Tasa de trabajo por producciones bibliográficas que no están en custodia, se
	 * iterará un 1% aleatorio de las producciones bibliográficas que no están en
	 * custodia hasta el 11/06/2020 (es decir 1% de 4706 redondeado 47)
	 */
	private static final int CASOSPRODUCCIONESBIBLIOGRAFICASPORITERACION = 2;

	/*
	 * Tasa de trabajo por producciones que no están en custodia, se iterará un 1%
	 * aleatorio de las producciones que no están en custodia hasta el 11/06/2020
	 * (es decir 1% de 15609 redondeado 156)
	 */
	private static final int CASOSPRODUCCIONESPORITERACION = 2;

	@Autowired
	private InvestigadorDAO investigadorDAO;

	@Autowired
	private ProduccionDAO produccionDAO;

	@PersistenceContext
	private EntityManager em;

	/**
	 * Tarea encargada de generar los casos bpm de revisión y subida de producciones
	 * de investigación en el servidor bonita de la Universidad del Quindío en los
	 * cuales están involucrados todos los lideres de grupos para los cuales el GRI
	 * reporta que tienen producciones que aún no están en custodia
	 */
	@Transactional
	@Scheduled(fixedDelay = 86400000 , initialDelay = 0)
	public void generarCasosDeSubidaYRevisionDeProduccionesDeInvestigacion() {

		List<ProduccionBGrupo> produccionBibliograficaSinCustodia = produccionDAO.getProduccionesBSinCustodia();
		List<ProduccionGrupo> produccionSinCustodia = produccionDAO.getProduccionesSinCustodia();
		int cantidadDeProduccionesBibliograficasSinCustodia = 0;
		int cantidadDeProduccionesSinCustodia = 0;

		if (produccionBibliograficaSinCustodia != null) {
			cantidadDeProduccionesBibliograficasSinCustodia = produccionBibliograficaSinCustodia.size();
		}
		if (produccionSinCustodia != null) {
			cantidadDeProduccionesSinCustodia = produccionSinCustodia.size();
		}

		log.info("Cantidad de producciones que no están en custodia: "
				+ produccionDAO.getProduccionesSinCustodia().size());
		log.info("Cantidad de producciones bibliográficas que no están en custodia: "
				+ produccionDAO.getProduccionesBSinCustodia().size());

		String idDelProcesoBonita = null;

		/*
		 * Inicio de la conexión al servidor bonita, esto siempre y cuando existan
		 * producciones que no estén en custodia sobre las cuales se pueda apoyar el
		 * proceso
		 */
		if (cantidadDeProduccionesBibliograficasSinCustodia + cantidadDeProduccionesSinCustodia > 0) {
			iniciarClienteHttp();

			try {
				iniciarSesionEnBonita();
				idDelProcesoBonita = obtenerIdDelProceso();
			} catch (URISyntaxException | IOException e) {
				// Manejo de excepción en caso de que no se encuentre el servidor bonita de la
				// Universidad del Quindío
				log.error(e.getMessage());
				return;
			}
		} else {
			log.info(
					"Toda la productividad de investigación se encuentra en custodia, no se generarán casos para subida y revisón de producciones");
			return;
		}

		if (cantidadDeProduccionesBibliograficasSinCustodia > 0) {
			for (int i = 0; i < CASOSPRODUCCIONESBIBLIOGRAFICASPORITERACION; i++) {
				// Selección de una producción bibliográfica aleatoria para la generación de un
				// caso
				int posProduccionBibliograficaAleatoria = (int) (Math.random()
						* cantidadDeProduccionesBibliograficasSinCustodia);
				generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(
						produccionBibliograficaSinCustodia.get(posProduccionBibliograficaAleatoria), null,
						idDelProcesoBonita);
			}
		}
		if (cantidadDeProduccionesSinCustodia > 0) {
			for (int i = 0; i < CASOSPRODUCCIONESPORITERACION; i++) {
				// Selección de una producción aleatoria para la generación de un caso
				int posProduccionAleatoria = (int) (Math.random() * cantidadDeProduccionesSinCustodia);
				generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(null,
						produccionSinCustodia.get(posProduccionAleatoria), idDelProcesoBonita);
			}
		}

		try {
			cerrarClienteHttp();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(ProduccionBGrupo produccionBGrupo,
			ProduccionGrupo produccionGrupo, String idDelProcesoBonita) {

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
			return;
		}

		parametros.put((new JSONObject()).put("name", "nombreGrupo").put("value", grupoDeInvestigacion.getNombre()));
		parametros.put(
				(new JSONObject()).put("name", "nombreDeLiderDeGrupo").put("value", grupoDeInvestigacion.getLider()));

		LiderGrupo liderGrupo = investigadorDAO.getLiderDeUnGrupo(grupoDeInvestigacion.getId());

		if (liderGrupo == null) {
			log.warn("No se encontró un lider de grupo para el grupo " + grupoDeInvestigacion.getNombre()
					+ " verifique si este grupo cuenta con un lider registrado en la tabla lideresgrupos");
			return;
		}

		parametros.put((new JSONObject()).put("name", "correoDelLiderDelGrupo").put("value", liderGrupo.getEmail()));
		parametros.put((new JSONObject()).put("name", "diDelLiderDelGrupo").put("value", liderGrupo.getDi()));

		try {
			// Creación del caso
			iniciarCaso(idDelProcesoBonita, parametros);

			// Actualización del estado de la producción en cuestión, esta cambia su estado
			// a 2, es decir "en proceso de recolección", se hace uso del entity manager
			// para
			// realizar la actualización inmediatamente ya que dejar ese objeto sin
			// actualizar
			// sincrónicamente puede generar que un caso se repita en la misma iteración.

			if (produccionBGrupo != null) {
				produccionDAO.actualizarEstadoDeProduccion(produccionBGrupo.getId(), "bibliografica", 2);
				em.refresh(produccionBGrupo);
			} else {
				produccionDAO.actualizarEstadoDeProduccion(produccionGrupo.getId(), "generica", 2);
				em.refresh(produccionGrupo);
			}

		} catch (URISyntaxException | IOException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * Método que inicia un caso específico en el servidor bonita asociado a los
	 * parámetros enviados
	 * 
	 * @param idDelProcesoBonita id del proceso a inciar
	 * @param parametros         arreglo JSON con los parámetros a enviar, estos se
	 *                           inyectarán como variables en el caso iniciado
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis el identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */

	public void iniciarCaso(String idDelProcesoBonita, JSONArray parametros)
			throws URISyntaxException, ClientProtocolException, IOException {

		builder = new URIBuilder(servidorBonitaInicioCaso);
		HttpPost iniciacionDeCaso = new HttpPost(builder.build());

		// Creación del objeto JSON para el envío de los parámetros
		JSONObject modeloDeEnvio = new JSONObject();
		modeloDeEnvio.put("processDefinitionId", idDelProcesoBonita);
		modeloDeEnvio.put("variables", parametros);

		log.info("Iniciando caso \"" + nombreDelProcesoBonita + "\" con id: " + idDelProcesoBonita + " con parametros: "
				+ modeloDeEnvio.toString());

		// Creación de la entidad HTTP bajo la codificación UTF-8 con el objeto JSON
		// previo
		StringEntity se = new StringEntity(modeloDeEnvio.toString(), "UTF-8");
		iniciacionDeCaso.addHeader("content-type", "application/json");
		iniciacionDeCaso.setEntity(se);

		// Se ejecuta la petición POST
		try (CloseableHttpResponse response = httpClient.execute(iniciacionDeCaso)) {
			log.info("Respuesta obtenida bajo " + response.getProtocolVersion() + ", Status: "
					+ response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
		}
	}

	/**
	 * Método encargado de establecer una conexión HTTP con el servidor bonita de la
	 * Universidad del Quindío el servidor devolverá una cookie que se guarda en el
	 * cliente HTTP, mediante la cual se puede autenticar para transacciones futuras
	 * que requiran la autenticación.
	 * 
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis el identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */
	public void iniciarSesionEnBonita() throws URISyntaxException, ClientProtocolException, IOException {
		log.info("Iniciando sesión en el servidor bonita con el usuario " + usuario);
		builder = new URIBuilder(servidorBonitaLogin);
		builder.setParameter("username", usuario).setParameter("password", password).setParameter("redirect", "false");
		HttpPost solicitudDeLogin = new HttpPost(builder.build());

		try (CloseableHttpResponse response = httpClient.execute(solicitudDeLogin)) {

			Header[] headers = response.getHeaders("Set-Cookie");
			String headersString = "[";
			String separador = "";
			for (Header h : headers) {
				headersString += separador + h.getValue().toString();
				separador = " | ";
			}
			headersString += "]";

			log.info("Respuesta obtenida bajo " + response.getProtocolVersion() + ", Status: "
					+ response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase()
					+ " cookies asiganadas: " + headersString);

		}
	}

	/**
	 * Método encargado de obtener la id del proceso mediante una petción al
	 * servidor bonita de la Universidad del Quindío este método retorna el id del
	 * proceso en el contexto del servidor bonita
	 * 
	 * @return el id del proceso en el contexto del servidor bonita
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis el identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */
	public String obtenerIdDelProceso() throws URISyntaxException, ClientProtocolException, IOException {

		log.info("Recuperando id del proceso con nombre \"" + nombreDelProcesoBonita + "\"");
		builder = new URIBuilder(servidorBonitaConsultaIdProceso);
		builder.setParameter("s", nombreDelProcesoBonita);
		HttpGet solicitudIdDelProceso = new HttpGet(builder.build());

		try (CloseableHttpResponse response = httpClient.execute(solicitudIdDelProceso)) {

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				JSONObject respuestaJSON = new JSONObject(EntityUtils.toString(entity).replaceAll("[\\[\\]]", ""));
				String id = respuestaJSON.get("id").toString();
				log.info("Respuesta obtenida bajo " + response.getProtocolVersion() + ", Status: "
						+ response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase()
						+ ", el id del proceso: \"" + nombreDelProcesoBonita + "\" corresponde a:" + id);
				return id;
			} else {
				return null;
			}
		}
	}

	/**
	 * Método encargado de iniciar un caso para el proceso con el id dado por
	 * parámetro en el servidor bonita de la Universidad del Quindío
	 * 
	 * @param idDelProcesoBonita el id del proceso en el contexto bonita del
	 *                           servidor bonita de la Universidad del Quindío
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis el identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */

	/**
	 * Método encargado de iniciar un cliente de conexión HTTP
	 */
	public void iniciarClienteHttp() {
		httpClient = HttpClients.createDefault();
	}

	/**
	 * Método que cierra el cliente HTTP
	 * 
	 * @throws IOException en caso de generarse un error relacionado a la conexión
	 */
	public void cerrarClienteHttp() throws IOException {
		httpClient.close();
	}

}