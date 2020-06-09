package co.edu.uniquindio.gri.schedulingtasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

	//Variables de entorno declaradas localmente (application.properties)
	
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

	/**
	 * Tarea encargada de generar los casos bpm de revisión y subida de evidencias
	 * en el servidor bonita de la Universidad del Quindío en los cuales están
	 * involucrados todos los individuos los cuales el GRI reporta que tienen
	 * producciones que aún no están en custodia
	 * 
	 * @throws IOException        en caso de generarse un error relacionado a la
	 *                            conexión
	 * @throws URISyntaxException en caso de generarse un error de en la sintaxis el
	 *                            identificador unico de recursos (URI)
	 */
	@Scheduled(fixedRate = 20000)
	public void generarCasosDeSubidaYRevisionDeProduccionesDeInvestigacion() throws IOException, URISyntaxException {
		iniciarClienteHttp();
		iniciarSesionEnBonita();
		iniciarCaso(obtenerIdDelProceso());
		cerrarClienteHttp();
	}

	/**
	 * Método encargado de iniciar un cliente de conexión HTTP
	 */
	public void iniciarClienteHttp() {
		httpClient = HttpClients.createDefault();
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
					+ " cookies asiganadas correctamente: " + headersString);

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
	 * @param id el id del proceso en el contexto bonita del servidor bonita de la
	 *           Universidad del Quindío
	 * @throws URISyntaxException      en caso de generarse un error de en la
	 *                                 sintaxis el identificador unico de recursos
	 *                                 (URI)
	 * @throws ClientProtocolException En caso de generarse algun error en la
	 *                                 ejecución de la solicitud
	 * @throws IOException             en caso de generarse un error relacionado a
	 *                                 la conexión
	 */
	public void iniciarCaso(String id) throws URISyntaxException, ClientProtocolException, IOException {

		log.info("Iniciando caso \"" + nombreDelProcesoBonita + "\" con id: " + id);
		builder = new URIBuilder(servidorBonitaInicioCaso);
		System.out.println((new JSONObject()).put("processDefinitionId", id).toString());
		HttpPost iniciacionDeCaso = new HttpPost(builder.build());
		StringEntity se = new StringEntity((new JSONObject()).put("processDefinitionId", id).toString());
		iniciacionDeCaso.addHeader("content-type", "application/json");
		iniciacionDeCaso.setEntity(se);

		try (CloseableHttpResponse response = httpClient.execute(iniciacionDeCaso)) {
			log.info("Respuesta obtenida bajo " + response.getProtocolVersion() + ", Status: "
					+ response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
		}
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