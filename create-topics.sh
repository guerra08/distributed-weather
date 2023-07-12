#!/bin/sh

podman exec distributed-weather-kafka-1 kafka-topics --create --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1 --topic temperature-topic

podman exec distributed-weather-kafka-1 kafka-topics --create --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1 --topic uv-topic

podman exec distributed-weather-kafka-1 kafka-topics --create --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1 --topic weather-topic
