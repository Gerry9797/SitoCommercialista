package com.commercialista.backend.models;

import com.commercialista.backend.enums.ERole;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

  @Id
  @Column(length = 50)
  @Enumerated(EnumType.STRING)
  private ERole id;

  private String denominazione;

  public Role() {

  }

  public ERole getId() {
    return id;
  }

  public void setId(ERole id) {
    this.id = id;
  }

  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }
}