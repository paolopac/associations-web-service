package com.faraday.webapp.ControllerTest.CRUDTests;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.faraday.webapp.Application;
import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.repository.AssociationRepository;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
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

  @Test
  public void testCreateAssociation() throws Exception {

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
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associations ASD RUNNERS inserita con successo"))
    .andReturn();
    
    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void testGetAllAssociations() throws Exception {

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
    
    mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/all")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.[*][?(@.nome == 'ASD RUNNERS')]").exists())
    .andReturn();

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void testSelByCodAssociations() throws Exception {

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

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void testSelByFIDALId() throws Exception {

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

    mockMvc.perform(MockMvcRequestBuilders.get("/associations/search/fidalid/BA000")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.fidalId").exists())
    .andExpect(jsonPath("$.fidalId").value("BA000"))
    .andReturn();

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void  testUpdateAssociations() throws Exception {

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

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void testDeleteAssociations() throws Exception {

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

    Associations associationFinded = associationRepository.findByFidalId("BA000");

    mockMvc.perform(MockMvcRequestBuilders.delete("/associations/delete/"+associationFinded.getId()) 
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.code").value("200 OK"))
    .andExpect(jsonPath("$.message").value("Associazione "+associationFinded.getNome()+" con id " + associationFinded.getId() + " cancellata con successo"))
    .andDo(print());

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

  @Test
  public void verifyAssociationById() throws Exception {

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

    int associationId = associationRepository.findByFidalId("BA000").getId();
    mockMvc.perform(MockMvcRequestBuilders.get("/associations/verify/code/"+associationId)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andReturn();

    associationRepository.delete(associationRepository.findByFidalId("BA000"));

  }

}
