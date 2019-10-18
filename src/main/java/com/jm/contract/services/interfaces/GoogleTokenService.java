package com.jm.contract.services.interfaces;

import com.jm.contract.models.GoogleToken;

import java.util.Optional;

public interface GoogleTokenService {

    Optional<GoogleToken> getToken(GoogleToken.TokenType tokenType);

    void createOrUpdate(GoogleToken accessToken, GoogleToken.TokenType tokenType);

    Optional<GoogleToken> getRefreshedToken(GoogleToken.TokenType tokenType);

}
