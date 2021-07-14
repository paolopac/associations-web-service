package com.faraday.webapp.controllers;

import java.util.HashMap;
import java.util.Map;

import com.faraday.webapp.models.InfoWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import lombok.extern.java.Log;


@RestController
@RequestMapping("/check")
@Log
public class CheckController {

  @Autowired
  InfoWS infoWS;

	@RequestMapping(value="/info", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<InfoWS> getInfo() {

    log.info("********** Get webservice info ");
    return new ResponseEntity<InfoWS>(infoWS, HttpStatus.OK);

	}

}
