package com.brookezb.bhs.service;

import com.brookezb.bhs.exception.NotFoundException;
import com.brookezb.bhs.mapper.FriendMapper;
import com.brookezb.bhs.model.Friend;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private FriendMapper friendMapper;

    @Override
    public Friend selectById(Long fid) {
        Friend friend = friendMapper.selectById(fid);
        if (friend == null) {
            throw new NotFoundException("友链不存在");
        }
        return friend;
    }

    @Override
    public List<Friend> selectAll() {
        return friendMapper.selectAll();
    }

    @Override
    public boolean insert(Friend friend) {
        return friendMapper.insert(friend) > 0;
    }

    @Override
    public void update(Friend friend) {
        if (friendMapper.verifyFriend(friend.getFid()) == null) {
            throw new NotFoundException("友链不存在");
        }
        friendMapper.update(friend);
    }

    @Override
    public void delete(Long fid) {
        if (friendMapper.verifyFriend(fid) == null) {
            throw new NotFoundException("友链不存在");
        }
        friendMapper.delete(fid);
    }
}
