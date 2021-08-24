package com.faraday.webapp.ControllerTest.ExceptionsTests;

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


import java.io.IOException;

import com.faraday.webapp.Application;
import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.repository.AssociationRepository;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class ExceptionsControllerTests {

  @Autowired
  private AssociationRepository associationRepository;
  
  private MockMvc mockMvc;

  @Autowired
	private WebApplicationContext wac;

  @BeforeEach
	public void setup() throws JSONException, IOException
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

 
  @Test
  public void testDuplicateExceptionError() throws Exception {

    Associations association = new Associations();
    association.setNome("ASD RUNNERS");
    association.setFidalId("BA000");
    association.setCF("CQVPLA878UU7YY7U");
    association.setPIva("07004700725");
    association.setCitta("altamura");
    association.setIndirizzo("Via che non c'è");
    association.setCAP("70022");
    association.setProvincia("bari");
    association.setTelefono("3333333333");
    association.setEMail("asdhappyrunners@gmail.com");
    associationRepository.save(association);

    String JsonData = "{\r\n" + 
    "	\"nome\": \"ASD RUNNERS\",\r\n" + 
    "	\"fidalId\": \"BA000\",\r\n" + 
    "	\"cf\":	\"CQVPLA878UU7YY7U\",\r\n" + 
    "	\"piva\":	\"07004700725\",\r\n" + 
    "	\"citta\":	\"altamura\",\r\n" + 
    "	\"indirizzo\":	\"Via che non c'è\",\r\n" + 
    "	\"cap\":	\"70022\",\r\n" + 
    "	\"provincia\":	\"bari\",\r\n" + 
    "	\"telefono\":	\"3333333333\",\r\n" + 
    "	\"email\":	\"asdhappyrunners@gmail.com\"\r\n" + 
    "}";

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonData)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotAcceptable())
    .andExpect(jsonPath("$.code").value(406))
    .andExpect(jsonPath("$.message").value("Associazione con FIDAL ID BA000 presente"))
    .andReturn();
    
    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void testBindingExceptionNomeNNError() throws Exception {

    String JsonDataErrorBindingNomeNN = "{\r\n" + 
    "	\"fidalId\": \"BA000\",\r\n" + 
    "	\"cf\":	\"CQVPLA878UU7YY7U\",\r\n" + 
    "	\"piva\":	\"07004700725\",\r\n" + 
    "	\"citta\":	\"altamura\",\r\n" + 
    "	\"indirizzo\":	\"Via che non c'è\",\r\n" + 
    "	\"cap\":	\"70022\",\r\n" + 
    "	\"provincia\":	\"bari\",\r\n" + 
    "	\"telefono\":	\"3333333333\",\r\n" + 
    "	\"email\":	\"asdhappyrunners@gmail.com\"\r\n" + 
    "}";

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataErrorBindingNomeNN)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value(400))
    .andExpect(jsonPath("$.message").value("Il campo nome non deve essere vuoto"))
    .andDo(print());

  }

  @Test
  public void testBindingExceptionNomeSizeError() throws Exception {

    String JsonDataErrorBindingNomeSize = "{\r\n" + 
    "	\"nome\": \"ASD HAPPY RUNNERS ALTAMURAASD HAPPY RUNNERS ALTAMURAASD\",\r\n" + 
    "	\"fidalId\": \"BA000\",\r\n" + 
    "	\"cf\":	\"CQVPLA878UU7YY7U\",\r\n" + 
    "	\"piva\":	\"07004700725\",\r\n" + 
    "	\"citta\":	\"altamura\",\r\n" + 
    "	\"indirizzo\":	\"Via che non c'è\",\r\n" + 
    "	\"cap\":	\"70022\",\r\n" + 
    "	\"provincia\":	\"bari\",\r\n" + 
    "	\"telefono\":	\"3333333333\",\r\n" + 
    "	\"email\":	\"asdhappyrunners@gmail.com\"\r\n" + 
    "}";

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataErrorBindingNomeSize)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value(400))
    .andExpect(jsonPath("$.message").value("Il nome deve avere massimo 40 caratteri"))
    .andDo(print());

  }

  // @Test
  // @Order(5)
  // public void testSelAllAssociationsNotFoundError() throws Exception {

  //   mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/all")
  //   .accept(MediaType.APPLICATION_JSON))
  //   .andExpect(status().isNotFound())
  //   .andExpect(jsonPath("$.message").value("Nessuna associazione presente"))
  //   .andReturn();    

  // }

  @Test
  public void  testUpdateDuplicateExceptionError() throws Exception {

    Associations association1 = new Associations();
    association1.setCAP("70022");
    association1.setCitta("altamura");
    association1.setEMail("asdhappyrunners@gmail.com");
    association1.setFidalId("B0000");
    association1.setIndirizzo("Via che non c'è");
    association1.setNome("ASD RUNNERS");
    association1.setPIva("07004700725");
    association1.setProvincia("Bari");
    association1.setTelefono("3333333333");
    associationRepository.save(association1);

    Associations association2 = new Associations();
    association2.setCAP("70022");
    association2.setCitta("altamura");
    association2.setEMail("asdhappyrunners@gmail.com");
    association2.setFidalId("BAAAA");
    association2.setIndirizzo("Via che non c'è");
    association2.setNome("ASD RUNNERS");
    association2.setPIva("07004700725");
    association2.setProvincia("Bari");
    association2.setTelefono("3333333333");
    associationRepository.save(association2);

    Integer associationId = associationRepository.findByFidalId("B0000").getId();

    String JsonDataModify = "{\r\n" + 
    "	\"id\": \""+ associationId +"\",\r\n" + 
    "	\"nome\": \"ASD RUNNERS 2\",\r\n" + 
    "	\"fidalId\": \"BAAAA\",\r\n" + 
    "	\"cf\":	\"CQVPLA878UU7YY72\",\r\n" + 
    "	\"piva\":	\"07004700722\",\r\n" + 
    "	\"citta\":	\"altamura in puglia\",\r\n" + 
    "	\"indirizzo\":	\"Via che non c'è 2\",\r\n" + 
    "	\"cap\":	\"70020\",\r\n" + 
    "	\"provincia\":	\"Milano\",\r\n" + 
    "	\"telefono\":	\"3333333332\",\r\n" + 
    "	\"email\":	\"asdhappyrunners2@gmail.com\"\r\n" + 
    "}";

    mockMvc.perform(MockMvcRequestBuilders.put("/associations/update")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataModify)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotAcceptable())
    .andExpect(jsonPath("$.code").value(406))
    .andExpect(jsonPath("$.message").value("Associazione con FIDAL ID BAAAA presente"))
    .andDo(print());

    associationRepository.delete(associationRepository.findByFidalId("BAAAA"));
    associationRepository.delete(associationRepository.findByFidalId("B0000"));

  }

  @Test
  public void testUpdateBindingExceptionNomeSizeError() throws Exception {

    Associations association1 = new Associations();
    association1.setCAP("70022");
    association1.setCitta("altamura");
    association1.setEMail("asdhappyrunners@gmail.com");
    association1.setFidalId("BA000");
    association1.setIndirizzo("Via che non c'è");
    association1.setNome("ASD RUNNERS");
    association1.setPIva("07004700725");
    association1.setProvincia("Bari");
    association1.setTelefono("3333333333");
    associationRepository.save(association1);

    Integer associationId = associationRepository.findByFidalId("BA000").getId();

    String JsonDataErrorBindingNomeSize = "{\r\n" + 
    "	\"id\": \""+ associationId +"\",\r\n" + 
    "	\"nome\": \"ASD RUNNERS 2ASD RUNNERS 2ASD RUNNERS 2ASD RUNNERS 2ASD RUNNERS 2\",\r\n" + 
    "	\"fidalId\": \"BA000\",\r\n" + 
    "	\"cf\":	\"CQVPLA878UU7YY72\",\r\n" + 
    "	\"piva\":	\"07004700722\",\r\n" + 
    "	\"citta\":	\"altamura in puglia\",\r\n" + 
    "	\"indirizzo\":	\"Via che non c'è 2\",\r\n" + 
    "	\"cap\":	\"70020\",\r\n" + 
    "	\"provincia\":	\"Milano\",\r\n" + 
    "	\"telefono\":	\"3333333332\",\r\n" + 
    "	\"email\":	\"asdhappyrunners2@gmail.com\"\r\n" + 
    "}";

    mockMvc.perform(MockMvcRequestBuilders.put("/associations/update")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataErrorBindingNomeSize)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value(400))
    .andExpect(jsonPath("$.message").value("Il nome deve avere massimo 40 caratteri"))
    .andDo(print());

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }


  @Test
  public void testUpdateNotFoundExceptionError() throws Exception {

    String JsonDataModify = "{\r\n" + 
  "	\"id\": \"9999999\",\r\n" + 
  "	\"nome\": \"ASD RUNNERS\",\r\n" + 
  "	\"fidalId\": \"BA000\",\r\n" + 
  "	\"cf\":	\"CQVPLA878UU7YY72\",\r\n" + 
  "	\"piva\":	\"07004700722\",\r\n" + 
  "	\"citta\":	\"altamura in puglia\",\r\n" + 
  "	\"indirizzo\":	\"Via che non c'è 2\",\r\n" + 
  "	\"cap\":	\"70020\",\r\n" + 
  "	\"provincia\":	\"Milano\",\r\n" + 
  "	\"telefono\":	\"3333333332\",\r\n" + 
  "	\"email\":	\"asdhappyrunners2@gmail.com\"\r\n" + 
  "}";

    mockMvc.perform(MockMvcRequestBuilders.put("/associations/update")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataModify)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.code").value(404))
    .andExpect(jsonPath("$.message").value("Nessuna associazione presente con id 9999999"))
    .andDo(print());

  }

  @Test
  public void testDeleteAssociations() throws Exception {

    Associations association1 = new Associations();
    association1.setCAP("70022");
    association1.setCitta("altamura");
    association1.setEMail("asdhappyrunners@gmail.com");
    association1.setFidalId("BA000");
    association1.setIndirizzo("Via che non c'è");
    association1.setNome("ASD RUNNERS");
    association1.setPIva("07004700725");
    association1.setProvincia("Bari");
    association1.setTelefono("3333333333");
    associationRepository.save(association1);

    Associations association = associationRepository.findByFidalId("BA000");
    mockMvc.perform(MockMvcRequestBuilders.delete("/associations/delete/"+association.getId()) 
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associazione "+association.getNome()+" con id " + association.getId() + " cancellata con successo"))
    .andDo(print());

  }

  @Test
  public void testDeleteNotFoundExceptionError() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/associations/delete/99999999")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.code").value(404))
    .andExpect(jsonPath("$.message").value("Nessuna associazione presente con id 99999999"))
    .andDo(print());

  }
  
  @Test
  public void NotFoundException_verifyAssociationById() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/associations/verify/code/9999999")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.code").value(404))
    .andExpect(jsonPath("$.message").value("Nessuna associazione presente con id 9999999"))
    .andDo(print());

  }
}
