package com.covid.countries.service.impl;

import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.repository.CountriesCovidInfoRepository;
import com.covid.countries.service.CountryCovid19InfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CountryCovid19InfoServiceImpl implements CountryCovid19InfoService {
    private final CountriesCovidInfoRepository countriesCovidInfoRepository;
    private final ModelMapper modelMapper;


    public CountryCovid19InfoServiceImpl(CountriesCovidInfoRepository countriesCovidInfoRepository, ModelMapper modelMapper) {
        this.countriesCovidInfoRepository = countriesCovidInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveCovid19InfoInDb(ArrayList<CountryCovidInfo> covidInfo19) {
        if (this.countriesCovidInfoRepository.count() > 0) {
            this.countriesCovidInfoRepository.deleteAll();
        }
        this.countriesCovidInfoRepository.saveAll(covidInfo19);
    }


    @Override
    public CountryCovidViewModel findByCountryCode(String countryCode) {
        Optional<CountryCovidInfo> countryByCountryCode = this.countriesCovidInfoRepository.findByCountryCode(countryCode.toUpperCase(Locale.ROOT));
        if (countryByCountryCode.isPresent()) {
            CountryCovidInfo countryCovidInfo = countryByCountryCode.get();
            return this.modelMapper.map(countryCovidInfo, CountryCovidViewModel.class);
        } else {
            return null;
        }
    }

}
