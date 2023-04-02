package snow.commons.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import snow.commons.persistence.entities.Ticker;

public interface TickerRepository extends CrudRepository<Ticker, Long> {
	boolean existsTickerBytckName(String tckName);
	Ticker findTickerBytckName(String tckName);
}
