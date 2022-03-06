package com.covid.countries.init;

import com.covid.countries.globalconstants.GlobalConstants;
import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.service.CountryCovid19InfoService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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
        LOGGER.info("Started at {}", LocalDateTime.now());
//        extractAndSaveDataFromURL();
        URL url = new URL("https://api.covid19api.com/summary");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        if (responseCode != 200) {
            throw new RuntimeException(String.format("%d", responseCode));
        } else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());
            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            //close the scanner
            scanner.close();
            System.out.println(inline);

            //Using the JSON simple library parse the string into a json object
            JSONParser jsonParser = new JSONParser();
            JSONObject inputObjectFromApi = (JSONObject) jsonParser.parse(inline);
            Object countries = inputObjectFromApi.get("Countries");
            JSONArray countriesArray = (JSONArray) countries;
            ArrayList<CountryCovidInfo> covidInfo19 = new ArrayList<>();
            for (int i = 0; i < countriesArray.size(); i++) {
                JSONObject new_currentCountry = (JSONObject) countriesArray.get(i);
                String country = new_currentCountry.get("Country").toString();
                String countryCode = new_currentCountry.get("CountryCode").toString();
                String newConfirmed = new_currentCountry.get("NewConfirmed").toString();
                String totalConfirmed = new_currentCountry.get("TotalConfirmed").toString();
                String newDeaths = new_currentCountry.get("NewDeaths").toString();
                String totalDeaths = new_currentCountry.get("TotalDeaths").toString();
                String newRecovered = new_currentCountry.get("NewRecovered").toString();
                String totalRecovered = new_currentCountry.get("TotalRecovered").toString();
                String slug = new_currentCountry.get("Slug").toString();
                String date = new_currentCountry.get("Date").toString();
                String id = new_currentCountry.get("ID").toString();
                // тръабва да видя как да третирам премиум!
                String premium = new_currentCountry.get("Premium").toString();

                CountryCovidInfo countryCovidInfo = new CountryCovidInfo()
                        .setCountry(country)
                        .setID(id)
                        .setCountryCode(countryCode)
                        .setSlug(slug)
                        .setNewConfirmed(newConfirmed)
                        .setTotalConfirmed(totalConfirmed)
                        .setNewDeaths(newDeaths)
                        .setTotalDeaths(totalDeaths)
                        .setNewRecovered(newRecovered)
                        .setTotalRecovered(totalRecovered)
                        .setDate(date)
                        .setPremium("premium");
                covidInfo19.add(countryCovidInfo);
            }
            System.out.println();
            this.countryCovid19InfoService.saveCovid19InfoInDb(covidInfo19);
        }


    }

//    @Scheduled(cron = "*/60 * * * * *")
//    private void extractAndSaveDataFromURL() {
//        LOGGER.info("Started extracting at {}", LocalDateTime.now());
//        ResponseEntity<Object> inputCovid19Data = restTemplate.getForEntity(GlobalConstants.URL, Object.class);
//        HttpStatus statusCode = inputCovid19Data.getStatusCode();
//        if (statusCode.is2xxSuccessful()) {
//            HttpHeaders headers = inputCovid19Data.getHeaders();
//            if (Objects.requireNonNull(headers.get("Content-Type")).contains("application/json; charset=UTF-8")) {
//                String inputData = inputCovid19Data.getBody().toString().trim();
//                this.countryCovid19InfoService.processCovid19Data(inputData);
//            } else {
//                LOGGER.info("Could not execute extracting at {}", LocalDateTime.now());
//            }
//        }
//        LOGGER.info("Finished at {}", LocalDateTime.now());
//    }
}
