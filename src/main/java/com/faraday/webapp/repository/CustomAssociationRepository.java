package com.faraday.webapp.repository;

import com.faraday.webapp.entities.Associations;


public interface CustomAssociationRepository {

  void detach(Associations Associations);

}
