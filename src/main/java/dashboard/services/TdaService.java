package dashboard.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.client.TdaClient;
import com.studerw.tda.model.account.Position;
import com.studerw.tda.model.account.SecuritiesAccount;
import com.studerw.tda.model.account.Session;
import com.studerw.tda.model.account.Duration;
import com.studerw.tda.model.account.EquityInstrument;
import com.studerw.tda.model.account.Instrument;
import com.studerw.tda.model.account.Order;
import com.studerw.tda.model.account.OrderLegCollection;
import com.studerw.tda.model.account.OrderLegCollection.Instruction;

import dashboard.data.models.Parameter;
import dashboard.data.models.Stop;
import dashboard.data.models.Ticker;
import dashboard.data.repositories.*;

import com.studerw.tda.model.account.OrderStrategyType;
import com.studerw.tda.model.account.OrderType;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class TdaService {
	
	private final TdaClient client;
	private final TickerRepository tickerRepo;
	private final StopRepository stopRepo;
	private final ParameterRepository parameterRepo;
	@Value("${tdaAccount}")
	private String tdaAccount;

	public TdaService(TickerRepository tickerRepo, StopRepository stopRepo, ParameterRepository parameterRepo, @Value("${tdaClient}") String tdaClient) {
		this.tickerRepo = tickerRepo;
		this.stopRepo = stopRepo;
		this.parameterRepo = parameterRepo;

		Parameter parTdaToken = this.parameterRepo.findParameterByparCode("TDA_REFRESH_TOKEN");  
		
		log.info("tdaClient: " + tdaClient + " - parTdaToken: " + parTdaToken.getPar_string());
		
		Properties props = new Properties();
		props.setProperty("tda.client_id", tdaClient);
		props.setProperty("tda.token.refresh", parTdaToken.getPar_string());

		this.client = new HttpTdaClient(props);
	}
	
	public void showPriceTest() {

		List<SecuritiesAccount> accs = client.getAccounts(true, true);
		
		for (SecuritiesAccount acc : accs) {
			for (Position pos : acc.getPositions()) {
				Instrument inst = pos.getInstrument();
				if (!tickerRepo.existsTickerBytckName(inst.getSymbol())) {
					Ticker tck = Ticker.builder().tckName(inst.getSymbol()).tck_description(inst.getDescription())
						       .tck_type(inst.getAssetType().name()).build();
					tickerRepo.save(tck);
				}
			}
		}
	}
	
	public void syncStops() {
		List<SecuritiesAccount> accs = client.getAccounts(true, true);
		accs.stream().map(acc -> acc.getPositions())
		             .forEach(positions -> { positions.stream().forEach(pos -> {syncStop(pos);});});
	}

	private void syncStop(Position pos) {
		Instrument inst = pos.getInstrument();
		Ticker tck = tickerRepo.findTickerBytckName(inst.getSymbol());
		if (!stopRepo.existsStopBystpTckId(tck.getTck_id())) {
			double tmp = Math.round(pos.getAveragePrice().doubleValue() * 0.9 *100);
			double limit = tmp/100;
			Stop stp = Stop.builder().stp_limit(limit).stp_price(0.0).stp_qty(pos.getLongQuantity().doubleValue())
					                 .stp_status("SYNC").stp_type("MARKET").stpTckId(tck.getTck_id()).stp_exec_type("MANUAL").build();
		    stopRepo.save(stp);
		}
	}
	
	public void execStop(Stop stop) {

		  Order order = new Order(); 
		  order.setOrderType(OrderType.MARKET);
		  order.setSession(Session.NORMAL); 
		  order.setDuration(Duration.DAY);
		  order.setOrderStrategyType(OrderStrategyType.SINGLE);
		  
		  OrderLegCollection olc = new OrderLegCollection();
		  olc.setInstruction(Instruction.SELL); 
		  olc.setQuantity(new BigDecimal(stop.getStp_qty()));
		  order.getOrderLegCollection().add(olc);
		  
		  Instrument instrument = new EquityInstrument(); 
		  instrument.setSymbol(stop.getTicker().getTckName());
		  olc.setInstrument(instrument);
		  
		  log.info("Ejecutando Stop - Tipo: " + order.getOrderType() + " - Ticker: " + instrument.getSymbol() + " - Cant.: " + olc.getQuantity());
		  
		  client.placeOrder(tdaAccount, order);
		  stop.setStp_status("EJECUTADA");
		  stopRepo.save(stop);
	}

}
