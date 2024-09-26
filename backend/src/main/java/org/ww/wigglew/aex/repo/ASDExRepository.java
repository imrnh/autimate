package org.ww.wigglew.aex.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.ww.wigglew.aex.entity.ASDExEntity;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ASDExRepository extends MongoRepository<ASDExEntity, String> {
    List<ASDExEntity> findByUsername(String username);
    Optional<ASDExEntity> findByRequestID(String requestID);
}