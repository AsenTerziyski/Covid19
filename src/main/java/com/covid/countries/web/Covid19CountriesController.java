package com.covid.countries.web;

import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.service.CountryCovid19InfoService;
import com.covid.countries.validator.ValidationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping("/country")
public class Covid19CountriesController {

    private final CountryCovid19InfoService countryCovid19InfoService;
    private final ValidationUtil validationUtil;

    public Covid19CountriesController(CountryCovid19InfoService countryCovid19InfoService, ValidationUtil validationUtil) {
        this.countryCovid19InfoService = countryCovid19InfoService;
        this.validationUtil = validationUtil;
    }

    @GetMapping("/{countryCode}")
    public ResponseEntity<CountryCovidViewModel> getCountryCovid19InfoByCountryCode(@PathVariable String countryCode) {
        CountryCovidViewModel countryViewByCountryCode = this.countryCovid19InfoService.findByCountryCode(countryCode);
        boolean countryViewByCountryCodeIsValid = this.validationUtil.isValid(countryViewByCountryCode);
        if (countryViewByCountryCode == null
                || !countryCode.toUpperCase(Locale.ROOT).equals(countryCode)
                || !countryViewByCountryCodeIsValid) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(countryViewByCountryCode);
        }
//        return ResponseEntity.ok(countryViewByCountryCode);
    }

}
