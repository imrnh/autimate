package org.ww.wigglew.controller.aex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



//import ai.onnxruntime.OnnxTensor;
//import com.thjavafest.wigglewonders.Wigglewonders.entity.ASDExEntity;
//import com.thjavafest.wigglewonders.Wigglewonders.entity.QuestionExamEntity;
//import com.thjavafest.wigglewonders.Wigglewonders.repo.ASDExRepository;
//import com.thjavafest.wigglewonders.Wigglewonders.repo.QuestionExamRepository;
//import com.thjavafest.wigglewonders.Wigglewonders.services.ASDExServerlessInvokeService;
//import com.thjavafest.wigglewonders.Wigglewonders.services.AutismExQ10Service;
//import com.thjavafest.wigglewonders.Wigglewonders.services.ASDExDBService;
//import com.thjavafest.wigglewonders.Wigglewonders.services.BucketStorageService;


import org.ww.wigglew.entity.aex.ASDExEntity;
import org.ww.wigglew.entity.aex.QuestionExamEntity;
import org.ww.wigglew.repo.aex.ASDExRepository;
import org.ww.wigglew.service.aex.ASDExDBService;
import org.ww.wigglew.service.aex.ASDExServerlessInvokeService;
import org.ww.wigglew.service.aex.AutismExQ10Service;
import org.ww.wigglew.service.BucketStorageService;

@RestController
@RequestMapping("/api/v1/aex/")
public class AutismExController {
    @Autowired
    AutismExQ10Service autismExQ10Service;

    @Autowired
    BucketStorageService bucketStorageService;

    @Autowired
    ASDExRepository asdExRepository;

    @Autowired
    ASDExDBService asdExDBService;

    @Autowired
    ASDExServerlessInvokeService asdExServerlessInvokeService;

    @Value("${SERVERLESS_ML_VIDEO_URL}")
    private String serverlessBaseUrl;

    @PostMapping("/questions")
    public String submitQuestionnaire(@RequestBody QuestionExamEntity questionnaire) {
        String username = "110011";
        String testType = "Questionnaire";
        String confidence = autismExQ10Service.q10Test(questionnaire);
        String asdStatus = Double.parseDouble(confidence) > 0.5 ? "1" : "0";
        String requestID = "";

        ASDExEntity savedEntity = asdExDBService.saveASDExEntity(username, testType, asdStatus, confidence, requestID);
        ResponseEntity.ok(savedEntity);
        return confidence;
    }


    @GetMapping("/url/presigned")
    public String fetchPreSignedUrl() throws Exception {
        return bucketStorageService.getPreSignedUrl("mp4");
    }

    @PostMapping("/serverless/invoke/{video_path}")
    public HttpStatus invokeServerless(@PathVariable String video_path) throws Exception {
        Map<String, String> queryParams = Map.of(
                "username", "110011",
                "video_name", video_path
        );

        String err = asdExServerlessInvokeService.invokeServerless(serverlessBaseUrl, queryParams);

        if (err == null){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/tests/{username}")
    public void getAllTest(@PathVariable String username){
        List<ASDExEntity> testInfos = asdExRepository.findByUsername(username);
    }


    /**
     * @return List all the tests the user has conducted.
     */
    @GetMapping("/results/username/{username}")
    public List<HashMap<String, Object>> getDocumentsByUsername(@PathVariable String username) {
        return asdExDBService.getDocumentsByUsername(username);
    }

    /**
     * @return Return a single test's result. Necessary for Video Invoking.
     */
    @GetMapping("/result/{requestID}")
    public HashMap<String, Object> getDocumentByRequestID(@PathVariable String requestID) {
        return asdExDBService.getDocumentByRequestID(requestID);
    }
}
