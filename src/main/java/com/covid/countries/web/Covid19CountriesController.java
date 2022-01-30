package com.covid.countries.web;

import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.service.CountryCovid19InfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/country")
public class Covid19CountriesController {

    private final CountryCovid19InfoService countryCovid19InfoService;

    public Covid19CountriesController(CountryCovid19InfoService countryCovid19InfoService) {
        this.countryCovid19InfoService = countryCovid19InfoService;
    }

    @GetMapping("/{countryCode}")
    public ResponseEntity<CountryCovidViewModel> getCountryCovid19InfoByCountryCode(@PathVariable String countryCode) {
        CountryCovidViewModel byCountryCode = this.countryCovid19InfoService.findByCountryCode(countryCode);
        if (byCountryCode == null || !countryCode.toUpperCase(Locale.ROOT).equals(countryCode)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(byCountryCode);
        }
    }
}
