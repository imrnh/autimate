package com.thjavafest.wigglewonders.Wigglewonders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {

    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }
}
