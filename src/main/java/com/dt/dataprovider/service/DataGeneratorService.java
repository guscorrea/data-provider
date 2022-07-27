package com.dt.dataprovider.service;

import org.springframework.scheduling.annotation.Async;

import com.dt.dataprovider.model.enums.MeasurementType;

public interface DataGeneratorService {

	@Async
	void generateData(String serviceName, MeasurementType measurementType, int number, int rate, String customPropertyName);

}
