package top.brookezb.bhs.constant;

/**
 * @author brooke_zb
 */
public class AppConstants {
    public static final String APP_NAME = "BlackHoleStation";

    public static final String REDIS_PREFIX = "bhs:";
    public static final String REDIS_USER_TOKEN = REDIS_PREFIX + "user:token:";

    public static final String CSRF_HEADER = "X-CSRF-TOKEN";
    public static final String AUTH_TOKEN_HEADER = "Authorization";
}
