/*
 *
 * You can use the following import statements
 * 
 * java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.geohub.repository;

import com.example.geohub.model.Country;
import com.example.geohub.model.City;

import java.util.List;

public interface CountryRepository {
    List<Country> getCountries();

    Country getCountryById(int countryId);

    Country addCountry(Country country);

    Country updateCountry(int countryId, Country country);

    void deleteCountry(int countryId);

    List<City> getCountryCities(int countryId);
}
