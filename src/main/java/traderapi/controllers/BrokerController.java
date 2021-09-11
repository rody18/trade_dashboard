package traderapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import traderapi.models.broker.Position;
import traderapi.services.BrokerService;

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
}
