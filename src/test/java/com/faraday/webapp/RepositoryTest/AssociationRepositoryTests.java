package com.faraday.webapp.RepositoryTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Collectors;

import com.faraday.webapp.Application;
import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.repository.AssociationRepository;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@ActiveProfiles({"test"})
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
    association.setFidalId("BA000");
    association.setIndirizzo("Via che non c'è");
    association.setNome("ASD RUNNERS");
    association.setPIva("07004700725");
    association.setProvincia("Bari");
    association.setTelefono("3333333333");
    associationRepository.save(association);

    List<Associations> associations = associationRepository.findAll();

    List<Associations> associationFinded = associations.stream()
    .filter(ass -> "ASD RUNNERS".equals(ass.getNome()))
    .collect(Collectors.toList());

    assertThat(associationRepository.findById(associationFinded.get(0).getId())
    .orElse(null))
    .extracting(Associations::getNome)
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

    List<Associations> associations = associationRepository.findAll();

    List<Associations> associationFinded = associations.stream()
    .filter(ass -> "ASD RUNNERS".equals(ass.getNome()))
    .collect(Collectors.toList());

    assertThat(associationRepository.findById(associationFinded.get(0).getId())
    .orElse(null))
    .extracting(Associations::getNome)
    .isEqualTo("ASD RUNNERS");

  }

  @Test
  @Order(4)
  public void testSelByFIDALId() {

    assertThat(associationRepository.findByFidalId("BA000"))
    .extracting(Associations::getFidalId)
    .isEqualTo("BA000");
      
  }

  @Test
  @Order(5)
  public void testUpdateAssociations() {

    Associations association = associationRepository.findByFidalId("BA000");

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
    assertThat(association).extracting(Associations::getNome).isEqualTo("ASD RUNNER 2");
    assertThat(association).extracting(Associations::getCAP).isEqualTo("70033");
    assertThat(association).extracting(Associations::getCitta).isEqualTo("altamura in puglia");
    assertThat(association).extracting(Associations::getCF).isEqualTo("CQVPLA878UU7YY7U");
    assertThat(association).extracting(Associations::getEMail).isEqualTo("asdhappyrunners22@gmail.com");
    assertThat(association).extracting(Associations::getFidalId).isEqualTo("BA___");
    assertThat(association).extracting(Associations::getPIva).isEqualTo("00000000000");
    assertThat(association).extracting(Associations::getIndirizzo).isEqualTo("Via che non c'è 2");
    assertThat(association).extracting(Associations::getProvincia).isEqualTo("Milano");
    assertThat(association).extracting(Associations::getTelefono).isEqualTo("2222222222");

  }

  @Test
  @Order(6)
  public void testDelAssociations() {

    Associations association = associationRepository.findByFidalId("BA___");
    associationRepository.delete(association);
    assertNull(associationRepository.findByFidalId("BA___"));
    
  }
}