package com.brookezb.bhs.utils;

import org.springframework.http.ResponseCookie;

/**
 * @author brooke_zb
 */
public class CookieUtils {
    private static final String domain;
    private static final boolean secure;

    public static ResponseCookie.ResponseCookieBuilder from(String name, String value) {
        return ResponseCookie.from(name, value)
                .domain(domain)
                .secure(secure)
                .path("/");
    }

    static {
        domain = PropertyUtils.getProperty("server.servlet.session.cookie.domain");
        secure = Boolean.parseBoolean(PropertyUtils.getProperty("server.servlet.session.cookie.secure"));
    }
}
