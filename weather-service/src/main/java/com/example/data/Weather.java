package com.example.data;

import java.util.function.Function;

public record Weather(String location, double temperature, int uvIndex, String summary) {

    public Weather(String location, double temperature, int uvIndex) {
        this(location, temperature, uvIndex, buildSummary(temperature, uvIndex));
    }

    private static String buildSummary(double temperature, int uvIndex) {
        Function<Double, String> temperatureSummaryBuilder = temp -> {
            if (temp < 70.0) {
                return "It's cold right now!";
            } else if (temp > 87.0) {
                return "It's hot right now!";
            } else {
                return "The temperature is just right!";
            }
        };

        Function<Integer, String> uvSummaryBuilder = uv -> {
            if (uvIndex > 5) {
                return " Be sure to use some sun protection!";
            }
            return "";
        };

        return temperatureSummaryBuilder.apply(temperature) + uvSummaryBuilder.apply(uvIndex);
    }

}
