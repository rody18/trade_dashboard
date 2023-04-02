package snow.dashboard.services;

import org.springframework.stereotype.Service;

import snow.commons.persistence.entities.Stop;
import snow.traderapi.services.BrokerService;

@Service
public class StopService {
    private final BrokerService brokerService;

    public StopService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public void execute(Stop stop) {
        brokerService.executeStop(stop);
    }
}
