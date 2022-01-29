package com.covid.countries.service.impl;

import com.covid.countries.model.CountryCovidInfo;
import com.covid.countries.model.CountryCovidViewModel;
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
            for (int i = 0; i < countriesInfo.length; i++) {
                String currentCountryCode = "";
                if (countriesInfo[i].contains("CountryCode=")) {
                    int countryCodeIndex = countriesInfo[i].lastIndexOf("CountryCode=");
                    currentCountryCode = countriesInfo[i].substring(countryCodeIndex).split(", ")[0].split("=")[1];
                }
                String[] currentCountryInfo = countriesInfo[i].split(", ");
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
                        .setDate(splitInputData(currentCountryInfo[10]).split("T")[0]);
                countries.add(countryCovidInfo);
            }
            System.out.println();
            if (this.countriesCovidInfoRepository.count() == 0) {
                this.countriesCovidInfoRepository.saveAll(countries);
            } else {
                List<CountryCovidInfo> all = this.countriesCovidInfoRepository.findAll();
                for (int i = 0; i < all.size(); i++) {
                    CountryCovidInfo countryCovidInfo = all.get(i);
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
                            .setDate(countryInput.getDate());
                    this.countriesCovidInfoRepository.save(countryCovidInfo);
                }
            }
        }
    }


    @Override
    public CountryCovidViewModel findByCountryCode(String countryCode) {
        Optional<CountryCovidInfo> byCountryCode = this.countriesCovidInfoRepository.findByCountryCode(countryCode.toUpperCase(Locale.ROOT));
        if (byCountryCode.isPresent()) {
            CountryCovidInfo countryCovidInfo = byCountryCode.get();
            return this.modelMapper.map(countryCovidInfo, CountryCovidViewModel.class);
        } else {
            return null;
        }
    }

    private String splitInputData(String str) {
        return str.split("=")[1];
    }
}
