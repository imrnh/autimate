package com.thjavafest.wigglewonders.Wigglewonders.controller;

import ai.onnxruntime.OnnxTensor;
import com.thjavafest.wigglewonders.Wigglewonders.entity.QuestionExamEntity;
import com.thjavafest.wigglewonders.Wigglewonders.repo.QuestionExamRepository;
import com.thjavafest.wigglewonders.Wigglewonders.services.AutismExService;
import com.thjavafest.wigglewonders.Wigglewonders.services.BucketStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AutismExController {
    @Autowired
    AutismExService autismExService;

    @Autowired
    BucketStorageService bucketStorageService;

    @PostMapping("/asd/test/q10")
    public String submitQuestionnaire(@RequestBody QuestionExamEntity questionnaire) {
        return autismExService.q10Test(questionnaire);
    }


    @GetMapping("/asd/test/video")
    public String fetchPreSignedUrl() throws Exception {
        return bucketStorageService.getPreSignedUrl();
    }

}
