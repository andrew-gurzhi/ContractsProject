package com.jm.contract.repository.interfaces;

import com.jm.contract.models.GoogleToken;

public interface GoogleTokenRepository extends CommonGenericRepository<GoogleToken> {

    GoogleToken getByTokenType(GoogleToken.TokenType tokenType);
}
