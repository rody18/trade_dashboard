package traderapi.models.amtd;

public class Position {
    public int shortQuantity;
    public double averagePrice;
    public int currentDayCost;
    public int currentDayProfitLoss;
    public int currentDayProfitLossPercentage;
    public int longQuantity;
    public int settledLongQuantity;
    public int settledShortQuantity;
    public Instrument instrument;
    public double marketValue;
    public int maintenanceRequirement;
    public int previousSessionLongQuantity;
}
