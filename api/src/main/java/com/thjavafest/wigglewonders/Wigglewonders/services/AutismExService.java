package com.thjavafest.wigglewonders.Wigglewonders.services;

import com.thjavafest.wigglewonders.Wigglewonders.entity.QuestionExamEntity;
import org.springframework.stereotype.Service;
import ai.onnxruntime.*;

import java.nio.FloatBuffer;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *  Contain codes for model calling and evaluation.
 *
 */

@Service public class AutismExService {

    private final OrtEnvironment env = OrtEnvironment.getEnvironment();

    public String q10Test(QuestionExamEntity questionnaire) {
        String modelPath = "src/main/resources/static/q10_autism_detection_model.onnx";
        try (OrtSession session = this.env.createSession(modelPath)) {
            float[] inputArray = this.listToFloatArray(questionnaire.getQuestionAnswers());
            OnnxTensor onnxTensor = this.createOnnxTensor(inputArray);
            Map<String, OnnxTensor> inputs = Map.of("input", onnxTensor);
            OrtSession.Result inf_res = session.run(inputs);

            float[] output = (float[]) inf_res.get(0).getValue();
            float value = output[0];

            return String.format("%.2f", value);

        } catch (OrtException e) {
            return e.getMessage();
        }
    }

    private float[] listToFloatArray(List<Integer> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).floatValue();
        }
        return array;
    }

    private OnnxTensor createOnnxTensor(float[] inputArray) throws OrtException {
        long[] shape = new long[]{inputArray.length}; // Shape = (14,)
        return OnnxTensor.createTensor(this.env, FloatBuffer.wrap(inputArray), shape);
    }
}