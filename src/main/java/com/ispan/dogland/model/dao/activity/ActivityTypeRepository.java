package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepository extends JpaRepository<ActivityType,Integer> {
    ActivityType findByActivityTypeId(Integer activityTypeId);

    ActivityType findByActivityTypeName(String activityTypeName);
}
