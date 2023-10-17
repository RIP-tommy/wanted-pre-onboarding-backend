package com.wanted.onboarding.unit;

import com.wanted.onboarding.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplyRepository applyRepository;
}
