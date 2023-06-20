package com.mao.tytmistake.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class BaseControllerTests {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

}
