package com.haozi.mayberight.session.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @Description
 * @date 2018-11-27 14:42
 */
@RestController
@RequestMapping("/index")
public class HomeController {

    @GetMapping
    public String index() {
        return "Hello World!";
    }

}
