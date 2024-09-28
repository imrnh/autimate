package org.ww.wigglew.service.aex;


import org.springframework.stereotype.Service;
import org.ww.wigglew.entity.aex.ASDExEntity;
import org.ww.wigglew.repo.aex.ASDExRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ASDExDBService {

    private final ASDExRepository asdExRepository;

    public ASDExDBService(ASDExRepository asdExRepository) {
        this.asdExRepository = asdExRepository;
    }

    public ASDExEntity saveASDExEntity(String username, String testType, String asdStatus, String confidence, String requestID) {
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

    public List<HashMap<String, Object>> getDocumentsByUsername(String username) {
        List<ASDExEntity> documents = asdExRepository.findByUsername(username);
        return documents.stream()
                .map(this::documentToMap)
                .collect(Collectors.toList());
    }

    public HashMap<String, Object> getDocumentByRequestID(String requestID) {
        Optional<ASDExEntity> document = asdExRepository.findByRequestID(requestID);
        return documentToMap(document);
    }

    private HashMap<String, Object> documentToMap(ASDExEntity document) {
        HashMap<String, Object> map = new HashMap<>();
        if (document != null) {
            map.put("id", document.getId());
            map.put("username", document.getUsername());
            map.put("requestID", document.getRequestID());
            map.put("testType", document.getTestType());
            map.put("testDate", document.getTestDate());
            map.put("asdStatus", document.getAsdStatus());
            map.put("confidence", document.getConfidence());
        }
        return map;
    }

    private HashMap<String, Object> documentToMap(Optional<ASDExEntity> document) {
        return document.map(this::documentToMap).orElse(new HashMap<>());
    }
}
