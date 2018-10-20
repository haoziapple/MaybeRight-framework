package com.haozi.mayberight.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ASUS on 2018/10/20.
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @GetMapping("/test")
    public String test(String id) {
        return "success";
    }
}
