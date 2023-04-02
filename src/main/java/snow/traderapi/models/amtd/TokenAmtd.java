package snow.traderapi.models.amtd;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenAmtd {
    @JsonProperty("access_token")
    private String accessToken;
    private String scope;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("token_yype")
    private String tokenType;
}
