package com.covid.countries.service;

import com.covid.countries.model.view.CountryCovidViewModel;

public interface CountryCovid19InfoService {

    void processCovid19Data(String inputData);

    CountryCovidViewModel findByCountryCode(String countryCode);
}
