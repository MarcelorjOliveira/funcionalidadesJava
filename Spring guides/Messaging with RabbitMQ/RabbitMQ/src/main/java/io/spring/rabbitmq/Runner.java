package io.spring.rabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
	
	@Autowired
	private final RabbitTemplate rabbitTemplate;
	@Autowired
	private final Receiver receiver;
	
	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message...");
		
		rabbitTemplate.convertAndSend(RabbitMqApplication.topicExchangeName, "foo.bar.baz", "hello from RabbitMQ"  );
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS );
	}
	
	
}
