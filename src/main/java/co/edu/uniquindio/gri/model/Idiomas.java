package co.edu.uniquindio.gri.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The Class Idiomas.
 */
@Entity(name = "IDIOMAS")
@Table(name = "IDIOMAS", schema = "gri")
public class Idiomas implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@JsonIgnore
	private long id;

	/** The idioma. */
	@Column(name = "IDIOMA")
	private String idioma;

	/** The habla. */
	@Column(name = "HABLA")
	private String habla;

	/** The escribe. */
	@Column(name = "ESCRIBE")
	private String escribe;

	/** The lee. */
	@Column(name = "LEE")
	private String lee;

	/** The entiende. */
	@Column(name = "ENTIENDE")
	private String entiende;

	/** The investigador. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INVESTIGADORES_ID")
	@JsonIgnore
	private Investigador investigador;

	/**
	 * Instantiates a new idiomas.
	 *
	 * @param id the id
	 * @param idioma the idioma
	 * @param habla the habla
	 * @param escribe the escribe
	 * @param lee the lee
	 * @param entiende the entiende
	 * @param investigador the investigador
	 */
	public Idiomas(long id, String idioma, String habla, String escribe, String lee, String entiende,
			Investigador investigador) {
		this.id = id;
		this.idioma = idioma;
		this.habla = habla;
		this.escribe = escribe;
		this.lee = lee;
		this.entiende = entiende;
		this.investigador = investigador;
	}

	/**
	 * Instantiates a new idiomas.
	 */
	public Idiomas() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the idioma.
	 *
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Sets the idioma.
	 *
	 * @param idioma the new idioma
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Gets the habla.
	 *
	 * @return the habla
	 */
	public String getHabla() {
		return habla;
	}

	/**
	 * Sets the habla.
	 *
	 * @param habla the new habla
	 */
	public void setHabla(String habla) {
		this.habla = habla;
	}

	/**
	 * Gets the escribe.
	 *
	 * @return the escribe
	 */
	public String getEscribe() {
		return escribe;
	}

	/**
	 * Sets the escribe.
	 *
	 * @param escribe the new escribe
	 */
	public void setEscribe(String escribe) {
		this.escribe = escribe;
	}

	/**
	 * Gets the lee.
	 *
	 * @return the lee
	 */
	public String getLee() {
		return lee;
	}

	/**
	 * Sets the lee.
	 *
	 * @param lee the new lee
	 */
	public void setLee(String lee) {
		this.lee = lee;
	}

	/**
	 * Gets the entiende.
	 *
	 * @return the entiende
	 */
	public String getEntiende() {
		return entiende;
	}

	/**
	 * Sets the entiende.
	 *
	 * @param entiende the new entiende
	 */
	public void setEntiende(String entiende) {
		this.entiende = entiende;
	}

	/**
	 * Gets the investigador.
	 *
	 * @return the investigador
	 */
	public Investigador getInvestigador() {
		return investigador;
	}

	/**
	 * Sets the investigador.
	 *
	 * @param investigador the new investigador
	 */
	public void setInvestigador(Investigador investigador) {
		this.investigador = investigador;
	}

}
