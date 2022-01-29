package com.covid.countries.model;

public class CountryCovidViewModel {
    private String ID;
    private String country;
    private String countryCode;
    private String slug;
    private String newConfirmed;
    private String totalConfirmed;
    private String newDeaths;
    private String totalDeaths;
    private String newRecovered;
    private String totalRecovered;
    private String date;

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

    public String getNewConfirmed() {
        return newConfirmed;
    }

    public CountryCovidViewModel setNewConfirmed(String newConfirmed) {
        this.newConfirmed = newConfirmed;
        return this;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public CountryCovidViewModel setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
        return this;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public CountryCovidViewModel setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
        return this;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public CountryCovidViewModel setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
        return this;
    }

    public String getNewRecovered() {
        return newRecovered;
    }

    public CountryCovidViewModel setNewRecovered(String newRecovered) {
        this.newRecovered = newRecovered;
        return this;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public CountryCovidViewModel setTotalRecovered(String totalRecovered) {
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
}
