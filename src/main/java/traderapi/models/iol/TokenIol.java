package traderapi.models.iol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenIol {
    public String access_token;
    public String token_type;
    public int expires_in;
    public String refresh_token;
    @JsonProperty(".issued") 
    public String issued;
    @JsonProperty(".expires") 
    public String expires;
    @JsonProperty(".refreshexpires") 
    public String refreshexpires;
}