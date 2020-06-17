package co.edu.uniquindio.gri.bonitaapi;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uniquindio.gri.schedulingtasks.ScheduledTasks;

/**
 * API que brinda un pool de métodos para comunicación con Bonita
 * 
 * @author Jhon Sebastian Montes R
 *
 */
public class BonitaConnectorAPI {

	private CloseableHttpClient httpClient;
	private URIBuilder builder;
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private String servidorBonita;
	private String servidorBonitaLogin;
	private String servidorBonitaConsultaIdProceso;
	private String usuario;
	private String password;
	private String servidorBonitaInicioCaso;
	private String servidorBonitaEliminacionCaso;

	/**
	 * Constructor vacío
	 */
	public BonitaConnectorAPI() {
	}

	/**
	 * Método constructor de una instancia de la API Bonita, se encarga de mapear
	 * las direcciones de utilidad (otras APIs) basado en el servidor ingresado.
	 * 
	 * @param servidorBonita dirección del servidor bonita
	 */
	public BonitaConnectorAPI(String servidorBonita) {
		this.servidorBonita = servidorBonita;
		this.servidorBonitaLogin = servidorBonita + "loginservice";
		this.servidorBonitaInicioCaso = servidorBonita + "api/bpm/case";
		this.servidorBonitaConsultaIdProceso = servidorBonita + "api/bpm/process";
		this.servidorBonitaEliminacionCaso = servidorBonita + "api/bpm/case";
	}

	/**
	 * Método encargado de establecer una conexión HTTP con el servidor bonita, el
	 * servidor devolverá una cookie que se guarda en el cliente HTTP, mediante la
	 * cual se puede autenticar para transacciones futuras que requiran la
	 * autenticación.
	 * 
	 * @param servidorBonitaLogin
	 * @param usuario
	 * @param password
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis el identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */
	public void iniciarSesionEnBonita(String usuario, String password)
			throws URISyntaxException, ClientProtocolException, IOException {
		log.info("Accediendo a " + servidorBonitaLogin);
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
	 * Método que inicia un caso específico en el servidor bonita del proceso con el
	 * id dado por parámetro y con el JSON dado por parámetro.
	 * 
	 * @param idDelProcesoBonita id del proceso a inciar
	 * @param parametros         arreglo JSON con los parámetros a enviar, estos se
	 *                           inyectarán como variables en el caso iniciado
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis del identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */

	public void iniciarCasoConVariables(String nombreDelProcesoBonita, String idDelProcesoBonita, JSONArray parametros)
			throws URISyntaxException, ClientProtocolException, IOException {
		log.info("Accediendo a " + servidorBonitaInicioCaso);
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
	 * Método encargado de obtener la id del proceso mediante una petición al
	 * servidor bonita, este método retorna el id del proceso en el contexto del
	 * servidor bonita.
	 * 
	 * @return el id del proceso en el contexto del servidor bonita
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis del identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */
	public String obtenerIdDelProceso(String nombreDelProcesoBonita)
			throws URISyntaxException, ClientProtocolException, IOException {
		log.info("Accediendo a " + servidorBonitaConsultaIdProceso);
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
	 * 
	 * Método que elimina un caso de bonita con el id dado por parámetro
	 * 
	 * @param idDeCaso
	 * @return true si se borró satisfactoriamente
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	public boolean eliminarCaso(String idDeCaso) throws URISyntaxException, ClientProtocolException, IOException {

		log.info("Accediendo a " + servidorBonitaEliminacionCaso);
		log.info("Eliminado caso con id: " + idDeCaso);

		builder = new URIBuilder(servidorBonitaEliminacionCaso + "/" + idDeCaso);
		HttpDelete solicitudIdDelProceso = new HttpDelete(builder.build());

		try (CloseableHttpResponse response = httpClient.execute(solicitudIdDelProceso)) {

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				log.info("Respuesta obtenida de proceso de eliminación bajo " + response.getProtocolVersion()
						+ ", Status: " + response.getStatusLine().getStatusCode());
				return true;
			} else {
				return false;
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
	 *                                 sintaxis del identificador unico de recursos
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

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getServidorBonita() {
		return servidorBonita;
	}

	public void setServidorBonita(String servidorBonita) {
		this.servidorBonita = servidorBonita;
	}

	public String getServidorBonitaLogin() {
		return servidorBonitaLogin;
	}

	public void setServidorBonitaLogin(String servidorBonitaLogin) {
		this.servidorBonitaLogin = servidorBonitaLogin;
	}

	public String getServidorBonitaConsultaIdProceso() {
		return servidorBonitaConsultaIdProceso;
	}

	public void setServidorBonitaConsultaIdProceso(String servidorBonitaConsultaIdProceso) {
		this.servidorBonitaConsultaIdProceso = servidorBonitaConsultaIdProceso;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServidorBonitaInicioCaso() {
		return servidorBonitaInicioCaso;
	}

	public void setServidorBonitaInicioCaso(String servidorBonitaInicioCaso) {
		this.servidorBonitaInicioCaso = servidorBonitaInicioCaso;
	}

	@Override
	public String toString() {
		return "BonitaConnectorAPI [httpClient=" + httpClient + ", builder=" + builder + ", servidorBonita="
				+ servidorBonita + ", servidorBonitaLogin=" + servidorBonitaLogin + ", servidorBonitaConsultaIdProceso="
				+ servidorBonitaConsultaIdProceso + ", usuario=" + usuario + ", servidorBonitaInicioCaso="
				+ servidorBonitaInicioCaso + "]";
	}

}
