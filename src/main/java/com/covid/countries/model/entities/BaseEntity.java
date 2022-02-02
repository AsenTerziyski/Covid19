package com.covid.countries.model.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdDb;

    public BaseEntity() {
    }

    public Long getIdDb() {
        return IdDb;
    }

    public void setIdDb(Long id) {
        this.IdDb = id;
    }


}
