package com.faraday.webapp.ControllerTest;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.runners.MethodSorters;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AssociationControllerTests {
  
  private MockMvc mockMvc;

  @Autowired
	private WebApplicationContext wac;

  @Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

  String JsonData = "{\r\n" + 
  "	\"codAssociation\":	\"67109999\",\r\n" + 
  "}";
}
