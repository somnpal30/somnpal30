package com.sample.natsproducer;

import com.sample.natsproducer.utils.NatsConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NatsListener {

    private static Logger log = LoggerFactory.getLogger(NatsListener.class);
    NatsConnection natsConnection;



    public NatsListener(NatsConnection natsConnection) {

    }
}
