package top.brookezb.bhs.service;

import org.springframework.lang.NonNull;
import top.brookezb.bhs.model.User;

/**
 * 用户服务
 *
 * @author brooke_zb
 */
public interface UserService {
    @NonNull
    User checkUser(String username, String password);

    User selectById(Long uid);

    String generateAuthToken(Long uid);
}
