package top.brookezb.bhs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.constant.AppConstants;
import top.brookezb.bhs.exception.AuthenticationException;
import top.brookezb.bhs.exception.InvalidException;
import top.brookezb.bhs.exception.NotFoundException;
import top.brookezb.bhs.mapper.RoleMapper;
import top.brookezb.bhs.mapper.UserMapper;
import top.brookezb.bhs.model.User;
import top.brookezb.bhs.utils.CryptUtils;
import top.brookezb.bhs.utils.RedisUtils;

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
    public PageInfo<List<User>> selectAll(int page, int size, String username, Boolean enabled) {
        return PageHelper.startPage(page, size)
                .doSelectPageInfo(() -> userMapper.selectAll(username, enabled));
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
            throw new InvalidException("该用户名已存在");
        }
        if (user.getRole() == null || user.getRole().getRid() == null) {
            throw new InvalidException("未选择角色");
        }
        if (roleMapper.selectById(user.getRole().getRid()) == null) {
            throw new InvalidException("未找到该角色");
        }

        if (userMapper.insert(user) > 0) {
            return;
        }
        throw new InvalidException("用户插入失败");
    }

    @Override
    @CacheEvict(key = "#user.uid")
    public void update(User user) {
        if (userMapper.selectById(user.getUid()) != null) {
            if (userMapper.update(user) > 0) {
                return;
            }
            throw new InvalidException("更新用户信息失败");
        }
        throw new NotFoundException("没有找到该用户");
    }

    @Override
    @CacheEvict(key = "#uid")
    public void updateStatus(Long uid, Boolean enabled) {
        if (userMapper.selectById(uid) == null) {
            throw new NotFoundException("没有找到该用户");
        }
        userMapper.updateStatus(uid, enabled);
    }

    @Override
    @CacheEvict(key = "#uid")
    public void delete(Long uid) {
        if (userMapper.selectById(uid) == null) {
            throw new NotFoundException("没有找到该用户");
        }
        if (userMapper.delete(uid) > 0) {
            return;
        }
        throw new InvalidException("删除用户失败");
    }
}
