package co.edu.uniquindio.gri.utilities;

import java.io.Serializable;
import java.util.List;


public interface GenericServiceApi<T, ID extends Serializable> {
	
	T save(T entity);
	
	void delete(ID id);
	
	T get(ID id);
	
	List<T> getAll();
}
