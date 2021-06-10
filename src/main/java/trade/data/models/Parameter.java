package trade.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

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
@Table(name = "parameters")
public class Parameter {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long par_id;
	@Column(name = "par_code", nullable = false)
	private String parCode;
	private Double par_number;
	private String par_string;
}
