package traderapi.models.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
	public int quantity;
	public double averagePrice;
	public double lastPrice;
	public Ticker ticker;
	public String broker;
}