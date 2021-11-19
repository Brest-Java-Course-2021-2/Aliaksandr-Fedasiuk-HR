package com.epam.brest.web_app;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Deprecated
public class MvcMappingExceptionResolver extends SimpleMappingExceptionResolver {

    public MvcMappingExceptionResolver() {
        setWarnLogCategory(MvcMappingExceptionResolver.class.getName());
    }

    @Override
    public String buildLogMessage(Exception e, HttpServletRequest req) {
        ExceptionHandlerExceptionResolver r;
        return e.getLocalizedMessage();
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest req,
                                              HttpServletResponse resp,
                                              Object handler,
                                              Exception ex) {
        ModelAndView mav = super.doResolveException(req, resp, handler, ex);
        mav.addObject("timestamp", new Date());
        mav.addObject("url", req.getRequestURL());
        return mav;
    }
}
