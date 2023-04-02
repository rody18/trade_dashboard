package snow.traderapi.models.broker;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class OhlcBrk {
    private double open;
    private double high;
    private double close;
    private double low;
    private LocalDateTime date;
    private int volume;
}
