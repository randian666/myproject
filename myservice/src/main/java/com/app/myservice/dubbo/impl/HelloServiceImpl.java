package com.app.myservice.dubbo.impl;

import com.app.myclient.interfaces.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by liuxun on 2015/11/19.
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService{
    public String sayHelloService(String name) {
        return "Hello "+name;
    }

    public String helloworldService() {
        return "hello world dubbo";
    }
}
