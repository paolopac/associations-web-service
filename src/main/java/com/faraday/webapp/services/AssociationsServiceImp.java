package com.faraday.webapp.services;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;

import com.faraday.webapp.entities.An003Associtation;
import com.faraday.webapp.repository.An003Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AssociationsServiceImp  implements AssociationsService{

  @Autowired
  private An003Repository associationRepository;

  @Override
  @Transactional
  // PAOLOAC: NOTA
  // DATA RIDONDANZA E' NECESSARIO SI REALIZZI UN MECCANISMO CHE PERMETTA IL RESET
  // ALL'EVENTO DI UPDATE O INSERIMENTO IN ISTANZE MICROSERVICE RIDONDATE MA, PRESENTI
  // NEL CLUSTER MEDESIMO.
  // SOLO IN TAL CASO SI HA MODO ID UTILIZZO DEL POTENTE MECCANISMO DI CAHCEING.
  // LE LIBRERIE PERMANGONO INSTALLATE. IL NUOVO UTILIZZO NECESSITO DEL SOLO DECOMMENT DELLE
  // ANNOTATON RISPETTIVE ALL'INTERNO DELLA SEZIONE SERVICE.

  // @Caching(evict = {
  //   @CacheEvict(value="allAssociationsCache", allEntries=true),
  //   @CacheEvict(value="selByFIDALIdCache", allEntries=true)
  // })
  public void insAssociations(An003Associtation association){

    associationRepository.save(association);

  }
  
  @Override
  // VEDI PAOLOAC: NOTA
  // @Cacheable(value = "allAssociationsCache", sync = true)
  public List<An003Associtation> selAllAssociations(){

    return associationRepository.findAll();
    
  }

  @Override
  // VEDI PAOLOAC: NOTA
  // @Cacheable(value = "selByAssociationsIdCache", key="#id", sync = true)
  public An003Associtation selByAssociationsId(Long id) {

    return associationRepository.findById(id).orElse(null);

  }

  @Override
  // VEDI PAOLOAC: NOTA
  // @Cacheable(value = "selByFIDALIdCache", sync = true)
  public An003Associtation selByFIDALId(String FIDALId) {

    return associationRepository.findByFidalId(FIDALId);

  }

  @Override
  @Transactional
  // VEDI PAOLOAC: NOTA
  // @Caching(evict = {
  //   @CacheEvict(value="allAssociationsCache", allEntries=true),
  //   @CacheEvict(value="selByAssociationsIdCache", allEntries=true),
  //   @CacheEvict(value="selByFIDALIdCache", allEntries=true)
  // })
  public An003Associtation updateAssociations(An003Associtation association) {

    return associationRepository.save(association);

  }

  @Override
  @Transactional
  // VEDI PAOLOAC: NOTA
  // @Caching(evict = {
  //   @CacheEvict(value="allAssociationsCache", allEntries=true),
  //   @CacheEvict(value="selByAssociationsIdCache", key="#association.id"),
  //   @CacheEvict(value="selByFIDALIdCache", allEntries=true)
  // })
  public void delAssociations(An003Associtation association) {

    associationRepository.delete(association);

  }

  @Override
  public void detachAssociations(An003Associtation association) {

    associationRepository.detach(association);

  }
}
