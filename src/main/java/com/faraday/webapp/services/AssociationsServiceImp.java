package com.faraday.webapp.services;


import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;

import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.repository.AssociationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AssociationsServiceImp  implements AssociationsService{

  @Autowired
  private AssociationRepository associationRepository;

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
  public void insAssociations(Associations association){

    associationRepository.save(association);

  }
  
  @Override
  // VEDI PAOLOAC: NOTA
  // @Cacheable(value = "allAssociationsCache", sync = true)
  public List<Associations> selAllAssociations(){

    return associationRepository.findAll();
    
  }

  @Override
  // VEDI PAOLOAC: NOTA
  // @Cacheable(value = "selByAssociationsIdCache", key="#id", sync = true)
  public Associations selByAssociationsId(int id) {

    return associationRepository.findById(id).orElse(null);

  }

  @Override
  // VEDI PAOLOAC: NOTA
  // @Cacheable(value = "selByFIDALIdCache", sync = true)
  public Associations selByFIDALId(String FIDALId) {

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
  public Associations updateAssociations(Associations association) {

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
  public void delAssociations(Associations association) {

    associationRepository.delete(association);

  }

  @Override
  public void detachAssociations(Associations association) {

    associationRepository.detach(association);

  }
}
