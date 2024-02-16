package com.sample.springwebsecurity.repository;

import com.sample.springwebsecurity.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    @Modifying
    @Query("update Country  c set c.countryName = :countryName where c.countryCode = :countryCode")
    int updateCountry(String countryCode, String countryName);


}
