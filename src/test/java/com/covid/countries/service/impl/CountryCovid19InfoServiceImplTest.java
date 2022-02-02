package com.covid.countries.service.impl;

import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.repository.CountriesCovidInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
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
        this.country1 = new CountryCovidInfo().setCountryCode("ZZ");
        this.country2 = new CountryCovidInfo().setCountryCode("XX");
        this.country3 = new CountryCovidInfo().setCountryCode("NN");
    }

    @BeforeEach
    void initCountryCovid19InfoServiceToTest() {
        ModelMapper modelMapper = new ModelMapper();
        this.countryCovid19InfoServiceToTest = new CountryCovid19InfoServiceImpl(mockedRepo, modelMapper);
    }

    @Test
    void processCovid19Data() {
        String testInput = "{ID=3bbe3c8e-1e48-46a7-837d-49b0031d76d7, Message=, Global={NewConfirmed=3368438, TotalConfirmed=378639918, NewDeaths=8957, TotalDeaths=5672772, NewRecovered=0, TotalRecovered=0, Date=2022-02-02T06:59:35.139Z}, Countries=[{ID=1a3ab171-22f9-41e4-9da3-c07c1091ef81x, Country=TestXX, CountryCode=XX, Slug=testx, NewConfirmed=1, TotalConfirmed=1, NewDeaths=1, TotalDeaths=1, NewRecovered=1, TotalRecovered=1, Date=2022-02-02T06:59:35.139Z, Premium={}}, {ID=1a3ab171-22f9-41e4-9da3-c07c1091ef81xxx, Country=TestZZ, CountryCode=ZZ, Slug=testz, NewConfirmed=2, TotalConfirmed=2, NewDeaths=2, TotalDeaths=2, NewRecovered=2, TotalRecovered=2, Date=2022-02-02T06:59:35.139Z, Premium={}}], Date=2022-02-02T06:59:35.139Z}";
        List<CountryCovidInfo> countryCovidInfos = this.countryCovid19InfoServiceToTest.processCovid19Data(testInput);
        assertEquals(2, countryCovidInfos.size());
        assertEquals("XX", countryCovidInfos.get(0).getCountryCode());
        assertEquals("ZZ", countryCovidInfos.get(1).getCountryCode());
    }

    @Test
    void saveProcessedInputInfoInDb() {
        Mockito.when(this.mockedRepo.findAll()).thenReturn(List.of(country1,country2));
        List<CountryCovidInfo> countryCovidInfos = this.countryCovid19InfoServiceToTest.saveProcessedInputInfoInDb(List.of(country1, country2));
        List<CountryCovidInfo> all = this.mockedRepo.findAll();
        assertEquals(countryCovidInfos.get(0), all.get(0));
        assertEquals(countryCovidInfos.get(1), all.get(1));
        assertEquals(2L, countryCovidInfos.size());
        Mockito.when(this.mockedRepo.findAll()).thenReturn(List.of(country1,country2,country3));
        List<CountryCovidInfo> countryCovidInfos2 = this.countryCovid19InfoServiceToTest.saveProcessedInputInfoInDb(List.of(country1, country2, country3));
        assertNotNull(country3);
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
