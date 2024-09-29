package org.ww.wigglew.controller.aex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


import org.ww.wigglew.config.jwt.JWTExtractorService;
import org.ww.wigglew.entity.aex.ASDExEntity;
import org.ww.wigglew.entity.aex.QuestionExamEntity;
import org.ww.wigglew.models.request.AsdExRequest;
import org.ww.wigglew.repo.aex.ASDExRepository;
import org.ww.wigglew.service.ChildService;
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

    @Autowired
    private JWTExtractorService jwtExtractorService;

    @Autowired
    private ChildService childService;

    @Value("${SERVERLESS_ML_VIDEO_URL}")
    private String serverlessBaseUrl;

    @Value("${SERVERLESS_SECRET_TOKEN}")
    private String serverlessSecretToken;

    private static final Logger logger = LoggerFactory.getLogger(AutismExController.class);

    @GetMapping("/invoke")
    public ResponseEntity<?> invokeServerless(@RequestBody AsdExRequest asdExRequest, @RequestHeader("Authorization") String jwtToken) {
        logger.info("Received /invoke request");
        try {
            String childId = childService.getActiveChild(jwtToken);
            logger.debug("Child ID: {}", childId);

            asdExRequest.setChildId(childId);
            asdExRequest.setSecret_token(serverlessSecretToken);

            String serverlessUrl = "https://hossen1907012--autism-video-analysis-fn-main.modal.run";
            logger.info("Invoking serverless function at {}", serverlessUrl);

            String err = asdExServerlessInvokeService.invokeServerless(serverlessUrl, asdExRequest);

            if (err == null) {
                logger.info("Serverless invocation successful");
                return ResponseEntity.ok("Invocation successful");
            } else {
                logger.error("Serverless invocation failed with error: {}", err);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invocation failed");
            }
        } catch (Exception e) {
            logger.error("Exception during /invoke processing", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    /**
     * @return Pre-signed url to access digitalocean s3
     */
    @GetMapping("/url/presigned")
    public String fetchPreSignedUrl() throws Exception {
        return bucketStorageService.getPreSignedUrl("mp4");
    }

//    @PostMapping("/serverless/invoke/{video_path}")
//    public HttpStatus invokeServerless(@PathVariable String video_path) throws Exception {
//        Map<String, String> queryParams = Map.of(
//                "username", "110011",
//                "video_name", video_path
//        );
//
//        String err = asdExServerlessInvokeService.invokeServerless(serverlessBaseUrl, queryParams);
//
//        if (err == null){
//            return HttpStatus.OK;
//        }
//        return HttpStatus.BAD_REQUEST;
//    }
    //
//    @PostMapping("/questions")
//    public String submitQuestionnaire(@RequestHeader("Authorization") String jwtToken, @RequestBody QuestionExamEntity questionnaire) {
//        String token = jwtToken.substring(7);
//        String username = jwtExtractorService.extractUsername(token);
//
//        String testType = "Questionnaire";
//        String confidence = autismExQ10Service.q10Test(questionnaire);
//        String asdStatus = Double.parseDouble(confidence) > 0.5 ? "1" : "0";
//
//        ASDExEntity savedEntity = asdExDBService.saveASDExEntity(username, testType, asdStatus, confidence, ""); //null request id.
//        ResponseEntity.ok(savedEntity);
//        return confidence;
//    }
//
//



    @GetMapping("/tests")
    public ResponseEntity<?> getAllTest(@RequestHeader("Authorization") String jwtToken){
        String childId = childService.getActiveChild(jwtToken);
        if(childId != null){
            return ResponseEntity.ok(asdExRepository.findByUsername(childId));
        }
        return ResponseEntity.status(400).body("No child found");
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
