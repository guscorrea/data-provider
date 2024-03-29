package com.dt.dataprovider.service;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.dataprovider.config.MqttGateway;
import com.dt.dataprovider.model.CustomMeasure;
import com.dt.dataprovider.model.Pressure;
import com.dt.dataprovider.model.Temperature;
import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.model.enums.MeasurementType;
import com.dt.dataprovider.utils.RandomValueGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TubingDataService implements DataGeneratorService {

	private final ObjectMapper objectMapper;

	private final MqttGateway mqttGateway;

	private final RandomValueGenerator randomValueGenerator;

	@Autowired
	public TubingDataService(ObjectMapper objectMapper, MqttGateway mqttGateway, RandomValueGenerator randomValueGenerator) {
		this.objectMapper = objectMapper;
		this.mqttGateway = mqttGateway;
		this.randomValueGenerator = randomValueGenerator;
	}

	@SneakyThrows
	public void generateData(String serviceName, MeasurementType measurementType, int number, int rate, String customPropertyName) {
		String topic = ComponentType.tubing + "." + serviceName + "." + measurementType;
		log.info("Generating {} messages for topic: {}", number, topic);

		IntStream.range(0, number).forEach(value -> {

			switch (measurementType) {
			case temperature:
				Temperature temperature = buildTemperature(randomValueGenerator.buildRandomValue());
				sendMessageToBroker(rate, topic, temperature);
				break;
			case pressure:
				Pressure pressure = buildPressure(randomValueGenerator.buildRandomValue());
				sendMessageToBroker(rate, topic, pressure);
				break;
			default:
				CustomMeasure customMeasure = buildCustomMeasure(randomValueGenerator.buildRandomValue(), customPropertyName);
				sendMessageToBroker(rate, topic, customMeasure);
			}
		});

		log.info("Data generation is finished");
	}

	private void sendMessageToBroker(int rate, String topic, Object object) {
		try {
			log.info("Sending message: {} to MQTT gateway", object.toString());
			mqttGateway.sendToMqtt(objectMapper.writeValueAsString(object), topic, 2);
			Thread.sleep(rate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Pressure buildPressure(double random) {
		return Pressure.builder().value(String.format("%.4f", random)).timeStamp(LocalDateTime.now().toString()).build();
	}

	private Temperature buildTemperature(double random) {
		return Temperature.builder().value(String.format("%.4f", random)).timeStamp(LocalDateTime.now().toString()).build();
	}

	private CustomMeasure buildCustomMeasure(double random, String customPropertyName) {
		return CustomMeasure.builder()
				.propertyType(customPropertyName)
				.value(String.format("%.4f", random))
				.timeStamp(LocalDateTime.now().toString()).build();
	}

}
