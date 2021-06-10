package trade.data.repositories;

import org.springframework.data.repository.CrudRepository;

import trade.data.models.Stop;

public interface StopRepository extends CrudRepository<Stop, Long>{

	public boolean existsStopBystpTckId (long stpTckId);
}
