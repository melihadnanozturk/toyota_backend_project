package com.mao.tytconduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class BaseControllerTests {

    @Autowired
    protected MockMvc mockMvc;

}
