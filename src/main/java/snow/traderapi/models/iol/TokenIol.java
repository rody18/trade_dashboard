package snow.traderapi.models.iol;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenIol {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty(".issued") 
    private String issued;
    @JsonProperty(".expires") 
    private String expires;
    @JsonProperty(".refreshexpires") 
    private String refreshexpires;
}
