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
    public List<CountryCovidInfo> processCovid19Data(String inputData) {
        List<CountryCovidInfo> countries = new ArrayList<>();
        if (inputData.contains("Countries=")) {
            String[] countriesInfo = inputData
                    .substring(inputData.lastIndexOf("Countries="))
                    .substring("Countries=".length() + 1)
                    .split("], ")[0]
                    .split("}, ");
            for (String countryInfo : countriesInfo) {
                String currentCountryCode = "";
                if (countryInfo.contains("CountryCode=")) {
                    int countryCodeIndex = countryInfo.lastIndexOf("CountryCode=");
                    currentCountryCode = countryInfo.substring(countryCodeIndex).split(", ")[0].split("=")[1];
                }
                String[] currentCountryInfo = countryInfo.split(", ");
                CountryCovidInfo countryCovidInfo =
                        new CountryCovidInfo()
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
        }
        saveProcessedInputInfoInDb(countries);
        return countries;
    }

    @Override
    public List<CountryCovidInfo> saveProcessedInputInfoInDb(List<CountryCovidInfo> countries) {
        if (this.countriesCovidInfoRepository.count() == 0 && countries.size() != 0) {
            this.countriesCovidInfoRepository.saveAll(countries);
        } else {
            List<CountryCovidInfo> allCountriesInDb = this.countriesCovidInfoRepository.findAll();
            if (allCountriesInDb.size() == countries.size() && countries.size() != 0) {
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
        return this.countriesCovidInfoRepository.findAll();
    }

    @Override
    public void saveCovid19InfoInDb(ArrayList<CountryCovidInfo> covidInfo19) {
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

    private String splitInputData(String str) {
        return str.split("=")[1];
    }
}
