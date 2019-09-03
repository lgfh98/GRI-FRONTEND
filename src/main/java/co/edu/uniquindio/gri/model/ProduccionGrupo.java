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
 * The Class ProduccionGrupo.
 */
@Entity(name = "PRODUCCIONESG")
@Table(name = "PRODUCCIONESG", schema = "gri")
public class ProduccionGrupo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	/** The autores. */
	@Column(name = "AUTORES", length = 2000)
	private String autores;

	/** The anio. */
	@Column(name = "ANIO", length = 10)
	private String anio;

	/** The referencia. */
	@Column(name = "REFERENCIA", length = 4000)
	private String referencia;

	/** The repetido. */
	@Column(name = "REPETIDO", length = 20)
	private String repetido;

	@Column(name = "INVENTARIO")
	private int estado;
	
	/** The tipo. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_ID")
	@JsonIgnore
	private Tipo tipo;

	/** The grupo. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GRUPOS_ID")
	@JsonIgnore
	private Grupo grupo;

	/**
	 * Instantiates a new produccion grupo.
	 *
	 * @param id the id
	 * @param autores the autores
	 * @param anio the anio
	 * @param referencia the referencia
	 * @param tipo the tipo
	 * @param repetido the repetido
	 * @param tipoProduccion the tipo produccion
	 * @param grupo the grupo
	 * @param estado the estado
	 */
	public ProduccionGrupo(long id, String autores, String anio, String referencia, Tipo tipo, String repetido,
			TipoProduccion tipoProduccion, Grupo grupo, int estado) {
		this.id = id;
		this.autores = autores;
		this.anio = anio;
		this.referencia = referencia;
		this.tipo = tipo;
		this.grupo = grupo;
		this.estado= estado;
	}
	
	

	/**
	 * Instantiates a new produccion grupo.
	 *
	 * @param id the id
	 * @param autores the autores
	 * @param anio the anio
	 * @param referencia the referencia
	 */
	public ProduccionGrupo(long id, String autores, String anio, String referencia) {
		this.id = id;
		this.autores = autores;
		this.anio = anio;
		this.referencia = referencia;
	}



	/**
	 * Instantiates a new produccion grupo.
	 */
	public ProduccionGrupo() {
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
	 * Gets the autores.
	 *
	 * @return the autores
	 */
	public String getAutores() {
		return autores;
	}

	/**
	 * Sets the autores.
	 *
	 * @param autores the new autores
	 */
	public void setAutores(String autores) {
		this.autores = autores;
	}

	/**
	 * Gets the anio.
	 *
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * Sets the anio.
	 *
	 * @param anio the new anio
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * Gets the referencia.
	 *
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * Sets the referencia.
	 *
	 * @param referencia the new referencia
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
	 * Gets the repetido.
	 *
	 * @return the repetido
	 */
	public String getRepetido() {
		return repetido;
	}

	/**
	 * Sets the repetido.
	 *
	 * @param repetido the new repetido
	 */
	public void setRepetido(String repetido) {
		this.repetido = repetido;
	}

	/**
	 * Gets the grupo.
	 *
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * Sets the grupo.
	 *
	 * @param grupo the new grupo
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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
		ProduccionGrupo other = (ProduccionGrupo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
