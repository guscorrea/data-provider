package com.dt.dataprovider.service;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.dataprovider.config.MqttGateway;
import com.dt.dataprovider.model.Pressure;
import com.dt.dataprovider.model.Temperature;
import com.dt.dataprovider.model.enums.MeasurementType;
import com.dt.dataprovider.model.enums.ComponentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

@Service
public class ChokeValveDataService implements DataGeneratorService {

	public static final double MAX_VALUE = 99.9999;

	public static final double MIN_VALUE = 0.0001;

	private final ObjectMapper objectMapper;

	private final MqttGateway mqttGateway;

	@Autowired
	public ChokeValveDataService(ObjectMapper objectMapper, MqttGateway mqttGateway) {
		this.objectMapper = objectMapper;
		this.mqttGateway = mqttGateway;
	}

	@SneakyThrows
	public void generateData(String serviceName, MeasurementType measurementType, int number, int rate, boolean useCsvFile) {
		String topic = ComponentType.choke + "." + serviceName + "." + measurementType;
		System.out.println("Generating " + number + " messages for topic: " + topic);

		IntStream.range(0, number).forEach(value -> {

			double random = MIN_VALUE + Math.random() * (MAX_VALUE - MIN_VALUE);

			if (MeasurementType.temperature.equals(measurementType)) {
				sendMessageToBroker(rate, topic, buildTemperature(random));
			}
			else {
				sendMessageToBroker(rate, topic, buildPressure(random));
			}

		});

		System.out.println("Process is finished");
	}

	private void sendMessageToBroker(int rate, String topic, Object object) {
		try {
			System.out.println("Sending message: " + object.toString() + " to MQTT gateway");
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

}
