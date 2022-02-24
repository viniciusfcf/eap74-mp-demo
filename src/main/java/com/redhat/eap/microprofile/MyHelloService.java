package com.redhat.eap.microprofile;

import org.eclipse.microprofile.opentracing.Traced;

@Traced
public class MyHelloService {

    String createHelloMessage(String name) {
        return "Hello, " + name + "!";
    }

}
