package co.edu.uniquindio.gri.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "LIDERESGRUPOS")
@Table(name = "LIDERESGRUPOS", schema = "gri")
public class LiderGrupo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@JoinColumn(name = "grupo_id", unique = true)
	@OneToOne(cascade = CascadeType.ALL)
	private Grupo grupo;
	private String email;
	private String di;

	public LiderGrupo() {
	}
	
	public LiderGrupo(int id, Grupo grupo, String email, String di) {
		super();
		this.id = id;
		this.grupo = grupo;
		this.email = email;
		this.di = di;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDi() {
		return di;
	}

	public void setDi(String di) {
		this.di = di;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((di == null) ? 0 : di.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
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
		LiderGrupo other = (LiderGrupo) obj;
		if (di == null) {
			if (other.di != null)
				return false;
		} else if (!di.equals(other.di))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LiderGrupo [id=" + id + ", grupo=" + grupo + ", email=" + email + ", di=" + di + "]";
	}

}
