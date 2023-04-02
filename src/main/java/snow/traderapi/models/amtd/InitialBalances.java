package snow.traderapi.models.amtd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialBalances {
    private int accruedInterest;
    private double cashAvailableForTrading;
    private double cashAvailableForWithdrawal;
    private double cashBalance;
    private int bondValue;
    private int cashReceipts;
    private double liquidationValue;
    private int longOptionMarketValue;
    private double longStockValue;
    private int moneyMarketFund;
    private int mutualFundValue;
    private int shortOptionMarketValue;
    private int shortStockValue;
    private boolean isInCall;
    private int unsettledCash;
    private int cashDebitCallValue;
    private int pendingDeposits;
    private double accountValue;
}
