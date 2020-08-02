package co.edu.uniquindio.gri.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.CrudRepository.UserCrudRepository;
import co.edu.uniquindio.gri.model.User;
import co.edu.uniquindio.gri.repository.UserRepository;
import co.edu.uniquindio.gri.service.api.UserServiceApi;
import co.edu.uniquindio.gri.utilities.GenericServiceImpl;

/**
 * Class UserDAO.
 */
@Service
public class UserDAO extends GenericServiceImpl<User, Long> implements UserServiceApi{

	/** Repository para user. */
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserCrudRepository userCrudRepository; 

	
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
	
	/**
	 * Metodo que permite obtener el ultimo usuario registrado
	 * @return
	 */
	public User findLastRegisterUser() {
		
		return userRepository.findLastRegister();
	}
	
	@Override
	public CrudRepository<User, Long> getCrudRepository() {
		 return userCrudRepository;
	}
}
