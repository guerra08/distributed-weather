package com.example;

import com.example.data.Temperature;
import com.example.data.Uv;
import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class WeatherService {

    private static final String temperatureTopic = "temperature-topic";
    private static final String uvTopic = "uv-topic";
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Produces
    public Topology buildTopologyForWeatherService() {
        StreamsBuilder builder = new StreamsBuilder();

        try (ObjectMapperSerde<Temperature> temperatureSerde = new ObjectMapperSerde<>(Temperature.class);
             ObjectMapperSerde<Uv> uvSerde = new ObjectMapperSerde<>(Uv.class)) {
            builder.stream(temperatureTopic, Consumed.with(Serdes.String(), temperatureSerde))
                .peek((key, value) -> log.info("Temperature with key: {} and value: {}", key, value));

            return builder.build();
        }
    }

}
