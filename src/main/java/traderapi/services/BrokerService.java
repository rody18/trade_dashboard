package traderapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import traderapi.models.broker.*;
import traderapi.models.iol.Activo;

@Slf4j
@Service
public class BrokerService {
	private final IolService iolService;
	private final AmtdService amtdService;
	
	@Autowired
	public BrokerService(IolService iolService, AmtdService amtdService) {
		log.info("BrokerService --> Autowired --> IolService & AmtdService");
		this.iolService = iolService;
		this.amtdService = amtdService;
	}
	
	public Position[] getPositions() {
		List<Position> posiciones = new ArrayList<Position>();
		List<Activo> iolActivos = new ArrayList<Activo>();
		List<Position> posIOL = new ArrayList<Position>();
		try {
			iolActivos = iolService.getPortfolio().activos;
			
			posIOL = iolActivos.stream()
								.map(activo -> {
									return Position.builder().quantity((int) activo.cantidad)
											                 .averagePrice(activo.ppc)
											                 .lastPrice(activo.ultimoPrecio)
											                 .ticker(Ticker.builder().name(activo.titulo.simbolo)
											                		                 .description(activo.titulo.descripcion)
											                		                 .market(activo.titulo.mercado)
											                		                 .build())
											                 .broker("IOL")
											                 .build();
								}).collect(Collectors.toList());
			posiciones.addAll(posIOL);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		List<traderapi.models.amtd.Position> amtdPositions = new ArrayList<>();
		List<Position> posAMTD = new ArrayList<Position>();
		try {
			amtdPositions = amtdService.getAccount().securitiesAccount.positions;
			
			posAMTD = amtdPositions.stream()
						.map(pos -> {
							return Position.builder().quantity(pos.longQuantity)
					                 .averagePrice(pos.averagePrice)
					                 .lastPrice(pos.marketValue/pos.longQuantity)
					                 .ticker(Ticker.builder().name(pos.instrument.symbol)
					                		                 .description(pos.instrument.assetType)
					                		                 .market("USA")
					                		                 .build())
					                 .broker("AMTD")
					                 .build();
						}).collect(Collectors.toList());
			posiciones.addAll(posAMTD);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return posiciones.toArray(new Position[posiciones.size()]);
	}
	
}
