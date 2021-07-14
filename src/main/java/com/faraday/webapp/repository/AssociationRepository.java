package com.faraday.webapp.repository;

import com.faraday.webapp.entities.Associations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<Associations, Integer>, CustomAssociationRepository {

  Associations findByFidalId(String fidalId);

}
