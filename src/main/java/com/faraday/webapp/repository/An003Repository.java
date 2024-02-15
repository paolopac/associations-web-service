package com.faraday.webapp.repository;

import com.faraday.webapp.entities.An003Associtation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface An003Repository extends JpaRepository<An003Associtation, Long>, CustomAn003Repository {

  An003Associtation findByFidalId(String fidalId);

}
