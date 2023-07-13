package com.sample.rabbitmq.receiver;

import com.sample.rabbitmq.model.Employee;

import java.util.concurrent.CountDownLatch;

//@Component
public class MessageReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Employee e) {

        System.out.println("Received <" + e + ">");
        latch.countDown();
    }

    public void receiveMessage(byte[] m) {
        System.out.println(">>>>message receive");
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
