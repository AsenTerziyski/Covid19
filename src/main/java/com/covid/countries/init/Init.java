package com.covid.countries.init;

import com.covid.countries.globalconstants.GlobalConstants;
import com.covid.countries.model.entities.CountryCovidInfo;
import com.covid.countries.service.CountryCovid19InfoService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Component
public class Init implements CommandLineRunner {

    private final CountryCovid19InfoService countryCovid19InfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);

    public Init(CountryCovid19InfoService countryCovid19InfoService) {
        this.countryCovid19InfoService = countryCovid19InfoService;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Started at {}", LocalDateTime.now());
        try {
            extractInfoFromSourceREST();
        } catch (Exception ex) {
            LOGGER.info("Oops, something went wrong! Exception message: {}", ex.getMessage());
        }
    }

    @Scheduled(cron = "*/60 * * * * *")
    private void extractInfoFromSourceREST() throws IOException, ParseException {
        LOGGER.info("Extracted info at {}", LocalDateTime.now());
        URL url = new URL(GlobalConstants.URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        LOGGER.info("Response code is {}.", responseCode);
        if (responseCode != 200) {
            throw new RuntimeException(String.format("%d", responseCode));
        }

        String inline = "";
        Scanner scanner = new Scanner(url.openStream());

        //Write all the JSON data into a string using a scanner
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        //close the scanner
        scanner.close();

        //Using the JSON simple library parse the string into a json object
        JSONParser jsonParser = new JSONParser();
        JSONObject inputObjectFromApi = (JSONObject) jsonParser.parse(inline);
        Object countries = inputObjectFromApi.get("Countries");
        JSONArray countriesArray = (JSONArray) countries;

        ArrayList<CountryCovidInfo> covidInfo19 = new ArrayList<>();
        for (int i = 0; i < countriesArray.size(); i++) {
            JSONObject currentCountry = (JSONObject) countriesArray.get(i);
            String country = currentCountry.get("Country").toString();
            String countryCode = currentCountry.get("CountryCode").toString();
            String newConfirmed = currentCountry.get("NewConfirmed").toString();
            String totalConfirmed = currentCountry.get("TotalConfirmed").toString();
            String newDeaths = currentCountry.get("NewDeaths").toString();
            String totalDeaths = currentCountry.get("TotalDeaths").toString();
            String newRecovered = currentCountry.get("NewRecovered").toString();
            String totalRecovered = currentCountry.get("TotalRecovered").toString();
            String slug = currentCountry.get("Slug").toString();
            String date = currentCountry.get("Date").toString();
            String id = currentCountry.get("ID").toString();
            // todo трябва да видя как да третирам премиум!
            String premium = currentCountry.get("Premium").toString();
            covidInfo19.add(new CountryCovidInfo()
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
                    .setPremium("premium"));
        }
        this.countryCovid19InfoService.saveCovid19InfoInDb(covidInfo19);
    }
}
