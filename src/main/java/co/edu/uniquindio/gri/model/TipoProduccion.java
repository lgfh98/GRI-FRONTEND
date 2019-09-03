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
 * The Class TipoProduccion.
 */
@Entity(name = "TIPOPRODUCCION")
@Table(name = "TIPOPRODUCCION", schema = "gri")
public class TipoProduccion implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID")
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE", length = 100)
	private String nombre;

	/** The tipos. */
	@OneToMany(mappedBy = "tipoProduccion", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Tipo> tipos = new ArrayList<Tipo>();

	/**
	 * Instantiates a new tipo produccion.
	 */
	public TipoProduccion() {
	}

	/**
	 * Instantiates a new tipo produccion.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 */
	public TipoProduccion(long id, String nombre) {
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
	 * Gets the tipos.
	 *
	 * @return the tipos
	 */
	public List<Tipo> getTipos() {
		return tipos;
	}

	/**
	 * Sets the tipos.
	 *
	 * @param tipos the new tipos
	 */
	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}
	

}
