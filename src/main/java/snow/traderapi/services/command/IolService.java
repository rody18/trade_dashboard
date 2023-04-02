package snow.traderapi.services.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import snow.commons.persistence.entities.Stop;
import snow.commons.persistence.entities.Ticker;
import snow.traderapi.models.broker.OhlcBrk;
import snow.traderapi.models.broker.Position;
import snow.traderapi.models.iol.OhlcIol;
import snow.traderapi.models.iol.Portafolio;
import snow.traderapi.models.iol.TokenIol;

@Slf4j
@Component
public class IolService implements BrokerCommand {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
    private TokenIol tokenIOL;
    private String userName;
    private String password;
    private LocalDateTime tokenDateTime;
    private int tokenDurationMinutes = 14;

    public IolService(@Value("${iol.username}") String userName, @Value("${iol.password}") String password) {
        this.userName = userName;
        this.password = password;
        this.tokenDateTime = LocalDateTime.now();
    }

    public void getToken() {
        long gap = ChronoUnit.MINUTES.between(tokenDateTime, LocalDateTime.now());
        if (gap < tokenDurationMinutes && tokenIOL != null) {
            return;
        }
        this.tokenDateTime = LocalDateTime.now();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", userName);
        requestBody.add("password", password);

        HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<TokenIol> response =
                restTemplate.exchange("https://api.invertironline.com/token", HttpMethod.POST, formEntity, TokenIol.class);

        tokenIOL = response.getBody();
    }

    public Portafolio getPortfolio() {
        getToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenIOL.getAccessToken());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");

        HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Portafolio> response =
                restTemplate.exchange("https://api.invertironline.com/api/v2/portafolio/argentina", HttpMethod.GET, formEntity, Portafolio.class);

        return response.getBody();
    }

    public OhlcBrk[] getPriceHistory(String ticket, String start, String end) {
        getToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenIOL.getAccessToken());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");

        HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);

        OhlcIol[] response =
                Arrays.stream(restTemplate.exchange(String.format("https://api.invertironline.com/api/v2/bCBA/Titulos/%s/Cotizacion/seriehistorica/%s/%s/ajustada", ticket, start, end),
                                                    HttpMethod.GET,
                                                    formEntity, OhlcIol[].class)
                                          .getBody())
                      .sorted()
                      .distinct()
                      .toArray(OhlcIol[]::new);

        return Arrays.stream(response)
                     .map(ohlc -> OhlcBrk.builder()
                                         .open(ohlc.getApertura())
                                         .high(ohlc.getMaximo())
                                         .low(ohlc.getMinimo())
                                         .close(ohlc.getUltimoPrecio())
                                         .date(ohlc.getFechaHora())
                                         .build())
                     .toArray(OhlcBrk[]::new);
    }

    @Override
    public Position[] getPositions() {
        List<Position> positions = new ArrayList<>();
        try {
            List<Position> posIOL = getPortfolio().getActivos().stream()
                                                          .map(activo -> Position.builder()
                                                                                 .quantity((int) activo.getCantidad())
                                                                                 .averagePrice(activo.getPpc())
                                                                                 .lastPrice(activo.getUltimoPrecio())
                                                                                 .ticker(Ticker.builder()
                                                                                               .tckName(activo.getTitulo().getSimbolo())
                                                                                               .tckDescription(activo.getTitulo().getDescripcion())
                                                                                               .tckType(activo.getTitulo().getTipo())
                                                                                               .tckBroker("IOL")
                                                                                               .build())
                                                                                 .broker("IOL")
                                                                                 .build()).collect(Collectors.toList());
            positions.addAll(posIOL);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return positions.toArray(new Position[positions.size()]);
    }

    @Override
    public String getName() {
        return "IOL";
    }

    @Override
    public void executeStop(Stop stop) {
        getToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", tokenIOL.getAccessToken()));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("mercado", "bCBA");
        requestBody.add("simbolo", stop.getTicker().getTckName());
        requestBody.add("cantidad", stop.getStpQty().toString());
        requestBody.add("precio", stop.getStpPrice().toString());
        requestBody.add("validez", DATE_FORMAT.format(LocalDateTime.now().plusHours(1)));
        requestBody.add("tipoOrden", "precioMercado");

        HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange("https://api.invertironline.com/api/v2/operar/Vender", HttpMethod.POST, formEntity, String.class);
    }
}
