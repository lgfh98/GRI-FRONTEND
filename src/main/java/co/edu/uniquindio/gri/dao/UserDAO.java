package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.model.User;
import co.edu.uniquindio.gri.repository.UserRepository;

/**
 * Class UserDAO.
 */
@Service
public class UserDAO {

	/** Repository para user. */
	@Autowired
	UserRepository userRepository;
	
	
	/**
	 * Encuentra y retorna un usuario.
	 *
	 * @param username el username
	 * @return el usuario encontrado
	 */
	public User findOne(String username) {
		return userRepository.findOne(username);
	}
	
	/**
	 * Obtiene todos los usuarios
	 * @return la lista con todos los usuarios
	 */
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
}
