package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Facultad.
 */
@Entity(name = "FACULTADES")
@Table(name = "FACULTADES", schema = "gri")


public class Facultad implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID")
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE")
	private String nombre;

	/** The centros. */
	@OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Centro> centros = new ArrayList<>();

	/** The programas. */
	@OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Programa> programas = new ArrayList<>();

	/**
	 * Instantiates a new facultad.
	 */
	public Facultad() {
	}

	/**
	 * Instantiates a new facultad.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 */
	public Facultad(long id, String nombre) {
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
	 * Gets the centros.
	 *
	 * @return the centros
	 */
	public List<Centro> getCentros() {
		return centros;
	}

	/**
	 * Sets the centros.
	 *
	 * @param centros the new centros
	 */
	public void setCentros(List<Centro> centros) {
		this.centros = centros;
	}

	/**
	 * Gets the programa.
	 *
	 * @return the programa
	 */
	@JsonIgnore
	public List<Programa> getPrograma() {
		return programas;
	}

	/**
	 * Sets the programa.
	 *
	 * @param programa the new programa
	 */
	public void setPrograma(List<Programa> programa) {
		this.programas = programa;
	}

}
