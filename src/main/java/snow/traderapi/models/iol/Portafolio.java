package snow.traderapi.models.iol;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portafolio {
    private String pais;
    private List<Activo> activos;
}
