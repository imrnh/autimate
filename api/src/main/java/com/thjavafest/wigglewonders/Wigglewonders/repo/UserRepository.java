package com.thjavafest.wigglewonders.Wigglewonders.repo;

import java.util.Optional;

import com.thjavafest.wigglewonders.Wigglewonders.entity.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {//should i use id as id is the primary key???...Here they used user in order to retrieve the full user class????
  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
