package com.haozi.mayberight.session.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/getB")
    public B getB() {
        A a1 = new A("name1");
        A a2 = new A("name2");
        List<A> aList = new ArrayList<>();
        aList.add(a1);
        aList.add(a2);
        B b = new B();
        b.setaList(aList);

        String s = "{\"aList\":[{\"name\":\"name1\"},{\"name\":\"name2\"}]}";
        b  = JSON.parseObject(s, B.class);

        JSONObject jsonObject = JSON.parseObject(s);
        b = jsonObject.getObject(s, B.class);

        return b;
    }


    public static class A {
        String name;

        public A() {
        }

        public A(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class B {
        List<A> aList;

        public List<A> getaList() {
            return aList;
        }

        public void setaList(List<A> aList) {
            this.aList = aList;
        }
    }

}
