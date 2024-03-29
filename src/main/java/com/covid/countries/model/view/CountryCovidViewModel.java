package com.covid.countries.model.view;

import javax.validation.constraints.*;


public class CountryCovidViewModel {

    private String ID;
    private String country;
    private String countryCode;
    private String slug;
    private Integer newConfirmed;
    private Integer totalConfirmed;
    private Integer newDeaths;
    private Integer totalDeaths;
    private Integer newRecovered;
    private Integer totalRecovered;
    private String date;
    private Object premium;

    public CountryCovidViewModel() {
    }

    @NotBlank
    public String getID() {
        return ID;
    }

    public CountryCovidViewModel setID(String ID) {
        this.ID = ID;
        return this;
    }

    @NotBlank
    public String getCountry() {
        return country;
    }

    public CountryCovidViewModel setCountry(String country) {
        this.country = country;
        return this;
    }

    @NotBlank
    @Size(min = 2)
    public String getCountryCode() {
        return countryCode;
    }

    public CountryCovidViewModel setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    @NotBlank
    public String getSlug() {
        return slug;
    }

    public CountryCovidViewModel setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    @NotNull
    public Integer getNewConfirmed() {
        return newConfirmed;
    }

    public CountryCovidViewModel setNewConfirmed(Integer newConfirmed) {
        this.newConfirmed = newConfirmed;
        return this;
    }

    @NotNull
    public Integer getTotalConfirmed() {
        return totalConfirmed;
    }

    public CountryCovidViewModel setTotalConfirmed(Integer totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
        return this;
    }

    @NotNull
    public Integer getNewDeaths() {
        return newDeaths;
    }

    public CountryCovidViewModel setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
        return this;
    }

    @NotNull
    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    public CountryCovidViewModel setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
        return this;
    }

    @NotNull
    public Integer getNewRecovered() {
        return newRecovered;
    }

    public CountryCovidViewModel setNewRecovered(Integer newRecovered) {
        this.newRecovered = newRecovered;
        return this;
    }

    @NotNull
    public Integer getTotalRecovered() {
        return totalRecovered;
    }

    public CountryCovidViewModel setTotalRecovered(Integer totalRecovered) {
        this.totalRecovered = totalRecovered;
        return this;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    public CountryCovidViewModel setDate(String date) {
        this.date = date;
        return this;
    }

    @NotNull
    public Object getPremium() {
        return premium;
    }

    public CountryCovidViewModel setPremium(Object premium) {
        this.premium = premium;
        return this;
    }
}
