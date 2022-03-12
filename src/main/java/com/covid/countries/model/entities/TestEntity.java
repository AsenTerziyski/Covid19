package com.covid.countries.model.entities;

import javax.persistence.*;

@Entity
public class TestEntity extends BaseEntity {
    private String test;


    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
