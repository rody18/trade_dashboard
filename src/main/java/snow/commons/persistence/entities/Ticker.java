package snow.commons.persistence.entities;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tck_id")
    private long tckId;
    @Column(name = "tck_name", nullable = false)
    private String tckName;
    @Column(name = "tck_description")
    private String tckDescription;
    @Column(name = "tck_type")
    private String tckType;
    @Column(name = "tck_broker")
    private String tckBroker;
}
