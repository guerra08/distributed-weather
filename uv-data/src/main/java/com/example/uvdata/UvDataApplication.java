package com.example.uvdata;

import com.example.uvdata.data.Uv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class UvDataApplication {

    private final KafkaTemplate<String, Uv> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(UvDataApplication.class);

    public UvDataApplication(KafkaTemplate<String, Uv> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(UvDataApplication.class, args);
    }

    @Scheduled(fixedRate = 2000)
    public void sendToKafka() {
        log.info("Sending uv data to uv-topic topic...");
        kafkaTemplate.send("uv-topic", new Uv("POA", getRandomNumber(0, 11)));
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
