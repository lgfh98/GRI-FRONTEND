package co.edu.uniquindio.gri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.gri.model.User;

/**
 * Interface UserRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Encuentra y retorna un usuario.
	 *
	 * @param username el username
	 * @return el usuario encontrado
	 */
	@Query("FROM co.edu.uniquindio.gri.model.User WHERE username = :username")
	User findOne(@Param("username") String username);
}
