package com.stratio.security.authorization.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ProtectedController.class)
public class ProtectedControllerTest {

  @Autowired
  protected MockMvc mvc;

  @Test
  public void HelloControllerOk() throws Exception {

    this.mvc.perform(get("/")
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

  }

}
