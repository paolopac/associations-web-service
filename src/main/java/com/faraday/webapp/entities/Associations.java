package com.faraday.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="associations")
@Data
public class Associations implements Serializable {
  private static final long serialVersionUID = 1000000000000000000L;

  @Id
  @Column(name="ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  Integer id;

  @Column(name="NOME")
  @NotNull(message = "{NotNull.Association.nome.Validation}")
  @Size(max=40, message = "{Size.Association.nome.Validation}")
  String nome;

  @Column(name="IDFIDAL")
  @NotNull(message = "{NotNull.Association.idFidal.Validation}")
  @Size(max=10, message = "{Size.Association.idFidal.Validation}")
  String idFidal;

  @Column(name="CF")
  @Size(max =16, message = "{Size.Association.cF.Validation}")
  String cF;

  @Column(name="PIVA")
  @Size(min = 11, max =11 , message = "{Size.Association.PIva.Validation}")
  String pIva;

  @Column(name="CITTA")
  @NotNull(message = "{NotNull.Association.citta.Validation}")
  @Size(max=50, message = "{Size.Association.citta.Validation}")
  String citta;

  @Column(name="INDIRIZZO")
  @NotNull(message = "{NotNull.Association.indirizzo.Validation}")
  @Size(max=50, message = "{Size.Association.indirizzo.Validation}")
  String indirizzo;

  @Column(name="CAP")
  @NotNull(message = "{NotNull.Association.cAP.Validation}")
  @Size(min = 5, max = 5, message = "{Size.Association.cAP.Validation}")
  String cAP;

  @Column(name="PROVINCIA")
  @NotNull(message = "{NotNull.Association.provincia.Validation}")
  @Size(max=50, message = "{Size.Association.provincia.Validation}")
  String provincia;

  @Column(name="TELEFONO")
  @NotNull(message = "{NotNull.Association.telefono.Validation}")
  @Size(max=20, message = "{Size.Association.telefono.Validation}")
  String telefono;

  @Column(name="EMAIL")
  @NotNull(message = "{NotNull.Association.eMail.Validation}")
  @Size(max=30, message = "{Size.Association.eMail.Validation}")
  String eMail;
}