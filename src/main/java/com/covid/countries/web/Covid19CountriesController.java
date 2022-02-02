package com.covid.countries.web;

import com.covid.countries.model.view.CountryCovidViewModel;
import com.covid.countries.service.CountryCovid19InfoService;
import com.covid.countries.validator.ValidationUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

//    @GetMapping("/{countryCode}")
//    public ResponseEntity<CountryCovidViewModel> getCountryCovid19InfoByCountryCode(@PathVariable String countryCode) {
//        CountryCovidViewModel countryViewByCountryCode = this.countryCovid19InfoService.findByCountryCode(countryCode);
//        if (countryViewByCountryCode == null || !countryCode.toUpperCase(Locale.ROOT).equals(countryCode)) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(countryViewByCountryCode);
//        }
//    }

    @GetMapping("/{countryCode}")
    public ResponseEntity<String> getCountryCovid19InfoByCountryCode(@PathVariable String countryCode) {
        CountryCovidViewModel countryViewByCountryCode = this.countryCovid19InfoService.findByCountryCode(countryCode);
        System.out.println();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        boolean isValid = this.validationUtil.isValid(countryViewByCountryCode);
        String output = gson.toJson(countryViewByCountryCode);

        if (!isValid || output.isBlank() || !countryCode.toUpperCase(Locale.ROOT).equals(countryCode)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(gson.toJson(countryViewByCountryCode));
        }
    }


}
