/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.geohub.service;

import com.example.geohub.model.Country;
import com.example.geohub.model.City;
import com.example.geohub.repository.CountryJpaRepository;
import com.example.geohub.repository.CountryRepository;
import com.example.geohub.repository.CityJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CountryJpaService implements CountryRepository {

    @Autowired
    private CountryJpaRepository countryJpaRepository;
    @Autowired
    private CityJpaRepository cityJpaRepository;

    @Override
    public List<Country> getCountries() {
        return countryJpaRepository.findAll();
    }

    @Override
    public Country getCountryById(int countryId) {
        try {
            return countryJpaRepository.findById(countryId).get();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Country addCountry(Country country) {
        return countryJpaRepository.save(country);

    }

    @Override
    public Country updateCountry(int countryId, Country country) {
        try {
            Country newCountry = countryJpaRepository.findById(countryId).get();
            if (country.getCountryName() != null) {
                newCountry.setCountryName(country.getCountryName());
            }
            if (country.getCurrency() != null) {
                newCountry.setCurrency(country.getCurrency());
            }
            if (country.getPopulation() != 0) {
                newCountry.setPopulation(country.getPopulation());
            }
            if (country.getLatitude() != null) {
                newCountry.setLatitude(country.getLatitude());
            }
            if (country.getLongitude() != null) {
                newCountry.setLongitude(country.getLongitude());
            }
            return countryJpaRepository.save(newCountry);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCountry(int countryId) {
        try {
            Country country = countryJpaRepository.findById(countryId).get();
            List<City> cities = cityJpaRepository.findByCountry(country);
            for (City city : cities) {
                city.setCountry(null);
            }
            cityJpaRepository.saveAll(cities);
            countryJpaRepository.deleteById(countryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<City> getCountryCities(int countryId) {
        try {
            Country country = countryJpaRepository.findById(countryId).get();
            return cityJpaRepository.findByCountry(country);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
