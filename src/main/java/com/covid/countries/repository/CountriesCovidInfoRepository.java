package com.covid.countries.repository;

import com.covid.countries.model.CountryCovidInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountriesCovidInfoRepository extends JpaRepository<CountryCovidInfo, Long> {
    Optional<CountryCovidInfo> findByCountryCode(String countryCode);
}
