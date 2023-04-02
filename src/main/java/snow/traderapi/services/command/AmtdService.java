package snow.traderapi.services.command;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

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

import lombok.extern.slf4j.Slf4j;

import snow.commons.persistence.entities.Stop;
import snow.traderapi.models.amtd.Account;
import snow.traderapi.models.amtd.TokenAmtd;
import snow.traderapi.models.broker.OhlcBrk;
import snow.traderapi.models.broker.Position;

@Slf4j
@Component
public class AmtdService implements BrokerCommand {
    private TokenAmtd tokenAmtd;
    private String refreshToken;
    private String clientId;
    private LocalDateTime tokenDateTime;
    private int tokenDurationMinutes = 29;

    public AmtdService(@Value("${amtd.client_id}") String clientId,
                       @Value("${amtd.refresh_token}") String refreshToken) {
        this.clientId = clientId;
        this.refreshToken = refreshToken;
        this.tokenDateTime = LocalDateTime.now();
    }

    public void getToken() {
        long gap = ChronoUnit.MINUTES.between(tokenDateTime, LocalDateTime.now());
        if (gap < tokenDurationMinutes && tokenAmtd != null) {
            return;
        }
        this.tokenDateTime = LocalDateTime.now();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", refreshToken);
        requestBody.add("client_id", clientId);

        HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<TokenAmtd> response =
                restTemplate.exchange("https://api.tdameritrade.com/v1/oauth2/token", HttpMethod.POST, formEntity, TokenAmtd.class);

        tokenAmtd = response.getBody();
    }

    public Account getAccount() {
        getToken();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenAmtd.getAccessToken());

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Account[]> response =
                restTemplate.exchange("https://api.tdameritrade.com/v1/accounts?fields=positions", HttpMethod.GET, formEntity, Account[].class);

        log.info(Arrays.toString(response.getBody()));

        Account[] acc = response.getBody();

        return acc != null ? acc[0] : null;
    }

    @Override
    public String getName() {
        return "AMTD";
    }

    @Override
    public void executeStop(Stop stop) {
        // TODO
    }

    @Override
    public OhlcBrk[] getPriceHistory(String ticker, String start, String end) {
        return new OhlcBrk[0];
    }

    @Override
    public Position[] getPositions() {
        return new Position[0];
    }
}
