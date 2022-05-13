package com.covid.countries.service;

import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.model.view.CountryCovidViewModel;

import java.util.ArrayList;
import java.util.List;

public interface CountryCovid19InfoService {

    CountryCovidViewModel findByCountryCode(String countryCode);

    void saveCovid19InfoInDb(ArrayList<CountryCovidInfo> covidInfo19);
}
