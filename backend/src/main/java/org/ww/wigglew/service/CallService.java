package org.ww.wigglew.service;


import com.squareup.okhttp.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ww.wigglew.entity.CallEntity;
import org.ww.wigglew.repo.CallRepository;

import java.util.List;

@Service
public class CallService {

    @Autowired
    private CallRepository callRepository;

    public boolean deleteCall(String callId) {
        try {
            CallEntity call = callRepository.findByCallId(callId).orElseThrow();
            callRepository.delete(call);

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean addCall(String callId) {
        try {
            CallEntity call = new CallEntity();
            call.setCallId(callId);
            callRepository.save(call);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<CallEntity> getAll(){
        return callRepository.findAll();
    }
}
