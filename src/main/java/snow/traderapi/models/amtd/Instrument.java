package snow.traderapi.models.amtd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Instrument {
    private String assetType;
    private String cusip;
    private String symbol;
}
