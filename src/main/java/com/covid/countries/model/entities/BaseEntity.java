package com.covid.countries.model.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private Long IdDb;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdDb() {
        return IdDb;
    }

    public void setIdDb(Long id) {
        this.IdDb = id;
    }


}
