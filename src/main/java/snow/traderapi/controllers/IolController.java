package snow.traderapi.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import snow.traderapi.models.broker.OhlcBrk;
import snow.traderapi.models.iol.Portafolio;
import snow.traderapi.services.command.IolService;

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

    @GetMapping("pricehistory")
    public OhlcBrk[] getPriceHistory(@RequestParam String ticket, @RequestParam String start,
                                     @RequestParam String end) {
        return iolService.getPriceHistory(ticket, start, end);
    }
}
