package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.exception.AuthenticationException;
import top.brookezb.bhs.mapper.UserMapper;
import top.brookezb.bhs.model.User;
import top.brookezb.bhs.utils.CryptUtils;
import top.brookezb.bhs.utils.RedisUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private RedisUtils redisUtils;

    @NonNull
    @Override
    public User checkUser(String username, String password) {
        // 获取用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new AuthenticationException("没有找到该用户");
        }

        // 检查密码
        if (!CryptUtils.BCrypt.matches(password, user.getPassword())) {
            throw new AuthenticationException("密码错误");
        }

        return user;
    }

    @Override
    public User selectById(Long uid) {
        return userMapper.selectById(uid);
    }

    @Override
    public String generateAuthToken(Long uid) {
        String token = UUID.randomUUID().toString();
        redisUtils.setStringValue("bhs:user:token:" + token, uid.toString(), 7, TimeUnit.DAYS);

        return token;
    }
}
