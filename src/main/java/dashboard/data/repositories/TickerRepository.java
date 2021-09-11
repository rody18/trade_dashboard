package dashboard.data.repositories;

import org.springframework.data.repository.CrudRepository;

import dashboard.data.models.Ticker;

public interface TickerRepository extends CrudRepository<Ticker, Long> {

	public boolean existsTickerBytckName(String tckName);
	public Ticker findTickerBytckName(String tckName);
}
