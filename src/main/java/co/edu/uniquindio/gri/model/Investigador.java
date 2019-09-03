package co.edu.uniquindio.gri.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Investigador.
 */
@Entity(name = "INVESTIGADORES")
@Table(name = "INVESTIGADORES", schema = "gri")
public class Investigador implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID")
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE", length = 200)
	private String nombre;

	/** The categoria. */
	@Column(name = "CATEGORIA", length = 200)
	private String categoria;

	/** The nivel academico. */
	@Column(name = "NIVELACADEMICO", length = 200)
	private String nivelAcademico;

	/** The pertenencia. */
	@Column(name = "PERTENENCIA", length = 50)
	private String pertenencia;

	/** The idiomas. */
	@OneToMany( mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Idiomas> idiomas = new ArrayList<Idiomas>();

	/** The lineas investigacion. */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "INVEST_LINEAS", joinColumns = { @JoinColumn(name = "INVESTIGADORES_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "LINEASINVESTIGACION_ID") }, schema = "gri")
	private List<LineasInvestigacion> lineasInvestigacion = new ArrayList<LineasInvestigacion>();

	/** The producciones. */
	@OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Produccion> producciones = new ArrayList<Produccion>();

	/** The producciones bibliograficas. */
	@OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ProduccionB> produccionesBibliograficas = new ArrayList<ProduccionB>();

	/** The grupos. */
	@OneToMany(mappedBy = "investigadores", cascade = CascadeType.ALL)
	private List<GruposInves> grupos = new ArrayList<GruposInves>();

	/**
	 * Instantiates a new investigador.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param categoria the categoria
	 * @param nivelAcademico the nivel academico
	 * @param idiomas the idiomas
	 * @param lineasInvestigacion the lineas investigacion
	 * @param producciones the producciones
	 * @param produccionesBibliograficas the producciones bibliograficas
	 */
	public Investigador(long id, String nombre, String categoria, String nivelAcademico, List<Idiomas> idiomas,
			List<LineasInvestigacion> lineasInvestigacion, List<Produccion> producciones,
			List<ProduccionB> produccionesBibliograficas) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.nivelAcademico = nivelAcademico;
		this.idiomas = idiomas;
		this.lineasInvestigacion = lineasInvestigacion;
		this.produccionesBibliograficas = produccionesBibliograficas;

	}
	
	

	



	/**
	 * Instantiates a new investigador.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param categoria the categoria
	 * @param nivelAcademico the nivel academico
	 * @param pertenencia the pertenencia
	 */
	public Investigador(long id, String nombre, String categoria, String nivelAcademico, String pertenencia) {
		this.id = id;
		this.nombre = nombre;
		this.categoria = categoria;
		this.nivelAcademico = nivelAcademico;
		this.pertenencia = pertenencia;
	}







	/**
	 * Instantiates a new investigador.
	 */
	public Investigador() {
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
	 * Gets the categoria.
	 *
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Sets the categoria.
	 *
	 * @param categoria the new categoria
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * Gets the nivel academico.
	 *
	 * @return the nivel academico
	 */
	public String getNivelAcademico() {
		return nivelAcademico;
	}

	/**
	 * Sets the nivel academico.
	 *
	 * @param nivelAcademico the new nivel academico
	 */
	public void setNivelAcademico(String nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	/**
	 * Gets the idiomas.
	 *
	 * @return the idiomas
	 */
	public List<Idiomas> getIdiomas() {
		return idiomas;
	}

	/**
	 * Sets the idiomas.
	 *
	 * @param idiomas the new idiomas
	 */
	public void setIdiomas(List<Idiomas> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * Gets the pertenencia.
	 *
	 * @return the pertenencia
	 */
	public String getPertenencia() {
		return pertenencia;
	}

	/**
	 * Sets the pertenencia.
	 *
	 * @param pertenencia the new pertenencia
	 */
	public void setPertenencia(String pertenencia) {
		this.pertenencia = pertenencia;
	}

	/**
	 * Gets the producciones.
	 *
	 * @return the producciones
	 */
	public List<Produccion> getProducciones() {
		return producciones;
	}

	/**
	 * Sets the producciones.
	 *
	 * @param producciones the new producciones
	 */
	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	/**
	 * Gets the producciones bibliograficas.
	 *
	 * @return the producciones bibliograficas
	 */
	public List<ProduccionB> getProduccionesBibliograficas() {
		return produccionesBibliograficas;
	}

	/**
	 * Sets the producciones bibliograficas.
	 *
	 * @param produccionesBibliograficas the new producciones bibliograficas
	 */
	public void setProduccionesBibliograficas(List<ProduccionB> produccionesBibliograficas) {
		this.produccionesBibliograficas = produccionesBibliograficas;
	}

	/**
	 * Gets the lineas investigacion.
	 *
	 * @return the lineas investigacion
	 */
	public List<LineasInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	/**
	 * Sets the lineas investigacion.
	 *
	 * @param lineasInvestigacion the new lineas investigacion
	 */
	public void setLineasInvestigacion(List<LineasInvestigacion> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}	

	/**
	 * Gets the grupos.
	 *
	 * @return the grupos
	 */
	public List<GruposInves> getGrupos() {
		return grupos;
	}


	/**
	 * Sets the grupos.
	 *
	 * @param grupos the new grupos
	 */
	public void setGrupos(List<GruposInves> grupos) {
		this.grupos = grupos;
	}


	/**
	 * Removes the lineas investigacion.
	 *
	 * @param lineas the lineas
	 */
	public void removeLineasInvestigacion(LineasInvestigacion lineas) {
		lineasInvestigacion.remove(lineas);
		lineas.getInvestigadores().remove(this);
	}







	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Investigador other = (Investigador) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
