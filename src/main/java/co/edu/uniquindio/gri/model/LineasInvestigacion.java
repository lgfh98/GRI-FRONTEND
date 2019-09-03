package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class LineasInvestigacion.
 */
@Entity(name = "LINEASINVESTIGACION")
@Table(name = "LINEASINVESTIGACION", schema = "gri")
public class LineasInvestigacion implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE", length = 400)
	private String nombre;

	/** The investigadores. */
	@ManyToMany(mappedBy = "lineasInvestigacion", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Investigador> investigadores = new ArrayList<Investigador>();

	/** The grupos. */
	@ManyToMany(mappedBy = "lineasInvestigacion", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Grupo> grupos = new ArrayList<Grupo>();

	/**
	 * Instantiates a new lineas investigacion.
	 */
	public LineasInvestigacion() {
	}

	/**
	 * Instantiates a new lineas investigacion.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 */
	public LineasInvestigacion(long id, String nombre) {
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
	 * Gets the grupos.
	 *
	 * @return the grupos
	 */
	public List<Grupo> getGrupos() {
		return grupos;
	}

	/**
	 * Sets the grupos.
	 *
	 * @param grupos the new grupos
	 */
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	/**
	 * Gets the investigadores.
	 *
	 * @return the investigadores
	 */
	public List<Investigador> getInvestigadores() {
		return investigadores;
	}

	/**
	 * Sets the investigadores.
	 *
	 * @param investigadores the new investigadores
	 */
	public void setInvestigadores(List<Investigador> investigadores) {
		this.investigadores = investigadores;
	}

}