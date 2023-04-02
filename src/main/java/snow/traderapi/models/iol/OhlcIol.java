package snow.traderapi.models.iol;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OhlcIol implements Comparable<OhlcIol>{
    private double ultimoPrecio;
    private double variacion;
    private double apertura;
    private double maximo;
    private double minimo;
    private LocalDateTime fechaHora;
    private int volumenNominal;
    private double precioPromedio;
    private String moneda;
    private double precioAjuste;

    @Override
    public int compareTo(@NotNull OhlcIol o) {
        return fechaHora.compareTo(o.fechaHora);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OhlcIol))
            return false;
        OhlcIol other = (OhlcIol)o;

        return this.compareTo(other) == 0;
    }
}
