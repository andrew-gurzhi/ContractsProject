package com.jm.contract.services.interfaces;

import com.google.api.client.auth.oauth2.Credential;
import com.jm.contract.models.GoogleToken;

public interface GoogleAuthorizationService {

    String authorize(GoogleToken.TokenType tokenType);

    Credential tokenResponse(String code, GoogleToken.TokenType tokenType);

    Credential getCredential(GoogleToken.TokenType tokenType);
}
