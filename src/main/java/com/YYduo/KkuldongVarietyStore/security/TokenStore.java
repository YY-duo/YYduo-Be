package com.YYduo.KkuldongVarietyStore.security;

public interface TokenStore {
    void storeRefreshToken(String username, String refreshToken);
    boolean validateRefreshToken(String username, String refreshToken);
    void removeRefreshToken(String username);
}

