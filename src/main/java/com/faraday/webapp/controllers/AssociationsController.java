package com.faraday.webapp.controllers;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

import javax.validation.Valid;

import com.faraday.webapp.entities.Associations;
import com.faraday.webapp.exception.BindingException;
import com.faraday.webapp.exception.NotFoundException;
import com.faraday.webapp.services.AssociationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/associations")
@Log
public class AssociationsController {

  @Autowired
  private AssociationsService associationsService;
  
	@Autowired
	private ResourceBundleMessageSource errMessage;

  @ApiOperation(
    value= "Inserisce una nuova associazione sportiva",
    notes= "",
    response = Associations.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associations Inserito"),
    @ApiResponse(code = 400, message = "Dati inseriti non validi"),
  })
  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<?> insAssociations(@ApiParam("Associations da inserire") @Valid @RequestBody Associations association, BindingResult bindingResult) throws BindingException {
    
    log.info(String.format("Save associations %s", association.getNome()));

    if(bindingResult.hasErrors()) {

      String errMsg = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

      log.warning(errMsg);

      throw new BindingException(errMsg);
    }

    associationsService.InsAssociations(association);

    HttpHeaders header = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode responseNode = objectMapper.createObjectNode();

    header.setContentType(MediaType.APPLICATION_JSON);
    responseNode.put("code", HttpStatus.OK.toString());
    responseNode.put("message", "Associations " + association.getNome() + " inserita con successo");
  
    return new ResponseEntity<>(responseNode, header, HttpStatus.CREATED);
  }

  @RequestMapping(value="/search/all", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<Associations>> getAllAssociations() throws NotFoundException {

    log.info("Get all associations");

    List<Associations> associations = associationsService.getAllAssociations();

    if(associations.isEmpty()){

      String errMsg = String.format("Nessuna associazione è presente");

      log.warning(errMsg);

      HttpHeaders header = new HttpHeaders();
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode responseNode = objectMapper.createObjectNode();

      header.setContentType(MediaType.APPLICATION_JSON);
      responseNode.put
      responseNode.put

      throw new NotFoundException(errMsg);
    }


    
    errMessage.getMessage("Size.Association.nome.Validation", null, LocaleContextHolder.getLocale());

  }
  
}
