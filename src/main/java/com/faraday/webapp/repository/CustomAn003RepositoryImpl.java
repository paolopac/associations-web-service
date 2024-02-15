package com.faraday.webapp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.faraday.webapp.entities.An003Associtation;

import org.springframework.stereotype.Repository;

@Repository
public class CustomAn003RepositoryImpl implements CustomAn003Repository {

  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public void detach(An003Associtation association) {

    entityManager.detach(association);

  }

}
