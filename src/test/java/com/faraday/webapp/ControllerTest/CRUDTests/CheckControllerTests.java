package com.faraday.webapp.ControllerTest.CRUDTests;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.faraday.webapp.Application;
import com.faraday.webapp.entities.An003Associtation;
import com.faraday.webapp.repository.An003Repository;

@SpringBootTest
@ActiveProfiles({"test"})
@ContextConfiguration(classes = Application.class)
public class CheckControllerTests {
  
  private MockMvc mockMvc;

  @Autowired
	private WebApplicationContext wac;

  @BeforeEach
	public void setup() throws JSONException, IOException
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

  @Test
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  public void testCheckHealth() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/check/health")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andReturn();

  }

}
