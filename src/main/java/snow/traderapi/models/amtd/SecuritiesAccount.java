package snow.traderapi.models.amtd;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuritiesAccount {
    private String type;
    private String accountId;
    private int roundTrips;
    private boolean isDayTrader;
    private boolean isClosingOnlyRestricted;
    private List<Position> positions;
    private InitialBalances initialBalances;
    private CurrentBalances currentBalances;
    private ProjectedBalances projectedBalances;
}
