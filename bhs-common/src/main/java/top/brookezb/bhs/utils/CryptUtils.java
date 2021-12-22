package top.brookezb.bhs.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author brooke_zb
 */
public class CryptUtils {
    private static final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public static class BCrypt {
        public static String encode(String rawPassword) {
            return bcryptEncoder.encode(rawPassword);
        }

        public static boolean matches(String rawPassword, String encodedPassword) {
            return bcryptEncoder.matches(rawPassword, encodedPassword);
        }
    }
}
