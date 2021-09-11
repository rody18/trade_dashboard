package traderapi.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import traderapi.models.amtd.Account;
import traderapi.models.amtd.TokenAmtd;

@Service
@Slf4j
public class AmtdService {
	private TokenAmtd tokenAmtd;
	private String refreshToken;
	private String clientId;
	private LocalDateTime tokenDateTime;
	private int tokenDurationMinutes = 29;
	
	public AmtdService (@Value("${amtd.client_id}") String clientId, @Value("${amtd.refresh_token}") String refreshToken) {
		this.clientId=clientId;
		this.refreshToken=refreshToken;
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

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("grant_type", "refresh_token");
		requestBody.add("refresh_token", refreshToken);
		requestBody.add("client_id", clientId);

		HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<TokenAmtd> response = 
				restTemplate.exchange("https://api.tdameritrade.com/v1/oauth2/token", HttpMethod.POST, formEntity, TokenAmtd.class);
		
		tokenAmtd = response.getBody();

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
//		LocalDateTime ld = LocalDateTime.now();
//		String dates = ld.format(formatter);
//		System.out.println(ld.format(formatter));
//		
//		LocalDateTime dateTime = LocalDateTime.parse("Sun, 05 Sep 2021 14:19:10 GMT", formatter);
//		System.out.println(dateTime.format(formatter));
		
	}
	
	public Account getAccount() {
		getToken();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + tokenAmtd.access_token);
		    
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
//		requestBody.add("grant_type", "password");
		    
		HttpEntity<?> formEntity = new HttpEntity<Object>(requestBody, headers);
		
		ResponseEntity<Account[]> response = 
		restTemplate.exchange("https://api.tdameritrade.com/v1/accounts?fields=positions", HttpMethod.GET, formEntity, Account[].class);
		
		log.info(response.getBody().toString());
		
		Account[] acc = response.getBody();
		
		return acc[0];
	}
}
