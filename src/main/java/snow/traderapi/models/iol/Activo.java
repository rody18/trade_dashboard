package snow.traderapi.models.iol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Activo {
    private double cantidad;
    private double comprometido;
    private double puntosVariacion;
    private double variacionDiaria;
    private double ultimoPrecio;
    private double ppc;
    private double gananciaPorcentaje;
    private double gananciaDinero;
    private double valorizado;
    private Titulo titulo;
    private Object parking;
}
