package snow.traderapi.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import snow.traderapi.models.broker.Position;
import snow.traderapi.models.broker.OhlcBrk;
import snow.traderapi.services.BrokerService;

@RestController
@Slf4j
@RequestMapping("/broker/")
public class BrokerController {
	private final BrokerService brokerService;

	@Autowired
	public BrokerController(BrokerService brokerService) {
		log.info("BrokerController --> Autowired --> brokerService");
		this.brokerService = brokerService;
	}
	
	@GetMapping("positions")
	public Position[] getPortfolio() {
		return brokerService.getPositions();
	}

	@GetMapping("pricehistory")
	public OhlcBrk[] getPriceHistory(@RequestParam String ticker, @RequestParam String start, @RequestParam String end) {
		return brokerService.getPriceHistory(ticker, start, end);
	}
}
