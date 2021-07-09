package com.faraday.webapp.repository;

import com.faraday.webapp.entities.Associations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<Associations, Integer>{}
