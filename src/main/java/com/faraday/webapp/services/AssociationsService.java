package com.faraday.webapp.services;

import java.util.List;

import com.faraday.webapp.entities.Associations;

public interface AssociationsService {

  public void InsAssociations(Associations asspciation);
  
  public List<Associations> getAllAssociations();
  
}
