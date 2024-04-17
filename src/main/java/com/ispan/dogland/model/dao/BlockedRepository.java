package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Blockeds;
import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedRepository extends JpaRepository<Blockeds, Users> {
}
