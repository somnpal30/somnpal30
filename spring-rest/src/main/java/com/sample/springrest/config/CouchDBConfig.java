package com.sample.springrest.config;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.DesignDocument;

import java.net.MalformedURLException;


public class CouchDBConfig {


    public CouchDBConfig() throws MalformedURLException {
        System.out.println("couchdb init...");
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("admin")
                .password("borosil1234")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        //--------------- Creating database----------------------------//
        CouchDbConnector db = new StdCouchDbConnector("javatpoint", dbInstance);
        db.createDatabaseIfNotExists();
//--------------- Creating Document----------------------------//
        DesignDocument dd = new DesignDocument("light");
        db.create(dd);

    }
}
