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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


// TODO: Auto-generated Javadoc
/**
 * The Class Programa.
 */
@Entity(name = "PROGRAMAS")
@Table(name = "PROGRAMAS", schema = "gri")
public class Programa implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID")
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE")
	private String nombre;
	
	/** The facultad. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FACULTADES_ID")
	private Facultad facultad;

	/** The grupos. */
	@ManyToMany(mappedBy = "programas", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Grupo> grupos = new ArrayList<Grupo>();

	/**
	 * Instantiates a new programa.
	 */
	public Programa() {
	}

	/**
	 * Instantiates a new programa.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param facultad the facultad
	 */
	public Programa(long id, String nombre, Facultad facultad) {
		this.id = id;
		this.nombre = nombre;
		this.facultad = facultad;
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
	 * Gets the facultad.
	 *
	 * @return the facultad
	 */
	public Facultad getFacultad() {
		return facultad;
	}

	/**
	 * Sets the facultad.
	 *
	 * @param facultad the new facultad
	 */
	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
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

}