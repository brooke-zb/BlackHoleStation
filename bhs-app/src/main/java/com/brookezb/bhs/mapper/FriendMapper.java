package com.brookezb.bhs.mapper;

import com.brookezb.bhs.model.Friend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author brooke_zb
 */
@Mapper
public interface FriendMapper {
    Friend selectById(Long fid);

    List<Friend> selectAll();

    Integer verifyFriend(Long fid);

    int insert(Friend friend);

    int update(Friend friend);

    int delete(Long fid);
}
