package com.faraday.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
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
	private ResourceBundleMessageSource messager; 

  @Value("${spring.application.name}")
  private String appName;

	@RequestMapping(value="/health", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Void> getHealth() {

    log.info(String.format(messager.getMessage("Ok.Check.health.ping", null, LocaleContextHolder.getLocale()), appName));

    return new ResponseEntity<>(HttpStatus.OK);

	}

}
