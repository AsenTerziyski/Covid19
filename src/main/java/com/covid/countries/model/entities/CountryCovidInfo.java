package com.covid.countries.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class CountryCovidInfo extends BaseEntity {

    @NotBlank
    @Column(unique = true)
    private String ID;
    @NotBlank
    @Column(unique = true)
    private String country;
    @NotBlank
    @Column(unique = true)
    @Size(min = 2)
    private String countryCode;
    @NotBlank
    @Column(unique = true)
    private String slug;
    @NotBlank
    private String newConfirmed;
    @NotBlank
    private String totalConfirmed;
    @NotBlank
    private String newDeaths;
    @NotBlank
    private String totalDeaths;
    @NotBlank
    private String newRecovered;
    @NotBlank
    private String totalRecovered;
    @NotBlank
    private String date;
    @NotBlank
    private String premium;

    public CountryCovidInfo() {
    }

    public String getID() {
        return ID;
    }

    public CountryCovidInfo setID(String inpId) {
        this.ID = inpId;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CountryCovidInfo setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public CountryCovidInfo setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public CountryCovidInfo setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getNewConfirmed() {
        return newConfirmed;
    }

    public CountryCovidInfo setNewConfirmed(String newConfirmed) {
        this.newConfirmed = newConfirmed;
        return this;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public CountryCovidInfo setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
        return this;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public CountryCovidInfo setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
        return this;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public CountryCovidInfo setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
        return this;
    }

    public String getNewRecovered() {
        return newRecovered;
    }

    public CountryCovidInfo setNewRecovered(String newRecovered) {
        this.newRecovered = newRecovered;
        return this;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public CountryCovidInfo setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CountryCovidInfo setDate(String date) {
        this.date = date;
        return this;
    }

    public String getPremium() {
        return premium;
    }

    public CountryCovidInfo setPremium(String premium) {
        this.premium = premium;
        return this;
    }
}
