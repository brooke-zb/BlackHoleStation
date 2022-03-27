package com.brookezb.bhs.service;

import com.brookezb.bhs.model.Friend;

import java.util.List;

/**
 * @author brooke_zb
 */
public interface FriendService {
    Friend selectById(Long fid);

    List<Friend> selectAll();

    boolean insert(Friend friend);

    void update(Friend friend);

    void delete(Long fid);
}
