package dashboard.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickers")
public class Ticker {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long tck_id;
	@Column(name = "tck_name", nullable = false)
	private String tckName;
	private String tck_description;
	private String tck_type;

	
}
