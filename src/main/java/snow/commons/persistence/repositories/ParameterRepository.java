package snow.commons.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import snow.commons.persistence.entities.Parameter;

public interface ParameterRepository extends CrudRepository<Parameter, Long>{
	Parameter findParameterByparCode (String parCode);

}
