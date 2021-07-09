package com.faraday.webapp.RepositoryTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


import static org.assertj.core.api.Assertions.assertThat;


import com.faraday.webapp.Application;
import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.repository.AssociationRepository;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AssociationRepositoryTests {

  @Autowired
  private AssociationRepository associationRepository;
  
  @Test
  @Order(1)
  public void testAssociationsInsert(){

    Associations association = new Associations();
    association.setCAP("70022");
    association.setCitta("altamura");
    association.setEMail("asdhappyrunners@gmail.com");
    association.setIdFidal("BA703");
    association.setIndirizzo("Via S. Quasimodo ");
    association.setNome("ASD HAPPY RUNNERS ALTAMURA");
    association.setPIva("07004700725");
    association.setProvincia("Bari");
    association.setTelefono("3333333333");
    associationRepository.save(association);

  }

  @Test
  @Order(2)
  public void testAssociationsGetAll(){

    assertThat(associationRepository.findAll())
    .extracting(associations -> associations.getNome()).contains("ASD HAPPY RUNNERS ALTAMURA");
 
  }
}