package com.thjavafest.wigglewonders.Wigglewonders.repo;

import com.thjavafest.wigglewonders.Wigglewonders.entity.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

}
