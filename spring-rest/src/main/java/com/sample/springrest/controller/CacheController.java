package com.sample.springrest.controller;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.security.UsernamePasswordCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@RestController("/cache")
public class CacheController {
    HazelcastInstance hazelcastClient;

    public CacheController() {
        ClientConfig clientConfig = new ClientConfig();

        UsernamePasswordCredentials usernamePasswordCredentials
                = new UsernamePasswordCredentials("dev", "dev-pass");

        clientConfig.getSecurityConfig().setCredentials(usernamePasswordCredentials);


        this.hazelcastClient = HazelcastClient.newHazelcastClient();

        setDataToCache();

        System.out.println("*********Adding party data.**********");
        //IMap<Object, Object> map = this.hazelcastClient.getMap("country1");

        /*Config config = hazelcastClient.getConfig();


        config.addMapConfig(new MapConfig()
                .setName("country1")
                .setTimeToLiveSeconds(10));*/
    }

    @GetMapping("/country/{countryCode}/{country}")
    public ResponseEntity addCountry(@PathVariable String countryCode, @PathVariable String country) {
        System.out.println(countryCode + ":" + country);
        Map<String, String> map = hazelcastClient.getMap("country1");
        map.put(countryCode, country);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/remove/country/{countryCode}")
    public ResponseEntity removeCountryByCode(@PathVariable String countryCode) {
        System.out.println("deleting country : " + countryCode);
        Map<String, String> map = hazelcastClient.getMap("country1");
        map.remove(countryCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/country/{countryCode}")
    public ResponseEntity<String> getCountryByCode(@PathVariable String countryCode) {
        System.out.println("searching country : " + countryCode);
        Map<String, String> map = hazelcastClient.getMap("country1");
        return ResponseEntity.ok(map.get(countryCode));
    }

    @GetMapping("/user")
    public ResponseEntity<String> addUser() {
        setDataToCache();
        return ResponseEntity.ok().build();

    }

    private void setDataToCache() {
        IMap hazelcastClientMap = hazelcastClient.getMap("users");

        Map<String, String> userMap = new HashMap<>();
        userMap.put("senderId", "somnath.pal1");
        userMap.put("userLang", "en");
        userMap.put("userName", "somnath p");
        userMap.put("userType", "SUBSCRIBER");

        hazelcastClientMap.put("12345", userMap);
        System.out.println(" ttl : " + hazelcastClientMap.getEntryView("12345").getTtl());

    }

}
