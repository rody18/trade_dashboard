package traderapi.models.amtd;

import java.util.List;

public class SecuritiesAccount {
    public String type;
    public String accountId;
    public int roundTrips;
    public boolean isDayTrader;
    public boolean isClosingOnlyRestricted;
    public List<Position> positions;
    public InitialBalances initialBalances;
    public CurrentBalances currentBalances;
    public ProjectedBalances projectedBalances;
}
