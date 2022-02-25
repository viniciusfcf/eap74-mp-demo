package com.redhat.eap.microprofile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.Tracer.SpanBuilder;

@WebServlet(name = "my-servlet", urlPatterns = { "/my-servlet" })
public class MyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    @Metric(name = "servlet-counter")
    Counter counter;

    @Inject
    MyHelloService service;

    @Inject
    Tracer configuredTracer;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        counter.inc();
        SpanBuilder buildSpan = configuredTracer.buildSpan("doGet");
        Span span = buildSpan.start();
        try (Scope activateSpan = configuredTracer.activateSpan(span)) {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            writer.println("<h1>"+digaOla()+"</h1>");
            writer.close();
            span.finish();
        }
    }

    private String digaOla() {
        SpanBuilder buildSpan = configuredTracer.buildSpan("digaOla");
        Span span = buildSpan.start();
        try (Scope activateSpan = configuredTracer.activateSpan(span)) {
            String retorno = service.createHelloMessage("Servlet");
            span.finish();
            return retorno;
        }
    }


}