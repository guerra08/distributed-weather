package com.example;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
class WeatherServiceTest {

    @Inject
    WeatherService application;

}
