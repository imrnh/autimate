package com.thjavafest.wigglewonders.Wigglewonders.repo;

import java.util.Optional;

import com.thjavafest.wigglewonders.Wigglewonders.entity.user.ERoleEntity;
import com.thjavafest.wigglewonders.Wigglewonders.entity.user.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface RoleRepository extends MongoRepository<RoleEntity, String> {
  Optional<RoleEntity> findByName(ERoleEntity name);
}


