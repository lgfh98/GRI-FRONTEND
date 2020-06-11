package co.edu.uniquindio.gri.schedulingtasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.gri.exception.IntegridadDeDatosDeIncioDeCasoInvalidaException;
import co.edu.uniquindio.gri.model.Grupo;
import co.edu.uniquindio.gri.model.LiderGrupo;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;
import co.edu.uniquindio.gri.repository.LiderGrupoRepository;
import co.edu.uniquindio.gri.repository.ProduccionRepository;

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

	// Tasa de trabajo por producciones bibliográficas que no están en custodia,
	// se iterará un 1% aleatorio de las producciones bibliográficas que no están en
	// custodia hasta el 11/06/2020 (es decir 1% de 4706 redondeado 47)
	private static final int CASOSPRODUCCIONESBIBLIGRAFICASPORITERACION = (int) (4706 * 0.01);

	// Tasa de trabajo por producciones que no están en custodia,
	// se iterará un 1% aleatorio de las producciones que no están en
	// custodia hasta el 11/06/2020 (es decir 1% de 15609 redondeado 156)
	private static final int CASOSPRODUCCIONESPORITERACION = (int) (15609 * 0.01);

	@Autowired
	private LiderGrupoRepository lideresInvestigadores;

	@Autowired
	private ProduccionRepository produccionesDisponibles;

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
	@Transactional
	@Scheduled(fixedRate = 60000)
	public void generarCasosDeSubidaYRevisionDeProduccionesDeInvestigacion() {
//
//		iniciarClienteHttp();
//		String idDelProcesoBonita;
//		try {
//			iniciarSesionEnBonita();
//			idDelProcesoBonita = obtenerIdDelProceso();
//		} catch (URISyntaxException | IOException e) {
//			log.error(e.getMessage());
//			return;
//		}
//
//		System.out.println(produccionesDisponibles.getProduccionesBSinCustodia().size());
//		System.out.println(produccionesDisponibles.getProduccionesSinCustodia().size());
//
//		List<ProduccionBGrupo> produccionBibliograficaSinCustodia = produccionesDisponibles
//				.getProduccionesBSinCustodia();
//		List<ProduccionGrupo> produccionSinCustodia = produccionesDisponibles.getProduccionesSinCustodia();
//		int cantidadDeProduccionesBibliograficasSinCustodia = produccionBibliograficaSinCustodia.size();
//		int cantidadDeProduccionesSinCustodia = produccionSinCustodia.size();
//		for (int i = 0; i < CASOSPRODUCCIONESBIBLIGRAFICASPORITERACION; i++) {
//			int posProduccionBibliograficaAleatoria = (int) (Math.random()
//					* cantidadDeProduccionesBibliograficasSinCustodia);
//			generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(
//					produccionBibliograficaSinCustodia.get(posProduccionBibliograficaAleatoria), null,
//					idDelProcesoBonita);
//		}
//		for (int i = 0; i < CASOSPRODUCCIONESPORITERACION; i++) {
//			int posProduccionAleatoria = (int) (Math.random() * cantidadDeProduccionesSinCustodia);
//			generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(null,
//					produccionSinCustodia.get(posProduccionAleatoria), idDelProcesoBonita);
//		}

//		cerrarClienteHttp();
	}

	public void generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(ProduccionBGrupo produccionBGrupo,
			ProduccionGrupo produccionGrupo, String idDelProcesoBonita) {

		// Extraer datos de la produccion
		//String nombreDeProduccion = null, nombreGrupo = null, nombreDeLiderDeGrupo = null,
		//		correoDelLiderDelGrupo = null, diDelLiderDelGrupo = null, tipoDeProduccion = null, idDeProduccion = null;

		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("idDelProcesoBonita", idDelProcesoBonita);
		
		if (produccionBGrupo != null) {

			parametros.put("idDeProduccion", produccionBGrupo.getId()+"");
			parametros.put("nombreDeProduccion", produccionBGrupo.getReferencia());
			parametros.put("tipoDeProduccion", "BIBLIOGRAFICA");
			
			Grupo grupoDeInvestigacion = produccionBGrupo.getGrupo();
			parametros.put("nombreGrupo", grupoDeInvestigacion.getNombre());
			parametros.put("nombreDeLiderDeGrupo", grupoDeInvestigacion.getLider());

			LiderGrupo liderGrupo = lideresInvestigadores.getLiderDeUnGrupo(grupoDeInvestigacion.getId());
			parametros.put("correoDelLiderDelGrupo", liderGrupo.getEmail());
			parametros.put("diDelLiderDelGrupo", liderGrupo.getDi());

		} else if (produccionGrupo != null) {

			parametros.put("idDeProduccion", produccionGrupo.getId()+"");
			parametros.put("nombreDeProduccion", produccionGrupo.getReferencia());
			parametros.put("tipoDeProduccion", "GENERAL");
			
			Grupo grupoDeInvestigacion = produccionGrupo.getGrupo();
			parametros.put("nombreGrupo", grupoDeInvestigacion.getNombre());
			parametros.put("nombreDeLiderDeGrupo", grupoDeInvestigacion.getLider());

			LiderGrupo liderGrupo = lideresInvestigadores.getLiderDeUnGrupo(grupoDeInvestigacion.getId());
			parametros.put("correoDelLiderDelGrupo", liderGrupo.getEmail());
			parametros.put("diDelLiderDelGrupo", liderGrupo.getDi());

		}

		try {
			Iterator<String> it = parametros.keySet().iterator();
			while(it.hasNext()){
			  String key = it.next();
			  if(parametros.get(key) == null) {
				  throw new IntegridadDeDatosDeIncioDeCasoInvalidaException();
			  }
			}		
			
			System.out.println("Iniciando caso, parametros: " + parametros.toString());

		} catch (IntegridadDeDatosDeIncioDeCasoInvalidaException e) {
			log.error(e.getMessage());
		}

	}

	public void iniciarCaso(String idDelProcesoBonita, int idDeProduccion, String tipoDeProduccion,
			String nombreDeProduccion, String nombreGrupo, String nombreDeLiderDeGrupo, String correoDelLiderDelGrupo,
			String diDelLiderDelGrupo) throws URISyntaxException, ClientProtocolException, IOException {

		log.info("Iniciando caso \"" + nombreDelProcesoBonita + "\" con id: " + idDelProcesoBonita);
		builder = new URIBuilder(servidorBonitaInicioCaso);
		System.out.println((new JSONObject()).put("processDefinitionId", idDelProcesoBonita).toString());
		HttpPost iniciacionDeCaso = new HttpPost(builder.build());
		StringEntity se = new StringEntity(
				(new JSONObject()).put("processDefinitionId", idDelProcesoBonita).toString());
		iniciacionDeCaso.addHeader("content-type", "application/json");
		iniciacionDeCaso.setEntity(se);

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