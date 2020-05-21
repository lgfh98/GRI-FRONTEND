package co.edu.uniquindio.gri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.gri.dao.UserDAO;
import co.edu.uniquindio.gri.model.User;
import co.edu.uniquindio.gri.service.api.UserServiceApi;

@RestController
@RequestMapping("/rest/service")
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	
	@GetMapping("/usuarios/secure/pass=58952@63hjk")
	public List<User> getAllUsers(){
		return userDAO.getAllUsers();
	}
	
	

}
