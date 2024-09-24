package com.thjavafest.wigglewonders.Wigglewonders.controller;

import ai.onnxruntime.OnnxTensor;
import com.thjavafest.wigglewonders.Wigglewonders.entity.ASDExEntity;
import com.thjavafest.wigglewonders.Wigglewonders.entity.QuestionExamEntity;
import com.thjavafest.wigglewonders.Wigglewonders.repo.ASDExRepository;
import com.thjavafest.wigglewonders.Wigglewonders.repo.QuestionExamRepository;
import com.thjavafest.wigglewonders.Wigglewonders.services.AutismExQ10Service;
import com.thjavafest.wigglewonders.Wigglewonders.services.ASDExDBService;
import com.thjavafest.wigglewonders.Wigglewonders.services.BucketStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AutismExController {
    @Autowired
    AutismExQ10Service autismExQ10Service;

    @Autowired
    BucketStorageService bucketStorageService;

    @Autowired
    ASDExRepository asdExRepository;

    @Autowired  ASDExDBService asdExDBService;



    @PostMapping("/api/ex/q10")
    public String submitQuestionnaire(@RequestBody QuestionExamEntity questionnaire) {
        String username = "101011";
        String testType = "Questionnaire";
        String confidence = autismExQ10Service.q10Test(questionnaire);
        String asdStatus = Double.parseDouble(confidence) > 0.5 ? "1" : "0";

        ASDExEntity savedEntity = asdExDBService.saveASDExEntity(username, testType, asdStatus, confidence);
        ResponseEntity.ok(savedEntity);
        return confidence;
    }


    @GetMapping("/api/ex/pre-signed-url")
    public String fetchPreSignedUrl() throws Exception {
        return bucketStorageService.getPreSignedUrl();
    }

    @PostMapping("/api/ex/invoke-video-ex")
    public String invokeServerless(){
        return "";
    }

    @GetMapping("/api/ex/get-all-test/{username}")
    public void getAllTest(@PathVariable String username){
        List<ASDExEntity> testInfos = asdExRepository.findByUsername(username);
    }
}
