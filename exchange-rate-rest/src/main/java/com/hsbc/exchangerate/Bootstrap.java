package com.hsbc.exchangerate;

import com.hsbc.exchangerate.config.ApplicationConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by guneg on 06/05/2016.
 */
public class Bootstrap extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationConfig.class);
    }

    /*@Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }*/
}
