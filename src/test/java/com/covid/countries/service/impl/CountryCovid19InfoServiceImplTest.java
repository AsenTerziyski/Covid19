package com.covid.countries.service.impl;

import com.covid.countries.globalconstants.GlobalConstants;
import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.repository.CountriesCovidInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountryCovid19InfoServiceImplTest {

    private CountryCovid19InfoServiceImpl countryCovid19InfoServiceToTest;
    @Mock
    private CountriesCovidInfoRepository mockedRepo;

    private CountryCovidInfo country1;
    private CountryCovidInfo country2;
    private CountryCovidInfo country3;

    @BeforeEach
    void initCountries() {
        this.country1 = new CountryCovidInfo()
                .setID("testId")
                .setCountry("TestCountry1")
                .setSlug("test1")
                .setCountryCode("ZZ")
                .setNewConfirmed("1")
                .setTotalConfirmed("1")
                .setNewRecovered("1")
                .setNewDeaths("1")
                .setTotalDeaths("1")
                .setTotalRecovered("1").setDate("testDate");

        this.country2 = new CountryCovidInfo().setCountryCode("XX")
                .setNewConfirmed("2")
                .setNewRecovered("2")
                .setNewDeaths("2");
        this.country3 = new CountryCovidInfo().setCountryCode("NN");
    }

    @BeforeEach
    void initCountryCovid19InfoServiceToTest() {
        ModelMapper modelMapper = new ModelMapper();
        this.countryCovid19InfoServiceToTest = new CountryCovid19InfoServiceImpl(mockedRepo, modelMapper);
    }

    @Test
    void processCovid19Data() {
        List<CountryCovidInfo> countryCovidInfos = this.countryCovid19InfoServiceToTest.processCovid19Data(GlobalConstants.TEST_INPUT);
        assertEquals(2, countryCovidInfos.size());

        assertEquals("ZZ", countryCovidInfos.get(0).getCountryCode());
        assertEquals("1", countryCovidInfos.get(0).getNewConfirmed());
        assertEquals("1", countryCovidInfos.get(0).getNewDeaths());
        assertEquals("1", countryCovidInfos.get(0).getNewRecovered());

        assertEquals("XX", countryCovidInfos.get(1).getCountryCode());
        assertEquals("2", countryCovidInfos.get(1).getNewConfirmed());
        assertEquals("2", countryCovidInfos.get(1).getNewDeaths());
        assertEquals("2", countryCovidInfos.get(1).getNewRecovered());
    }

    @Test
    void saveProcessedInputInfoInDb() {
        Mockito.when(this.mockedRepo.findAll()).thenReturn(List.of(country1, country2));
        List<CountryCovidInfo> countryCovidInfos = this.countryCovid19InfoServiceToTest.processCovid19Data(GlobalConstants.TEST_INPUT);
        List<CountryCovidInfo> allCountriesInMockedRepo = this.mockedRepo.findAll();
        assertEquals(countryCovidInfos.get(0).getCountryCode(), allCountriesInMockedRepo.get(0).getCountryCode());
        assertEquals(countryCovidInfos.get(1).getCountryCode(), allCountriesInMockedRepo.get(1).getCountryCode());
        assertEquals(2L, countryCovidInfos.size());
        Mockito.when(this.mockedRepo.findAll()).thenReturn(List.of(country1, country2, country3));
        List<CountryCovidInfo> countryCovidInfos2 = this.countryCovid19InfoServiceToTest.saveProcessedInputInfoInDb(List.of(country1, country2, country3));
        assertEquals("NN", countryCovidInfos2.get(2).getCountryCode());
    }

    @Test
    void findByCountryCode() {
        Mockito.when(mockedRepo.findByCountryCode("ZZ")).thenReturn(Optional.ofNullable(country1));
        CountryCovidViewModel existingCountry = this.countryCovid19InfoServiceToTest.findByCountryCode("ZZ");
        assertEquals("ZZ", existingCountry.getCountryCode());
        CountryCovidViewModel notExistingCountry = this.countryCovid19InfoServiceToTest.findByCountryCode("TT");
        assertNull(notExistingCountry);
    }
}
