package com.faraday.webapp.controllers;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

import javax.validation.Valid;

import com.faraday.webapp.entities.An003Associtation;
import com.faraday.webapp.exception.BindingException;
import com.faraday.webapp.exception.NotFoundException;
import com.faraday.webapp.exception.DuplicateException;
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
import org.springframework.web.bind.annotation.PathVariable;
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
	private ResourceBundleMessageSource messager;

  @ApiOperation(
    value= "Inserisce una nuova associazione sportiva",
    notes= "",
    response = An003Associtation.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazione Inserita"),
    @ApiResponse(code = 400, message = "Dati inseriti non validi"),
    @ApiResponse(code = 406, message = "Associazione con FIDAL Id presente")
  })
  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<?> insAssociations(@ApiParam("Associations da inserire") @Valid @RequestBody An003Associtation association, BindingResult bindingResult) 
  throws BindingException, DuplicateException {
    
    log.info(String.format("********** Save associations %s", association.getNome()));

    if(bindingResult.hasErrors()) {

      String errMsg = messager.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

      log.warning(errMsg);

      throw new BindingException(errMsg);
    }

    log.info("********** Check duplicate by FIDAL id ");


    if(null != associationsService.selByFIDALId(association.getFidalId())) {

      String errMsg = String.format(messager.getMessage("Duplicate.Associations.byFIDALId", null, LocaleContextHolder.getLocale()), association.getFidalId());

      log.warning(errMsg);

      throw new DuplicateException(errMsg);

    }

    associationsService.insAssociations(association);

    HttpHeaders header = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode responseNode = objectMapper.createObjectNode();

    header.setContentType(MediaType.APPLICATION_JSON);
    responseNode.put("code", HttpStatus.OK.toString());
    responseNode.put("message", "Associations " + association.getNome() + " inserita con successo");
  
    return new ResponseEntity<>(responseNode, header, HttpStatus.CREATED);
  }

  @ApiOperation(
    value= "Reperimento lista associazioni",
    notes= "Recupera tutte le associazioni",
    responseContainer = "List",
    response = An003Associtation.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazioni trovate"),
    @ApiResponse(code = 404, message = "Associazioni non presenti"),
  })
  @RequestMapping(value="/search/all", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<An003Associtation>> selAllAssociations() throws NotFoundException {

    log.info("********** Get all associations");

    List<An003Associtation> associations = associationsService.selAllAssociations();

    if(associations.isEmpty()){

      String errMsg = messager.getMessage("NotFound.Association.selAll", null, LocaleContextHolder.getLocale());

      log.warning(errMsg);

      throw new NotFoundException(errMsg);
    }
    
    log.info("********** Associations found:" + associations.size());

    return new ResponseEntity<List<An003Associtation>>(associations, HttpStatus.OK);
    
  }
  
  @ApiOperation(
    value= "Reperimento associazione attraverso Id",
    notes= "Recupera l'associazione attraverso l'id associazine",
    response = An003Associtation.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazione trovata"),
    @ApiResponse(code = 404, message = "Associazione non presente"),
  })
  @RequestMapping(value="/search/code/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<An003Associtation> selByAssociationsId(@ApiParam("Id associazione da ricercare") @PathVariable("id") Long id) throws NotFoundException {
    
    log.info("********** Get associations with id: " + id);

    An003Associtation association = associationsService.selByAssociationsId(id);

    if(null == association) {

      String errMsg = String.format(messager.getMessage("NotFound.Association.selById", null, LocaleContextHolder.getLocale()), id);

      throw new NotFoundException(errMsg);
    }

    return new ResponseEntity<An003Associtation>(association, HttpStatus.OK);

  }
  
  
  @ApiOperation(
    value= "Reperimento associazione attraverso FIDAL Id",
    notes= "Recupera l'associazione attraverso il FIDAL Id",
    response = An003Associtation.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazione trovata"),
    @ApiResponse(code = 404, message = "Associazione non presente"),
  }) 
  @RequestMapping(value="/search/fidalid/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<An003Associtation> selByFIDALId(@ApiParam("FIDAL Id") @PathVariable("id") String id) throws NotFoundException{

    log.info("********** Get associations with FIDAL id: " + id);

    An003Associtation association = associationsService.selByFIDALId(id);

    if(null == association) {

      String errMsg = String.format(messager.getMessage("NotFound.Association.selByFIDALId", null, LocaleContextHolder.getLocale()), id);

      throw new  NotFoundException(errMsg);

    }

    return new ResponseEntity<An003Associtation>(association, HttpStatus.OK);

  }

  @ApiOperation(
    value= "Modifica associazione",
    notes= "",
    response = An003Associtation.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazione modificata"),
    @ApiResponse(code = 404, message = "Associazione non presente"),
    @ApiResponse(code = 400, message = "Dati inseriti non validi"),
    @ApiResponse(code = 406, message = "Associazione con FIDAL Id presente")
  }) 
  @RequestMapping(value="/update", method = RequestMethod.PUT, produces ="application/json")
  public ResponseEntity<?> updateAssociations(@ApiParam("Associazione contenente proprietà aggiornate") @Valid @RequestBody An003Associtation association, BindingResult bindingResult) throws BindingException, NotFoundException, DuplicateException {
    
    log.info(String.format("********** Modify associations %s", association.getNome()));

    if(bindingResult.hasErrors()){

      String errMsg = messager.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

      log.warning(errMsg);

      throw new BindingException(errMsg);

    }

    An003Associtation associationOld = new An003Associtation();

    associationOld =  associationsService.selByAssociationsId(association.getId());

    if(null == associationOld) {
                                                        
      String errMsg = String.format(messager.getMessage("NotFound.Association.selById",null, LocaleContextHolder.getLocale()), association.getId());

      log.warning(errMsg);

      throw new NotFoundException(errMsg);

    }
    
    if(!association.getFidalId().equals(associationOld.getFidalId())) {

      An003Associtation associationFinded = associationsService.selByFIDALId(association.getFidalId());

      if(null != associationFinded) {
        
        String errMsg = String.format(messager.getMessage("Duplicate.Associations.byFIDALId", null, LocaleContextHolder.getLocale()), association.getFidalId());

        log.warning(errMsg);

        throw new DuplicateException(errMsg);

      }

    }

    associationsService.detachAssociations(associationOld);
    associationsService.updateAssociations(association);

    HttpHeaders header = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode responseNode = objectMapper.createObjectNode();

    header.setContentType(MediaType.APPLICATION_JSON);
    responseNode.put("code", HttpStatus.OK.toString());

    responseNode.put("message", String.format(messager.getMessage("Ok.Associations.update", null, LocaleContextHolder.getLocale()), associationOld.getNome()));

    return new ResponseEntity<>(responseNode, header, HttpStatus.CREATED);

  }

  @ApiOperation(
    value= "Eliminazione associazione",
    notes= "",
    response = An003Associtation.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazione eliminata"),
    @ApiResponse(code = 404, message = "Associazione non presente")
  }) 
  @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> delAssociations(@ApiParam("Id associazione") @PathVariable("id") Long id) throws NotFoundException {

    log.info(String.format("********** Delete associations %s", id));

    An003Associtation association = associationsService.selByAssociationsId(id);

    if(null == association) {

      String errMsg = String.format(messager.getMessage("NotFound.Association.selById", null, LocaleContextHolder.getLocale()), id);

      throw new NotFoundException(errMsg);
    }

    associationsService.delAssociations(association);
 
    HttpHeaders header = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode responseNode = objectMapper.createObjectNode();

    header.setContentType(MediaType.APPLICATION_JSON);
    responseNode.put("code", HttpStatus.OK.toString());
    responseNode.put("message", String.format(messager.getMessage("Ok.Associations.delete", null, LocaleContextHolder.getLocale()), association.getNome(),id));
    return new ResponseEntity<>(responseNode, header, HttpStatus.OK);
    
  }


  @ApiOperation(
    value= "Verifica esistenza associazione tramite Id",
    notes= "",
    response = HttpStatus.class,
    produces = "application/json")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Associazione trovata"),
    @ApiResponse(code = 404, message = "Associazione non presente"),
  })
  @RequestMapping(value="/verify/code/{id}", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<Void> verifyAssociationsById(@ApiParam("Id associazione di cui è necessario verificarne l'esistenza") @PathVariable("id") Long id) throws NotFoundException {
    
    log.info(String.format(messager.getMessage("Ok.Associations.Pre.selAssociationsById", null, LocaleContextHolder.getLocale()), id));

    An003Associtation association = associationsService.selByAssociationsId(id);

    if(null == association) {

      String errMsg = String.format(messager.getMessage("NotFound.Association.verifyAssociationsById", null, LocaleContextHolder.getLocale()), id);

      throw new NotFoundException(errMsg);
    }

    return new ResponseEntity<>(HttpStatus.OK);

  }
  
}
