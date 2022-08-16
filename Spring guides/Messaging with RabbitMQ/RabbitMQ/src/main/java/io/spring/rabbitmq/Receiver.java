package io.spring.rabbitmq;

import java.util.concurrent.CountDownLatch;

public class Receiver {
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	public void receiveMessage(String message) {
		System.out.println("Received < " + message + ">");
	}
	
	public CountDownLatch getLatch() {
		return latch;
	}
}
