package com.example.temperaturedata;

import com.example.temperaturedata.data.Temperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class TemperatureDataApplication {
    private final KafkaTemplate<String, Temperature> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(TemperatureDataApplication.class);

    public TemperatureDataApplication(KafkaTemplate<String, Temperature> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(TemperatureDataApplication.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void sendToKafka() {
        log.info("Sending temperature data to temperature-topic topic...");
        String location = "POA";
        kafkaTemplate.send("temperature-topic", location, new Temperature(location, new Random().nextDouble() * 100.0));
    }

}
