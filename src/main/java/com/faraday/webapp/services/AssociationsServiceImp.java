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
  @Caching(evict = {
    @CacheEvict(value="allAssociationsCache", allEntries=true),
   // @CacheEvict(value="selByAssociationsIdCache", allEntries=true),
   @CacheEvict(value="selByFIDALIdCache", allEntries=true)
  })
  public void insAssociations(Associations association){

    associationRepository.save(association);

  }
  
  @Override
  @Cacheable(value = "allAssociationsCache", sync = true)
  public List<Associations> selAllAssociations(){

    return associationRepository.findAll();
    
  }

  @Override
  @Cacheable(value = "selByAssociationsIdCache", key="#id", sync = true)
  public Associations selByAssociationsId(int id) {

    return associationRepository.findById(id).orElse(null);

  }

  @Override
  @Cacheable(value = "selByFIDALIdCache", sync = true)
  public Associations selByFIDALId(String FIDALId) {

    return associationRepository.findByFidalId(FIDALId);

  }

  @Override
  @Transactional
  @Caching(evict = {
    @CacheEvict(value="allAssociationsCache", allEntries=true),
    @CacheEvict(value="selByAssociationsIdCache", allEntries=true),
    @CacheEvict(value="selByFIDALIdCache", allEntries=true)
  })
  public Associations updateAssociations(Associations association) {

    return associationRepository.save(association);

  }

  @Override
  @Transactional
  @Caching(evict = {
    @CacheEvict(value="allAssociationsCache", allEntries=true),
    @CacheEvict(value="selByAssociationsIdCache", key="#association.id"),
    @CacheEvict(value="selByFIDALIdCache", allEntries=true)
  })
  public void delAssociations(Associations association) {

    associationRepository.delete(association);

  }

  @Override
  public void detachAssociations(Associations association) {

    associationRepository.detach(association);

  }
}
