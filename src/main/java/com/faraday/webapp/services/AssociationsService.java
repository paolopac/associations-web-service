package com.faraday.webapp.services;

import java.util.List;

import com.faraday.webapp.entities.An003Associtation;

public interface AssociationsService {

  public void insAssociations(An003Associtation association);
  
  public List<An003Associtation> selAllAssociations();

  public An003Associtation selByAssociationsId(Long id);

  public An003Associtation selByFIDALId(String FIDALId);

  public An003Associtation updateAssociations(An003Associtation association);

  public void delAssociations(An003Associtation association);
  
  public void detachAssociations(An003Associtation association);

}
