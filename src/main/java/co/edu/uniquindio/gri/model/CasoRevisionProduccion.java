package co.edu.uniquindio.gri.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CASOSREVISIONPRODUCCION")
@Table(name = "CASOSREVISIONPRODUCCION", schema = "gri")
public class CasoRevisionProduccion implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name =  "idproduccion")
	private long idProduccion;
	
	@Column(name = "tipoproduccion")
	private String tipoProduccion;
	
	@Column(name = "estado")
	private String estado;
	
	public CasoRevisionProduccion() {
	}
	
	public CasoRevisionProduccion(long id, long idProduccion, String tipoProduccion, String estado) {
		super();
		this.id = id;
		this.idProduccion = idProduccion;
		this.tipoProduccion = tipoProduccion;
		this.estado = estado;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdProduccion() {
		return idProduccion;
	}

	public void setIdProduccion(long idProduccion) {
		this.idProduccion = idProduccion;
	}

	public String getTipoProduccion() {
		return tipoProduccion;
	}

	public void setTipoProduccion(String tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (idProduccion ^ (idProduccion >>> 32));
		result = prime * result + ((tipoProduccion == null) ? 0 : tipoProduccion.hashCode());
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
		CasoRevisionProduccion other = (CasoRevisionProduccion) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id != other.id)
			return false;
		if (idProduccion != other.idProduccion)
			return false;
		if (tipoProduccion == null) {
			if (other.tipoProduccion != null)
				return false;
		} else if (!tipoProduccion.equals(other.tipoProduccion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CasoRevisionProduccion [id=" + id + ", idProduccion=" + idProduccion + ", tipoProduccion="
				+ tipoProduccion + ", estado=" + estado + "]";
	}
	
	
	
}
