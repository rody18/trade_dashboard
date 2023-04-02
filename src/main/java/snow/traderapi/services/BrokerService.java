package snow.traderapi.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snow.commons.persistence.entities.Stop;
import snow.traderapi.models.broker.OhlcBrk;
import snow.traderapi.models.broker.Position;
import snow.traderapi.services.command.BrokerCommand;

@Slf4j
@Service
public class BrokerService {

    private final Map<String, BrokerCommand> brokers;

    @Autowired
    public BrokerService(List<BrokerCommand> brokerList) {
        log.info("BrokerService --> Autowired --> IolService & AmtdService");
        brokers = brokerList.stream()
                            .collect(Collectors.toMap(BrokerCommand::getName, Function.identity()));
    }

    public Position[] getPositions() {
        return brokers.get("IOL").getPositions();
//		List<traderapi.models.amtd.Position> amtdPositions;
//		List<Position> posAMTD;
//		try {
//			amtdPositions = amtdService.getAccount().securitiesAccount.positions;
//
//			posAMTD = amtdPositions.stream()
//								   .map(pos -> Position.builder().quantity(pos.longQuantity)
//																 .averagePrice(pos.averagePrice)
//																 .lastPrice(pos.marketValue/pos.longQuantity)
//																 .ticker(Ticker.builder().name(pos.instrument.symbol)
//																						 .description(pos.instrument.assetType)
//																						 .market("USA")
//																						 .build())
//																 .broker("AMTD")
//																 .build()).collect(Collectors.toList());
//			posiciones.addAll(posAMTD);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
    }

    public OhlcBrk[] getPriceHistory(String ticker, String start, String end) {
        return brokers.get("IOL").getPriceHistory(ticker, start, end);
    }

    public void executeStop(Stop stop) {
        brokers.get(stop.getTicker().getTckBroker()).executeStop(stop);
    }
}
