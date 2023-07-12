package com.example;

import com.example.data.Temperature;
import com.example.data.Uv;
import com.example.data.Weather;
import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@ApplicationScoped
public class WeatherService {

    private static final String temperatureTopic = "temperature-topic";
    private static final String uvTopic = "uv-topic";
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Produces
    public Topology buildTopologyForWeatherService() {
        StreamsBuilder builder = new StreamsBuilder();

        try (ObjectMapperSerde<Temperature> temperatureSerde = new ObjectMapperSerde<>(Temperature.class);
             ObjectMapperSerde<Uv> uvSerde = new ObjectMapperSerde<>(Uv.class);
             ObjectMapperSerde<Weather> weatherSerde = new ObjectMapperSerde<>(Weather.class)) {
            KStream<String, Temperature> temperatureStream = builder
                .stream(temperatureTopic, Consumed.with(Serdes.String(), temperatureSerde));
            KStream<String, Uv> uvStream = builder
                .stream(uvTopic, Consumed.with(Serdes.String(), uvSerde));

            ValueJoiner<Temperature, Uv, Weather> valueJoiner = (leftValue, rightValue) ->
                new Weather(leftValue.location(), leftValue.temperature(), rightValue.index());

            temperatureStream
                .join(uvStream,
                    valueJoiner,
                    JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10)),
                    StreamJoined.with(Serdes.String(), temperatureSerde, uvSerde))
                .peek((key, value) -> log.info("{} -> {}", key, value));

            return builder.build();
        }
    }

}
