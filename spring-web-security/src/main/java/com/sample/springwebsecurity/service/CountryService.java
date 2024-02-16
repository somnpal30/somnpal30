package com.sample.springwebsecurity.service;

import com.sample.springwebsecurity.entity.Country;
import com.sample.springwebsecurity.repository.CountryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;


    public void addCountry(Country country1) {
        countryRepository.save(country1);
    }

    public List<Country> fetchCountries() {
        return countryRepository.findAll();
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country fetchCountryById(String countryId) {
        return countryRepository.findById(countryId).orElse(Country.builder().countryCode("NO_COUNTRY_AVAILABLE").countryName("No Country Found").build());
    }

    @Transactional
    public int updateCoutryById(String countryId, Country country) {
        return countryRepository.updateCountry(countryId, country.getCountryName());
    }

    public void deleteCoutryById(String countryId) {
        countryRepository.deleteById(countryId);
    }

    @Transactional
    public int updateCoutry(Country country) {
        return countryRepository.updateCountry(country.getCountryCode(), country.getCountryName());
    }
}
