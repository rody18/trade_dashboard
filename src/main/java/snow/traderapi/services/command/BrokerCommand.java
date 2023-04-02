package snow.traderapi.services.command;

import snow.commons.persistence.entities.Stop;
import snow.traderapi.models.broker.OhlcBrk;
import snow.traderapi.models.broker.Position;

public interface BrokerCommand {
    String getName();
    void executeStop(Stop stop);
    OhlcBrk[] getPriceHistory(String ticker, String start, String end);
    Position[] getPositions();
}
