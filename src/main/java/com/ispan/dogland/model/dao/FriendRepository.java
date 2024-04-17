package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Friends;
import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friends, Users> {

}
