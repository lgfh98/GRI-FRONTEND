package co.edu.uniquindio.gri.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PERTENENCIA_INVES")
@Table(name = "PERTENENCIA_INVES", schema = "gri")
public class Pertenencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INVESTIGADORES_ID", length = 300)
	private long investigador_id;

	@Column(name = "PERTENENCIA", length = 100)
	private String pertenencia;

	public Pertenencia(long investigador_id, String pertenencia) {

		this.investigador_id = investigador_id;
		this.pertenencia = pertenencia;

	}

	public Pertenencia() {

	}

	public long getInvestigador_id() {
		return investigador_id;
	}

	public void setInvestigador_id(long investigador_id) {
		this.investigador_id = investigador_id;
	}

	public String getPertenencia() {
		return pertenencia;
	}

	public void setPertenencia(String pertenencia) {
		this.pertenencia = pertenencia;
	}

}
