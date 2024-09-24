package com.thjavafest.wigglewonders.Wigglewonders.services;

import com.thjavafest.wigglewonders.Wigglewonders.entity.ASDExEntity;
import com.thjavafest.wigglewonders.Wigglewonders.repo.ASDExRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ASDExDBService {

    private final ASDExRepository asdExRepository;

    public ASDExDBService(ASDExRepository asdExRepository) {
        this.asdExRepository = asdExRepository;
    }

    public ASDExEntity saveASDExEntity(String username, String testType, String asdStatus, String confidence) {
        ASDExEntity entity = new ASDExEntity();
        entity.setUsername(username);
        entity.setTestType(testType);
        entity.setAsdStatus(asdStatus);
        entity.setConfidence(confidence);
        entity.setTestDate(LocalDateTime.now());

        return asdExRepository.save(entity);
    }

    public List<ASDExEntity> getASDExEntitiesByUsername(String username) {
        return asdExRepository.findByUsername(username);
    }

    public Optional<ASDExEntity> getASDExEntityById(String id) {
        return asdExRepository.findById(id);
    }

    public ASDExEntity updateASDExEntity(String id, String asdStatus, String confidence) {
        Optional<ASDExEntity> optionalEntity = asdExRepository.findById(id);
        if (optionalEntity.isPresent()) {
            ASDExEntity entity = optionalEntity.get();
            entity.setAsdStatus(asdStatus);
            entity.setConfidence(confidence);
            return asdExRepository.save(entity);
        }
        throw new RuntimeException("Entity with id " + id + " not found");
    }

    public void deleteASDExEntity(String id) {
        asdExRepository.deleteById(id);
    }
}
