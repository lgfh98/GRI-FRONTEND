package co.edu.uniquindio.gri.schedulingtasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.gri.bonitaapi.BonitaConnectorAPI;
import co.edu.uniquindio.gri.bonitaapi.GestorDeCasosBonita;
import co.edu.uniquindio.gri.dao.ProduccionDAO;
import co.edu.uniquindio.gri.model.ProduccionBGrupo;
import co.edu.uniquindio.gri.model.ProduccionGrupo;

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

	// Variables de entorno declaradas localmente (application.properties)

	@Value("${bonita.nombre.proceso}")
	private String nombreDelProcesoBonita;
	@Value("${bonita.usuario}")
	private String usuario;
	@Value("${bonita.password}")
	private String password;
	@Value("${bonita.servidor.base}")
	private String servidor;
	@Value("${gri.recolecciondeproducciones.automatica}")
	private boolean tareaDeGeneracionDeCasos;

	@Autowired
	private ProduccionDAO produccionDAO;

	@Autowired
	private GestorDeCasosBonita gestorDeCasosBonita;

	/*
	 * Tasa de trabajo por producciones bibliográficas que no están en custodia, se
	 * iterará un 1% aleatorio de las producciones bibliográficas que no están en
	 * custodia hasta el 11/06/2020 (es decir 1% de 4706 redondeado 47)
	 */
	@Value("${gri.recolecciondeproducciones.tasa.bibliograficas}")
	private int casosProduccionesBibliograficasPorIteracion = 2;

	/*
	 * Tasa de trabajo por producciones que no están en custodia, se iterará un 1%
	 * aleatorio de las producciones que no están en custodia hasta el 11/06/2020
	 * (es decir 1% de 15609 redondeado 156)
	 */
	@Value("${gri.recolecciondeproducciones.tasa.bibliograficas}")
	private int casosProduccionesGenericasPorIteracion = 2;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	/**
	 * Tarea encargada de generar los casos bpm de revisión y subida de producciones
	 * de investigación en el servidor bonita de la Universidad del Quindío en los
	 * cuales están involucrados todos los lideres de grupos para los cuales el GRI
	 * reporta que tienen producciones que aún no están en custodia. Esta tarea se
	 * ejecuta de acuedo a la unidad definida en el FixedDelay (milisegundos)
	 */
	@Transactional
	@Scheduled(fixedDelay = 86400000, initialDelay = 0)
	public void generarCasosDeSubidaYRevisionDeProduccionesDeInvestigacion() {

		if (tareaDeGeneracionDeCasos) {
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
			log.info("Iniciando tarea de generación de procesos de recolección de producciones de investigación");
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
			BonitaConnectorAPI bonita;
			if (cantidadDeProduccionesBibliograficasSinCustodia + cantidadDeProduccionesSinCustodia > 0) {
				bonita = new BonitaConnectorAPI(servidor);

				try {
					bonita.iniciarSesionEnBonita(usuario, password);
					;
					idDelProcesoBonita = bonita.obtenerIdDelProceso(nombreDelProcesoBonita);
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
				for (int i = 0; i < casosProduccionesBibliograficasPorIteracion; i++) {
					// Selección de una producción bibliográfica aleatoria para la generación de un
					// caso
					int posProduccionBibliograficaAleatoria = (int) (Math.random()
							* cantidadDeProduccionesBibliograficasSinCustodia);
					gestorDeCasosBonita.generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(bonita,
							produccionBibliograficaSinCustodia.get(posProduccionBibliograficaAleatoria), null,
							idDelProcesoBonita, nombreDelProcesoBonita);
				}
			}
			if (cantidadDeProduccionesSinCustodia > 0) {
				for (int i = 0; i < casosProduccionesGenericasPorIteracion; i++) {
					// Selección de una producción aleatoria para la generación de un caso
					int posProduccionAleatoria = (int) (Math.random() * cantidadDeProduccionesSinCustodia);
					gestorDeCasosBonita.generarCasoDeSubidaYRevisionDeProduccionesDeInvestigacion(bonita, null,
							produccionSinCustodia.get(posProduccionAleatoria), idDelProcesoBonita,
							nombreDelProcesoBonita);
				}
			}

			try {
				bonita.cerrarClienteHttp();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		} else {
			log.warn(
					"La generación de casos de recolección de evidencias está desactivada, por favor ajuste los parámetros en el archivo .properties para activarla");
		}

	}

}