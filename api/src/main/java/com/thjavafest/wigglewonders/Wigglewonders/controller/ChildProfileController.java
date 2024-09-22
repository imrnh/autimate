package com.thjavafest.wigglewonders.Wigglewonders.controller;

import com.thjavafest.wigglewonders.Wigglewonders.entity.ChildEntity;
import com.thjavafest.wigglewonders.Wigglewonders.repo.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChildProfileController {
    @Autowired
    ChildRepository childRepository;

    @GetMapping("/save")
    public String save(@RequestBody ChildEntity child) {
        childRepository.save(child);
        return  "Things worked";
    }
}
