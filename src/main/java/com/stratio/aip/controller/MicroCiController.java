package com.stratio.aip.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MicroCiController {

  @RequestMapping("/")
  public String protectedResource(HttpServletRequest request) {
    return "Hello from CI World!";
  }

}
