package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.constant.AppConstants;
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
@CacheConfig(cacheNames = "user")
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
        System.out.println(user);
        if (user == null) {
            throw new AuthenticationException("没有找到该用户");
        }

        // 检查密码
        if (!CryptUtils.BCrypt.matches(password, user.getPassword())) {
            throw new AuthenticationException("密码错误");
        }

        // 检查启用状态
        if (!user.isEnabled()) {
            throw new AuthenticationException("该用户已被禁用，请联系管理员");
        }

        return user;
    }

    @Cacheable(key = "'user:' + #uid")
    @Override
    public User selectById(Long uid) {
        return userMapper.selectById(uid);
    }

    @Override
    public String generateAuthToken(Long uid, Long expire) {
        String token = UUID.randomUUID().toString();
        redisUtils.setStringValue(AppConstants.REDIS_USER_TOKEN + token, uid.toString(), expire, TimeUnit.SECONDS);

        return token;
    }

    @Override
    public boolean removeAuthToken(String token) {
        return redisUtils.delete(AppConstants.REDIS_USER_TOKEN + token);
    }
}
