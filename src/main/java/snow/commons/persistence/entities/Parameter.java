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
@Table(name = "parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "par_id")
    private long parId;
    @Column(name = "par_code", nullable = false)
    private String parCode;
    @Column(name = "par_number")
    private Double parNumber;
    @Column(name = "par_string")
    private String parString;
}
