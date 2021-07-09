package com.faraday.webapp.services;


import java.util.List;

import javax.persistence.Cacheable;

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
  public void InsAssociations(Associations association){

    associationRepository.save(association);

  }
  
  @Override
  @Cacheable(value = "associationssache", sync = true)
  public List<Associations> getAllAssociations(){

    return associationRepository.findAll();
    
  }
}
