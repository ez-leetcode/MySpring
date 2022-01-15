package com.spring.MySpring.test;

import com.spring.MySpring.spring.Autowired;
import com.spring.MySpring.springmvc.Controller;
import com.spring.MySpring.springmvc.RequestMapping;
import com.spring.MySpring.springmvc.RequestParam;

@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/test")
    public String test(@RequestParam("test") String test){
        System.out.println(test);
        return "你是鼠吗";
    }

}
