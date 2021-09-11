package traderapi.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import traderapi.models.iol.Portafolio;
import traderapi.models.iol.TokenIol;

import org.springframework.http.MediaType;

@Service
public class IolService {

	private TokenIol tokenIOL;
	private String userName;
	private String password;
	private LocalDateTime tokenDateTime;
	private int tokenDurationMinutes = 14;
	
	public IolService (@Value("${iol.username}") String userName, @Value("${iol.password}")  String password) {
		this.userName=userName;
		this.password=password;
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

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("grant_type", "password");
		requestBody.add("username", userName);
		requestBody.add("password", password);

		HttpEntity<?> formEntity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<TokenIol> response = 
				restTemplate.exchange("https://api.invertironline.com/token", HttpMethod.POST, formEntity, TokenIol.class);
		
		tokenIOL = response.getBody();
	}
	
	public Portafolio getPortfolio () {
		getToken();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + tokenIOL.access_token);
		    
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
		requestBody.add("grant_type", "password");
		    
		HttpEntity<?> formEntity = new HttpEntity<Object>(requestBody, headers);
		
		ResponseEntity<Portafolio> response = 
		restTemplate.exchange("https://api.invertironline.com/api/v2/portafolio/estados_unidos", HttpMethod.GET, formEntity, Portafolio.class);
		
		return response.getBody();
		
	}
}
