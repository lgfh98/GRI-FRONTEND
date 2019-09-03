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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Grupo.
 */
@Entity(name = "GRUPOS")
@Table(name = "GRUPOS", schema = "gri")
public class Grupo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID", length = 300)
	private long id;

	/** The nombre. */
	@Column(name = "NOMBRE", length = 300)	
	private String nombre;

	/** The area conocimiento. */
	@Column(name = "AREACONOCIMIENTO", length = 300)
	private String areaConocimiento;

	/** The anio fundacion. */
	@Column(name = "ANIOFUNDACION", length = 300)
	private String anioFundacion;

	/** The lider. */
	@Column(name = "LIDER", length = 300)
	private String lider;

	/** The categoria. */
	@Column(name = "CATEGORIA", length = 300)
	private String categoria;

	/** The lineas investigacion. */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "GRUPOS_LINEAS", joinColumns = { @JoinColumn(name = "GRUPOS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "LINEASINVESTIGACION_ID") }, schema = "gri")
	@JsonIgnore
	private List<LineasInvestigacion> lineasInvestigacion = new ArrayList<LineasInvestigacion>();

	/** The produccion. */
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ProduccionGrupo> produccion = new ArrayList<ProduccionGrupo>();

	/** The produccion bibliografica. */
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ProduccionBGrupo> produccionBibliografica = new ArrayList<ProduccionBGrupo>();

	/** The centro. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CENTROS_ID") 
	private Centro centro;

	/** The programas. */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PROGRAMAS_GRUPOS", joinColumns = { @JoinColumn(name = "GRUPOS_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PROGRAMAS_ID") }, schema = "gri")
	private List<Programa> programas = new ArrayList<Programa>();

	/** The investigadores. */
	@OneToMany(mappedBy = "grupos", cascade = CascadeType.MERGE)
	@JsonIgnore
	private List<GruposInves> investigadores = new ArrayList<GruposInves>();

	/**
	 * Instantiates a new grupo.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param areaConocimiento the area conocimiento
	 * @param anioFundacion the anio fundacion
	 * @param lider the lider
	 * @param categoria the categoria
	 * @param lineasInvestigacion the lineas investigacion
	 * @param produccion the produccion
	 * @param produccionBibliografica the produccion bibliografica
	 * @param centro the centro
	 */
	public Grupo(long id, String nombre, String areaConocimiento, String anioFundacion, String lider, String categoria,
			List<LineasInvestigacion> lineasInvestigacion, List<ProduccionGrupo> produccion,
			List<ProduccionBGrupo> produccionBibliografica, Centro centro, List<GruposInves> investigadores) {
		this.id = id;
		this.nombre = nombre;
		this.areaConocimiento = areaConocimiento;
		this.anioFundacion = anioFundacion;
		this.lider = lider;
		this.categoria = categoria;
		this.lineasInvestigacion = lineasInvestigacion;
		this.produccion = produccion;
		this.produccionBibliografica = produccionBibliografica;
		this.centro = centro;
		this.investigadores = investigadores;
	}
	
	

	/**
	 * Instantiates a new grupo.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param categoria the categoria
	 * @param lider the lider
	 * 
	 */
	public Grupo(long id, String nombre, String categoria, String lider) {
		this.id = id;
		this.nombre = nombre;
		this.lider = lider;
		this.categoria = categoria;
	}



	/**
	 * Instantiates a new grupo.
	 */
	public Grupo() {

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
	 * Gets the area conocimiento.
	 *
	 * @return the area conocimiento
	 */
	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	/**
	 * Sets the area conocimiento.
	 *
	 * @param areaConocimiento the new area conocimiento
	 */
	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	/**
	 * Gets the anio fundacion.
	 *
	 * @return the anio fundacion
	 */
	public String getAnioFundacion() {
		return anioFundacion;
	}

	/**
	 * Sets the anio fundacion.
	 *
	 * @param anioFundacion the new anio fundacion
	 */
	public void setAnioFundacion(String anioFundacion) {
		this.anioFundacion = anioFundacion;
	}

	/**
	 * Gets the lider.
	 *
	 * @return the lider
	 */
	public String getLider() {
		return lider;
	}

	/**
	 * Sets the lider.
	 *
	 * @param lider the new lider
	 */
	public void setLider(String lider) {
		this.lider = lider;
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
	 * Gets the produccion.
	 *
	 * @return the produccion
	 */
	public List<ProduccionGrupo> getProduccion() {
		return produccion;
	}

	/**
	 * Sets the produccion.
	 *
	 * @param produccion the new produccion
	 */
	public void setProduccion(List<ProduccionGrupo> produccion) {
		this.produccion = produccion;
	}

	/**
	 * Gets the produccion bibliografica.
	 *
	 * @return the produccion bibliografica
	 */
	public List<ProduccionBGrupo> getProduccionBibliografica() {
		return produccionBibliografica;
	}

	/**
	 * Sets the produccion bibliografica.
	 *
	 * @param produccionBibliografica the new produccion bibliografica
	 */
	public void setProduccionBibliografica(List<ProduccionBGrupo> produccionBibliografica) {
		this.produccionBibliografica = produccionBibliografica;
	}

	/**
	 * Gets the centro.
	 *
	 * @return the centro
	 */
	public Centro getCentro() {
		return centro;
	}

	/**
	 * Sets the centro.
	 *
	 * @param centro the new centro
	 */
	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	/**
	 * Gets the programas.
	 *
	 * @return the programas
	 */
	public List<Programa> getProgramas() {
		return programas;
	}

	/**
	 * Sets the programas.
	 *
	 * @param programas the new programas
	 */
	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}	

	/**
	 * Gets the investigadores.
	 *
	 * @return the investigadores
	 */
	@JsonIgnore
	public List<GruposInves> getInvestigadores() {
		return investigadores;
	}

	/**
	 * Sets the investigadores.
	 *
	 * @param investigadores the new investigadores
	 */
	public void setInvestigadores(List<GruposInves> investigadores) {
		this.investigadores = investigadores;
	}

	/**
	 * Removes the lineas investigacion.
	 *
	 * @param lineas the lineas
	 */
	public void removeLineasInvestigacion(LineasInvestigacion lineas) {
		lineasInvestigacion.remove(lineas);
		lineas.getGrupos().remove(this);
	}

	/**
	 * Removes the investigador.
	 *
	 * @param investigador the investigador
	 */
	public void removeInvestigador(Investigador investigador) {
		investigadores.remove(investigador);
		investigador.getGrupos().remove(this);
	}

	/**
	 * Removes the programa.
	 *
	 * @param programa the programa
	 */
	public void removePrograma(Programa programa) {
		programas.remove(programa);
		programa.getGrupos().remove(this);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}