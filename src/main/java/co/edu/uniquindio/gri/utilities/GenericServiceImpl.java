package co.edu.uniquindio.gri.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericServiceApi<T, ID> {

	@Override
	public T save(T entity) {
		return getCrudRepository().save(entity);
	}

	@Override
	public void delete(ID id) {
		getCrudRepository().deleteById(id);
	}

	@Override
	public T get(ID id) {
		
		Optional<T> obj = getCrudRepository().findById(id);
		
		if( obj.isPresent() ) {
			return obj.get();
		   }
		
		return null;
		
	}

	@Override
	public List<T> getAll() {
		List <T> returnList = new ArrayList<>(); 
		getCrudRepository().findAll().forEach(obj -> returnList.add(obj));
		return returnList;
	}
	
	public abstract CrudRepository<T, ID> getCrudRepository();

}
