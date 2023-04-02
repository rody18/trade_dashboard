package snow.traderapi.models.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import snow.commons.persistence.entities.Ticker;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
	private int quantity;
	private double averagePrice;
	private double lastPrice;
	private Ticker ticker;
	private String broker;
}
