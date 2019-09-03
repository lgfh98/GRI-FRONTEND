package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Tipo.
 */
@Entity(name = "TIPOS")
@Table(name = "TIPOS", schema = "gri")
public class Tipo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID")	
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE", length = 100)	
	private String nombre;

	/** The produccion. */
	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Produccion> produccion = new ArrayList<Produccion>();

	/** The produccion bibliografica. */
	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ProduccionB> produccionBibliografica = new ArrayList<ProduccionB>();

	/** The produccion G. */
	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ProduccionGrupo> produccionG = new ArrayList<ProduccionGrupo>();

	/** The produccion bibliografica G. */
	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ProduccionBGrupo> produccionBibliograficaG = new ArrayList<ProduccionBGrupo>();

	
	/** The tipo produccion. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPOPRODUCCION_ID")	
	private TipoProduccion tipoProduccion;
	
	/**
	 * Instantiates a new tipo.
	 */
	public Tipo() {
	}

	/**
	 * Instantiates a new tipo.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 */
	public Tipo(long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
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
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the produccion.
	 *
	 * @return the produccion
	 */
	public List<Produccion> getProduccion() {
		return produccion;
	}

	/**
	 * Sets the produccion.
	 *
	 * @param produccion the new produccion
	 */
	public void setProduccion(List<Produccion> produccion) {
		this.produccion = produccion;
	}

	/**
	 * Gets the produccion G.
	 *
	 * @return the produccion G
	 */
	public List<ProduccionGrupo> getProduccionG() {
		return produccionG;
	}

	/**
	 * Sets the produccion G.
	 *
	 * @param produccionG the new produccion G
	 */
	public void setProduccionG(List<ProduccionGrupo> produccionG) {
		this.produccionG = produccionG;
	}

	/**
	 * Gets the produccion bibliografica.
	 *
	 * @return the produccion bibliografica
	 */
	public List<ProduccionB> getProduccionBibliografica() {
		return produccionBibliografica;
	}

	/**
	 * Sets the produccion bibliografica.
	 *
	 * @param produccionBibliografica the new produccion bibliografica
	 */
	public void setProduccionBibliografica(List<ProduccionB> produccionBibliografica) {
		this.produccionBibliografica = produccionBibliografica;
	}

	/**
	 * Gets the produccion bibliografica G.
	 *
	 * @return the produccion bibliografica G
	 */
	public List<ProduccionBGrupo> getProduccionBibliograficaG() {
		return produccionBibliograficaG;
	}

	/**
	 * Sets the produccion bibliografica G.
	 *
	 * @param produccionBibliograficaG the new produccion bibliografica G
	 */
	public void setProduccionBibliograficaG(
			List<ProduccionBGrupo> produccionBibliograficaG) {
		this.produccionBibliograficaG = produccionBibliograficaG;
	}

	/**
	 * Gets the tipo produccion.
	 *
	 * @return the tipo produccion
	 */
	public TipoProduccion getTipoProduccion() {
		return tipoProduccion;
	}

	/**
	 * Sets the tipo produccion.
	 *
	 * @param tipoProduccion the new tipo produccion
	 */
	public void setTipoProduccion(TipoProduccion tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}
	
	

}
