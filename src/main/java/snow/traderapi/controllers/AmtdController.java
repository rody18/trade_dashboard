package snow.traderapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import snow.traderapi.models.amtd.Account;
import snow.traderapi.services.command.AmtdService;

@RestController
@Slf4j
@RequestMapping("/amtd/")
public class AmtdController {
	private final AmtdService amtdService;

	@Autowired
	public AmtdController(AmtdService amtdService) {
		log.info("AmtdController --> Autowired --> amtdService");
		this.amtdService = amtdService;
	}
	
	@GetMapping("account")
	public Account getAccount() {
		return amtdService.getAccount();
	}
}
