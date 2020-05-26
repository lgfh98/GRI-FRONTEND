package co.edu.uniquindio.gri.CrudRepository;

import org.springframework.data.repository.CrudRepository;

import co.edu.uniquindio.gri.model.User;

/**
 * Clase que se comportara como repository para el crud de Users
 * @author jefferson arenas castaño
 *
 */
public interface UserCrudRepository extends CrudRepository<User, Long> {
	
	

}
