package snow.traderapi.models.amtd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentBalances {
    private int accruedInterest;
    private double cashBalance;
    private int cashReceipts;
    private int longOptionMarketValue;
    private double liquidationValue;
    private double longMarketValue;
    private int moneyMarketFund;
    private int savings;
    private int shortMarketValue;
    private int pendingDeposits;
    private double cashAvailableForTrading;
    private double cashAvailableForWithdrawal;
    private int cashCall;
    private double longNonMarginableMarketValue;
    private double totalCash;
    private int shortOptionMarketValue;
    private int mutualFundValue;
    private int bondValue;
    private int cashDebitCallValue;
    private int unsettledCash;
}
