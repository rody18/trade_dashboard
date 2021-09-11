package dashboard.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
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
@Table(name = "stops")
public class Stop {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long stp_id;
	@Min(value = 0, message = "Seleccione un ticker")
	@Column(name = "stp_tck_id", nullable = false)
	private long stpTckId;
	private String stp_type;
	@DecimalMin("0.01") //@Min(value = 0.1, message = "El precio límite debe ser mayor a cero")
	private double stp_limit;
	private double stp_price;
	private double stp_qty;
	private String stp_exec_type;
	private String stp_status;
	
	@ManyToOne()
	@JoinColumn(name="stp_tck_id", referencedColumnName = "tck_id", insertable = false, updatable = false)    
	private Ticker ticker;

}
