package org.ww.wigglew.aex.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.ww.wigglew.aex.entity.QuestionExamEntity;


public interface QuestionExamRepository extends MongoRepository<QuestionExamEntity, Long> {
}

