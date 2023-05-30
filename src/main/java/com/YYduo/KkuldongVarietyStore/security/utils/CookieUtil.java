package com.YYduo.KkuldongVarietyStore.security.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void createHttpOnlyCookie(HttpServletResponse response, String name, String value, int maxAge) {
        String cookieValue = String.format("%s=%s; HttpOnly; SameSite=None; Secure; Path=/; Max-Age=%s",
                name, value, maxAge);
        response.setHeader("Set-Cookie", cookieValue);
    }

//    public static Cookie createHttpOnlyCookie(String name, String value) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        return cookie;
//    }

//    public static void addSameSiteAttribute(HttpServletResponse response, String cookieName, String cookieValue) {
//        String headerValue = String.format("%s=%s; SameSite=None; Secure", cookieName, cookieValue);
//        response.setHeader("Set-Cookie", headerValue);
//    }

    public static String base64UrlEncode(String value) {
        return Base64.getUrlEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64UrlDecode(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }

}
