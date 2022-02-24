package com.redhat.eap.microprofile;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.opentracing.Traced;

@Traced
@ApplicationScoped
public class MyHelloService {

    @Traced(operationName = "createHelloMessage")
    public String createHelloMessage(String name) {
        return "Hello, " + name + "!";
    }

}
