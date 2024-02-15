package com.faraday.webapp.RepositoryTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Collectors;

import com.faraday.webapp.Application;
import com.faraday.webapp.entities.An003Associtation;
import com.faraday.webapp.repository.An003Repository;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@ActiveProfiles({"test"})
@TestMethodOrder(OrderAnnotation.class)
public class AssociationRepositoryTests {

  @Autowired
  private An003Repository associationRepository;
  
  @Test
  @Order(1)
  public void testAssociationsInsert(){

    An003Associtation association = new An003Associtation();
    association.setCAP("70022");
    association.setCitta("altamura");
    association.setEMail("asdhappyrunners@gmail.com");
    association.setFidalId("BA000");
    association.setIndirizzo("Via che non c'è");
    association.setNome("ASD RUNNERS");
    association.setPIva("07004700725");
    association.setProvincia("Bari");
    association.setTelefono("3333333333");
    associationRepository.save(association);

    List<An003Associtation> associations = associationRepository.findAll();

    List<An003Associtation> associationFinded = associations.stream()
    .filter(ass -> "ASD RUNNERS".equals(ass.getNome()))
    .collect(Collectors.toList());

    assertThat(associationRepository.findById(associationFinded.get(0).getId())
    .orElse(null))
    .extracting(An003Associtation::getNome)
    .isEqualTo("ASD RUNNERS");

  }

  @Test
  @Order(2)
  public void testAssociationsGetAll(){

    assertThat(associationRepository.findAll())
    .extracting(associations -> associations.getNome()).contains("ASD RUNNERS");
 
  }

  @Test
  @Order(3)
  public void testSelByAssociationsId(){

    List<An003Associtation> associations = associationRepository.findAll();

    List<An003Associtation> associationFinded = associations.stream()
    .filter(ass -> "ASD RUNNERS".equals(ass.getNome()))
    .collect(Collectors.toList());

    assertThat(associationRepository.findById(associationFinded.get(0).getId())
    .orElse(null))
    .extracting(An003Associtation::getNome)
    .isEqualTo("ASD RUNNERS");

  }

  @Test
  @Order(4)
  public void testSelByFIDALId() {

    assertThat(associationRepository.findByFidalId("BA000"))
    .extracting(An003Associtation::getFidalId)
    .isEqualTo("BA000");
      
  }

  @Test
  @Order(5)
  public void testUpdateAssociations() {

    An003Associtation association = associationRepository.findByFidalId("BA000");

    association.setNome("ASD RUNNER 2");
    association.setCAP("70033");
    association.setCitta("altamura in puglia");
    association.setCF("CQVPLA878UU7YY7U");
    association.setEMail("asdhappyrunners22@gmail.com");
    association.setFidalId("BA___");
    association.setPIva("00000000000");
    association.setIndirizzo("Via che non c'è 2");
    association.setProvincia("Milano");
    association.setTelefono("2222222222");
    associationRepository.save(association);

    association = associationRepository.findByFidalId("BA___");
    assertThat(association).extracting(An003Associtation::getNome).isEqualTo("ASD RUNNER 2");
    assertThat(association).extracting(An003Associtation::getCAP).isEqualTo("70033");
    assertThat(association).extracting(An003Associtation::getCitta).isEqualTo("altamura in puglia");
    assertThat(association).extracting(An003Associtation::getCF).isEqualTo("CQVPLA878UU7YY7U");
    assertThat(association).extracting(An003Associtation::getEMail).isEqualTo("asdhappyrunners22@gmail.com");
    assertThat(association).extracting(An003Associtation::getFidalId).isEqualTo("BA___");
    assertThat(association).extracting(An003Associtation::getPIva).isEqualTo("00000000000");
    assertThat(association).extracting(An003Associtation::getIndirizzo).isEqualTo("Via che non c'è 2");
    assertThat(association).extracting(An003Associtation::getProvincia).isEqualTo("Milano");
    assertThat(association).extracting(An003Associtation::getTelefono).isEqualTo("2222222222");

  }

  @Test
  @Order(6)
  public void testDelAssociations() {

    An003Associtation association = associationRepository.findByFidalId("BA___");
    associationRepository.delete(association);
    assertNull(associationRepository.findByFidalId("BA___"));
    
  }
}