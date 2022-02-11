package com.brookezb.bhs.service;

import com.brookezb.bhs.constant.AppConstants;
import com.brookezb.bhs.exception.AuthenticationException;
import com.brookezb.bhs.exception.InvalidException;
import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.mapper.RoleMapper;
import com.brookezb.bhs.mapper.UserMapper;
import com.brookezb.bhs.model.User;
import com.brookezb.bhs.utils.CryptUtils;
import com.brookezb.bhs.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private RedisUtils redisUtils;

    @NonNull
    @Override
    public User checkUser(String username, String password) {
        // 获取用户
        User user = userMapper.selectByName(username);
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

    @NonNull
    @Override
    @Cacheable(key = "#uid")
    public User selectById(Long uid) {
        User user = userMapper.selectById(uid);
        if (user != null) {
            return user;
        }
        throw new NotFoundException("没有找到该用户");
    }

    @Override
    public List<User> selectAll(String username, Boolean enabled) {
        return userMapper.selectAllByIdList(userMapper.selectAll(username, enabled));
    }

    @Override
    public String generateAuthToken(Long uid, Long expire) {
        String token = UUID.randomUUID().toString();
        redisUtils.setStringValue(AppConstants.REDIS_USER_TOKEN + token, uid.toString(), expire, TimeUnit.SECONDS);

        return token;
    }

    @Override
    public void removeAuthToken(String token) {
        redisUtils.delete(AppConstants.REDIS_USER_TOKEN + token);
    }

    @Override
    public void insert(User user) {
        if (userMapper.selectByName(user.getName()) != null) {
            throw new InvalidException("该用户名已被注册");
        }
        if (userMapper.verifyMail(user.getMail()) != null) {
            throw new InvalidException("该邮箱已被注册");
        }
        if (user.getRole() == null || user.getRole().getRid() == null) {
            throw new InvalidException("未选择角色");
        }
        if (roleMapper.verifyRole(user.getRole().getRid()) == null) {
            throw new InvalidException("未找到该角色");
        }

        user.setPassword(CryptUtils.BCrypt.encode(user.getPassword()));
        if (userMapper.insert(user) > 0) {
            return;
        }
        throw new InvalidException("用户插入失败");
    }

    @Override
    @CacheEvict(key = "#user.uid")
    public void update(User user) {
        if (userMapper.verifyUser(user.getUid()) != null) {
            if (userMapper.update(user) > 0) {
                return;
            }
            throw new InvalidException("更新用户信息失败");
        }
        throw new NotFoundException("没有找到该用户");
    }

    @Override
    @CacheEvict(key = "#uid")
    public void updatePassword(Long uid, String oldPassword, String newPassword) {
        User user = userMapper.selectById(uid);
        if (user == null) {
            throw new NotFoundException("没有找到该用户");
        }
        if (!CryptUtils.BCrypt.matches(oldPassword, user.getPassword())) {
            throw new AuthenticationException("旧密码错误");
        }
        userMapper.updatePassword(uid, CryptUtils.BCrypt.encode(newPassword));
    }

    @Override
    @CacheEvict(key = "#uid")
    public void updateStatus(Long uid, Boolean enabled) {
        if (userMapper.verifyUser(uid) == null) {
            throw new NotFoundException("没有找到该用户");
        }
        userMapper.updateStatus(uid, enabled);

        // 封禁用户后删除登录状态
        if (!enabled) {

        }
    }

    @Override
    @CacheEvict(key = "#uid")
    public void delete(Long uid) {
        if (userMapper.verifyUser(uid) == null) {
            throw new NotFoundException("没有找到该用户");
        }
        if (userMapper.delete(uid) > 0) {
            return;
        }
        throw new InvalidException("删除用户失败");
    }
}
