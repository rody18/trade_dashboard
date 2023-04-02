package snow.commons.persistence.entities;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stp_id")
    private long stpId;
    @Min(value = 0, message = "Seleccione un ticker")
    @Column(name = "stp_tck_id", nullable = false)
    private long stpTckId;
    @Column(name = "stp_type")
    private String stpType;
    @DecimalMin("0.01") //@Min(value = 0.1, message = "El precio límite debe ser mayor a cero")
    @Column(name = "stp_limit")
    private Double stpLimit;
    @Column(name = "stp_price")
    private Double stpPrice;
    @Column(name = "stp_qty")
    private Long stpQty;
    @Column(name = "stp_exec_type")
    private String stpExecType;
    @Column(name = "stp_status")
    private String stpStatus;
    @ManyToOne()
    @JoinColumn(name = "stp_tck_id", referencedColumnName = "tck_id", insertable = false, updatable = false)
    private Ticker ticker;
}
