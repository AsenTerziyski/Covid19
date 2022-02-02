package com.covid.countries.model.view;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.gson.annotations.Expose;
import jdk.jfr.DataAmount;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.executable.ValidateOnExecution;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CountryCovidViewModel {

    @Expose
    private String ID;
    @Expose
    private String country;
    @Expose
    private String countryCode;
    @Expose
    private String slug;
    @Expose
    private Integer newConfirmed;
    @Expose
    private Integer totalConfirmed;
    @Expose
    private Integer newDeaths;
    @Expose
    private Integer totalDeaths;
    @Expose
    private Integer newRecovered;
    @Expose
    private Integer totalRecovered;
    @Expose
    private String date;
    @Expose
    private Object premium;

    public CountryCovidViewModel() {
    }

    public String getID() {
        return ID;
    }

    public CountryCovidViewModel setID(String ID) {
        this.ID = ID;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CountryCovidViewModel setCountry(String country) {
        this.country = country;
        return this;
    }


    @Size(min = 1)
    public String getCountryCode() {
        return countryCode;
    }

    public CountryCovidViewModel setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public CountryCovidViewModel setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public Integer getNewConfirmed() {
        return newConfirmed;
    }

    public CountryCovidViewModel setNewConfirmed(Integer newConfirmed) {
        this.newConfirmed = newConfirmed;
        return this;
    }

    public Integer getTotalConfirmed() {
        return totalConfirmed;
    }

    public CountryCovidViewModel setTotalConfirmed(Integer totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
        return this;
    }

    public Integer getNewDeaths() {
        return newDeaths;
    }

    public CountryCovidViewModel setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
        return this;
    }

    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    public CountryCovidViewModel setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
        return this;
    }

    public Integer getNewRecovered() {
        return newRecovered;
    }

    public CountryCovidViewModel setNewRecovered(Integer newRecovered) {
        this.newRecovered = newRecovered;
        return this;
    }

    public Integer getTotalRecovered() {
        return totalRecovered;
    }

    public CountryCovidViewModel setTotalRecovered(Integer totalRecovered) {
        this.totalRecovered = totalRecovered;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CountryCovidViewModel setDate(String date) {
        this.date = date;
        return this;
    }

    public Object getPremium() {
        return premium;
    }

    public CountryCovidViewModel setPremium(Object premium) {
        this.premium = premium;
        return this;
    }
}
