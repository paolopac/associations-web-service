package com.faraday.webapp.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("application")
@Data
public class InfoWS {
  
  String env;
  String authorName;
  String authorSurname;
  String authorContact;

}
