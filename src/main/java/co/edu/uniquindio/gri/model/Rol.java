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


/**
 * Class Rol.
 */
@Entity(name = "USER_ROLE")
@Table(name = "USER_ROLE", schema = "gri")

public class Rol implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "ID", length = 300)
	private long id;

	/** The role. */
	@Column(name = "ROLE", length = 300)	
	private String role;
	
	/** The users. */
	@OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users = new ArrayList<User>();	

	/**
	 * Instantiates a new rol.
	 */
	public Rol() {
	}

	/**
	 * Instantiates a new rol.
	 *
	 * @param id the id
	 * @param role the role
	 * @param users the users
	 */
	public Rol(long id, String role, List<User> users) {
		this.id = id;
		this.role = role;
		this.users = users;
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
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
