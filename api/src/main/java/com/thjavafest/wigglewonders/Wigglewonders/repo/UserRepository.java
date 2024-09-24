package com.thjavafest.wigglewonders.Wigglewonders.repo;

import java.util.Optional;

import com.thjavafest.wigglewonders.Wigglewonders.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<UserEntity, String> {//should i use id as id is the primary key???...Here they used user in order to retrieve the full user class????
  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}