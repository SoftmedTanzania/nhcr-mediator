package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the authentication response message from RITA.
 */
public class RitaAuthenticationResponse {
    /**
     * The access token.
     */

    @SerializedName("access_token")
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * The token type.
     */
    @SerializedName("token_type")
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * The expiry duration.
     */

    @SerializedName("expires_in")
    @JsonProperty("expires_in")
    private int expiresIn;

    /**
     * The token scope.
     */
    @SerializedName("scope")
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
