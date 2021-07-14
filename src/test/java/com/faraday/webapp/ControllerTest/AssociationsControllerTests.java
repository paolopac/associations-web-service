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


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.faraday.webapp.Application;
import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.repository.AssociationRepository;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(OrderAnnotation.class)
public class AssociationsControllerTests {

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

  private String JsonData = "{\r\n" + 
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

  @Test
  @Order(1) 
  public void testCreateAssociation() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonData)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associations ASD RUNNERS inserita con successo"))
    .andReturn();
    
  }

  @Test
  @Order(2)
  public void testDuplicateExceptionError() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonData)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotAcceptable())
    .andExpect(jsonPath("$.code").value(406))
    .andExpect(jsonPath("$.message").value("Associazione con FIDAL ID BA000 presente"))
    .andReturn();
    
  }

  @Test
  @Order(3)
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
    .andExpect(jsonPath("$.message").value("Il nome NON deve essere NULL"))
    .andDo(print());

  }

  private String JsonDataErrorBindingNomeSize = "{\r\n" + 
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

  @Test
  @Order(4)
  public void testBindingExceptionNomeSizeError() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/associations/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(JsonDataErrorBindingNomeSize)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value(400))
    .andExpect(jsonPath("$.message").value("Il nome deve avere massimo 40 caratteri"))
    .andDo(print());

  }

  @Test
  @Order(5)
  public void testGetAllAssociations() throws Exception {
    
    mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/all")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.[*][?(@.nome == 'ASD RUNNERS')]").exists())
    .andReturn();

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
  @Order(6)
  public void testSelByCodAssociations() throws Exception {

    List<Associations> associations = associationRepository.findAll();

    List<Associations> associationFinded = associations.stream()
    .filter(ass -> "ASD RUNNERS".equals(ass.getNome()))
    .collect(Collectors.toList());

    mockMvc.perform(MockMvcRequestBuilders
    .get("/associations/search/code/"+associationFinded.get(0).getId())
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.id").value(associationFinded.get(0).getId()))
    .andExpect(jsonPath("$.nome").exists())
		.andExpect(jsonPath("$.nome").value("ASD RUNNERS"))
    .andExpect(jsonPath("$.fidalId").exists())
		.andExpect(jsonPath("$.fidalId").value("BA000"))
    .andExpect(jsonPath("$.citta").exists())
		.andExpect(jsonPath("$.citta").value("altamura"))
    .andExpect(jsonPath("$.indirizzo").exists())
		.andExpect(jsonPath("$.indirizzo").value("Via che non c'è"))
    .andExpect(jsonPath("$.provincia").exists())
		.andExpect(jsonPath("$.provincia").value("bari"))
    .andExpect(jsonPath("$.telefono").exists())
		.andExpect(jsonPath("$.telefono").value("3333333333"))
    .andExpect(jsonPath("$.cf").exists())
		.andExpect(jsonPath("$.cf").value("CQVPLA878UU7YY7U"))
    .andExpect(jsonPath("$.piva").exists())
		.andExpect(jsonPath("$.piva").value("07004700725"))
    .andExpect(jsonPath("$.cap").exists())
		.andExpect(jsonPath("$.cap").value("70022"))
    .andExpect(jsonPath("$.email").exists())
		.andExpect(jsonPath("$.email").value("asdhappyrunners@gmail.com"))
    .andReturn();

  }
  
  @Test
  @Order(7)
  public void testSelByCodAssociationsNotFoundError() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/code/0")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.message").value("Nessuna associazione presente con id 0"))
    .andReturn();

  }

  @Test
  @Order(8)
  public void testSelByFIDALId() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/fidalid/BA000")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.fidalId").exists())
    .andExpect(jsonPath("$.fidalId").value("BA000"))
    .andReturn();

  }

  @Test
  @Order(9)
  public void  testUpdateAssociations() throws Exception {

    Integer associationId = associationRepository.findByFidalId("BA000").getId();

    String JsonDataModify = "{\r\n" + 
  "	\"id\": \""+ associationId +"\",\r\n" + 
  "	\"nome\": \"ASD RUNNERS 2\",\r\n" + 
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
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associazione ASD RUNNERS modificata con successo"))
    .andDo(print());

  }

  @Test
  @Order(10)
  public void  testUpdateDuplicateExceptionError() throws Exception {

    Associations association = new Associations();
    association.setCAP("70022");
    association.setCitta("altamura");
    association.setEMail("asdhappyrunners@gmail.com");
    association.setFidalId("BAAAA");
    association.setIndirizzo("Via che non c'è");
    association.setNome("ASD RUNNERS");
    association.setPIva("07004700725");
    association.setProvincia("Bari");
    association.setTelefono("3333333333");
    associationRepository.save(association);

    Integer associationId = associationRepository.findByFidalId("BA000").getId();

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

  }

  @Test
  @Order(11)
  public void testUpdateBindingExceptionNomeSizeError() throws Exception {

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

  }


  @Test
  @Order(12)
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
  @Order(13)
  public void testDeleteAssociations() throws Exception {

    Associations association = associationRepository.findByFidalId("BA000");
    mockMvc.perform(MockMvcRequestBuilders.delete("/associations/delete/"+association.getId()) 
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associazione "+association.getNome()+" con id " + association.getId() + " cancellata con successo"))
    .andDo(print());

    Associations association1 = associationRepository.findByFidalId("BAAAA");
    mockMvc.perform(MockMvcRequestBuilders.delete("/associations/delete/"+association1.getId()) 
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associazione "+association1.getNome()+" con id " + association1.getId() + " cancellata con successo"))
    .andDo(print());

  }

  @Test
  @Order(14)
  public void testDeleteNotFoundExceptionError() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/associations/delete/99999999")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.code").value(404))
    .andExpect(jsonPath("$.message").value("Nessuna associazione presente con id 99999999"))
    .andDo(print());

  }

}
