package com.app.myweb.web;

import com.app.myclient.interfaces.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuxun on 2015/11/19.
 */
@Controller
@RequestMapping("/myweb")
public class TestAction {
    @Autowired
    private HelloService helloService;

    @RequestMapping(value="/index")
       public String index(HttpServletRequest request,
                           Model view){
        String name=helloService.sayHelloService("liuxun");
        String str=helloService.helloworldService();
        view.addAttribute("name",name);
        view.addAttribute("str",str);
        return "myindex";
    }

    @RequestMapping(value="/myjd")
    public String myjd(HttpServletRequest request,Model view){
        return "index";
    }
}
