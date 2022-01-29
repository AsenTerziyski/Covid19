package com.covid.countries.init;

import com.covid.countries.globalconstants.GlobalConstants;
import com.covid.countries.model.CountryCovidInfo;
import com.covid.countries.service.CountryCovid19InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final CountryCovid19InfoService countryCovid19InfoService;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);

    public Init(CountryCovid19InfoService countryCovid19InfoService, RestTemplate restTemplate) {
        this.countryCovid19InfoService = countryCovid19InfoService;
        this.restTemplate = restTemplate;
    }

    @Override

    public void run(String... args) throws Exception {
        LOGGER.info("Started app at {}", LocalDateTime.now());
        extractingAndSavingDataFromUrl();
        LOGGER.info("Finished at {}", LocalDateTime.now());
    }

    @Scheduled(cron = "*/60 * * * * *")
    private void extractingAndSavingDataFromUrl() {
        LOGGER.info("Started extracting at {}", LocalDateTime.now());
        ResponseEntity<Object> inputCovid19Data = restTemplate.getForEntity(GlobalConstants.URL, Object.class);
        HttpStatus statusCode = inputCovid19Data.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            String inputData = inputCovid19Data.getBody().toString();
            this.countryCovid19InfoService.processCovid19Data(inputData);
        }
        LOGGER.info("Finished extracting at {}", LocalDateTime.now());
    }
}
