package com.faraday.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="AN003_ASSOCIATION", schema = "tarahumara")
@Data
public class An003Associtation implements Serializable {
  
  private static final long serialVersionUID = 1000000000000000000L;

  @Id
  @Column(name="AN003_ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long  id;

  @Column(name="AN003_NOME")
  @NotNull(message = "{NotNull.Association.nome.Validation}")
  @Size(max=40, message = "{Size.Association.nome.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.nome.Validation}")
  private String nome;

  @Column(name="AN003_FIDALID")
  @NotNull(message = "{NotNull.Association.fidalId.Validation}")
  @Size(max=10, message = "{Size.Association.fidalId.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.fidalId.Validation}")
  private String fidalId;

  @Column(name="AN003_CF")
  @Size(max =16, message = "{Size.Association.cF.Validation}")
  private String cF;

  @Column(name="AN003_PIVA")
  @Size(min = 11, max =11 , message = "{Size.Association.PIva.Validation}")
  private String pIva;

  @Column(name="AN003_CITTA")
  @NotNull(message = "{NotNull.Association.citta.Validation}")
  @Size(max=50, message = "{Size.Association.citta.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.citta.Validation}")
  private String citta;

  @Column(name="AN003_INDIRIZZO")
  @NotNull(message = "{NotNull.Association.indirizzo.Validation}")
  @Size(max=50, message = "{Size.Association.indirizzo.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.indirizzo.Validation}")
  private String indirizzo;

  @Column(name="AN003_CAP")
  @NotNull(message = "{NotNull.Association.cAP.Validation}")
  @Size(min = 5, max = 5, message = "{Size.Association.cAP.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.cAP.Validation}")
  private String cAP;

  @Column(name="AN003_PROVINCIA")
  @NotNull(message = "{NotNull.Association.provincia.Validation}")
  @Size(max=50, message = "{Size.Association.provincia.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.provincia.Validation}")
  private String provincia;

  @Column(name="AN003_TELEFONO")
  @NotNull(message = "{NotNull.Association.telefono.Validation}")
  @Size(max=20, message = "{Size.Association.telefono.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.telefono.Validation}")
  private String telefono;

  @Column(name="AN003_EMAIL")
  @NotNull(message = "{NotNull.Association.eMail.Validation}")
  @Size(max=50, message = "{Size.Association.eMail.Validation}")
  @NotEmpty(message = "{NotEmpty.Association.eMail.Validation}")
  private String eMail;
}