package com.brookezb.bhs.constant;

/**
 * 常量储存类
 *
 * @author brooke_zb
 */
public class AppConstants {
    public static final String APP_NAME = "BlackHoleStation";

    public static final String REDIS_PREFIX = "bhs:";
    public static final String REDIS_USER_TOKEN = REDIS_PREFIX + "user:token:";
    public static final String REDIS_ARTICLE_VIEW_COUNT = REDIS_PREFIX + "article:view:";

    public static final String CSRF_HEADER = "X-CSRF-TOKEN";
    public static final String AUTH_TOKEN_HEADER = "Authorization";

    public static final String SESSION_USER_KEY = "uid";

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int TIMELINE_SIZE = 20;
}
