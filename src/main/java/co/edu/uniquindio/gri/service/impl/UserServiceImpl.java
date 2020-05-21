package co.edu.uniquindio.gri.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.gri.CrudRepository.UserCrudRepository;
import co.edu.uniquindio.gri.model.User;
import co.edu.uniquindio.gri.service.api.UserServiceApi;
import co.edu.uniquindio.gri.utilities.GenericServiceImpl;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserServiceApi{
	
	@Autowired
	private UserCrudRepository userCrudRepository; 
	
	@Override
	public CrudRepository<User, Long> getCrudRepository() {
	       return userCrudRepository;
	}

}
