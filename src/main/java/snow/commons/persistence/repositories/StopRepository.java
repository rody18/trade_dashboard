package snow.commons.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import snow.commons.persistence.entities.Stop;

public interface StopRepository extends CrudRepository<Stop, Long>{
	boolean existsStopBystpTckId (long stpTckId);
}
