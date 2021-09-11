package dashboard.data.repositories;

import org.springframework.data.repository.CrudRepository;

import dashboard.data.models.Parameter;

public interface ParameterRepository extends CrudRepository<Parameter, Long>{

	public Parameter findParameterByparCode (String parCode);

}
