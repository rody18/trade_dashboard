package traderapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import traderapi.models.iol.Portafolio;
import traderapi.models.iol.TokenIol;
import traderapi.services.IolService;

@RestController
@Slf4j
@RequestMapping("/iol/")
public class IolController {
	private final IolService iolService;

	@Autowired
	public IolController(IolService iolService) {
		log.info("IolController --> Autowired --> IolService");
		this.iolService = iolService;
	}
	
	@GetMapping("portfolio")
	public Portafolio getPortfolio() {
		return iolService.getPortfolio();
	}
}
