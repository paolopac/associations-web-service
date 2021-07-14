package com.faraday.webapp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.faraday.webapp.entities.Associations;

import org.springframework.stereotype.Repository;

@Repository
public class CustomAssociationRepositoryImpl implements CustomAssociationRepository {

  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public void detach(Associations association) {

    entityManager.detach(association);

  }

}
