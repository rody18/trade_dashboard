package trade.data.repositories;

import org.springframework.data.repository.CrudRepository;

import trade.data.models.Parameter;

public interface ParameterRepository extends CrudRepository<Parameter, Long>{

	public Parameter findParameterByparCode (String parCode);

}
