package org.ww.wigglew.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.ww.wigglew.auth.entity.PhoneNumberVerificationStatus;
import org.ww.wigglew.auth.entity.UserEntity;


import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<UserEntity, String> { //User ID type is String.
    Optional<UserEntity> findByPhone(String phone);
}
