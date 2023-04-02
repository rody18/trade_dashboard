package snow.traderapi.models.amtd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private int shortQuantity;
    private double averagePrice;
    private int currentDayCost;
    private int currentDayProfitLoss;
    private int currentDayProfitLossPercentage;
    private int longQuantity;
    private int settledLongQuantity;
    private int settledShortQuantity;
    private Instrument instrument;
    private double marketValue;
    private int maintenanceRequirement;
    private int previousSessionLongQuantity;
}
