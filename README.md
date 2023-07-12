# distributed-weather
A simple study application used to test usage of Kafka and Kafka Streams for a distributred weather service.

## Applications

This project consists of 3 main applications: 

- temperature-data: Kafka producer for temperature readings
- uv-data: Kafka producer for UV index readings
- weather-service: Weathe services that consumes data of temperature and UV with streams
