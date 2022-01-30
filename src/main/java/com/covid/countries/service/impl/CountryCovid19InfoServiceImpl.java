package com.covid.countries.service.impl;

import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.model.view.Premium;
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
    public void processCovid19Data(String inputData) {
        List<CountryCovidInfo> countries = new ArrayList<>();
        if (inputData.contains("Countries=")) {
            String[] countriesInfo = inputData
                    .substring(inputData.lastIndexOf("Countries="))
                    .substring("Countries=".length() + 1)
                    .split("], ")[0]
                    .split("}, ");
            for (String s : countriesInfo) {
                String currentCountryCode = "";
                if (s.contains("CountryCode=")) {
                    int countryCodeIndex = s.lastIndexOf("CountryCode=");
                    currentCountryCode = s.substring(countryCodeIndex).split(", ")[0].split("=")[1];
                }
                String[] currentCountryInfo = s.split(", ");
                CountryCovidInfo countryCovidInfo = new CountryCovidInfo()
                        .setID(splitInputData(currentCountryInfo[0]))
                        .setCountry(splitInputData(currentCountryInfo[1]))
                        .setCountryCode(currentCountryCode)
                        .setSlug(splitInputData(currentCountryInfo[3]))
                        .setNewConfirmed(splitInputData(currentCountryInfo[4]))
                        .setTotalConfirmed(splitInputData(currentCountryInfo[5]))
                        .setNewDeaths(splitInputData(currentCountryInfo[6]))
                        .setTotalDeaths(splitInputData(currentCountryInfo[7]))
                        .setNewRecovered(splitInputData(currentCountryInfo[8]))
                        .setTotalRecovered(splitInputData(currentCountryInfo[9]))
                        .setDate(splitInputData(currentCountryInfo[10]))
                        .setPremium(splitInputData(currentCountryInfo[11]));
                countries.add(countryCovidInfo);
            }

            if (this.countriesCovidInfoRepository.count() == 0) {
                this.countriesCovidInfoRepository.saveAll(countries);
            } else {
                List<CountryCovidInfo> allCountriesInDb = this.countriesCovidInfoRepository.findAll();
                if (allCountriesInDb.size() == countries.size()) {
                    for (int i = 0; i < allCountriesInDb.size(); i++) {
                        CountryCovidInfo countryCovidInfo = allCountriesInDb.get(i);
                        CountryCovidInfo countryInput = countries.get(i);
                        countryCovidInfo
                                .setID(countryInput.getID())
                                .setCountry(countryInput.getCountry())
                                .setCountryCode(countryInput.getCountryCode())
                                .setSlug(countryInput.getSlug())
                                .setNewConfirmed(countryInput.getNewConfirmed())
                                .setTotalConfirmed(countryInput.getTotalConfirmed())
                                .setNewDeaths(countryInput.getNewDeaths())
                                .setTotalDeaths(countryInput.getTotalDeaths())
                                .setNewRecovered(countryInput.getNewRecovered())
                                .setTotalRecovered(countryInput.getTotalRecovered())
                                .setDate(countryInput.getDate())
                                .setPremium(countryInput.getPremium());
                        this.countriesCovidInfoRepository.save(countryCovidInfo);
                    }
                } else {
                    this.countriesCovidInfoRepository.deleteAll();
                    this.countriesCovidInfoRepository.saveAll(countries);
                }
            }
        }
    }

    @Override
    public CountryCovidViewModel findByCountryCode(String countryCode) {
        Optional<CountryCovidInfo> byCountryCode = this.countriesCovidInfoRepository.findByCountryCode(countryCode.toUpperCase(Locale.ROOT));
        if (byCountryCode.isPresent()) {
            CountryCovidInfo countryCovidInfo = byCountryCode.get();
            CountryCovidViewModel map = this.modelMapper.map(countryCovidInfo, CountryCovidViewModel.class);
            List<Premium> premiums = new ArrayList<>();
            Premium premium = new Premium();
            premium.setPremium(countryCovidInfo.getPremium());
            map.setPremium(premiums);
            return map;
        } else {
            return null;
        }
    }

    private String splitInputData(String str) {
        return str.split("=")[1];
    }
}
