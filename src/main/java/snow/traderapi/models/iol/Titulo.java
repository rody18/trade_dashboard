package snow.traderapi.models.iol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Titulo {
    private String simbolo;
    private String descripcion;
    private String pais;
    private String mercado;
    private String tipo;
    private String plazo;
    private String moneda;
}
