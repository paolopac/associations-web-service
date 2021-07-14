package com.faraday.webapp.services;

import java.util.List;

import com.faraday.webapp.entities.Associations;

public interface AssociationsService {

  public void insAssociations(Associations association);
  
  public List<Associations> selAllAssociations();

  public Associations selByAssociationsId(int id);

  public Associations selByFIDALId(String FIDALId);

  public Associations updateAssociations(Associations association);

  public void delAssociations(Associations association);
  
  public void detachAssociations(Associations association);

}
