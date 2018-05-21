package com.stratio.security.authorization.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MicroCiController {

  @RequestMapping("/")
  public String protectedResource(HttpServletRequest request) {
    return "Hello from CI World!"
  }

}
