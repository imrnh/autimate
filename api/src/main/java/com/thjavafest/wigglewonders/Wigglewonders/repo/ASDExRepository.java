package com.thjavafest.wigglewonders.Wigglewonders.repo;

import com.thjavafest.wigglewonders.Wigglewonders.entity.ASDExEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ASDExRepository extends MongoRepository<ASDExEntity, String> {
    List<ASDExEntity> findByUsername(String username);
}