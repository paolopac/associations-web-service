package com.faraday.webapp.ControllerTest;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.IOException;

import com.faraday.webapp.Application;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(OrderAnnotation.class)
public class AssociationsControllerTests {
  
  private MockMvc mockMvc;

  @Autowired
	private WebApplicationContext wac;

  @BeforeEach
	public void setup() throws JSONException, IOException
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

  private String JsonData = "{\r\n" + 
  "	\"nome\": \"ASD HAPPY RUNNERS ALTAMURA\",\r\n" + 
  "	\"idFidal\": \"BA703\",\r\n" + 
  "	\"cf\":	\"CQVPLA878UU7YY7U\",\r\n" + 
  "	\"piva\":	\"07004700725\",\r\n" + 
  "	\"citta\":	\"altamura\",\r\n" + 
  "	\"indirizzo\":	\"Via S. Quasimodo 5\",\r\n" + 
  "	\"cap\":	\"70022\",\r\n" + 
  "	\"provincia\":	\"bari\",\r\n" + 
  "	\"telefono\":	\"3333333333\",\r\n" + 
  "	\"email\":	\"asdhappyrunners@gmail.com\"\r\n" + 
  "}";

  @Test
  @Order(1) 
  public void testCreateAssociation() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonData)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associations ASD HAPPY RUNNERS ALTAMURA inserita con successo"))
    .andDo(print());
    
  }

  private String JsonDataErrorBindingNomeNN = "{\r\n" + 
  
  "	\"idFidal\": \"BA703\",\r\n" + 
  "	\"cf\":	\"CQVPLA878UU7YY7U\",\r\n" + 
  "	\"piva\":	\"07004700725\",\r\n" + 
  "	\"citta\":	\"altamura\",\r\n" + 
  "	\"indirizzo\":	\"Via S. Quasimodo 5\",\r\n" + 
  "	\"cap\":	\"70022\",\r\n" + 
  "	\"provincia\":	\"bari\",\r\n" + 
  "	\"telefono\":	\"3333333333\",\r\n" + 
  "	\"email\":	\"asdhappyrunners@gmail.com\"\r\n" + 
  "}";

  @Test
  @Order(2)
  public void testBindingExceptionNomeNN() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataErrorBindingNomeNN)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value(400))
    .andExpect(jsonPath("$.message").value("Il nome NON deve essere NULL"))
    .andDo(print());

  }

  private String JsonDataErrorBindingNomeSize = "{\r\n" + 
  "	\"nome\": \"ASD HAPPY RUNNERS ALTAMURAASD HAPPY RUNNERS ALTAMURAASD\",\r\n" + 
  "	\"idFidal\": \"BA703\",\r\n" + 
  "	\"cf\":	\"CQVPLA878UU7YY7U\",\r\n" + 
  "	\"piva\":	\"07004700725\",\r\n" + 
  "	\"citta\":	\"altamura\",\r\n" + 
  "	\"indirizzo\":	\"Via S. Quasimodo 5\",\r\n" + 
  "	\"cap\":	\"70022\",\r\n" + 
  "	\"provincia\":	\"bari\",\r\n" + 
  "	\"telefono\":	\"3333333333\",\r\n" + 
  "	\"email\":	\"asdhappyrunners@gmail.com\"\r\n" + 
  "}";

  @Test
  @Order(3)
  public void testBindingExceptionNomeSize() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataErrorBindingNomeSize)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value(400))
    .andExpect(jsonPath("$.message").value("Il nome deve avere massimo 40 caratteri"))
    .andDo(print());

  }

  private String JsonDataList = "[" + JsonData + "]";

  @Test
  @Order(4)
  public void testGetAllAssociations() throws Exception {
    
    mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/all")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$", hasSize(1)))
    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    .andExpect(content().json(JsonDataList))
    .andReturn();

  }
}
